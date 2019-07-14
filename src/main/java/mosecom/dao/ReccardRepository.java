package mosecom.dao;

import mosecom.model.Reccard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReccardRepository extends JpaRepository<Reccard, Integer> {
}
