package ru.itis.javalab.util;

import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

@Profile("master")
@Component
public class EmailUtilImpl implements EmailUtil {

    private final JavaMailSender javaMailSender;

    private final ExecutorService executorService;

    public EmailUtilImpl(JavaMailSender javaMailSender, ExecutorService executorService) {
        this.javaMailSender = javaMailSender;
        this.executorService = executorService;
    }

    @Override
    public void sendMail(String to, String subject, String from, String text) {
        executorService.submit(() -> javaMailSender.send(mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(from);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(text, true);
        }));
    }
}
