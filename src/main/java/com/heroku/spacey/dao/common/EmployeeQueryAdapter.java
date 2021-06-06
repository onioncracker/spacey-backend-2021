package com.heroku.spacey.dao.common;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EmployeeQueryAdapter {

    private StringBuilder query;
    private List<Object> params;


    public EmployeeQueryAdapter createSelect(String inputQuery) {
        query = new StringBuilder();
        params = new ArrayList<>();
        query.append(inputQuery).append(" ");
        return this;
    }

    public EmployeeQueryAdapter addFilter(Map<String, String> filters) {
        if (filters == null || filters.isEmpty()) {
            return this;
        }

        query.append("WHERE ");
        List<String> filtersParts = new ArrayList<>();

        for (Map.Entry<String, String> filter : filters.entrySet()) {

            if (filter.getKey().equals("role")) {
                StringBuilder filterPart = new StringBuilder();
                List<String> filteredRoles = Arrays.asList(filter.getValue().split(","));
                params.addAll(filteredRoles);

                String inParams = filteredRoles.stream().map(id -> "?").collect(Collectors.joining(","));
                filterPart
                        .append("login_info.userrole IN ")
                        .append("(")
                        .append(inParams)
                        .append(") ");

                filtersParts.add(filterPart.toString());
            }

            if (filter.getKey().equals("status")) {

                String filterPart = "login_info.status = "
                        + "'"
                        + filter.getValue()
                        + "'";

                filtersParts.add(filterPart + "\n");
            }

            if (filter.getKey().equals("search")) {
                String searchPrompt = filter.getValue();

                String filterPart = "(LOWER(login_info.firstname) LIKE LOWER('%"
                        + searchPrompt
                        + "%') "
                        + "OR LOWER(login_info.lastname) LIKE LOWER('%"
                        + searchPrompt
                        + "%')) ";

                filtersParts.add(filterPart);
            }
        }

        query.append(String.join("AND ", filtersParts)).append("\n");
        return this;
    }


    public EmployeeQueryAdapter setPage(int page, int pageSize) {
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
}


