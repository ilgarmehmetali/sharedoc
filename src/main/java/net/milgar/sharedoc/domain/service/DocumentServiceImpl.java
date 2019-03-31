package net.milgar.sharedoc.domain.service;

import net.milgar.sharedoc.domain.model.Document;
import net.milgar.sharedoc.domain.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public void save(Document document) {
        documentRepository.save(document);
    }

    @Override
    public Document findByTitle(String title) {
        return documentRepository.findByTitle(title);
    }

    @Override
    public Document findById(Long id) {
        return documentRepository.findById(id).orElse(null);
    }
}
