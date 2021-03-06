package com.heroku.spacey.dao.common;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductCatalogQueryAdapter {
    private StringBuilder query;
    private List<Object> params;

    public ProductCatalogQueryAdapter createSelect(String inputQuery) {
        query = new StringBuilder();
        params = new ArrayList<>();
        query.append(inputQuery).append(" ");
        return this;
    }


    public ProductCatalogQueryAdapter addFilters(String prompt, String[] categories, String sex, Integer[] price, String[] colors) {
        boolean whereFlag = false;
        List<String> filtersParts = new ArrayList<>();

        if (prompt != null) {
            whereFlag = true;
            filtersParts.add("LOWER (productname) like '" + prompt + "%' ");
        }

        if (categories != null) {
            whereFlag = true;
            params.addAll(Arrays.asList(categories));
            filtersParts.add(generateInStatement("LOWER (namecategory)", categories));
        }

        if (price != null) {
            whereFlag = true;
            Collections.addAll(params, price);
            filtersParts.add("price BETWEEN ? AND ?\n");
        }

        if (colors != null) {
            whereFlag = true;
            params.addAll(Arrays.asList(colors));
            filtersParts.add(generateInStatement("LOWER (c.color)", colors));
        }


        if (sex != null) {
            whereFlag = true;
            params.add(sex.toLowerCase());
            filtersParts.add("LOWER (productsex) = ? ");
        }

        if (whereFlag) {
            query.append(" WHERE ");
        }

        query.append(String.join("AND ", filtersParts)).append("\n");
        return this;
    }


    public ProductCatalogQueryAdapter addOrdering(String order) {
        if (order == null) {
            return this;
        }
        if (order.equals("cheap")) {
            query.append("ORDER BY p.price ");
        }
        if (order.equals("expensive")) {
            query.append("ORDER BY p.price DESC ");
        }
        if (order.equals("new")) {
            query.append("ORDER BY createddate ");
        }
        if (order.equals("old")) {
            query.append("ORDER BY createddate DESC ");
        }

        if (order.equals("name")) {
            query.append("ORDER BY productname ");
        }
        return this;
    }

    public ProductCatalogQueryAdapter setPage(int page, int pageSize) {
        query.append("LIMIT ? ");
        query.append("OFFSET ? ");
        params.add(pageSize);
        params.add(page * pageSize);
        return this;
    }

    public List<Object> getParams() {
        return params;
    }

    public String build() {
        return query.toString();
    }

    private String generateInParams(int count) {
        ArrayList<String> arr = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            arr.add("?");
        }
        return String.join(",", arr);
    }

    private String generateInStatement(String columnName, String[] params) {
        StringBuilder filterPart = new StringBuilder();
        String inParams = generateInParams(params.length);
        filterPart
                .append(columnName)
                .append(" IN ")
                .append("(")
                .append(inParams)
                .append(")");
        return filterPart.toString();
    }
}
