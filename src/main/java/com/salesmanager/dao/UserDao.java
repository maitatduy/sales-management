package com.salesmanager.dao;

import com.salesmanager.model.User;

import java.util.Optional;

public interface UserDao {
    Optional<User> findByUsername(String username);
    User save(User user);
}