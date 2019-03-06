package mosecom.dao;

import mosecom.model.ConstructionType;
import mosecom.model.Horisont;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorisontRepository extends JpaRepository<Horisont, Integer> {
}

