package mosecom.controller;

import mosecom.dto.WellFullProjection;
import mosecom.model.Well;
import mosecom.model.WellsDoc;
import mosecom.model.WellsDocument;
import mosecom.model.WellsPassport;
import mosecom.service.DocService;
import mosecom.service.WellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

//import org.springframework.boot.context.config.ResourceNotFoundException;


@Controller
@RequestMapping("/fgi")
public class MainController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private WellService service;

    @Autowired
    private DocService docService;


    /*
        Учётные карточки
     */

    // Общий список учёток
    @RequestMapping("/reccards")
    public String reccardsPage(Model model) {
        model.addAttribute("reccards", service.getWellsList());
        return "list/list";
    }

    // редактирование учётки
    @RequestMapping(value = "/reccards/edit-card/{id}")
    public ModelAndView editCard(@PathVariable("id") Integer id) {
        Well well = service.getWell(id);
        // На самом деле лучше так не делать, а передавать во view полную DTO
        well.getConstructions();
        well.getDocuments();
        well.getGeologies();
        well.getStressTests();
        well.getWellDoc();
        well.getDepth();
        return editCard(well);
    }

    private ModelAndView editCard(Well well) {
        ModelAndView result = new ModelAndView("edit/edit-card");
        result.addObject("well", well);
        result.addObject("constructionTypes", service.getAllConstructionTypes());
        result.addObject("diametrs", service.getAllDiametrs());
        result.addObject("horisonts", service.getAllHorisonts());
        result.addObject("movedTypes", service.getAllMovedTypes());
        return result;
    }

    // Сабмит формы
    @RequestMapping(value = "/reccards/edit-card/submit", method = RequestMethod.POST)
    public String submitCard(
            @RequestParam(value = "file", required = false) MultipartFile[] files,
            @ModelAttribute WellFullProjection well) throws IOException {
        service.save(well, files, 3001);
        return "redirect:/fgi/reccards/";
    }

    // создание (пока не нужно)
//    @RequestMapping(value = "/add-card")
//    public ModelAndView addCard() {
//        return editCard(new Well());
//    }



    /*
        Паспорт скважины
     */

    // Общий список паспортов
    @RequestMapping("/passports")
    public String passportsPage(Model model) {
        model.addAttribute("reccards", service.getWellsList());
        return "list/passport";
    }

    // редактирование паспорта
    @RequestMapping(value = "/passports/edit-card/{id}")
    public ModelAndView editPassport(@PathVariable("id") Integer id) {
        Well well = service.getWell(id);

        well.getConstructions();  //TODO: подумать о своей жизни..

        well.getDocuments();
        well.getGeologies();
        well.getStressTests();
        well.getWellPassport();
        well.getDepth();
        return editPassport(well);
    }

    private ModelAndView editPassport(Well well) {
        ModelAndView result = new ModelAndView("edit/edit-passport");
        result.addObject("well", well);
        result.addObject("constructionTypes", service.getAllConstructionTypes());
        result.addObject("diametrs", service.getAllDiametrs());
        result.addObject("horisonts", service.getAllHorisonts());
        result.addObject("movedTypes", service.getAllMovedTypes());
        return result;
    }

    // сабмит формы паспорта
    @RequestMapping(value = "/passports/edit-card/submit", method = RequestMethod.POST)
    public String submitPassport(
            @RequestParam(value = "file", required = false) MultipartFile[] files,
            @ModelAttribute WellFullProjection well) throws IOException {
        service.save(well, files, 3002);
        return "redirect:/fgi/passports/";
    }


    /*
        Геологическое описание
     */

    // Общий список геологических описаний
    @RequestMapping("/descriptions")
    public String descriptionsPage(Model model) {
        model.addAttribute("descriptions", service.getWellsList());
        return "list/descriptions";
    }

    // редактирование геолог. описания
    @RequestMapping(value = "/descriptions/edit-card/{id}")
    public ModelAndView editDescription(@PathVariable("id") Integer id) {
        Well well = service.getWell(id);

        well.getConstructions();
        well.getDocuments();
        well.getGeologies();
        well.getStressTests();
        well.getWellsDescription();
        well.getDepth();
        return editDescription(well);
    }

    private ModelAndView editDescription(Well well) {
        ModelAndView result = new ModelAndView("edit/edit-description");
        result.addObject("well", well);
        result.addObject("constructionTypes", service.getAllConstructionTypes());
        result.addObject("diametrs", service.getAllDiametrs());
        result.addObject("horisonts", service.getAllHorisonts());
        result.addObject("movedTypes", service.getAllMovedTypes());
        return result;
    }

    // сабмит формы геологического описания
    @RequestMapping(value = "/descriptions/edit-card/submit", method = RequestMethod.POST)
    public String submitDescription(
            @RequestParam(value = "file", required = false) MultipartFile[] files,
            @ModelAttribute WellFullProjection well) throws IOException {
        service.save(well, files, 3007);
        return "redirect:/fgi/descriptions/";
    }


    /*
        Файлы
     */
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


    /*
        Форма логина
     */
    @RequestMapping(value = "/login")
    public String loginPage() {
        return "login";
    }
}


