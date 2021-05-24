package com.heroku.spacey.dao.product;

import com.heroku.spacey.entity.Product;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class ProductDaoImpl extends JdbcDaoSupport implements ProductDao {
    private ProductMapper mapper = new ProductMapper();

    public ProductDaoImpl(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    @Override
    public Product getById(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        Object[] params = new Object[]{id};
        return this.getJdbcTemplate().queryForObject(sql, params, mapper);
    }

    @Override
    public int insert(Product product) {
        String sql = "INSERT INTO products(categoryid, name, " +
                "createddate, productsex, price, photo, " +
                "description, discount, isavailable) " +
                "VALUES (?, ?, current_timestamp, ?, ?, ?, ?, ?, ?);";
        Object[] params = new Object[]{product.getCategoryId(), product.getName(),
        product.getProductSex(), product.getPrice(), product.getPhoto(), product.getDescription(), product.getDiscount(),
        product.isAvailable()};
        return getJdbcTemplate().update(sql, params);
    }

    @Override
    public int update(Product product) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }
}
