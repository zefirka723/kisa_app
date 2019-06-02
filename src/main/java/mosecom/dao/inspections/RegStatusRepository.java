package mosecom.dao.inspections;

import mosecom.model.inspections.dictionaries.RegStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegStatusRepository extends JpaRepository<RegStatus, Integer> {
}

