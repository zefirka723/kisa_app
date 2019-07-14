package mosecom.controller;

import mosecom.model.auth.User;
import mosecom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


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












}
