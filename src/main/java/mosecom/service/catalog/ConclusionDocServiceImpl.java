package mosecom.service.catalog;

import mosecom.dao.catalog.ConclusionDocRepository;
import mosecom.model.catalog.ConclusionDoc;
import mosecom.specification.ConclusionDocSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class ConclusionDocServiceImpl {

    @Autowired
    private ConclusionDocRepository docRepository;

    public Specification<ConclusionDoc> getSpec(Integer idFromField,
                                              String regStatusFromField,
                                              String regNumberFromField,
                                              Date dateProcessingFromField,
                                              Date dateProcessingToFromField,
                                              String organizationSourceFromField,
                                              String nameOfConclusionFromField,
                                              String employerFromField,
                                              String authorFromField,
                                              Integer compilationYearFromField,
                                              Integer compilationYearToFromField
    ) {

        Specification<ConclusionDoc> spec = Specification.where(null);
        if (idFromField != null) {
            spec = spec.and(ConclusionDocSpecification.idContains(idFromField));
        }
        if (regStatusFromField != null && !regStatusFromField.isEmpty()) {
            spec = spec.and(ConclusionDocSpecification.regStatusContains(regStatusFromField));
        }
        if (regNumberFromField != null && !regNumberFromField.isEmpty()) {
            spec = spec.and(ConclusionDocSpecification.regNumberContains(regNumberFromField));
        }
        if(dateProcessingFromField != null && dateProcessingToFromField != null) {
            spec = spec.and(ConclusionDocSpecification.dateProcessingBetween(dateProcessingFromField, dateProcessingToFromField));
        }
        if (dateProcessingFromField != null && dateProcessingToFromField == null) {
            spec = spec.and(ConclusionDocSpecification.dateProcessingContains(dateProcessingFromField));
        }
        if (dateProcessingToFromField != null && dateProcessingFromField == null) {
            spec = spec.and(ConclusionDocSpecification.dateProcessingToContains(dateProcessingToFromField));
        }
        if (organizationSourceFromField != null && !organizationSourceFromField.isEmpty()) {
            spec = spec.and(ConclusionDocSpecification.organizationSourceContains(organizationSourceFromField));
        }
        if (nameOfConclusionFromField != null && !nameOfConclusionFromField.isEmpty()) {
            spec = spec.and(ConclusionDocSpecification.nameOfConclusionContains(nameOfConclusionFromField));
        }
        if (employerFromField != null && !employerFromField.isEmpty()) {
            spec = spec.and(ConclusionDocSpecification.employerContains(employerFromField));
        }
        if (authorFromField != null && !authorFromField.isEmpty()) {
            spec = spec.and(ConclusionDocSpecification.authorContains(authorFromField));
        }
        if (compilationYearFromField != null && compilationYearToFromField == null) {
            spec = spec.and(ConclusionDocSpecification.compilationYearContains(compilationYearFromField));
        }
        if (compilationYearFromField != null && compilationYearToFromField != null) {
            spec = spec.and(ConclusionDocSpecification.compilationYearBetween(compilationYearFromField, compilationYearToFromField));
        }
        if (compilationYearFromField == null && compilationYearToFromField != null) {
            spec = spec.and(ConclusionDocSpecification.compilationYearToContains(compilationYearToFromField));
        }
        return spec;
    }

    public String getFiltresString(Integer idFromField,
                                   String regStatusFromField,
                                   String regNumberFromField,
                                   Date dateProcessingFromField,
                                   Date dateProcessingToFromField,
                                   String organizationSourceFromField,
                                   String nameOfConclusionFromField,
                                   String employerFromField,
                                   String authorFromField,
                                   Integer compilationYearFromField,
                                   Integer compilationYearToFromField) throws ParseException {
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
        if (nameOfConclusionFromField != null && !nameOfConclusionFromField.isEmpty()) {
            filtersBuilder.append("&nameOfConclusionFromField=" + nameOfConclusionFromField);
        }
        if (nameOfConclusionFromField != null && !nameOfConclusionFromField.isEmpty()) {
            filtersBuilder.append("&nameOfConclusionFromField=" + nameOfConclusionFromField);
        }
        if (employerFromField != null && !employerFromField.isEmpty()) {
            filtersBuilder.append("&employerFromField=" + employerFromField);
        }
        if (authorFromField != null && !authorFromField.isEmpty()) {
            filtersBuilder.append("&authorFromField=" + authorFromField);
        }
        if (compilationYearFromField != null) {
            filtersBuilder.append("compilationYearFromField=" + compilationYearFromField);
        }
        if (compilationYearToFromField != null) {
            filtersBuilder.append("compilationYearToFromField=" + compilationYearToFromField);
        }
        return filtersBuilder.toString();
    }

    public Page<ConclusionDoc> findAllByPagingAndFiltering(Specification<ConclusionDoc> specification, Pageable pageable) {
        return docRepository.findAll(specification, pageable);
    }

    public List<ConclusionDoc> findAllByFiltering(Specification<ConclusionDoc> specification) {
        return docRepository.findAll(specification);
    }

}


