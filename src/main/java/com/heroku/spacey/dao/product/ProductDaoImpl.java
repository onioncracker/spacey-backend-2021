package com.heroku.spacey.dao.product;

import com.heroku.spacey.dao.general.BaseDao;
import com.heroku.spacey.entity.Product;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

@Repository
public class ProductDaoImpl extends BaseDao implements ProductDao {
    private final ProductMapper mapper = new ProductMapper();

    public ProductDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Product getById(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        Object[] params = new Object[]{id};
        return Objects.requireNonNull(getJdbcTemplate()).queryForObject(sql, mapper, params);
    }

    @Override
    public int insert(Product product) {
        String sql = "INSERT INTO products(categoryid, name, " +
                "createddate, productsex, price, photo, " +
                "description, discount, isavailable) " +
                "VALUES (?, ?, current_timestamp, ?, ?, ?, ?, ?, ?);";
        try (PreparedStatement statement = getDataSource()
                .getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
            System.out.println(e.getMessage());
        }
        return -1;
    }

//    @Override
//    public int insert(Product product) {
//        String sql = "INSERT INTO products(categoryid, name, " +
//                "createddate, productsex, price, photo, " +
//                "description, discount, isavailable) " +
//                "VALUES (?, ?, current_timestamp, ?, ?, ?, ?, ?, ?);";
//        Object[] params = new Object[]{product.getCategoryId(), product.getName(), product.getProductSex(),
//        product.getPrice(), product.getPhoto(), product.getDescription(), product.getDiscount(), product.getIsAvailable()};
//        int count = getJdbcTemplate().update(sql, params);
//        return count;
//    }

    @Override
    public void addMaterialToProduct(int materialId, int productId) {
        String sql = "INSERT INTO material_to_products(materialid, productid) VALUES (?, ?)";
        Objects.requireNonNull(getJdbcTemplate()).update(sql, materialId, productId);
    }

    @Override
    public void update(Product product) {
        String sql = "UPDATE products SET name = ?, createddate = current_timestamp, " +
                "productsex = ?, price = ?, photo = ?, description = ?, discount = ?, isavailable=? " +
                "WHERE id = ?";
        Object[] params = new Object[]{
                product.getName(), product.getProductSex(), product.getPrice(),
                product.getPhoto(), product.getDescription(), product.getDiscount(),
                product.getIsAvailable(), product.getId()
        };
        Objects.requireNonNull(getJdbcTemplate()).update(sql, params);
    }

    @Override
    public void delete(int id) {
    }
}
