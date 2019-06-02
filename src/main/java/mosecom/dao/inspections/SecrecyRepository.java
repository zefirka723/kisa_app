package mosecom.dao.inspections;

import mosecom.model.inspections.dictionaries.Secrecy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecrecyRepository extends JpaRepository<Secrecy, Integer> {
}

