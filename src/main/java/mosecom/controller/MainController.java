package mosecom.controller;

import java.io.IOException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import mosecom.dto.WellFullProjection;
import mosecom.model.Well;
import mosecom.model.WellsDocument;
import mosecom.service.WellService;

@Controller
@RequestMapping("/reccards")
public class MainController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private WellService service;

    // Общий список документов
    @RequestMapping
    public String mainPage(Model model) {
        model.addAttribute("reccards", service.getWellsList());
        return "list/list";
    }

    // сщздание
    @RequestMapping(value = "/add-card")
    public ModelAndView addCard() {
        return editCard(new Well());
    }

    // редактирование
    @RequestMapping(value = "/edit-card/{id}")
    public ModelAndView editCard(@PathVariable("id") Integer id) {
        Well well = service.getWell(id);

        // На самом деле лучше так не делать, а передавать во view полную DTO
        well.getConstructions();
        well.getDocuments();
        well.getGeologies();
        well.getStressTests();
        well.getWellsDoc();


    //well.getDepth();
    //well.getWellsDocs();

        return editCard(well);
}

    private ModelAndView editCard(Well well) {
        ModelAndView result = new ModelAndView("edit/edit-card");
        result.addObject("well", well);
        result.addObject("constructionTypes", service.getAllConstructionTypes());
        result.addObject("diametrs", service.getAllDiametrs());
        result.addObject("horisonts", service.getAllHorisonts());
        return result;
    }

    @RequestMapping(value = "/edit-card/submit", method = RequestMethod.POST)
    public String submitCard(
            @RequestParam(value = "file", required = false) MultipartFile[] files,
            @ModelAttribute WellFullProjection well) throws IOException {
        service.save(well, files);
        return "redirect:/reccards/";
    }

    @RequestMapping(value = "/file/{id}")
    public ResponseEntity<FileSystemResource> downloadFile(@PathVariable("id") int id) throws IOException {
        WellsDocument doc = service.getWellDocument(id);
        FileSystemResource file = new FileSystemResource(doc.getFilePath() + doc.getFileName());
        if (!file.exists()) {
            //throw new ResourceNotFoundException("file", file);
            //added
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, doc.getFileContentType());
        headers.set(HttpHeaders.CONTENT_LENGTH, Long.toString(doc.getFileSize()));
        headers.set(HttpHeaders.CONTENT_ENCODING, "UTF-8");

        String fileName = URLEncoder.encode(doc.getFileName(), "UTF8").replace("+", "%20");
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + fileName);
        ResponseEntity<FileSystemResource> result = new ResponseEntity<>(file, headers, HttpStatus.OK);
        return result;
    }

    @RequestMapping(value = "/login")
    public String loginPage() {
        return "login";
    }
}


