package mosecom.dao.licensereport;

import mosecom.model.licencereport.LicenseReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LicenseReportRepository extends JpaRepository<LicenseReport, Integer> {

    @Query(value = "SELECT nextval('public.\"File_set_ID_seq\"')", nativeQuery = true)
    Integer getNextFileSetId();

}
