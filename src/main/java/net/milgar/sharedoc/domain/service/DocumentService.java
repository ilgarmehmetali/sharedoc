package net.milgar.sharedoc.domain.service;

import net.milgar.sharedoc.domain.model.Document;

public interface DocumentService {
    void save(Document termClass);

    Document findByTitle(String title);
    Document findById(Long id);
}
