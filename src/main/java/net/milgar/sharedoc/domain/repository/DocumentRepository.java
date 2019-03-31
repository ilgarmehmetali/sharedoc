package net.milgar.sharedoc.domain.repository;


import net.milgar.sharedoc.domain.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Document findByTitle(String title);
}