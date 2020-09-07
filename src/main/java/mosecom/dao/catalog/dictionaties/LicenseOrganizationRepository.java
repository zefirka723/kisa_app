package mosecom.dao.catalog.dictionaties;

import mosecom.model.catalog.dictionaries.LicenseOrganization;
import mosecom.model.catalog.dictionaries.TypeObserv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseOrganizationRepository extends JpaRepository<LicenseOrganization, Integer> {
    List<LicenseOrganization> findAllByOrderByNameAsc();
}
