package mosecom.dao.catalog;

import mosecom.model.catalog.OtherDoc;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherDocRepository extends PagingAndSortingRepository<OtherDoc, Integer>,
                                                        JpaSpecificationExecutor<OtherDoc> {
}