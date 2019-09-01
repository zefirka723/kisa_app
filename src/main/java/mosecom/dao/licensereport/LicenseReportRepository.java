package mosecom.dao.licensereport;

import mosecom.dto.licensereport.LicenseReportProjection;
import mosecom.model.licencereport.LicenseReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseReportRepository extends JpaRepository<LicenseReport, Integer> {
    @Query("select new mosecom.dto.licensereport.LicenseReportProjection"
            + "(r.id as id, r.date as date, r.ls as ls, r.tp as tp) "
            + "from LicenseReport r order by r.id")
    List<LicenseReportProjection> findLicenseReportsList();

    LicenseReportProjection findByLicense_Id(int license_id);
}
