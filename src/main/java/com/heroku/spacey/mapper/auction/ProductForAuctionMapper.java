package com.heroku.spacey.mapper.auction;

import com.heroku.spacey.dto.auction.AuctionProductDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ProductForAuctionMapper implements RowMapper<AuctionProductDto> {
    @Override
    public AuctionProductDto mapRow(ResultSet resultSet, int i) throws SQLException {
        AuctionProductDto product = new AuctionProductDto();
        product.setId(resultSet.getLong("productid"));
        product.setName(resultSet.getString("productname"));
        product.setProductSex(resultSet.getString("productsex"));
        product.setPhoto(resultSet.getString("photo"));
        product.setDescription(resultSet.getString("description"));
        product.setCategory(resultSet.getString("namecategory"));
        product.setColor(resultSet.getString("color"));

        Set<String> materials = new HashSet<>();
        do {
            materials.add(resultSet.getString("namematerial"));
        }
        while (resultSet.next());

        product.setMaterials(materials);
        return product;
    }
}
