package mosecom.dao;

import mosecom.model.Reccard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mosecom.model.WellsDocument;

@Repository
public interface ReccardRepository extends JpaRepository<Reccard, Integer> {
}
