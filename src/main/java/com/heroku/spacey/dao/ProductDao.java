package com.heroku.spacey.dao;
import com.heroku.spacey.entity.Product;
import com.heroku.spacey.entity.SizeToProduct;

import java.util.List;

public interface ProductDao {
    List<Product> getAllProducts();

    Product get(Long id);

    boolean isExist(Long id);

    void saveImage(Long id, String url);

    Long insert(Product product);

    void update(Product product);

    void delete(Long id);

    void deactivate(Long id);

    void addMaterialToProduct(Long materialId, Long productId);

    void addSizeToProduct(Long sizeId, Long productId, Long quantity);

    int updateSizeToProduct(SizeToProduct sizeToProduct);

    double getAmount(Long sizeId, Long productId);
}