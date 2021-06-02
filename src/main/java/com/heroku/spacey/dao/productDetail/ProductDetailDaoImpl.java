package com.heroku.spacey.dao.productDetail;

import com.heroku.spacey.dao.common.BaseDao;
import com.heroku.spacey.entity.ProductDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

@Slf4j
@Repository
public class ProductDetailDaoImpl extends BaseDao implements ProductDetailDao {
    private final ProductDetailMapper mapper = new ProductDetailMapper();

    public ProductDetailDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ProductDetail getById(int id) {
        var sql = "SELECT * FROM product_details WHERE id = ?";
        return Objects.requireNonNull(getJdbcTemplate()).queryForObject(sql, mapper, id);
    }

    @Override
    public int insert(ProductDetail productDetail) {
        var sql = "INSERT INTO product_details(productid, color, sizeproduct, amount) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = Objects.requireNonNull(getDataSource())
                .getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
        var sql = "UPDATE product_details SET color = ?, " +
                "sizeproduct = ?, amount = ? WHERE id = ?";
        var params = new Object[]{
                productDetail.getColor(), productDetail.getSizeProduct(),
                productDetail.getAmount(), productDetail.getId()
        };
        Objects.requireNonNull(getJdbcTemplate()).update(sql, params);
    }

    @Override
    public void delete(int id) {
        var sql = "DELETE FROM product_details WHERE id=?";
        Objects.requireNonNull(getJdbcTemplate()).update(sql, id);
    }
}
