package com.realestate.service;

import com.realestate.entity.Role;
import com.realestate.entity.User;
import java.util.List;

public interface UserService {
    User register(User user);
    List<User> getByRole(Role role);
    List<User> getAll();
    User getById(Long id);
}
