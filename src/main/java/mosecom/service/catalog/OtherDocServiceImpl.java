package mosecom.service.catalog;

import mosecom.dao.catalog.OtherDocRepository;
import mosecom.model.catalog.OtherDoc;
import mosecom.specification.OtherDocSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class OtherDocServiceImpl {

    @Autowired
    private OtherDocRepository docRepository;

    public Specification<OtherDoc> getSpec(Integer idFromField,
                                              String regStatusFromField,
                                              String regNumberFromField,
                                              Date dateProcessingFromField,
                                              Date dateProcessingToFromField,
                                              String organizationSourceFromField,
                                              String docTypeFromField,
                                              String reportNameFromField,
                                              String authorFromField,
                                              String organizationAuthorFromField,
                                              Integer compilationYearFromField,
                                              Integer compilationYearToFromField
    ) {

        Specification<OtherDoc> spec = Specification.where(null);
        if (idFromField != null) {
            spec = spec.and(OtherDocSpecification.idContains(idFromField));
        }
        if (regStatusFromField != null && !regStatusFromField.isEmpty()) {
            spec = spec.and(OtherDocSpecification.regStatusContains(regStatusFromField));
        }
        if (regNumberFromField != null && !regNumberFromField.isEmpty()) {
            spec = spec.and(OtherDocSpecification.regNumberContains(regNumberFromField));
        }
        if(dateProcessingFromField != null && dateProcessingToFromField != null) {
            spec = spec.and(OtherDocSpecification.dateProcessingBetween(dateProcessingFromField, dateProcessingToFromField));
        }
        if (dateProcessingFromField != null && dateProcessingToFromField == null) {
            spec = spec.and(OtherDocSpecification.dateProcessingContains(dateProcessingFromField));
        }
        if (dateProcessingToFromField != null && dateProcessingFromField == null) {
            spec = spec.and(OtherDocSpecification.dateProcessingToContains(dateProcessingToFromField));
        }
        if (organizationSourceFromField != null && !organizationSourceFromField.isEmpty()) {
            spec = spec.and(OtherDocSpecification.organizationSourceContains(organizationSourceFromField));
        }
        if (docTypeFromField != null && !docTypeFromField.isEmpty()) {
            spec = spec.and(OtherDocSpecification.docTypeContains(docTypeFromField));
        }
        if (reportNameFromField != null && !reportNameFromField.isEmpty()) {
            spec = spec.and(OtherDocSpecification.reportNameContains(reportNameFromField));
        }
        if (authorFromField != null && !authorFromField.isEmpty()) {
            spec = spec.and(OtherDocSpecification.authorContains(authorFromField));
        }
        if (organizationAuthorFromField != null && !organizationAuthorFromField.isEmpty()) {
            spec = spec.and(OtherDocSpecification.organizationAuthorContains(organizationAuthorFromField));
        }
        if (compilationYearFromField != null && compilationYearToFromField == null) {
            spec = spec.and(OtherDocSpecification.compilationYearContains(compilationYearFromField));
        }
        if (compilationYearFromField != null && compilationYearToFromField != null) {
            spec = spec.and(OtherDocSpecification.compilationYearBetween(compilationYearFromField, compilationYearToFromField));
        }
        if (compilationYearFromField == null && compilationYearToFromField != null) {
            spec = spec.and(OtherDocSpecification.compilationYearToContains(compilationYearToFromField));
        }
        return spec;
    }

    public String getFiltresString(Integer idFromField,
                                   String regStatusFromField,
                                   String regNumberFromField,
                                   Date dateProcessingFromField,
                                   Date dateProcessingToFromField,
                                   String organizationSourceFromField,
                                   String docTypeFromField,
                                   String reportNameFromField,
                                   String authorFromField,
                                   String organizationAuthorFromField,
                                   Integer compilationYearFromField,
                                   Integer compilationYearToFromField) {
        StringBuilder filtersBuilder = new StringBuilder();
        if (idFromField != null) {
            filtersBuilder.append("&idFromField=" + idFromField);
        }
        if (regNumberFromField != null && !regNumberFromField.isEmpty()) {
            filtersBuilder.append("&regNumberFromField=" + regNumberFromField);
        }
        if (regStatusFromField != null && !regStatusFromField.isEmpty()) {
            filtersBuilder.append("&regStatusFromField=" + regStatusFromField);
        }
        if (dateProcessingFromField != null) {
            filtersBuilder.append("&dateProcessingFromField=" + new SimpleDateFormat("yyyy-MM-dd").format(dateProcessingFromField));
        }
        if (dateProcessingToFromField != null) {
            filtersBuilder.append("&dateProcessingToFromField=" + new SimpleDateFormat("yyyy-MM-dd").format(dateProcessingToFromField));
        }
        if (organizationSourceFromField != null && !organizationSourceFromField.isEmpty()) {
            filtersBuilder.append("&organizationSourceFromField=" + organizationSourceFromField);
        }
        if (docTypeFromField != null && !docTypeFromField.isEmpty()) {
            filtersBuilder.append("&docTypeFromField=" + docTypeFromField);
        }
        if (reportNameFromField != null && !reportNameFromField.isEmpty()) {
            filtersBuilder.append("&reportNameFromField=" + reportNameFromField);
        }
        if (authorFromField != null && !authorFromField.isEmpty()) {
            filtersBuilder.append("&authorFromField=" + authorFromField);
        }
        if (organizationAuthorFromField != null && !organizationAuthorFromField.isEmpty()) {
            filtersBuilder.append("&organizationAuthorFromField=" + organizationAuthorFromField);
        }
        if (compilationYearFromField != null) {
            filtersBuilder.append("&compilationYearFromField=" + compilationYearFromField);
        }
        if (compilationYearToFromField != null) {
            filtersBuilder.append("&compilationYearToFromField=" + compilationYearToFromField);
        }
        return filtersBuilder.toString();
    }

    public Page<OtherDoc> findAllByPagingAndFiltering(Specification<OtherDoc> specification, Pageable pageable) {
        return docRepository.findAll(specification, pageable);
    }

    public List<OtherDoc> findAllByFiltering(Specification<OtherDoc> specification) {
        return docRepository.findAll(specification);
    }

}


