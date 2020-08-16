package mosecom.dao.catalog;

import mosecom.model.catalog.LicenseDoc;
import mosecom.model.catalog.PrimaryDoc;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseDocRepository extends PagingAndSortingRepository<LicenseDoc, Integer>,
                                                        JpaSpecificationExecutor<LicenseDoc> {
}