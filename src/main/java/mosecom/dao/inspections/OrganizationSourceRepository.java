package mosecom.dao.inspections;

import mosecom.model.inspections.dictionaries.OrganizationSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationSourceRepository extends JpaRepository<OrganizationSource, Integer> {
}

