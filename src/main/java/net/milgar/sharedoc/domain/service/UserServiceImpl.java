package net.milgar.sharedoc.domain.service;

import net.milgar.sharedoc.domain.model.User;
import net.milgar.sharedoc.domain.repository.RoleRepository;
import net.milgar.sharedoc.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user, String[] roleNames) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set roles = new HashSet<>();
        for (String role : roleNames) {
            roles.add(roleService.findOrCreateRole(role));
        }
        user.setRoles(roles);
//        user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}