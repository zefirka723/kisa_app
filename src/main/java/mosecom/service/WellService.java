package mosecom.service;

import java.io.IOException;
import java.util.List;

import mosecom.model.Diametr;
import org.springframework.web.multipart.MultipartFile;

import mosecom.dto.WellFullProjection;
import mosecom.dto.WellProjection;
import mosecom.model.ConstructionType;
import mosecom.model.Well;
import mosecom.model.WellsDocument;

public interface WellService {

	List<WellProjection> getWellsList();

	Well getWell(int id);

	Well save(WellFullProjection well, MultipartFile[] file) throws IllegalStateException, IOException;

	List<ConstructionType> getAllConstructionTypes();

	List<Diametr> getAllDiametrs();

	WellsDocument getWellDocument(int id);
}