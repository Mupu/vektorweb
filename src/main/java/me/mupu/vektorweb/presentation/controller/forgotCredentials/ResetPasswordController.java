package me.mupu.vektorweb.presentation.controller.forgotCredentials;

import me.mupu.vektorweb.HashPasswordEncoder;
import me.mupu.vektorweb.persistence.dao.User;
import me.mupu.vektorweb.presentation.form.ResetPasswordForm;
import me.mupu.vektorweb.CustomUser;
import me.mupu.vektorweb.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/forgotCredentials")
public class ResetPasswordController {

    @Autowired
    private CustomUserService userService;

    @Autowired
    private HashPasswordEncoder passwordEncoder;

    @GetMapping("/resetPassword")
    public ModelAndView getResetPassword(@RequestParam(required = false) String token,
                                         ResetPasswordForm resetPasswordForm,
                                         Authentication authentication) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("everyone/resetPassword");

        if (token != null && !token.equals(""))
            resetPasswordForm.setResetToken(token);

        mv.addObject("resetPasswordForm", resetPasswordForm);


        if (authentication != null)
            mv.addObject("user", ((CustomUser) authentication.getPrincipal()).getUser());

        User user = userService.findUserByResetPasswordToken(resetPasswordForm.getResetToken());
        if (user != null) {
            mv.addObject("resetToken", user.getResetPasswordToken());
        } else {
            mv.addObject("invalidToken", "");
        }

        return mv;
    }

    @PostMapping("/resetPassword")
    public ModelAndView resetPassword(@Valid ResetPasswordForm resetPasswordForm,
                                      BindingResult bindingResult,
                                      Authentication authentication) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("everyone/resetPassword");

        if (authentication != null)
            mv.addObject("user", ((CustomUser) authentication.getPrincipal()).getUser());

        // check if passwords are the same
        if (!resetPasswordForm.getPassword().equals(resetPasswordForm.getRepeatPassword()))
            bindingResult.rejectValue("repeatPassword", "repeatPassword", "Passwords do not match");

        if (!bindingResult.hasErrors()) {
            // check if token exists
            User user = userService.findUserByResetPasswordToken(resetPasswordForm.getResetToken());
            if (user != null) {
                try {

                    String newPassword = passwordEncoder.encode(resetPasswordForm.getPassword());
                    //todo implement
//                    user.setPassword(newPassword);
//                    user.setResetpasswordtoken("");
//                    user.store();
//
//                    mssqlContext.update(PLAYERACCOUNT).set(PLAYERACCOUNT.PASSWORD, newPassword)
//                            .where(PLAYERACCOUNT.ACCOUNT_ID.eq(user.getUsername())).execute();

                    mv.addObject("successMessage", "");
                } catch (Exception e) {
                    e.printStackTrace();
                    mv.addObject("resetToken", user.getResetPasswordToken());
                }

            } else {
                mv.addObject("invalidToken", "");
            }
        }

        return mv;
    }

}

