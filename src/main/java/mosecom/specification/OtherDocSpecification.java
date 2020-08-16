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
                -> criteriaBuilder.like(root.get("regStatus"), "%" + regStatusFromField + "%");
    }

    public static Specification<OtherDoc> regNumberContains(String regNumberFromField) {
        return (Specification<OtherDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get("regNumber"), "%" + regNumberFromField + "%");
    }

    public static Specification<OtherDoc> dateProcessingContains(Date dateProcessingFromField) {
        return (Specification<OtherDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("dateProcessing"), dateProcessingFromField );
    }

    public static Specification<OtherDoc> organizationSourceContains(String organizationSourceFromField) {
        return (Specification<OtherDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get("organizationSource"), "%" + organizationSourceFromField + "%");
    }

    public static Specification<OtherDoc> docTypeContains(String docTypeFromField) {
        return (Specification<OtherDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get("docType"), "%" + docTypeFromField + "%");
    }

    public static Specification<OtherDoc> reportNameContains(String reportNameFromField) {
        return (Specification<OtherDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get("reportName"), "%" + reportNameFromField + "%");
    }

    public static Specification<OtherDoc> organizationAuthorContains(String organizationAuthorFromField) {
        return (Specification<OtherDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get("organizationAuthor"), "%" + organizationAuthorFromField + "%");
    }

    public static Specification<OtherDoc> authorContains(String authorFromField) {
        return (Specification<OtherDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get("author"), "%" + authorFromField + "%");
    }

    public static Specification<OtherDoc> compilationYearContains(Integer compilationYearFromField) {
        return (Specification<OtherDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("compilationYear"), compilationYearFromField );
    }
}
