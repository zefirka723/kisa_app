package mosecom.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mosecom.model.DocumentType;

import java.util.List;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, Integer> {
    DocumentType findOneById(int id);
}

