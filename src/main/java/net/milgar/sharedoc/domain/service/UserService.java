package net.milgar.sharedoc.domain.service;

import net.milgar.sharedoc.domain.model.User;

public interface UserService {
    void save(User user, String[] roleNames);
    void update(User user);

    User findByUsername(String username);
}