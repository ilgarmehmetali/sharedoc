package net.milgar.sharedoc.domain.service;

import net.milgar.sharedoc.domain.model.TermClass;
import net.milgar.sharedoc.domain.model.User;

public interface TermClassService {
    void save(TermClass termClass);

    TermClass findByName(String name);
    TermClass findByCode(String code);

    String generateRandomCode();

    TermClass findById(long id);

    boolean canUserSeeTermClass(Long id, User user);
    boolean canUserEditTermClass(Long id, User user);
}
