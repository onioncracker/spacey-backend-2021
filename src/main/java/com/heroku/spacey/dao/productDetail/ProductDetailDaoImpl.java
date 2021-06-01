package com.heroku.spacey.dao.productDetail;

import com.heroku.spacey.dao.general.BaseDao;
import com.heroku.spacey.entity.ProductDetail;
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
    public ProductDetail getById(int id) {
        String sql = "SELECT * FROM product_details WHERE id = ?";
        Object[] params = new Object[]{id};
        return Objects.requireNonNull(getJdbcTemplate()).queryForObject(sql, mapper, params);
    }

    @Override
    public int insert(ProductDetail productDetail) {
        String sql = "INSERT INTO product_details(productid, color, sizeproduct, amount) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = getDataSource()
                .getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, productDetail.getProductId());
            statement.setString(2, productDetail.getColor());
            statement.setString(3, productDetail.getSizeProduct());
            statement.setInt(4, productDetail.getAmount());
            return add(statement);
        } catch (SQLException e) {
            e.getMessage();
        }
        return -1;
    }

    @Override
    public void update(ProductDetail productDetail) {
        String sql = "UPDATE product_details SET color = ?, " +
                "sizeproduct = ?, amount = ? WHERE id = ?";
        Object[] params = new Object[]{
                productDetail.getColor(), productDetail.getSizeProduct(),
                productDetail.getAmount(), productDetail.getId()
        };
        Objects.requireNonNull(getJdbcTemplate()).update(sql, params);
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM product_details WHERE id=?";
        Objects.requireNonNull(getJdbcTemplate()).update(sql, id);
    }
}
