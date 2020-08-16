package mosecom.dao.catalog;

import mosecom.model.catalog.PrimaryDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrimaryDocRepository extends PagingAndSortingRepository<PrimaryDoc, Integer>,
                                                        JpaSpecificationExecutor<PrimaryDoc> {
    }



//public interface PrimaryDocRepository extends JpaRepository<PrimaryDoc, Integer> {
//    List<PrimaryDoc> findAll();



