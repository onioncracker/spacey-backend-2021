package com.heroku.spacey.dao.productDetail;

import com.heroku.spacey.dao.common.BaseDao;
import com.heroku.spacey.entity.ProductDetail;
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
@PropertySource("classpath:sql/product_detail_queries.properties")
public class ProductDetailDaoImpl extends BaseDao implements ProductDetailDao {
    private final ProductDetailMapper mapper = new ProductDetailMapper();

    @Value("${product_detail_get_by_id}")
    private String getProductDetailById;
    @Value("${insert_product_detail}")
    private String editProductDetail;
    @Value("${update_product_detail}")
    private String updateProductDetail;
    @Value("${delete_product_detail}")
    private String deleteProductDetail;

    public ProductDetailDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ProductDetail getById(int id) {
        return Objects.requireNonNull(getJdbcTemplate()).queryForObject(getProductDetailById, mapper, id);
    }

    @Override
    public int insert(ProductDetail productDetail) {
        try (PreparedStatement statement = Objects.requireNonNull(getDataSource())
                .getConnection().prepareStatement(editProductDetail, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, productDetail.getProductId());
            statement.setString(2, productDetail.getColor());
            statement.setString(3, productDetail.getSizeProduct());
            statement.setInt(4, productDetail.getAmount());
            return add(statement);
        } catch (SQLException e) {
            log.info(e.getMessage());
        }
        return -1;
    }

    @Override
    public void update(ProductDetail productDetail) {
        var params = new Object[]{
                productDetail.getColor(), productDetail.getSizeProduct(),
                productDetail.getAmount(), productDetail.getId()
        };
        Objects.requireNonNull(getJdbcTemplate()).update(updateProductDetail, params);
    }

    @Override
    public void delete(int id) {
        Objects.requireNonNull(getJdbcTemplate()).update(deleteProductDetail, id);
    }
}
