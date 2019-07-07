package mosecom.controller;

import mosecom.dao.auth.DbUserRepository;
import mosecom.dto.auth.DbUserProjection;
import mosecom.model.auth.User;
import mosecom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Map;


/*
    Временно: для регистрации новых пользователей
 */

@Controller
public class UserInfoController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/pass/{id}")
    public ModelAndView editCard(@PathVariable("id") Integer id) {
        User user = userService.getUser(id);
        return editCard(user);
    }

    private ModelAndView editCard(User user) {
        ModelAndView result = new ModelAndView("pass");
        result.addObject("user", user);
        return result;
    }


    @PostMapping(value = "/pass")
    public String submitCard(@ModelAttribute DbUserProjection user) throws IOException {
        System.out.println("hello, world! or smthn' like that, u know..");
        userService.save(user);
        return "redirect:/reccards/"; //PROD
    }
//
//    // Сабмит формы
//    @RequestMapping(value = "pass/submit", method = RequestMethod.POST)
//    public String submitUser(@ModelAttribute DbUserProjection user) throws IOException {
//        userService.save(user);
//        return "redirect:/reccards/";
//    }










}
