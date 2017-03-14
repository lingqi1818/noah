package com.fangcloud.noah.service.service;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

/**
 * Created by chenke on 17-1-9.
 */
@Service
public class MailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    public void sendMailWithVelocityTemplate(String mailSubject, String[] to, String templateLocation, Map<String, Object> velocityContext) {

        MimeMessage mailMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
            messageHelper.setFrom("risk@helijia.com");
            messageHelper.setTo(to);
            messageHelper.setSubject(mailSubject);
            String mailText = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, "utf-8", velocityContext);
            messageHelper.setText(mailText, true);
            javaMailSender.send(mailMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

}
