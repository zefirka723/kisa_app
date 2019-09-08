package mosecom.dao.licensereport;

import mosecom.dto.licensereport.LicenseProjection;
import mosecom.model.licencereport.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseRepository extends JpaRepository<License, Integer> {

    @Query("select new mosecom.dto.licensereport.LicenseProjection"
            + "(l.id as id, l.licenseNumber as licenseNumber) "
            + "from License l order by l.id")
    List<LicenseProjection> findLicenseList();

    //License findByLicenseNumber(String licenseNumber);

    @Query("select new mosecom.dto.licensereport.LicenseProjection"
            + "(l.id as id, l.licenseNumber as licenseNumber) "
            + "from License l where l.id = :id")
    LicenseProjection getById(@Param("id") int id);


//    @Query("select new mosecom.dto.licensereport.LicenseProjection"
//            + "(l.id as id, l.licenseNumber as licenseNumber) "
//            + "from License l where l.licenseNumber = :licenseNumber")
//    LicenseProjection findByLicenseNumber(@Param("licenseNumber") String licenseNumber);


    License findByLicenseNumber(String licenseNumber);
}
