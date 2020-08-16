package mosecom.service.catalog;

import mosecom.dao.catalog.LicenseDocRepository;
import mosecom.dao.catalog.PrimaryDocRepository;
import mosecom.model.catalog.LicenseDoc;
import mosecom.model.catalog.PrimaryDoc;
import mosecom.specification.LicenseDocSpecification;
import mosecom.specification.PrimaryDocSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class LicenseDocServiceImpl {

    @Autowired
    private LicenseDocRepository licenseDocRepository;

    public Specification<LicenseDoc> getSpec(Integer idFromField,
                                             String regStatusFromField,
                                             String regNumberFromField,
                                             Date dateProcessingFromField,
                                             String licenseNumberFromField,
                                             String organizationsFromField,
                                             String subjectFromField,
                                             String statusFromField,
                                             Date dateStartFromField,
                                             Date dateEndFromField,
                                             Float flowRateSummFromField,
                                             String commentsDocsFromField) {

        Specification<LicenseDoc> spec = Specification.where(null);
        if (idFromField != null) {
            spec = spec.and(LicenseDocSpecification.idContains(idFromField));
        }
        if (regStatusFromField != null && !regStatusFromField.isEmpty()) {
            spec = spec.and(LicenseDocSpecification.regStatusContains(regStatusFromField));
        }
        if (regNumberFromField != null && !regNumberFromField.isEmpty()) {
            spec = spec.and(LicenseDocSpecification.regNumberContains(regNumberFromField));
        }
        if (dateProcessingFromField != null) {
            spec = spec.and(LicenseDocSpecification.dateProcessingContains(dateProcessingFromField));
        }
        if (licenseNumberFromField != null && !licenseNumberFromField.isEmpty()) {
            spec = spec.and(LicenseDocSpecification.licenseNumberContains(licenseNumberFromField));
        }
        if (organizationsFromField != null && !organizationsFromField.isEmpty()) {
            spec = spec.and(LicenseDocSpecification.organizationsContains(organizationsFromField));
        }
        if (subjectFromField != null && !subjectFromField.isEmpty()) {
            spec = spec.and(LicenseDocSpecification.subjectContains(subjectFromField));
        }
        if (statusFromField != null && !statusFromField.isEmpty()) {
            spec = spec.and(LicenseDocSpecification.statusContains(statusFromField));
        }
        if (dateStartFromField != null) {
            spec = spec.and(LicenseDocSpecification.dateStartContains(dateStartFromField));
        }
        if (dateEndFromField != null) {
            spec = spec.and(LicenseDocSpecification.dateEndContains(dateEndFromField));
        }
        if (flowRateSummFromField != null) {
            spec = spec.and(LicenseDocSpecification.flowRateSummContains(flowRateSummFromField));
        }
        if (commentsDocsFromField != null && !commentsDocsFromField.isEmpty()) {
            spec = spec.and(LicenseDocSpecification.commentsDocsContains(commentsDocsFromField));
        }
        return spec;
    }

    public String getFiltresString(Integer idFromField,
                                   String regStatusFromField,
                                   String regNumberFromField,
                                   Date dateProcessingFromField,
                                   String licenseNumberFromField,
                                   String organizationsFromField,
                                   String subjectFromField,
                                   String statusFromField,
                                   Date dateStartFromField,
                                   Date dateEndFromField,
                                   Float flowRateSummFromField,
                                   String commentsDocsFromField) {
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
            filtersBuilder.append("&dateProcessingFromField=" + dateProcessingFromField);
        }
        if (licenseNumberFromField != null && !licenseNumberFromField.isEmpty()) {
            filtersBuilder.append("&licenseNumberFromField=" + licenseNumberFromField);
        }
        if (organizationsFromField != null && !organizationsFromField.isEmpty()) {
            filtersBuilder.append("&organizationsFromField=" + organizationsFromField);
        }
        if (subjectFromField != null && !subjectFromField.isEmpty()) {
            filtersBuilder.append("&subjectFromField=" + subjectFromField);
        }
        if (statusFromField != null && !statusFromField.isEmpty()) {
            filtersBuilder.append("&statusFromField=" + statusFromField);
        }
        if (dateStartFromField != null) {
            filtersBuilder.append("&dateStartFromField=" + dateStartFromField);
        }
        if (dateEndFromField != null) {
            filtersBuilder.append("&dateEndFromField=" + dateEndFromField);
        }
        if (flowRateSummFromField != null) {
            filtersBuilder.append("&flowRateSummFromField=" + flowRateSummFromField);
        }
        if (commentsDocsFromField != null && !commentsDocsFromField.isEmpty()) {
            filtersBuilder.append("&commentsDocsFromField=" + commentsDocsFromField);
        }
        return filtersBuilder.toString();
    }

    public Page<LicenseDoc> findAllByPagingAndFiltering(Specification<LicenseDoc> specification, Pageable pageable) {
        return licenseDocRepository.findAll(specification, pageable);
    }

    public List<LicenseDoc> findAllByFiltering(Specification<LicenseDoc> specification) {
        return licenseDocRepository.findAll(specification);
    }

}


