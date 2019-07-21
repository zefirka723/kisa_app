package mosecom.controller;

import mosecom.dto.auth.DbUserProjection;
import mosecom.model.auth.User;
import mosecom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;


/*
    Временно: для регистрации новых пользователей
 */

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String registrationList(Model model) {
        model.addAttribute("users", userService.getUsersList());
        model.addAttribute("currentUserId", userService.getCurrentUserId()); // TODO переделать на роли
        return "user/user-list";
    }

    @RequestMapping(value = "/user/{id}")
    public ModelAndView editCard(@PathVariable("id") Integer id) {
        ModelAndView result = new ModelAndView("user/user-card");
        User user = userService.getUser(id);
        result.addObject("currentUserId", userService.getCurrentUserId()); // для тестов
        result.addObject("user", user);
        return result;
    }

    @RequestMapping(value = "/user/submit", method = RequestMethod.POST)
    public String wellDocCardSubmit(@ModelAttribute DbUserProjection user,
                                    @RequestParam(required = false) String newpass,
                                    @RequestParam(required = false) String oldpass) throws IOException {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (oldpass != null &&
                !passwordEncoder.matches(oldpass, userService.getUser(user.getId()).getPassword())) {
            return "redirect:/user/" + user.getId(); // надо показать на фронте, что не так
        }
        userService.save(user, oldpass, newpass);
        return "redirect:/users";
    }

    @RequestMapping(value = "/user/dropPass", method = RequestMethod.POST)
    public String dropPass(@ModelAttribute DbUserProjection user) {
        userService.dropPassword(user);
        return "redirect:/users";
    }
}