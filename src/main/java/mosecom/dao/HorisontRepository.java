package mosecom.dao;

import mosecom.model.Horisont;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HorisontRepository extends JpaRepository<Horisont, Integer> {
    List<Horisont> findAllByOrderNotNullOrderByOrderAsc();

}

