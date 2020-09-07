package mosecom.specification;

import mosecom.model.catalog.ConclusionDoc;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class ConclusionDocSpecification {

    public static Specification<ConclusionDoc> idContains(Integer idFromField) {
        return (Specification<ConclusionDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("id"), idFromField);
    }

    public static Specification<ConclusionDoc> regStatusContains(String regStatusFromField) {
        return (Specification<ConclusionDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("regStatus")), "%" + regStatusFromField.toLowerCase() + "%");
    }

    public static Specification<ConclusionDoc> regNumberContains(String regNumberFromField) {
        return (Specification<ConclusionDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("regNumber")), "%" + regNumberFromField.toLowerCase() + "%");
    }

    public static Specification<ConclusionDoc> dateProcessingContains(Date dateProcessingFromField) {
        return (Specification<ConclusionDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("dateProcessing"), dateProcessingFromField );
    }

    public static Specification<ConclusionDoc> dateProcessingToContains(Date dateProcessingToFromField) {
        return (Specification<ConclusionDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("dateProcessing"), dateProcessingToFromField );
    }

    public static Specification<ConclusionDoc> dateProcessingBetween(Date dateProcessingFromField, Date dateProcessingToFromField) {
        return (Specification<ConclusionDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.between(root.get("dateProcessing"), dateProcessingFromField, dateProcessingToFromField);
    }

    public static Specification<ConclusionDoc> organizationSourceContains(String organizationSourceFromField) {
        return (Specification<ConclusionDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("organizationSource")), "%" + organizationSourceFromField.toLowerCase() + "%");
    }

    public static Specification<ConclusionDoc> nameOfConclusionContains(String nameOfConclusionFromField) {
        return (Specification<ConclusionDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("nameOfConclusion")), "%" + nameOfConclusionFromField.toLowerCase() + "%");
    }

    public static Specification<ConclusionDoc> employerContains(String employerFromField) {
        return (Specification<ConclusionDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("employer")), "%" + employerFromField.toLowerCase() + "%");
    }

    public static Specification<ConclusionDoc> authorContains(String authorFromField) {
        return (Specification<ConclusionDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("author")), "%" + authorFromField.toLowerCase() + "%");
    }

    public static Specification<ConclusionDoc> compilationYearContains(Integer compilationYearFromField) {
        return (Specification<ConclusionDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("compilationYear"), compilationYearFromField );
    }

    public static Specification<ConclusionDoc> compilationYearToContains(Integer compilationYearToFromField) {
        return (Specification<ConclusionDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("compilationYear"), compilationYearToFromField);
    }

    public static Specification<ConclusionDoc> compilationYearBetween(Integer compilationYearFromField, Integer compilationYearToFromField) {
        return (Specification<ConclusionDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.between(root.get("compilationYear"), compilationYearFromField, compilationYearToFromField);
    }
}
