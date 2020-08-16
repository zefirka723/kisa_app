package mosecom.specification;

import mosecom.model.catalog.LicenseDoc;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class LicenseDocSpecification {

    public static Specification<LicenseDoc> idContains(Integer idFromField) {
        return (Specification<LicenseDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("id"), idFromField);
    }

    public static Specification<LicenseDoc> regStatusContains(String regStatusFromField) {
        return (Specification<LicenseDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get("regStatus"), "%" + regStatusFromField + "%");
    }

    public static Specification<LicenseDoc> regNumberContains(String regStatusFromField) {
        return (Specification<LicenseDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get("regNumber"), "%" + regStatusFromField + "%");
    }

    public static Specification<LicenseDoc> dateProcessingContains(Date dateProcessingFromField) {
        return (Specification<LicenseDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("dateProcessing"), dateProcessingFromField );
    }
    public static Specification<LicenseDoc> licenseNumberContains(String licenseNumberFromField) {
        return (Specification<LicenseDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get("licenseNumber"), "%" + licenseNumberFromField + "%");
    }

    public static Specification<LicenseDoc> organizationsContains(String organizationsFromField) {
        return (Specification<LicenseDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get("organizations"), "%" + organizationsFromField + "%");
    }

    public static Specification<LicenseDoc> subjectContains(String subjectFromField) {
        return (Specification<LicenseDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get("subject"), "%" + subjectFromField + "%");
    }

    public static Specification<LicenseDoc> statusContains(String statusFromField) {
        return (Specification<LicenseDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get("status"), "%" + statusFromField + "%");
    }

    public static Specification<LicenseDoc> dateStartContains(Date dateStartFromField) {
        return (Specification<LicenseDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("dateStart"), dateStartFromField );
    }

    public static Specification<LicenseDoc> dateEndContains(Date dateEndFromField) {
        return (Specification<LicenseDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("dateEnd"), dateEndFromField );
    }

    public static Specification<LicenseDoc> flowRateSummContains(Float flowRateSummFromField) {
        return (Specification<LicenseDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("flowRateSumm"), flowRateSummFromField);
    }

    public static Specification<LicenseDoc> commentsDocsContains(String commentsDocsFromField) {
        return (Specification<LicenseDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get("commentsDocs"), "%" + commentsDocsFromField + "%");
    }

}
