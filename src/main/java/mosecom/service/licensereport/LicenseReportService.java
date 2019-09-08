package mosecom.service.licensereport;

import mosecom.model.licencereport.LicenseReport;

public interface LicenseReportService {

	//List<LicenseReportProjection> getLicenseReportList();

	LicenseReport getLicenceReport(int id);

	//LicenseReport save(LicenseReportProjection licenseReportProjection, MultipartFile[] file) throws IllegalStateException, IOException;

//	Attachment getAttachment(int id);
}