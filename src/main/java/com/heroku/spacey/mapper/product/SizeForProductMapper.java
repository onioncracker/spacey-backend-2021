package com.heroku.spacey.mapper.product;

import com.heroku.spacey.entity.Size;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SizeForProductMapper implements RowMapper<List<Size>> {

    @Override
    public List<Size> mapRow(ResultSet resultSet, int i) throws SQLException {
        ArrayList<Size> sizes = new ArrayList<>();
        do {
            Size size = new Size();
            size.setId(resultSet.getLong("sizeid"));
            size.setName(resultSet.getString("sizename"));
            sizes.add(size);
        }
        while (resultSet.next());
        return sizes;
    }
}
