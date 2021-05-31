package com.heroku.spacey.dao.product;

import com.heroku.spacey.dao.general.BaseDao;
import com.heroku.spacey.entity.Product;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
public class ProductDaoImpl extends BaseDao implements ProductDao {
    private final ProductMapper mapper = new ProductMapper();

    public ProductDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Product getById(int id) {
        String sql = "SELECT p.*, c.id category_id, c.name category_name, m.id material_id, m.name material_name,\n" +
                "pd.id pd_id, pd.productid pd_product_id, pd.color pd_color, pd.sizeproduct pd_size, pd.amount pd_amount\n" +
                "FROM products p\n" +
                "INNER JOIN material_to_products mtp on p.id = mtp.productid\n" +
                "INNER JOIN materials m on mtp.materialid = m.id\n" +
                "INNER JOIN categories c on p.categoryid = c.id\n" +
                "INNER JOIN product_details pd on p.id = pd.productid\n" +
                "WHERE p.id = ?";
        Object[] params = new Object[]{id};
        var products = getJdbcTemplate().query(sql, mapper, params);
        if (products.isEmpty()) {
            return null;
        }
        return products.get(0);
    }

    @Override
    public List<Product> getByIdForDelete(int id) {
        String sql = "SELECT p.id, c.id category_id, m.id material_id, pd.id pd_id\n" +
                "FROM products p\n" +
                "INNER JOIN material_to_products mtp on p.id = mtp.productid\n" +
                "INNER JOIN materials m on mtp.materialid = m.id\n" +
                "INNER JOIN categories c on p.categoryid = c.id\n" +
                "INNER JOIN product_details pd on p.id = pd.productid\n" +
                "WHERE p.id = ?";
        Object[] params = new Object[]{id};
        var products = getJdbcTemplate().query(sql, mapper, params);
        if (products.isEmpty()) {
            return null;
        }
        return products;
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
        String sql = "DELETE FROM products WHERE id=?";
        Objects.requireNonNull(getJdbcTemplate()).update(sql, id);
    }

    @Override
    public void remove(int id) {
        String sql = "UPDATE products SET isdeleted = true WHERE id = ?";
        Objects.requireNonNull(getJdbcTemplate()).update(sql, id);
    }
}
