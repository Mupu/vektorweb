package me.mupu.vektorweb.controller;

import me.mupu.vektorweb.dao.User;
import me.mupu.vektorweb.model.CustomUser;
import me.mupu.vektorweb.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ConfirmationController {

    @Autowired
    private CustomUserService userDetailsService;

    // Activate user account
    @GetMapping("/confirmation")
    public ModelAndView getConfirmation(@RequestParam(required = false) String token, Authentication authentication) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("everyone/confirmation");

        User user = userDetailsService.findUserByConfimationToken(token);

        if (user != null && activateUserAccount(user)) {
            mv.addObject("success", "");
        } else
            mv.addObject("invalidToken", "");

        if (authentication != null)
            mv.addObject("user", ((CustomUser) authentication.getPrincipal()).getUser());

        return mv;
    }

    private boolean activateUserAccount(User user) {
//        user.setConfirmationtoken("");
//        return user.store() == 1;
        return true; // todo implement
    }

}

