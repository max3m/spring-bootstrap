package com.javamentor.springboot.dao;

import com.javamentor.springboot.model.User;

import java.util.List;

public interface UserDAO {
    List<User> allUsers();
    User getById(Long id);
    void save(User user);
    void update(User user);
    void delete(User user);
    User getUserByName(String username);
}
