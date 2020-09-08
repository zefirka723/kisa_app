package mosecom.service.catalog;

import mosecom.dao.catalog.PrimaryDocRepository;
import mosecom.model.catalog.PrimaryDoc;
import mosecom.specification.PrimaryDocSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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
                                             Date dateProcessingToFromField,
                                             String typeObservFromField,
                                             String observIdFromField,
                                             String docTypeFromField,
                                             Date datePrepareFromField,
                                             Date datePrepareToFromField) {

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
        if(dateProcessingFromField != null && dateProcessingToFromField != null) {
            spec = spec.and(PrimaryDocSpecification.dateProcessingBetween(dateProcessingFromField, dateProcessingToFromField));
        }
        if (dateProcessingFromField != null && dateProcessingToFromField == null) {
            spec = spec.and(PrimaryDocSpecification.dateProcessingContains(dateProcessingFromField));
        }
        if (dateProcessingToFromField != null && dateProcessingFromField == null) {
            spec = spec.and(PrimaryDocSpecification.dateProcessingToContains(dateProcessingToFromField));
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
        if (datePrepareFromField != null && datePrepareToFromField == null){
            spec = spec.and(PrimaryDocSpecification.datePrepareContains(datePrepareFromField));
        }
        if (datePrepareToFromField != null && datePrepareFromField == null){
            spec = spec.and(PrimaryDocSpecification.datePrepareToContains(datePrepareToFromField));
        }
        if (datePrepareFromField != null && datePrepareToFromField != null){
            spec = spec.and(PrimaryDocSpecification.datePrepareBetween(datePrepareFromField, datePrepareToFromField));
        }
        return spec;
    }

    public String getFiltresString(Integer idFromField,
                                   String regStatusFromField,
                                   String regNumberFromField,
                                   Date dateProcessingFromField,
                                   Date dateProcessingToFromField,
                                   String typeObservFromField,
                                   String observIdFromField,
                                   String docTypeFromField,
                                   Date datePrepareFromField,
                                   Date datePrepareToFromField) {
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
            filtersBuilder.append("&dateProcessingFromField=" + new SimpleDateFormat("yyyy-MM-dd").format(dateProcessingFromField));
        }
        if (dateProcessingToFromField != null) {
            filtersBuilder.append("&dateProcessingToFromField=" + new SimpleDateFormat("yyyy-MM-dd").format(dateProcessingToFromField));
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
            filtersBuilder.append("&datePrepareFromField=" + new SimpleDateFormat("yyyy-MM-dd").format(datePrepareFromField));
        }
        if (datePrepareToFromField != null){
            filtersBuilder.append("&datePrepareToFromField=" + new SimpleDateFormat("yyyy-MM-dd").format(datePrepareToFromField));
        }
        return filtersBuilder.toString();
    }

    public Page<PrimaryDoc> findAllByPagingAndFiltering(Specification<PrimaryDoc> specification, Pageable pageable) {
        return primaryDocRepository.findAll(specification, pageable);
    }

    public List<PrimaryDoc> findAllByFiltering(Specification<PrimaryDoc> specification) {
        return primaryDocRepository.findAll(specification);
    }

}


