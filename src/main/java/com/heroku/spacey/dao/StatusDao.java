package com.heroku.spacey.dao;

public interface StatusDao {
    Long getStatusId(String statusName);
    String getStatusName(long id);
    long insertStatus(String statusName);
}
