package mosecom.specification;

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
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("regStatus")), "%" + regStatusFromField.toLowerCase() + "%");
    }

    public static Specification<ProtocolDoc> regNumberContains(String regNumberFromField) {
        return (Specification<ProtocolDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("regNumber")), "%" + regNumberFromField.toLowerCase() + "%");
    }

    public static Specification<ProtocolDoc> dateProcessingContains(Date dateProcessingFromField) {
        return (Specification<ProtocolDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("dateProcessing"), dateProcessingFromField );
    }

    public static Specification<ProtocolDoc> dateProcessingToContains(Date dateProcessingToFromField) {
        return (Specification<ProtocolDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("dateProcessing"), dateProcessingToFromField );
    }

    public static Specification<ProtocolDoc> dateProcessingBetween(Date dateProcessingFromField, Date dateProcessingToFromField) {
        return (Specification<ProtocolDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.between(root.get("dateProcessing"), dateProcessingFromField, dateProcessingToFromField);
    }


    public static Specification<ProtocolDoc> subjectContains(String subjectFromField) {
        return (Specification<ProtocolDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("subject")), "%" + subjectFromField.toLowerCase() + "%");
    }

    public static Specification<ProtocolDoc> instanceContains(String instanceFromField) {
        return (Specification<ProtocolDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("instance")), "%" + instanceFromField.toLowerCase() + "%");
    }

    public static Specification<ProtocolDoc> numberContains(String numberFromField) {
        return (Specification<ProtocolDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("number")), "%" + numberFromField.toLowerCase() + "%");
    }

    public static Specification<ProtocolDoc> dateContains(Date dateFromField) {
        return (Specification<ProtocolDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("date"), dateFromField );
    }

    public static Specification<ProtocolDoc> dateToContains(Date dateToFromField) {
        return (Specification<ProtocolDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("date"), dateToFromField );
    }

    public static Specification<ProtocolDoc> dateBetween(Date dateFromField, Date dateToFromField) {
        return (Specification<ProtocolDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.between(root.get("date"), dateFromField, dateToFromField);
    }

    public static Specification<ProtocolDoc> licenseNumberContains(String licenseNumberFromField) {
        return (Specification<ProtocolDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("licenseNumber")), "%" + licenseNumberFromField.toLowerCase() + "%");
    }

    public static Specification<ProtocolDoc> fieldGeneralNameContains(String fieldGeneralNameFromField) {
        return (Specification<ProtocolDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("fieldGeneralName")), "%" + fieldGeneralNameFromField.toLowerCase() + "%");
    }

    public static Specification<ProtocolDoc> fieldNameContains(String fieldNameFromField) {
        return (Specification<ProtocolDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("fieldName")), "%" + fieldNameFromField.toLowerCase() + "%");
    }

}
