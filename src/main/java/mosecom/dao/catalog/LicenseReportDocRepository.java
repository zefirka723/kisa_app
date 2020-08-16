package mosecom.dao.catalog;

import mosecom.model.catalog.LicenseReportDoc;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseReportDocRepository extends PagingAndSortingRepository<LicenseReportDoc, Integer>,
                                                        JpaSpecificationExecutor<LicenseReportDoc> {
}