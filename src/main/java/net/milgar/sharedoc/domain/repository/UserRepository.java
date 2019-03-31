package net.milgar.sharedoc.domain.repository;

import net.milgar.sharedoc.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}