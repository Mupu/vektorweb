package me.mupu.vektorweb.controller;

import me.mupu.vektorweb.model.CustomUser;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.Base64;


@Controller
public class PublicController {

    @RequestMapping("/login")
    public ModelAndView login(Authentication authentication) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("everyone/login");

        if (authentication != null)
            mv.addObject("user", ((CustomUser) authentication.getPrincipal()).getUser());

        return mv;
    }

    @RequestMapping(value = {"/", "/home"})
    public ModelAndView home(Authentication authentication) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("everyone/home");

        mv.addObject("b64", Base64.getEncoder());

        if (authentication != null)
            mv.addObject("user", ((CustomUser) authentication.getPrincipal()).getUser());

        return mv;
    }

}
