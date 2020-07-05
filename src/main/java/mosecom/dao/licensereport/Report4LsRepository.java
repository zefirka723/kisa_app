package mosecom.dao.licensereport;

import mosecom.model.licencereport.Report4Ls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Report4LsRepository extends JpaRepository<Report4Ls, Integer> {

    public List<Report4Ls> findAllByReportDocIdOrderByDateDesc(Integer reportDocId);

}
