package mosecom.dao.catalog.dictionaties;

import mosecom.model.catalog.dictionaries.LicenseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseStatusRepository extends JpaRepository<LicenseStatus, Integer> {
    List<LicenseStatus> findAllByOrderByNameAsc();
}
