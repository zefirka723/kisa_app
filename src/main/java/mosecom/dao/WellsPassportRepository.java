package mosecom.dao;

import mosecom.model.WellsDoc;
import mosecom.model.WellsPassport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WellsPassportRepository extends JpaRepository<WellsPassport, Integer> {

  //  WellsDoc findOneByWellIAndId(int wellId, int docId);


}
//
////    WellsDoc findOneByWellId(int wellId);
////
////    List<WellsDoc> findAllByWellId(int wellId);
////
////    List<WellsDocProjection> findWellsDocList();
//
//    @Repository
//    public interface WellsDocRepository extends JpaRepository<WellsDoc, Integer> {
//
//        @Query("select new mosecom.dto.WellsDocProjection"
//                + "(d.id as id, d.docDate as docDate, d.docType as docType) "
//                + "from Docs_Wells d order by d.id")
//        List<WellsDoc> findWellsDocs();
//
//        List<WellsDocProjection> findWellsDocList();
////

//}
