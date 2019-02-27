package me.mupu.vektorweb.presentation.controller.forgotCredentials;

import me.mupu.vektorweb.persistence.dao.User;
import me.mupu.vektorweb.CustomUser;
import me.mupu.vektorweb.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/forgotCredentials")
public class ForgotUsernameController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/username")
    public ModelAndView getUsername(Authentication authentication) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("everyone/forgotUsername");

        if (authentication != null)
            mv.addObject("user", ((CustomUser) authentication.getPrincipal()).getUser());
        return mv;
    }

    @PostMapping("/username")
    public ModelAndView sendUsernameMail(@RequestParam(defaultValue = "") String email,
                                         HttpServletRequest request,
                                         Authentication authentication) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("everyone/forgotUsername");

        if (authentication != null)
            mv.addObject("user", ((CustomUser) authentication.getPrincipal()).getUser());

        if (!email.equals("")) {
            //todo UserRecord user = dslContext.selectFrom(USER).where(USER.EMAIL.eq(email)).fetchOne();
            User user = null;
            if (user != null) {
                if (sendUsernameMail(user, request)) {
                    mv.addObject("success", "");
                    mv.addObject("provider", user.getEmail().split("@")[1]);
                    return mv;
                }
            }
        }

        mv.addObject("error", "");

        return mv;
    }

    private boolean sendUsernameMail(User user, HttpServletRequest request) {
        if (user.getEmail().equals(""))
            return false;

        String appUrl = request.getScheme() + "://" + request.getServerName();

        Context context = new Context();
        context.setVariable("appUrl", appUrl);
        context.setVariable("username", user.getUsername());
        emailService.prepareAndSend(user.getEmail(),
                "Forgot Username",
                "emails/forgotUsernameEmail",
                context);
        return true;
    }

}
