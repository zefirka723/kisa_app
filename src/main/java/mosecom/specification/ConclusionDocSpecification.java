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
                -> criteriaBuilder.like(root.get("regStatus"), "%" + regStatusFromField + "%");
    }

    public static Specification<ConclusionDoc> regNumberContains(String regNumberFromField) {
        return (Specification<ConclusionDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get("regNumber"), "%" + regNumberFromField + "%");
    }

    public static Specification<ConclusionDoc> dateProcessingContains(Date dateProcessingFromField) {
        return (Specification<ConclusionDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("dateProcessing"), dateProcessingFromField );
    }

    public static Specification<ConclusionDoc> organizationSourceContains(String organizationSourceFromField) {
        return (Specification<ConclusionDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get("organizationSource"), "%" + organizationSourceFromField + "%");
    }

    public static Specification<ConclusionDoc> nameOfConclusionContains(String nameOfConclusionFromField) {
        return (Specification<ConclusionDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(criteriaBuilder.lower(root.get("nameOfConclusion")), "%" + nameOfConclusionFromField.toLowerCase() + "%");
    }

    public static Specification<ConclusionDoc> employerContains(String employerFromField) {
        return (Specification<ConclusionDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get("employer"), "%" + employerFromField + "%");
    }

    public static Specification<ConclusionDoc> authorContains(String authorFromField) {
        return (Specification<ConclusionDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.like(root.get("author"), "%" + authorFromField + "%");
    }

    public static Specification<ConclusionDoc> compilationYearContains(Integer compilationYearFromField) {
        return (Specification<ConclusionDoc>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("compilationYear"), compilationYearFromField );
    }
}
