package mosecom.dao;

import mosecom.model.ConstructionType;
import mosecom.model.Diametr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiametrRepository extends JpaRepository<Diametr, Integer> {
}

