package com.heroku.spacey.dao.impl;

import com.heroku.spacey.dao.ProductDao;
import com.heroku.spacey.entity.Product;
import com.heroku.spacey.mapper.ProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
@PropertySource("classpath:sql/product_queries.properties")
public class ProductDaoImpl implements ProductDao {
    private final ProductMapper mapper = new ProductMapper();
    private final JdbcTemplate jdbcTemplate;

    @Value("${product_get_by_id}")
    private String getProductById;
    @Value("${product_is_exist}")
    private String isExistProduct;
    @Value("${insert_product}")
    private String addProduct;
    @Value("${add_material_to_product}")
    private String materialToProduct;
    @Value("${update_product}")
    private String updateProduct;
    @Value("${delete_product}")
    private String deleteProduct;
    @Value("${deactivate_product}")
    private String deactivateProduct;

    public ProductDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Product get(int id) {
        List<Product> products = Objects.requireNonNull(jdbcTemplate).query(getProductById, mapper, id);
        if (products.isEmpty()) {
            return null;
        }
        return products.get(0);
    }

    @Override
    public boolean isExist(int id) {
        List<Integer> products = Objects.requireNonNull(jdbcTemplate)
                .query(isExistProduct, (rs, i) -> rs.getInt("productId"), id);
        return !products.isEmpty();
    }

    @Override
    public int insert(Product product) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(addProduct, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, product.getCategoryId());
            ps.setString(2, product.getName());
            ps.setString(3, product.getProductSex());
            ps.setBigDecimal(4, product.getPrice());
            ps.setString(5, product.getPhoto());
            ps.setString(6, product.getDescription());
            ps.setDouble(7, product.getDiscount());
            ps.setBoolean(8, product.getIsAvailable());
            ps.setBoolean(9, product.getIsOnAuction());
            return ps;
        }, holder);
        return (int) Objects.requireNonNull(holder.getKeys()).get("productId");
    }

    @Override
    public void addMaterialToProduct(int materialId, int productId) {
        Objects.requireNonNull(jdbcTemplate).update(materialToProduct, materialId, productId);
    }

    @Override
    public void update(Product product) {
        Object[] params = new Object[]{
                product.getName(), product.getProductSex(), product.getPrice(),
                product.getPhoto(), product.getDescription(), product.getDiscount(),
                product.getIsAvailable(), product.getId()
        };
        Objects.requireNonNull(jdbcTemplate).update(updateProduct, params);
    }

    @Override
    public void delete(int id) {
        Objects.requireNonNull(jdbcTemplate).update(deleteProduct, id);
    }

    @Override
    public void deactivate(int id) {
        Objects.requireNonNull(jdbcTemplate).update(deactivateProduct, id);
    }
}
