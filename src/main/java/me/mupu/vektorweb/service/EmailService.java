package me.mupu.vektorweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service("emailService")
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Async
//	public boolean prepareAndSend(String recipient, String subject, String template, Context context) {
    public void prepareAndSend(String recipient, String subject, String template, Context context) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("noreply@mupu.me");
            messageHelper.setTo(recipient);
            messageHelper.setSubject(subject);

            String content = buildEmail(template, context);
            messageHelper.setText(content, true);
        };
        try {
            mailSender.send(messagePreparator);
//			return true;
        } catch (MailException e) {
            // runtime exception; compiler will not force you to handle it
        }
//		return false;
    }

    private String buildEmail(String template, Context context) {
        return templateEngine.process(template, context);
    }
}
