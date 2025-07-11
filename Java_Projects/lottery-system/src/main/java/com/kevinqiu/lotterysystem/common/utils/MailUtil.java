package com.kevinqiu.lotterysystem.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MailUtil {

    @Value(value = "${spring.mail.username}")
    private String from;
    @Autowired
    private JavaMailSender mailSender;

    public Boolean sendSimpleMail(String to, String subject, String context) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(context);
        try{
            mailSender.send(simpleMailMessage);
            return true;
        } catch (Exception e) {
            log.error("向 {} 发送邮箱失败！e: ",to, e);
            return false;
        }
    }

}
