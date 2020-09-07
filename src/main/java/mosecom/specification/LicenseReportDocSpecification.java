package mosecom.specification;

import mosecom.model.catalog.LicenseReportDoc;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class LicenseReportDocSpecification {

    public static Specification<LicenseReportDoc> idContains(Integer idFromField) {
        return (Specification<LicenseReportDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("id"), idFromField);
    }

    public static Specification<LicenseReportDoc> regStatusContains(String regStatusFromField) {
        return (Specification<LicenseReportDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("regStatus")), "%" + regStatusFromField.toLowerCase() + "%");
    }

    public static Specification<LicenseReportDoc> regNumberContains(String regStatusFromField) {
        return (Specification<LicenseReportDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("regNumber")), "%" + regStatusFromField.toLowerCase() + "%");
    }

    public static Specification<LicenseReportDoc> dateProcessingContains(Date dateProcessingFromField) {
        return (Specification<LicenseReportDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("dateProcessing"), dateProcessingFromField );
    }

    public static Specification<LicenseReportDoc> dateProcessingToContains(Date dateProcessingToFromField) {
        return (Specification<LicenseReportDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("dateProcessing"), dateProcessingToFromField );
    }

    public static Specification<LicenseReportDoc> dateProcessingBetween(Date dateProcessingFromField, Date dateProcessingToFromField) {
        return (Specification<LicenseReportDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.between(root.get("dateProcessing"), dateProcessingFromField, dateProcessingToFromField);
    }

    public static Specification<LicenseReportDoc> licenseNumberContains(String regStatusFromField) {
        return (Specification<LicenseReportDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("licenseNumber")), "%" + regStatusFromField.toLowerCase() + "%");
    }

    public static Specification<LicenseReportDoc> subjectContains(String subjectFromField) {
        return (Specification<LicenseReportDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("subject")), "%" + subjectFromField.toLowerCase() + "%");
    }

    public static Specification<LicenseReportDoc> dateContains(Date dateFromField) {
        return (Specification<LicenseReportDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("date"), dateFromField );
    }

    public static Specification<LicenseReportDoc> dateToContains(Date dateToFromField) {
        return (Specification<LicenseReportDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("date"), dateToFromField );
    }

    public static Specification<LicenseReportDoc> dateBetween(Date dateFromField, Date dateToFromField) {
        return (Specification<LicenseReportDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.between(root.get("date"), dateFromField, dateToFromField);
    }

    public static Specification<LicenseReportDoc> reportTypeContains(String reportType) {
        return (Specification<LicenseReportDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("reportType")), "%" + reportType.toLowerCase() + "%");
    }

    public static Specification<LicenseReportDoc> reportingPeriodContains(String reportingPeriod) {
        return (Specification<LicenseReportDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("reportingPeriod"), reportingPeriod );
    }

    public static Specification<LicenseReportDoc> have4LSContains(String have4LS) {
        return (Specification<LicenseReportDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("have4LS"), have4LS );
    }

    public static Specification<LicenseReportDoc> have2TPContains(String have2TP) {
        return (Specification<LicenseReportDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("have2TP"), have2TP );
    }

    public static Specification<LicenseReportDoc> have3LSContains(String have3LS) {
        return (Specification<LicenseReportDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("have3LS"), have3LS );
    }
}
