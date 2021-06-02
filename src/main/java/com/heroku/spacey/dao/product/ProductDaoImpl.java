package com.heroku.spacey.dao.product;

import com.heroku.spacey.dao.common.BaseDao;
import com.heroku.spacey.dao.common.IdMapper;
import com.heroku.spacey.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

@Slf4j
@Repository
@PropertySource("classpath:sql/product_queries.properties")
public class ProductDaoImpl extends BaseDao implements ProductDao {
    private final ProductMapper mapper = new ProductMapper();
    private final IdMapper idMapper = new IdMapper();

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

    public ProductDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Product get(int id) {
        var products = Objects.requireNonNull(getJdbcTemplate()).query(getProductById, mapper, id);
        if (products.isEmpty()) {
            return null;
        }
        return products.get(0);
    }

    @Override
    public boolean isExist(int id) {
        var products = Objects.requireNonNull(getJdbcTemplate()).query(isExistProduct, idMapper, id);
        return !products.isEmpty();
    }

    @Override
    public int insert(Product product) {
        try (PreparedStatement statement = Objects.requireNonNull(getDataSource())
                .getConnection().prepareStatement(addProduct, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, product.getCategoryId());
            statement.setString(2, product.getName());
            statement.setString(3, product.getProductSex());
            statement.setBigDecimal(4, product.getPrice());
            statement.setString(5, product.getPhoto());
            statement.setString(6, product.getDescription());
            statement.setDouble(7, product.getDiscount());
            statement.setBoolean(8, product.getIsAvailable());
            return add(statement);
        } catch (SQLException e) {
            log.info(e.getMessage());
        }
        return -1;
    }

    @Override
    public void addMaterialToProduct(int materialId, int productId) {
        Objects.requireNonNull(getJdbcTemplate()).update(materialToProduct, materialId, productId);
    }

    @Override
    public void update(Product product) {
        var params = new Object[]{
                product.getName(), product.getProductSex(), product.getPrice(),
                product.getPhoto(), product.getDescription(), product.getDiscount(),
                product.getIsAvailable(), product.getId()
        };
        Objects.requireNonNull(getJdbcTemplate()).update(updateProduct, params);
    }

    @Override
    public void delete(int id) {
        Objects.requireNonNull(getJdbcTemplate()).update(deleteProduct, id);
    }

    @Override
    public void deactivate(int id) {
        Objects.requireNonNull(getJdbcTemplate()).update(deactivateProduct, id);
    }
}
