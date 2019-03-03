package mosecom.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mosecom.model.ConstructionType;

@Repository
public interface ConstructionTypeRepository extends JpaRepository<ConstructionType, Integer> {
}

