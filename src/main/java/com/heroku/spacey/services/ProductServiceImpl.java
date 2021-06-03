package com.heroku.spacey.services;

import com.heroku.spacey.dao.common.UnitOfWork;
import com.heroku.spacey.dto.product.AddProductDto;
import com.heroku.spacey.contracts.ProductService;
import com.heroku.spacey.dto.product.ProductDto;
import com.heroku.spacey.dto.product.UpdateProductDto;
import com.heroku.spacey.mapper.ProductConvertor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final UnitOfWork unitOfWork;
    private final ProductConvertor productConvertor;

    public ProductServiceImpl(UnitOfWork unitOfWork, ProductConvertor productConvertor) {
        this.unitOfWork = unitOfWork;
        this.productConvertor = productConvertor;
    }

    @Override
    public ProductDto getById(int id) {
        var product = unitOfWork.getProductDao().get(id);
        if (product == null) {
            return null;
        }
        return productConvertor.adapt(product);
    }

    @Override
    public void addProduct(AddProductDto addProductDto) {
        var product = productConvertor.adapt(addProductDto);
        var categoryId = addProductDto.getCategory().getId();
        product.setCategoryId(categoryId);

        var productId = unitOfWork.getProductDao().insert(product);
        product.getProductDetail().setProductId(productId);
        unitOfWork.getProductDetailDao().insert(product.getProductDetail());

        for (var i = 0; i < addProductDto.getMaterials().size(); i++) {
            var materialId = addProductDto.getMaterials().get(i).getId();
            unitOfWork.getProductDao().addMaterialToProduct(materialId, productId);
        }
        unitOfWork.commit();
    }

    @Override
    public void updateProduct(UpdateProductDto updateProductDto, int id) {
        var product = productConvertor.adapt(updateProductDto);
        product.setId(id);
        unitOfWork.getProductDao().update(product);
        unitOfWork.getProductDetailDao().update(product.getProductDetail());
        unitOfWork.commit();
    }

    @Override
    public void removeProduct(int id) {
        var isFound = unitOfWork.getProductDao().isExist(id);
        if (!isFound) {
            new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        unitOfWork.getProductDao().deactivate(id);
    }

    @Override
    public void cancelProduct(int id) {
        var isFound = unitOfWork.getProductDao().isExist(id);
        if (!isFound) {
            new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        unitOfWork.getProductDao().delete(id);
    }
}
