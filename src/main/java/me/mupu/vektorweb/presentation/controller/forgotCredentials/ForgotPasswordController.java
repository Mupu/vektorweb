package me.mupu.vektorweb.presentation.controller.forgotCredentials;

import me.mupu.vektorweb.HashPasswordEncoder;
import me.mupu.vektorweb.persistence.dao.User;
import me.mupu.vektorweb.CustomUser;
import me.mupu.vektorweb.service.CustomUserService;
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
public class ForgotPasswordController {

//    @Autowired
//    DSLContext dslContext;

    @Autowired
    EmailService emailService;

    @Autowired
    CustomUserService userService;

    @Autowired
    HashPasswordEncoder passwordEncoder;

    @GetMapping("/password")
    public ModelAndView getPassword(Authentication authentication) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("everyone/forgotPassword");

        if (authentication != null)
            mv.addObject("user", ((CustomUser) authentication.getPrincipal()).getUser());

        return mv;
    }

    @PostMapping("/password")
    public ModelAndView sendPasswordResetMail(@RequestParam(defaultValue = "") String email,
                                              HttpServletRequest request,
                                              Authentication authentication) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("everyone/forgotPassword");

        if (authentication != null)
            mv.addObject("user", ((CustomUser) authentication.getPrincipal()).getUser());

        User user = userService.findUserByEmail(email);
        if (user != null) {

            if (user.getResetPasswordToken().equals(""))
                // todo user.setResetpasswordtoken(UUID.randomUUID().toString());

            //
            if (sendPasswordResetMail(user, request)) {
                // todo safe user only if email has been sent
                //user.store();

                mv.addObject("success", "");
                mv.addObject("provider", user.getEmail().split("@")[1]);
                return mv;
            }
        }

        mv.addObject("error", "");

        return mv;
    }

    private boolean sendPasswordResetMail(User user, HttpServletRequest request) {
        if (user.getEmail().equals(""))
            return false;

        String appUrl = request.getScheme() + "://" + request.getServerName();

        Context context = new Context();
        context.setVariable("appUrl", appUrl);
        context.setVariable("username", user.getUsername());
        context.setVariable("resetPasswordToken", user.getResetPasswordToken());
        emailService.prepareAndSend(user.getEmail(),
                "Reset Password",
                "emails/resetPasswordEmail",
                context);
        return true;
    }


}

