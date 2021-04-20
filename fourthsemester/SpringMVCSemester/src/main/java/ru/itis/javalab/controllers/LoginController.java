package ru.itis.javalab.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.javalab.dto.FormDto;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @GetMapping(value = "/login")
    public String getPage(Model model, HttpSession session) {
        model.addAttribute("logInForm", new FormDto());
        session.setAttribute("Authenticated", "false");
        return "login";
    }

}
