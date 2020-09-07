package mosecom.dao.catalog.dictionaties;

import mosecom.model.catalog.dictionaries.TypeObserv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeObservRepository extends JpaRepository<TypeObserv, Integer> {
    List<TypeObserv> findAllByOrderByNameAsc();
}
