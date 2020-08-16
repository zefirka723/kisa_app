package mosecom.dao.catalog;

import mosecom.model.catalog.LicenseDoc;
import mosecom.model.catalog.ProtocolDoc;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProtocolDocRepository extends PagingAndSortingRepository<ProtocolDoc, Integer>,
                                                        JpaSpecificationExecutor<ProtocolDoc> {
}