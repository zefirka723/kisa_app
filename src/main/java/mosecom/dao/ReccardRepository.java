package mosecom.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mosecom.model.WellsDocument;

@Repository
public interface ReccardRepository extends JpaRepository<WellsDocument, Integer> {
}
