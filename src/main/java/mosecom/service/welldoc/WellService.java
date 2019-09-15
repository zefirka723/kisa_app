package mosecom.service.welldoc;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import mosecom.model.*;
import org.springframework.web.multipart.MultipartFile;

import mosecom.dto.WellFullProjection;
import mosecom.dto.WellProjection;

public interface WellService {

	List<WellProjection> getWellsList();

	Well getWell(int id);

//	Well save(WellFullProjection well, MultipartFile[] file, int docType) throws IllegalStateException, IOException, ParseException;

	List<ConstructionType> getAllConstructionTypes();

	List<Diametr> getAllDiametrs();

	List<Horisont> getAllHorisonts();

	List<MovedType> getAllMovedTypes();

	Attachment getWellDocument(int id);
}