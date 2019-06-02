package mosecom.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mosecom.model.WellsDocument;

@Repository
public interface WellsDocumentRepository extends JpaRepository<WellsDocument, Integer> {



}
