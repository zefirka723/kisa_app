package mosecom.specification;

import mosecom.model.catalog.PrimaryDoc;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class PrimaryDocSpecification {

    public static Specification<PrimaryDoc> idContains(Integer idFromField) {
        return (Specification<PrimaryDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("id"), idFromField);
                //.like(root.get("id"), "%" + idFromField + "%");
    }

    public static Specification<PrimaryDoc> regStatusContains(String regStatusFromField) {
        return (Specification<PrimaryDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("regStatus")), "%" + regStatusFromField.toLowerCase() + "%");
    }

    public static Specification<PrimaryDoc> regNumberContains(String regNumberFromField) {
        return (Specification<PrimaryDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("regNumber")), "%" + regNumberFromField.toLowerCase() + "%");
    }

    public static Specification<PrimaryDoc> dateProcessingContains(Date dateProcessingFromField) {
        return (Specification<PrimaryDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("dateProcessing"), dateProcessingFromField );
    }

    public static Specification<PrimaryDoc> dateProcessingToContains(Date dateProcessingToFromField) {
        return (Specification<PrimaryDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("dateProcessing"), dateProcessingToFromField );
    }

    public static Specification<PrimaryDoc> dateProcessingBetween(Date dateProcessingFromField, Date dateProcessingToFromField) {
        return (Specification<PrimaryDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.between(root.get("dateProcessing"), dateProcessingFromField, dateProcessingToFromField);
    }

    public static Specification<PrimaryDoc> typeObservContains(String typeObservFromField) {
        return (Specification<PrimaryDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("typeObserv")), "%" + typeObservFromField.toLowerCase() + "%");
    }

    public static Specification<PrimaryDoc> observIdContains(String observIdFromField) {
        return (Specification<PrimaryDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("observId"), observIdFromField);
    }

    public static Specification<PrimaryDoc> docTypeContains(String docTypeFromField) {
        return (Specification<PrimaryDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("docType")), "%" + docTypeFromField.toLowerCase() + "%");
    }

    public static Specification<PrimaryDoc> datePrepareContains(Date datePrepareFromField) {
        return (Specification<PrimaryDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("datePrepare"), datePrepareFromField);
                //.like(root.get("datePrepare"), "%" + datePrepareFromField + "%");
    }

    public static Specification<PrimaryDoc> datePrepareToContains(Date datePrepareToFromField) {
        return (Specification<PrimaryDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("datePrepare"), datePrepareToFromField);
    }

    public static Specification<PrimaryDoc> datePrepareBetween(Date datePrepareFromField, Date datePrepareToFromField) {
        return (Specification<PrimaryDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.between(root.get("datePrepare"), datePrepareFromField, datePrepareToFromField);
    }

}
