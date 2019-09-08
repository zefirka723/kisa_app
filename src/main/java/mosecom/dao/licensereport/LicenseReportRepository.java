package mosecom.dao.licensereport;

import mosecom.model.licencereport.LicenseReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LicenseReportRepository extends JpaRepository<LicenseReport, Integer> {
//    @Query("select new mosecom.dto.licensereport.LicenseReportProjection"
//            + "(r.id as id, r.date as date, r.ls as ls, r.tp as tp) "
//            + "from LicenseReport r order by r.id")
//    List<LicenseReportProjection> findLicenseReportsList();

    List<LicenseReport> findAll();

    //    LicenseReportProjection findByLicense_Id(int license_id);
    LicenseReport findByLicense_Id(int license_id);

    LicenseReport getOne(int id);

    LicenseReport findByLicense_IdAndDate(int licenseId, Date date);

    @Query(value = "SELECT nextval('public.\"File_set_ID_seq\"')", nativeQuery = true)
    Integer getNextFileSetId();

}
