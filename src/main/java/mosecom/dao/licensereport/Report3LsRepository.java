package mosecom.dao.licensereport;

import mosecom.model.licencereport.Report3Ls;
import mosecom.model.licencereport.Report4Ls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Report3LsRepository extends JpaRepository<Report3Ls, Integer> {

    public List<Report3Ls> findAllByReportDocIdOrderByDateDesc(Integer reportDocId);

}
