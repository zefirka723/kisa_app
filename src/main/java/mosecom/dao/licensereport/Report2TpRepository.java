package mosecom.dao.licensereport;

import mosecom.model.licencereport.Report2Tp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Report2TpRepository extends JpaRepository<Report2Tp, Integer> {

    public List<Report2Tp> findAllByReportDocIdOrderByDateDesc(Integer reportDocId);

}
