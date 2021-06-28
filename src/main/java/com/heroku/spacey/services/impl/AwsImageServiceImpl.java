package com.heroku.spacey.services.impl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.heroku.spacey.dao.ProductDao;
import com.heroku.spacey.services.AwsImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
@PropertySource("classpath:aws.service.properties")
@ConfigurationProperties(prefix = "aws.service")
public class AwsImageServiceImpl implements AwsImageService {

    private final String accessKey;
    private final String secretKey;
    private final String bucketName;
    private final AmazonS3 s3client;
    private final ProductDao product;

    public AwsImageServiceImpl(@Value("${aws.service.access-key}") String accessKey,
                               @Value("${aws.service.secret-key}") String secretKey,
                               @Value("${aws.service.bucket-name}") String bucketName,
                               @Autowired ProductDao product) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucketName = bucketName;
        this.product = product;


        AWSCredentials credentials = new BasicAWSCredentials(
                this.accessKey,
                this.secretKey
        );

        this.s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_WEST_2)
                .build();
    }


    @Override
    public void save(MultipartFile image, Long productId) throws IOException {
        if (image.isEmpty()) {
            throw new IllegalArgumentException("Empty image");
        }

        String photoName = productId + getPhotoExtensionByName(image.getOriginalFilename());
        if (s3client.doesObjectExist(bucketName, photoName)) {
            throw new IllegalArgumentException("image with this name already exist in AWSs3 cloud");
        }

        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + image.getName());
        image.transferTo(convFile);

        s3client.putObject(bucketName, photoName, convFile);
        String url = getUrl(photoName);
        product.saveImage(productId, url);
        log.info("image saved to AWS");
    }

    @Override
    public String getUrl(String fileName) {
        return s3client.getUrl(bucketName, fileName).toString();
    }

    @Override
    public void delete(String url) {
        if (url == null || url.isEmpty()) {
            return;
        }

        if (!s3client.doesObjectExist(bucketName, getPhotoNameByUrl(url))) {
            throw new NotFoundException("image not found in AWSs3 cloud");
        }
        s3client.deleteObject(bucketName, getPhotoNameByUrl(url));
        log.info("image deleted from AWS");
    }

    private String getPhotoNameByUrl(String url) {
        return url.substring(url.lastIndexOf('/') + 1);
    }

    private String getPhotoExtensionByName(String name) {
        if (name == null) {
            throw new NotFoundException("image not present");
        }
        return name.substring(name.lastIndexOf('.'));
    }
}
