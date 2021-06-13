package com.heroku.spacey.dao;

public interface RoleDao {
    Long getRoleId(String roleName);
    String getRoleName(long id);
    long insertRole(String roleName);
}
