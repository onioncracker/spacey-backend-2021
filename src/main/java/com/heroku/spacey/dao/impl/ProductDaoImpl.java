package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.ProductDao;
import com.heroku.spacey.entity.Product;
import com.heroku.spacey.entity.SizeToProduct;
import com.heroku.spacey.mapper.product.ProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.webjars.NotFoundException;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
@PropertySource("classpath:sql/product_queries.properties")
public class ProductDaoImpl implements ProductDao {
    private final ProductMapper mapper = new ProductMapper();
    private final JdbcTemplate jdbcTemplate;

    @Value("${get_all_products}")
    private String getAllProducts;
    @Value("${product_get_by_id}")
    private String getProductById;
    @Value("${product_is_exist}")
    private String isExistProduct;
    @Value("${insert_product}")
    private String addProduct;
    @Value("${add_material_to_product}")
    private String materialToProduct;
    @Value("${add_size_to_product}")
    private String sizeToProduct;
    @Value("${delete_material_to_product}")
    private String deleteMaterialToProduct;
    @Value("${delete_size_to_product}")
    private String deleteSizeToProduct;
    @Value("${update_product_quantity}")
    private String updateProductQuantity;
    @Value("${update_product}")
    private String updateProduct;
    @Value("${delete_product}")
    private String deleteProduct;
    @Value("${deactivate_product}")
    private String deactivateProduct;
    @Value("${save_photo}")
    private String savePhoto;
    @Value("${get_amount_by_size}")
    private String getAmountBySize;

    public ProductDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = Objects.requireNonNull(jdbcTemplate).query(getAllProducts, mapper);
        if (products.isEmpty()) {
            return new ArrayList<>();
        }
        return products;
    }

    @Override
    public Product get(Long id) {
        List<Product> products = Objects.requireNonNull(jdbcTemplate).query(getProductById, mapper, id);
        if (products.isEmpty()) {
            return null;
        }
        return products.get(0);
    }

    public void saveImage(Long id, String url) {
        if (!isExist(id)) {
            throw new NotFoundException("product with id " + id + " not exist");
        }
        Objects.requireNonNull(jdbcTemplate).update(savePhoto, url, id);
    }

    @Override
    public boolean isExist(Long id) {
        List<Integer> products = Objects.requireNonNull(jdbcTemplate)
            .query(isExistProduct, (rs, i) -> rs.getInt("productId"), id);
        return !products.isEmpty();
    }

    @Override
    public Long insert(Product product) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(addProduct, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, product.getCategoryId());
            ps.setLong(2, product.getColorId());
            ps.setString(3, product.getName());
            ps.setString(4, product.getProductSex());
            ps.setDouble(5, product.getPrice());
            ps.setString(6, product.getPhoto());
            ps.setString(7, product.getDescription());
            ps.setDouble(8, product.getDiscount());
            ps.setBoolean(9, product.getIsAvailable());
            return ps;
        }, holder);
        return (Long) Objects.requireNonNull(holder.getKeys()).get("productId");
    }

    @Override
    public void addMaterialToProduct(Long materialId, Long productId) {
        Objects.requireNonNull(jdbcTemplate).update(materialToProduct, materialId, productId);
    }

    @Override
    public void deleteMaterialToProduct(Long productId) {
        Objects.requireNonNull(jdbcTemplate).update(deleteMaterialToProduct, productId);
    }

    @Override
    public void addSizeToProduct(Long sizeId, Long productId, Long quantity) {
        Objects.requireNonNull(jdbcTemplate).update(sizeToProduct, sizeId, productId, quantity);
    }

    @Override
    public void deleteSizeToProduct(Long productId) {
        Objects.requireNonNull(jdbcTemplate).update(deleteSizeToProduct, productId);
    }

    @Override
    public int updateProductQuantity(SizeToProduct sizeToProduct) {
        Long quantity = sizeToProduct.getQuantity();
        Long sizeId = sizeToProduct.getSizeId();
        Long productId = sizeToProduct.getProductId();

        return Objects.requireNonNull(jdbcTemplate).update(updateProductQuantity, quantity, sizeId, productId);
    }

    @Override
    public void update(Product product) {
        Object[] params = new Object[]{
            product.getColorId(), product.getCategoryId(), product.getName(), product.getProductSex(), product.getPrice(),
            product.getPhoto(), product.getDescription(), product.getDiscount(),
            product.getIsAvailable(), product.getId()
        };
        Objects.requireNonNull(jdbcTemplate).update(updateProduct, params);
    }

    @Override
    public void delete(Long id) {
        Objects.requireNonNull(jdbcTemplate).update(deleteProduct, id);
    }

    @Override
    public void deactivate(Long id) {
        Objects.requireNonNull(jdbcTemplate).update(deactivateProduct, id);
    }

    @Override
    public double getAmount(Long sizeId, Long productId) {
        return DataAccessUtils.singleResult(jdbcTemplate.query(getAmountBySize,
            SingleColumnRowMapper.newInstance(Integer.class), sizeId, productId));
    }
}
