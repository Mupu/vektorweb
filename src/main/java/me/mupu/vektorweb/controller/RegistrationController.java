package me.mupu.vektorweb.controller;

import me.mupu.vektorweb.form.RegistrationForm;
import me.mupu.vektorweb.model.CustomUser;
import me.mupu.vektorweb.service.CustomUserService;
import me.mupu.vektorweb.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private CustomUserService userService;

    @Autowired
    private RegistrationService registrationService;


    @GetMapping("/registration")
    public ModelAndView registration(RegistrationForm registrationForm, Authentication authentication) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("everyone/registration");
        mv.addObject("registrationForm", registrationForm);

        if (authentication != null)
            mv.addObject("user", ((CustomUser) authentication.getPrincipal()).getUser());

        return mv;
    }

    @PostMapping("/registration")
    public ModelAndView acceptForm(@Valid RegistrationForm registrationForm,
                                   BindingResult bindingResult,
                                   HttpServletRequest request,
                                   Authentication authentication) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("everyone/registration");

        if (!registrationForm.getPassword().equals(registrationForm.getRepeatPassword()))
            bindingResult.rejectValue("repeatPassword", "repeatPassword", "Passwords do not match");

        if (userService.findUserByUsername(registrationForm.getUsername()) != null)
            bindingResult.rejectValue("username", "username", "Username already taken");

        if (userService.findUserByEmail(registrationForm.getEmail()) != null)
            bindingResult.rejectValue("email", "email", "Email already taken");

        if (!bindingResult.hasErrors()) {
            if (registrationService.registerUser(registrationForm, request) != null) {
                mv.addObject("success", "");
                mv.addObject("provider", registrationForm.getEmail().split("@")[1]);
            }
        }

        if (authentication != null)
            mv.addObject("user", ((CustomUser) authentication.getPrincipal()).getUser());

        return mv;
    }

}
