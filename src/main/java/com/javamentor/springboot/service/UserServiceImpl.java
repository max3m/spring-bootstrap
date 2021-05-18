package com.javamentor.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.javamentor.springboot.dao.RoleDAO;
import com.javamentor.springboot.dao.UserDAO;
import com.javamentor.springboot.model.User;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private UserDAO userDAO;
    private RoleDAO roleDAO;
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    public void setRoleDAO(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Autowired
    public void setbCryptPasswordEncoder(PasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<User> allUsers() {
        return userDAO.allUsers();
    }

    @Override
    public User getById(Long id) {
        return userDAO.getById(id);
    }

    @Override
    public void save(User user, String[] roles) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleDAO.getRoleSetForUser(roles));
        userDAO.save(user);
    }

    @Override
    public void update(User user, String[] roles) {
        if (!user.getPassword().isEmpty()) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        user.setRoles(roleDAO.getRoleSetForUser(roles));
        userDAO.update(user);
    }

    @Override
    public void delete(User user) {
        userDAO.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.getUserByName(username);
        return user;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
