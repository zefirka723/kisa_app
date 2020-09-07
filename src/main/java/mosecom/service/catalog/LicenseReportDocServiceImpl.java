package mosecom.service.catalog;

import mosecom.dao.catalog.LicenseReportDocRepository;
import mosecom.model.catalog.LicenseReportDoc;
import mosecom.specification.LicenseReportDocSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class LicenseReportDocServiceImpl {

    @Autowired
    private LicenseReportDocRepository licenseReportDocRepository;

    public Specification<LicenseReportDoc> getSpec(Integer idFromField,
                                             String regStatusFromField,
                                             String regNumberFromField,
                                             Date dateProcessingFromField,
                                             Date dateProcessingToFromField,
                                             String licenseNumberFromField,
                                             String subjectFromField,
                                             Date dateFromField,
                                             Date dateToFromField,
                                             String reportTypeFromField,
                                             String reportingPeriodFromField,
                                             String have4LSFromField,
                                             String have2TPFromField,
                                             String have3LSFromField
    ) {

        Specification<LicenseReportDoc> spec = Specification.where(null);
        if (idFromField != null) {
            spec = spec.and(LicenseReportDocSpecification.idContains(idFromField));
        }
        if (regStatusFromField != null && !regStatusFromField.isEmpty()) {
            spec = spec.and(LicenseReportDocSpecification.regStatusContains(regStatusFromField));
        }
        if (regNumberFromField != null && !regNumberFromField.isEmpty()) {
            spec = spec.and(LicenseReportDocSpecification.regNumberContains(regNumberFromField));
        }

        if(dateProcessingFromField != null && dateProcessingToFromField != null) {
            spec = spec.and(LicenseReportDocSpecification.dateProcessingBetween(dateProcessingFromField, dateProcessingToFromField));
        }
        if (dateProcessingFromField != null && dateProcessingToFromField == null) {
            spec = spec.and(LicenseReportDocSpecification.dateProcessingContains(dateProcessingFromField));
        }
        if (dateProcessingToFromField != null && dateProcessingFromField == null) {
            spec = spec.and(LicenseReportDocSpecification.dateProcessingToContains(dateProcessingToFromField));
        }


        if (licenseNumberFromField != null && !licenseNumberFromField.isEmpty()) {
            spec = spec.and(LicenseReportDocSpecification.licenseNumberContains(licenseNumberFromField));
        }
        if (subjectFromField != null && !subjectFromField.isEmpty()) {
            spec = spec.and(LicenseReportDocSpecification.subjectContains(subjectFromField));
        }

        if(dateFromField != null && dateToFromField != null) {
            spec = spec.and(LicenseReportDocSpecification.dateBetween(dateFromField, dateToFromField));
        }
        if (dateFromField != null && dateToFromField == null) {
            spec = spec.and(LicenseReportDocSpecification.dateContains(dateFromField));
        }
        if (dateToFromField != null && dateFromField == null) {
            spec = spec.and(LicenseReportDocSpecification.dateToContains(dateToFromField));
        }



        if (reportTypeFromField != null && !reportTypeFromField.isEmpty()) {
            spec = spec.and(LicenseReportDocSpecification.reportTypeContains(reportTypeFromField));
        }
        if (reportingPeriodFromField != null && !reportingPeriodFromField.isEmpty()) {
            spec = spec.and(LicenseReportDocSpecification.reportingPeriodContains(reportingPeriodFromField));
        }
        if (have4LSFromField != null && !have4LSFromField.isEmpty()) {
            spec = spec.and(LicenseReportDocSpecification.have4LSContains(have4LSFromField));
        }
        if (have2TPFromField != null && !have2TPFromField.isEmpty()) {
            spec = spec.and(LicenseReportDocSpecification.have2TPContains(have2TPFromField));
        }
        if (have3LSFromField != null && !have3LSFromField.isEmpty()) {
            spec = spec.and(LicenseReportDocSpecification.have3LSContains(have3LSFromField));
        }
        return spec;
    }

    public String getFiltresString(Integer idFromField,
                                   String regStatusFromField,
                                   String regNumberFromField,
                                   Date dateProcessingFromField,
                                   Date dateProcessingToFromField,
                                   String licenseNumberFromField,
                                   String subjectFromField,
                                   Date dateFromField,
                                   Date dateToFromField,
                                   String reportTypeFromField,
                                   String reportingPeriodFromField,
                                   String have4LSFromField,
                                   String have2TPFromField,
                                   String have3LSFromField) {
        StringBuilder filtersBuilder = new StringBuilder();
        if (idFromField != null) {
            filtersBuilder.append("&idFromField=" + idFromField);
        }
        if (regNumberFromField != null && !regNumberFromField.isEmpty()) {
            filtersBuilder.append("&regNumberFromField=" + regNumberFromField);
        }
        if (regStatusFromField != null && !regStatusFromField.isEmpty()) {
            filtersBuilder.append("&regStatusFromField=" + regStatusFromField);
        }
        if (dateProcessingFromField != null) {
            filtersBuilder.append("&dateProcessingFromField=" + dateProcessingFromField);
        }
        if (dateProcessingToFromField != null) {
            filtersBuilder.append("&dateProcessingToFromField=" + dateProcessingToFromField);
        }
        if (licenseNumberFromField != null && !licenseNumberFromField.isEmpty()) {
            filtersBuilder.append("&licenseNumberFromField=" + licenseNumberFromField);
        }
        if (subjectFromField != null && !subjectFromField.isEmpty()) {
            filtersBuilder.append("&subjectFromField=" + subjectFromField);
        }
        if (dateFromField != null) {
            filtersBuilder.append("&dateFromField=" + dateFromField);
        }
        if (dateToFromField != null) {
            filtersBuilder.append("&dateToFromField=" + dateToFromField);
        }
        if (reportTypeFromField != null && !reportTypeFromField.isEmpty()) {
            filtersBuilder.append("&reportTypeFromField=" + reportTypeFromField);
        }
        if (reportingPeriodFromField != null && !reportingPeriodFromField.isEmpty()) {
            filtersBuilder.append("&reportingPeriodFromField=" + reportingPeriodFromField);
        }
        if (have4LSFromField != null && !have4LSFromField.isEmpty()) {
            filtersBuilder.append("&have4LSFromField=" + have4LSFromField);
        }
        if (have2TPFromField != null && !have2TPFromField.isEmpty()) {
            filtersBuilder.append("&have2TPFromField=" + have2TPFromField);
        }
        if (have3LSFromField != null && !have3LSFromField.isEmpty()) {
            filtersBuilder.append("&have3LSFromField=" + have3LSFromField);
        }
        return filtersBuilder.toString();
    }

    public Page<LicenseReportDoc> findAllByPagingAndFiltering(Specification<LicenseReportDoc> specification, Pageable pageable) {
        return licenseReportDocRepository.findAll(specification, pageable);
    }

    public List<LicenseReportDoc> findAllByFiltering(Specification<LicenseReportDoc> specification) {
        return licenseReportDocRepository.findAll(specification);
    }

}


