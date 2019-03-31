package net.milgar.sharedoc.domain.repository;

import net.milgar.sharedoc.domain.model.TermClass;
import net.milgar.sharedoc.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermClassRepository extends JpaRepository<TermClass, Long> {
    TermClass findByName(String name);
    TermClass findByCode(String code);
    TermClass findByIdAndStudentsContaining(Long id, User student);
}
