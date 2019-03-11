package me.mupu.vektorweb.presentation.controller;

import me.mupu.vektorweb.CustomUser;
import org.hibernate.Session;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import java.util.Base64;


@Controller
public class PublicController {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private DSLContext context;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

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

//        context.execute("insert into user (confirmation_token, email, reset_password_token, username) values ('yolo', 'yolo', 'yolo', 'yolo')");
//        Session s = getSession();
//        var u = new User();
//        u.setUsername("test");
//        u.setEmail("test");
//        u.setConfirmationToken("test");
//        u.setResetPasswordToken("test");
//        s.persist(u);
//        getSession().beginTransaction().commit();


        if (authentication != null)
            mv.addObject("user", ((CustomUser) authentication.getPrincipal()).getUser());

        return mv;
    }

}
