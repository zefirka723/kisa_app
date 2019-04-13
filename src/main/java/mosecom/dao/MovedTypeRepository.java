package mosecom.dao;

import mosecom.model.MovedType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovedTypeRepository extends JpaRepository<MovedType, Integer> {
}

