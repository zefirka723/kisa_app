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
                -> criteriaBuilder.like(root.get("regStatus"), "%" + regStatusFromField + "%");
    }

    public static Specification<PrimaryDoc> regNumberContains(String regNumberFromField) {
        return (Specification<PrimaryDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get("regNumber"), "%" + regNumberFromField + "%");
    }

    public static Specification<PrimaryDoc> dateProcessingContains(Date dateProcessingFromField) {
        return (Specification<PrimaryDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("dateProcessing"), dateProcessingFromField );
    }

    public static Specification<PrimaryDoc> typeObservContains(String typeObservFromField) {
        return (Specification<PrimaryDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get("typeObserv"), "%" + typeObservFromField + "%");
    }

    public static Specification<PrimaryDoc> observIdContains(String observIdFromField) {
        return (Specification<PrimaryDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("observId"), observIdFromField);
    }

    public static Specification<PrimaryDoc> docTypeContains(String docTypeFromField) {
        return (Specification<PrimaryDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get("docType"), "%" + docTypeFromField + "%");
    }

    public static Specification<PrimaryDoc> datePrepareContains(Date datePrepareFromField) {
        return (Specification<PrimaryDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("datePrepare"), datePrepareFromField);
                //.like(root.get("datePrepare"), "%" + datePrepareFromField + "%");
    }

}
