package mosecom.controller;

import mosecom.dto.WellFullProjection;
import mosecom.dto.inspections.DocumentFullProjection;
import mosecom.dto.inspections.DocumentProjection;
import mosecom.model.Well;
import mosecom.model.WellsDocument;
import mosecom.model.inspections.Document;
import mosecom.service.WellService;
import mosecom.service.registration.DocumentService;
import mosecom.service.registration.WellsInspectionService;
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
import java.util.stream.Collectors;

//import org.springframework.boot.context.config.ResourceNotFoundException;


@Controller
//@RequestMapping("/fgi") //DEV
@RequestMapping("/") //PROD
public class MainController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private WellService service;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private WellsInspectionService wellsInspectionService;

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

//        well.getConstructions();
//        well.getDocumentsByReccard();
//        well.getGeologies();
//        well.getStressTests();
//        well.getReccard();
////        well.getWellDoc();
////        well.getWellsDocList().stream().filter(wellsDoc -> wellsDoc.getDocType() == 3001).collect(Collectors.toList());
//
//        well.getDepth();
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

//        return "redirect:/fgi/reccards/"; //DEV
        return "redirect:/reccards/"; //PROD
    }


    //
//
//    /*
//        Паспорт скважины
//     */
//
//    // Общий список паспортов
    @RequestMapping("/passports")
    public String passportsPage(Model model) {
        model.addAttribute("reccards", service.getWellsList());
        return "list/passport";
    }

    //    // редактирование паспорта
    @RequestMapping(value = "/passports/edit-card/{id}")
    public ModelAndView editPassport(@PathVariable("id") Integer id) {
        Well well = service.getWell(id);
//        well.getConstructions();
//        well.getDocuments();
//        well.getGeologies();
//        well.getStressTests();
//    //    well.getWellPassport();
//        well.getDepth();
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

    //
//    // сабмит формы паспорта
    @RequestMapping(value = "/passports/edit-card/submit", method = RequestMethod.POST)
    public String submitPassport(
            @RequestParam(value = "file", required = false) MultipartFile[] files,
            @ModelAttribute WellFullProjection well) throws IOException {
        service.save(well, files, 3002);
        //return "redirect:/fgi/passports/"; //DEV
        return "redirect:/passports/"; //PROD
    }


    //    /*
//        Геологическое описание
//     */
//
//    // Общий список геологических описаний
    @RequestMapping("/descriptions")
    public String descriptionsPage(Model model) {
        model.addAttribute("descriptions", service.getWellsList());
        return "list/descriptions";
    }

    //
//    // редактирование геолог. описания
    @RequestMapping(value = "/descriptions/edit-card/{id}")
    public ModelAndView editDescription(@PathVariable("id") Integer id) {
        Well well = service.getWell(id);
//        well.getConstructions();
//        well.getDocuments();
//        well.getGeologies();
//        well.getStressTests();
//        well.getWellsDescription();
//        well.getDepth();
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

    //    // сабмит формы геологического описания
    @RequestMapping(value = "/descriptions/edit-card/submit", method = RequestMethod.POST)
    public String submitDescription(
            @RequestParam(value = "file", required = false) MultipartFile[] files,
            @ModelAttribute WellFullProjection well) throws IOException {
        service.save(well, files, 3007);
        //return "redirect:/fgi/descriptions/"; //DEV
        return "redirect:/descriptions/"; //PROD
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


// создание (пока не нужно)
//    @RequestMapping(value = "/add-card")
//    public ModelAndView addCard() {
//        return editCard(new Well());
//    }


    // Регистрация
    @RequestMapping(value = "/registration/wells-inspection/{id}")
    public ModelAndView editRegCard(@PathVariable("id") Integer id) {
        Document document = documentService.getDocument(id);
        return editRegCard(document);
    }

    private ModelAndView editRegCard(Document document) {
        ModelAndView result = new ModelAndView("reg-inspections/wells-inspection");
        result.addObject("document", document);
        result.addObject("employees", documentService.getAllEmployees());
        result.addObject("organizationSource", documentService.getAllOrganizationSource());
        result.addObject("status", documentService.getAllRegStatus());
        result.addObject("secrecy", documentService.getAllSecrecy());
        return result;
    }

    @RequestMapping(value = "/registration/wells-inspecition/submit", method = RequestMethod.POST)
    public String submitWellsInspection(
            @RequestParam(value = "file", required = false) MultipartFile[] files,
            @ModelAttribute DocumentFullProjection document) throws IOException {
        documentService.save(document, files);
        //return "redirect:/fgi/descriptions/"; //DEV
        return "redirect:/wells-inspection-list/"; //PROD
    }

    @RequestMapping("/wells-inspection-list")
    public String wellsInspectionList(Model model, @RequestParam(required = false) Integer state) {
        //model.addAttribute("documents", documentService.getDocumentsList());
        model.addAttribute("documents", (state != null ? getDocsByState(state, 1001)
                                                            : documentService.getDocumentsList().stream()
                .filter(documentProjection -> documentProjection.getDocType() == 1001)
                .collect(Collectors.toList())));
        return "reg-inspections/wells-inspection-list";
    }


    //Родники
    @RequestMapping(value = "/registration/springs-inspection/{id}")
    public ModelAndView editSpringRegCard(@PathVariable("id") Integer id) {
        Document document = documentService.getDocument(id);
        return editSpringRegCard(document);
    }

    private ModelAndView editSpringRegCard(Document document) {
        ModelAndView result = new ModelAndView("reg-inspections/springs-inspection");
        result.addObject("document", document);
        result.addObject("employees", documentService.getAllEmployees());
        result.addObject("organizationSource", documentService.getAllOrganizationSource());
        result.addObject("status", documentService.getAllRegStatus());
        result.addObject("secrecy", documentService.getAllSecrecy());
        return result;
    }

    @RequestMapping(value = "/registration/springs-inspection/submit", method = RequestMethod.POST)
    public String submitSpringsInspection(
            @RequestParam(value = "file", required = false) MultipartFile[] files,
            @ModelAttribute DocumentFullProjection document) throws IOException {
        documentService.save(document, files);
        //return "redirect:/fgi/descriptions/"; //DEV
        return "redirect:/springs-inspection-list/"; //PROD
    }

    @RequestMapping("/springs-inspection-list")
    public String springsInspectionList(Model model, @RequestParam(required = false) Integer state) {
        //model.addAttribute("documents", documentService.getDocumentsList());
        model.addAttribute("documents", (state != null ? getDocsByState(state, 1002)
                                                            : documentService.getDocumentsList().stream()
                .filter(documentProjection -> documentProjection.getDocType() == 1002)
                .collect(Collectors.toList())));
        return "reg-inspections/springs-inspection-list";
    }

    public List<DocumentProjection> getDocsByState(Integer state, int docType) {
        List<DocumentProjection> docs = documentService.getDocumentsList().stream()
                .filter(documentProjection -> documentProjection.getDocType() == docType)
                .collect(Collectors.toList());
        if (state == -100 || state == null) {
            return docs;
        }
        return docs.stream().filter(documentProjection -> documentProjection.getRegStatusId() == state)
                .collect(Collectors.toList());
    }

    //Дроувеллсы
    @RequestMapping(value = "/registration/drawwells-inspection/{id}")
    public ModelAndView editDrawWellsCard(@PathVariable("id") Integer id) {
        Document document = documentService.getDocument(id);
        return editDrawWellsCard(document);
    }

    private ModelAndView editDrawWellsCard(Document document) {
        ModelAndView result = new ModelAndView("reg-inspections/drawwells-inspection");
        result.addObject("document", document);
        result.addObject("employees", documentService.getAllEmployees());
        result.addObject("organizationSource", documentService.getAllOrganizationSource());
        result.addObject("status", documentService.getAllRegStatus());
        result.addObject("secrecy", documentService.getAllSecrecy());
        return result;
    }

    @RequestMapping(value = "/registration/drawwells-inspecition/submit", method = RequestMethod.POST)
    public String submitDrawWellsInspection(
            @RequestParam(value = "file", required = false) MultipartFile[] files,
            @ModelAttribute DocumentFullProjection document) throws IOException {
        documentService.save(document, files);
        //return "redirect:/fgi/descriptions/"; //DEV
        return "redirect:/drawwells-inspection-list/"; //PROD
    }

    @RequestMapping("/drawwells-inspection-list")
    public String drawWellsInspectionList(Model model, @RequestParam(required = false) Integer state) {
        //model.addAttribute("documents", documentService.getDocumentsList());
        model.addAttribute("documents", (state != null ? getDocsByState(state, 1003)
                : documentService.getDocumentsList().stream()
                .filter(documentProjection -> documentProjection.getDocType() == 1003)
                .collect(Collectors.toList())));
        return "reg-inspections/drawwells-inspection-list";
    }

}