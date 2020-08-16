package mosecom.specification;

import mosecom.model.catalog.LicenseDoc;
import mosecom.model.catalog.ProtocolDoc;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class ProtocolDocSpecification {

    public static Specification<ProtocolDoc> idContains(Integer idFromField) {
        return (Specification<ProtocolDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("id"), idFromField);
    }

    public static Specification<ProtocolDoc> regStatusContains(String regStatusFromField) {
        return (Specification<ProtocolDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get("regStatus"), "%" + regStatusFromField + "%");
    }

    public static Specification<ProtocolDoc> regNumberContains(String regNumberFromField) {
        return (Specification<ProtocolDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get("regNumber"), "%" + regNumberFromField + "%");
    }

    public static Specification<ProtocolDoc> dateProcessingContains(Date dateProcessingFromField) {
        return (Specification<ProtocolDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("dateProcessing"), dateProcessingFromField );
    }

    public static Specification<ProtocolDoc> subjectContains(String subjectFromField) {
        return (Specification<ProtocolDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get("subject"), "%" + subjectFromField + "%");
    }

    public static Specification<ProtocolDoc> instanceContains(String instanceFromField) {
        return (Specification<ProtocolDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get("instance"), "%" + instanceFromField + "%");
    }

    public static Specification<ProtocolDoc> numberContains(String numberFromField) {
        return (Specification<ProtocolDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get("number"), "%" + numberFromField + "%");
    }

    public static Specification<ProtocolDoc> dateContains(String dateFromField) {
        return (Specification<ProtocolDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("date"), dateFromField );
    }

    public static Specification<ProtocolDoc> licenseNumberContains(String licenseNumberFromField) {
        return (Specification<ProtocolDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get("licenseNumber"), "%" + licenseNumberFromField + "%");
    }

    public static Specification<ProtocolDoc> fieldGeneralNameContains(String fieldGeneralNameFromField) {
        return (Specification<ProtocolDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get("fieldGeneralName"), "%" + fieldGeneralNameFromField + "%");
    }

    public static Specification<ProtocolDoc> fieldNameContains(String fieldNameFromField) {
        return (Specification<ProtocolDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get("fieldName"), "%" + fieldNameFromField + "%");
    }

}
