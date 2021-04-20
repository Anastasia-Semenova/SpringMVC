package ru.itis.javalab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itis.javalab.dto.RegistrationFormDto;
import ru.itis.javalab.services.SignUpService;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;

@Controller
public class RegistrationController {
    @Autowired
    private SignUpService signUpService;

    public RegistrationController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @GetMapping("/registration")
    public String getSignUpPage(Model model, HttpSession session) {
        model.addAttribute("registrationFormDto", new RegistrationFormDto());
        session.setAttribute("Authenticated", "false");
        return "registration";
    }

    @PostMapping("/registration")
    public String signUp(@Valid RegistrationFormDto form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {

            bindingResult.getAllErrors().stream().anyMatch(error -> {
                if (Objects.requireNonNull(error.getCodes())[0].equals("registrationFormDto.ValidNames")) {
                    model.addAttribute("namesErrorMessage", error.getDefaultMessage());
                }
                if (Objects.requireNonNull(error.getCodes())[0].equals("registrationFormDto.FieldMatch")) {
                    model.addAttribute("passwordMismatchErrorMessage", error.getDefaultMessage());
                }
                return true;
            });

            model.addAttribute("registrationFormDto", form);
            return "registration";
        }
        signUpService.signUp(form);
        return "redirect:/login";
    }
}
