package mosecom.controller;

import mosecom.dto.WellFullProjection;
import mosecom.model.Attachment;
import mosecom.model.Well;
import mosecom.service.welldoc.WellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

//import org.springframework.boot.context.config.ResourceNotFoundException;


@Controller
//@RequestMapping("/fgi") //DEV
@RequestMapping("/") //PROD
public class MainController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private WellService service;

    // Просмотр аттачментов в документах
    @RequestMapping(value = "/file/{id}")
    public ResponseEntity<FileSystemResource> downloadFile(@PathVariable("id") int id) throws IOException {
        Attachment doc = service.getWellDocument(id);
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


    // Страница входа
    @RequestMapping("/login")
    public String getLogin(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "logout", required = false) String logout,
                           Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "login";
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
        public String logout(HttpServletRequest request, HttpServletResponse response) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null){
                new SecurityContextLogoutHandler().logout(request, response, auth);
            }
            return "redirect:/login?logout";
    }
}