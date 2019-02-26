package me.mupu.vektorweb.controller;

import me.mupu.vektorweb.model.CustomUser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccessDeniedController {

    @GetMapping("/accessDenied")
    public ModelAndView getAccessDenied(Authentication authentication) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("everyone/accessDenied");

        if (authentication != null)
            mv.addObject("user", ((CustomUser) authentication.getPrincipal()).getUser());

        return mv;
    }

}

