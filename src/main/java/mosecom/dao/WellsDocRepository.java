//package mosecom.dao;
//
//import mosecom.dto.WellsDocProjection;
//import mosecom.model.WellsDoc;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface WellsDocRepository extends JpaRepository<WellsDoc, Integer> {
//
//    @Query("select new mosecom.dto.WellsDocProjection"
//            + "(d.id as id, d.docType as docType, d.docDate as docDate )"
//            + "from WellsDoc d where d.docType = 3001 order by d.id")
//    WellsDocProjection findWellDoc();
//
//
//}
////
//////    WellsDoc findOneByWellId(int wellId);
//////
//////    List<WellsDoc> findAllByWellId(int wellId);
//////
//////    List<WellsDocProjection> findWellsDocList();
////
//
