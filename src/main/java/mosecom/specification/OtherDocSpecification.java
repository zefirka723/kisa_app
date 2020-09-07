package mosecom.specification;

import mosecom.model.catalog.OtherDoc;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class OtherDocSpecification {

    public static Specification<OtherDoc> idContains(Integer idFromField) {
        return (Specification<OtherDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("id"), idFromField);
    }

    public static Specification<OtherDoc> regStatusContains(String regStatusFromField) {
        return (Specification<OtherDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("regStatus")), "%" + regStatusFromField.toLowerCase() + "%");
    }

    public static Specification<OtherDoc> regNumberContains(String regNumberFromField) {
        return (Specification<OtherDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("regNumber")), "%" + regNumberFromField.toLowerCase() + "%");
    }

    public static Specification<OtherDoc> dateProcessingContains(Date dateProcessingFromField) {
        return (Specification<OtherDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("dateProcessing"), dateProcessingFromField );
    }

    public static Specification<OtherDoc> dateProcessingToContains(Date dateProcessingToFromField) {
        return (Specification<OtherDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("dateProcessing"), dateProcessingToFromField );
    }

    public static Specification<OtherDoc> dateProcessingBetween(Date dateProcessingFromField, Date dateProcessingToFromField) {
        return (Specification<OtherDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.between(root.get("dateProcessing"), dateProcessingFromField, dateProcessingToFromField);
    }

    public static Specification<OtherDoc> organizationSourceContains(String organizationSourceFromField) {
        return (Specification<OtherDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("organizationSource")), "%" + organizationSourceFromField.toLowerCase() + "%");
    }

    public static Specification<OtherDoc> docTypeContains(String docTypeFromField) {
        return (Specification<OtherDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("docType")), "%" + docTypeFromField.toLowerCase() + "%");
    }

    public static Specification<OtherDoc> reportNameContains(String reportNameFromField) {
        return (Specification<OtherDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("reportName")), "%" + reportNameFromField.toLowerCase() + "%");
    }

    public static Specification<OtherDoc> organizationAuthorContains(String organizationAuthorFromField) {
        return (Specification<OtherDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("organizationAuthor")), "%" + organizationAuthorFromField.toLowerCase() + "%");
    }

    public static Specification<OtherDoc> authorContains(String authorFromField) {
        return (Specification<OtherDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("author")), "%" + authorFromField.toLowerCase() + "%");
    }

    public static Specification<OtherDoc> compilationYearContains(Integer compilationYearFromField) {
        return (Specification<OtherDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("compilationYear"), compilationYearFromField );
    }

    public static Specification<OtherDoc> compilationYearToContains(Integer compilationYearToFromField) {
        return (Specification<OtherDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("compilationYear"), compilationYearToFromField);
    }

    public static Specification<OtherDoc> compilationYearBetween(Integer compilationYearFromField, Integer compilationYearToFromField) {
        return (Specification<OtherDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.between(root.get("compilationYear"), compilationYearFromField, compilationYearToFromField);
    }
}
