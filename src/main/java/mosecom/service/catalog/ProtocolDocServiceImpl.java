package mosecom.service.catalog;

import mosecom.dao.catalog.LicenseDocRepository;
import mosecom.dao.catalog.ProtocolDocRepository;
import mosecom.model.catalog.LicenseDoc;
import mosecom.model.catalog.ProtocolDoc;
import mosecom.specification.LicenseDocSpecification;
import mosecom.specification.ProtocolDocSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class ProtocolDocServiceImpl {

    @Autowired
    private ProtocolDocRepository protocolDocRepository;

    public Specification<ProtocolDoc> getSpec(Integer idFromField,
                                              String regStatusFromField,
                                              String regNumberFromField,
                                              Date dateProcessingFromField,
                                              Date dateProcessingToFromField,
                                              String subjectFromField,
                                              String instanceFromField,
                                              String numberFromField,
                                              Date dateFromField,
                                              Date dateToFromField,
                                              String licenseNumberFromField,
                                              String fieldGeneralNameFromField,
                                              String fieldNameFromField) {

        Specification<ProtocolDoc> spec = Specification.where(null);
        if (idFromField != null) {
            spec = spec.and(ProtocolDocSpecification.idContains(idFromField));
        }
        if (regStatusFromField != null && !regStatusFromField.isEmpty()) {
            spec = spec.and(ProtocolDocSpecification.regStatusContains(regStatusFromField));
        }
        if (regNumberFromField != null && !regNumberFromField.isEmpty()) {
            spec = spec.and(ProtocolDocSpecification.regNumberContains(regNumberFromField));
        }
        if(dateProcessingFromField != null && dateProcessingToFromField != null) {
            spec = spec.and(ProtocolDocSpecification.dateProcessingBetween(dateProcessingFromField, dateProcessingToFromField));
        }
        if (dateProcessingFromField != null && dateProcessingToFromField == null) {
            spec = spec.and(ProtocolDocSpecification.dateProcessingContains(dateProcessingFromField));
        }
        if (dateProcessingToFromField != null && dateProcessingFromField == null) {
            spec = spec.and(ProtocolDocSpecification.dateProcessingToContains(dateProcessingToFromField));
        }
        if (subjectFromField != null && !subjectFromField.isEmpty()) {
            spec = spec.and(ProtocolDocSpecification.subjectContains(subjectFromField));
        }
        if (instanceFromField != null && !instanceFromField.isEmpty()) {
            spec = spec.and(ProtocolDocSpecification.instanceContains(instanceFromField));
        }
        if (numberFromField != null && !numberFromField.isEmpty()) {
            spec = spec.and(ProtocolDocSpecification.numberContains(numberFromField));
        }
        if(dateFromField != null && dateToFromField != null) {
            spec = spec.and(ProtocolDocSpecification.dateBetween(dateFromField, dateToFromField));
        }
        if (dateFromField != null && dateToFromField == null) {
            spec = spec.and(ProtocolDocSpecification.dateContains(dateFromField));
        }
        if (dateToFromField != null && dateFromField == null) {
            spec = spec.and(ProtocolDocSpecification.dateToContains(dateToFromField));
        }
        if (licenseNumberFromField != null && !licenseNumberFromField.isEmpty()) {
            spec = spec.and(ProtocolDocSpecification.licenseNumberContains(licenseNumberFromField));
        }
        if (fieldGeneralNameFromField != null && !fieldGeneralNameFromField.isEmpty()) {
            spec = spec.and(ProtocolDocSpecification.fieldGeneralNameContains(fieldGeneralNameFromField));
        }
        if (fieldNameFromField != null && !fieldNameFromField.isEmpty()) {
            spec = spec.and(ProtocolDocSpecification.fieldNameContains(fieldNameFromField));
        }
        return spec;
    }

    public String getFiltresString(Integer idFromField,
                                   String regStatusFromField,
                                   String regNumberFromField,
                                   Date dateProcessingFromField,
                                   Date dateProcessingToFromField,
                                   String subjectFromField,
                                   String instanceFromField,
                                   String numberFromField,
                                   Date dateFromField,
                                   Date dateToFromField,
                                   String licenseNumberFromField,
                                   String fieldGeneralNameFromField,
                                   String fieldNameFromField) {
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
        if (subjectFromField != null && !subjectFromField.isEmpty()) {
            filtersBuilder.append("&subjectFromField=" + subjectFromField);
        }
        if (instanceFromField != null && !instanceFromField.isEmpty()) {
            filtersBuilder.append("&instanceFromField=" + instanceFromField);
        }
        if (numberFromField != null && !numberFromField.isEmpty()) {
            filtersBuilder.append("&numberFromField=" + numberFromField);
        }
        if (dateFromField != null) {
            filtersBuilder.append("&dateFromField=" + new SimpleDateFormat("yyyy-MM-dd").format(dateFromField));
        }
        if (dateToFromField != null) {
            filtersBuilder.append("&dateToFromField=" + new SimpleDateFormat("yyyy-MM-dd").format(dateToFromField));
        }
        if (licenseNumberFromField != null && !licenseNumberFromField.isEmpty()) {
            filtersBuilder.append("&licenseNumberFromField=" + licenseNumberFromField);
        }
        if (fieldGeneralNameFromField != null && !fieldGeneralNameFromField.isEmpty()) {
            filtersBuilder.append("&fieldGeneralNameFromField=" + fieldGeneralNameFromField);
        }
        if (fieldNameFromField != null && !fieldNameFromField.isEmpty()) {
            filtersBuilder.append("&fieldNameFromField=" + fieldNameFromField);
        }
        return filtersBuilder.toString();
    }

    public Page<ProtocolDoc> findAllByPagingAndFiltering(Specification<ProtocolDoc> specification, Pageable pageable) {
        return protocolDocRepository.findAll(specification, pageable);
    }

    public List<ProtocolDoc> findAllByFiltering(Specification<ProtocolDoc> specification) {
        return protocolDocRepository.findAll(specification);
    }

}


