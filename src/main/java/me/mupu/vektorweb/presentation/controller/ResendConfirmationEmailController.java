package me.mupu.vektorweb.presentation.controller;

import me.mupu.vektorweb.persistence.dao.User;
import me.mupu.vektorweb.CustomUser;
import me.mupu.vektorweb.service.CustomUserService;
import me.mupu.vektorweb.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ResendConfirmationEmailController {

//    @Autowired
//    DSLContext dslContext;

    @Autowired
    CustomUserService userService;

    @Autowired
    RegistrationService registrationService;

    @GetMapping("/resendConfirmationEmail")
    public ModelAndView getConfirmation(Authentication authentication) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("everyone/resendConfirmationEmail");

        if (authentication != null)
            mv.addObject("user", ((CustomUser) authentication.getPrincipal()).getUser());

        return mv;
    }

    @PostMapping("/resendConfirmationEmail")
    public ModelAndView sendEmail(@RequestParam(defaultValue = "") String email,
                                  HttpServletRequest request,
                                  Authentication authentication) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("everyone/resendConfirmationEmail");

        if (authentication != null)
            mv.addObject("user", ((CustomUser) authentication.getPrincipal()).getUser());

        User user = userService.findUserByEmail(email);
        if (user != null) {
            if (registrationService.sendConfirmationMail(user, request)) {
                mv.addObject("success", "");
                mv.addObject("provider", user.getEmail().split("@")[1]);
                return mv;
            }
        }

        mv.addObject("error", "");

        return mv;
    }

}
