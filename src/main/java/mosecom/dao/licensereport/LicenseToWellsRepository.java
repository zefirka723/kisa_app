package mosecom.dao.licensereport;

import mosecom.dto.licensereport.LicenseReportProjection;
import mosecom.model.licencereport.License;
import mosecom.model.licencereport.LicenseReport;
import mosecom.model.licencereport.LicenseToWells;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseToWellsRepository extends JpaRepository<LicenseToWells, Integer> {
    public List<LicenseToWells> findAllByLicense(License license);
}
