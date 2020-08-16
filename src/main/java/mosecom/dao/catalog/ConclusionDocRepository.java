package mosecom.dao.catalog;

import mosecom.model.catalog.ConclusionDoc;
import mosecom.model.catalog.ProtocolDoc;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConclusionDocRepository extends PagingAndSortingRepository<ConclusionDoc, Integer>,
                                                        JpaSpecificationExecutor<ConclusionDoc> {
}