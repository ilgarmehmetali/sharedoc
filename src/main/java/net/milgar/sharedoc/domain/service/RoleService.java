package net.milgar.sharedoc.domain.service;

import net.milgar.sharedoc.domain.model.Role;

public interface RoleService {
    void save(Role role);

    Role findOrCreateRole(String roleLabel);
}
