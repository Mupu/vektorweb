package me.mupu.vektorweb.service;

import me.mupu.vektorweb.HashPasswordEncoder;
import me.mupu.vektorweb.dao.User;
import me.mupu.vektorweb.form.RegistrationForm;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@Service("registrationService")
public class RegistrationService {

    @Autowired
    private DSLContext dslContext;

    @Autowired
    private HashPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public User registerUser(@Valid RegistrationForm registrationForm, HttpServletRequest request) {
        User user = null;
        try {
            // todo add user to database


            sendConfirmationMail(user, request);
        } catch (Exception e) {
            user = null;
        }
        return user;
    }

    public boolean sendConfirmationMail(User user, HttpServletRequest request) {
        if (user.getEmail().equals("") || user.getConfirmationToken() == null || user.getConfirmationToken().equals(""))
            return false;

        String appUrl = request.getScheme() + "://" + request.getServerName();

        Context context = new Context();
        context.setVariable("appUrl", appUrl);
        context.setVariable("confirmationToken", user.getConfirmationToken());
        emailService.prepareAndSend(user.getEmail(),
                "Registration Confirmation",
                "emails/confirmationEmail",
                context);
        return true;
    }
//todo implement
}
