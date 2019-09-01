package mosecom.dao.licensereport;

import mosecom.dto.licensereport.LicenseProjection;
import mosecom.model.licencereport.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseRepository extends JpaRepository<License, Integer> {

    @Query("select new mosecom.dto.licensereport.LicenseProjection"
            + "(l.id as id, l.licenseNumber as licenseNumber) "
            + "from License l order by l.id")
    List<LicenseProjection> findLicenseList();


}
