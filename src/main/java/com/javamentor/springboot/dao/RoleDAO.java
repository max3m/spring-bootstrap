package com.javamentor.springboot.dao;

import com.javamentor.springboot.model.Role;

import java.util.Set;

public interface RoleDAO {
    void save(Role role);
    void delete(Role role);
    Role getById(Long id);
    Role getRoleByName(String rolename);
    Set<Role> getRoleSet();
    Set<Role> getRoleSetForUser(String[] rolenames);
}