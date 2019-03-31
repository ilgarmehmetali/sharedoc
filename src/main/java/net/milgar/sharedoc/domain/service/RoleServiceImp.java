package net.milgar.sharedoc.domain.service;

import net.milgar.sharedoc.domain.model.Role;
import net.milgar.sharedoc.domain.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImp implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role findOrCreateRole(String roleLabel) {
        Role role = roleRepository.findByName(roleLabel);
        if(role == null){
            role = new Role();
            role.setName(roleLabel);
            roleRepository.save(role);
        }
        return role;
    }
}
