package mosecom.service.catalog;

import mosecom.dao.catalog.PrimaryDocRepository;
import mosecom.model.catalog.PrimaryDoc;
import mosecom.specification.PrimaryDocSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class PrimaryDocServiceImpl {

    @Autowired
    private PrimaryDocRepository primaryDocRepository;

//    public List<PrimaryDoc> findAll() {
//        return primaryDocRepository.findAll();
//    }

    public Specification<PrimaryDoc> getSpec(Integer idFromField,
                                             String regStatusFromField,
                                             String regNumberFromField,
                                             Date dateProcessingFromField,
                                             String typeObservFromField,
                                             String observIdFromField,
                                             String docTypeFromField,
                                             Date datePrepareFromField) {

        Specification<PrimaryDoc> spec = Specification.where(null);
        if (idFromField != null) {
            spec = spec.and(PrimaryDocSpecification.idContains(idFromField));
        }
        if (regStatusFromField != null && !regStatusFromField.isEmpty()) {
            spec = spec.and(PrimaryDocSpecification.regStatusContains(regStatusFromField));
        }
        if (regNumberFromField != null && !regNumberFromField.isEmpty()) {
            spec = spec.and(PrimaryDocSpecification.regNumberContains(regNumberFromField));
        }
        if (dateProcessingFromField != null) {
            spec = spec.and(PrimaryDocSpecification.dateProcessingContains(dateProcessingFromField));
        }
        if (typeObservFromField != null && !typeObservFromField.isEmpty()) {
            spec = spec.and(PrimaryDocSpecification.typeObservContains(typeObservFromField));
        }
        if (observIdFromField != null && !observIdFromField.isEmpty()) {
            spec = spec.and(PrimaryDocSpecification.observIdContains(observIdFromField));
        }
        if (docTypeFromField != null && !docTypeFromField.isEmpty()) {
            spec = spec.and(PrimaryDocSpecification.docTypeContains(docTypeFromField));
        }
        if (datePrepareFromField != null){ // && !datePrepareFromField.isEmpty()) {
            spec = spec.and(PrimaryDocSpecification.datePrepareContains(datePrepareFromField));
        }
        return spec;
    }

    public String getFiltresString(Integer idFromField,
                                   String regStatusFromField,
                                   String regNumberFromField,
                                   Date dateProcessingFromField,
                                   String typeObservFromField,
                                   String observIdFromField,
                                   String docTypeFromField,
                                   Date datePrepareFromField) {
        StringBuilder filtersBuilder = new StringBuilder();
        if (idFromField != null) {
            filtersBuilder.append("&idFromField=" + idFromField);
        }
        if (regStatusFromField != null && !regStatusFromField.isEmpty()) {
            filtersBuilder.append("&regStatusFromField=" + regStatusFromField);
        }
        if (regNumberFromField != null && !regNumberFromField.isEmpty()) {
            filtersBuilder.append("&regNumberFromField=" + regNumberFromField);
        }
        if (dateProcessingFromField != null) {
            filtersBuilder.append("&dateProcessingFromField=" + dateProcessingFromField);
        }
        if (typeObservFromField != null && !typeObservFromField.isEmpty()) {
            filtersBuilder.append("&typeObservFromField=" + typeObservFromField);
        }
        if (observIdFromField != null && !observIdFromField.isEmpty()) {
            filtersBuilder.append("&observIdFromField=" + observIdFromField);
        }
        if (docTypeFromField != null && !docTypeFromField.isEmpty()) {
            filtersBuilder.append("&docTypeFromField=" + docTypeFromField);
        }
        if (datePrepareFromField != null){ // && !datePrepareFromField.isEmpty()) {
            filtersBuilder.append("&datePrepareFromField=" + datePrepareFromField);
        }
        return filtersBuilder.toString();
    }

    public Page<PrimaryDoc> findAllByPagingAndFiltering(Specification<PrimaryDoc> specification, Pageable pageable) {
        return primaryDocRepository.findAll(specification, pageable);
    }

}


