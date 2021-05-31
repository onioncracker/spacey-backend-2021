package com.heroku.spacey.dao.productDetail;

import com.heroku.spacey.dao.general.BaseDao;
import com.heroku.spacey.entity.ProductDetails;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

@Repository
public class ProductDetailDaoImpl extends BaseDao implements ProductDetailDao {
    private final ProductDetailMapper mapper = new ProductDetailMapper();

    public ProductDetailDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ProductDetails getById(int id) {
        String sql = "SELECT * FROM product_details WHERE id = ?";
        Object[] params = new Object[]{id};
        return Objects.requireNonNull(getJdbcTemplate()).queryForObject(sql, mapper, params);
    }

    @Override
    public int insert(ProductDetails productDetails) {
        String sql = "INSERT INTO product_details(productid, color, sizeproduct, amount) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = getDataSource()
                .getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, productDetails.getProductId());
            statement.setString(2, productDetails.getColor());
            statement.setString(3, productDetails.getSizeProduct());
            statement.setInt(4, productDetails.getAmount());
            return add(statement);
        } catch (SQLException e) {
            e.getMessage();
        }
        return -1;
    }

    @Override
    public void update(ProductDetails productDetails) {
        String sql = "UPDATE product_details SET color = ?, " +
                "sizeproduct = ?, amount = ? WHERE id = ?";
        Object[] params = new Object[]{
                productDetails.getColor(), productDetails.getSizeProduct(),
                productDetails.getAmount(), productDetails.getId()
        };
        Objects.requireNonNull(getJdbcTemplate()).update(sql, params);
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM product_details WHERE id=?";
        getJdbcTemplate().update(sql, id);
    }
}
