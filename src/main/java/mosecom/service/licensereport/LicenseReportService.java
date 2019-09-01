package mosecom.service.licensereport;

import mosecom.dto.WellFullProjection;
import mosecom.dto.WellProjection;
import mosecom.dto.licensereport.LicenseReportProjection;
import mosecom.model.*;
import mosecom.model.licencereport.LicenseReport;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface LicenseReportService {

	List<LicenseReportProjection> getLicenseReportList();

	LicenseReport getLicenceReport(int id);

	LicenseReport save(LicenseReportProjection licenseReportProjection, MultipartFile[] file) throws IllegalStateException, IOException;

//	Attachment getAttachment(int id);
}