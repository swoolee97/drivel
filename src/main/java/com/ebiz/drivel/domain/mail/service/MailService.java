package com.ebiz.drivel.domain.mail.service;

import com.ebiz.drivel.domain.mail.exception.InvalidMailException;
import com.ebiz.drivel.domain.mail.repository.AuthCodeRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    @Value("${spring.mail.username}")
    private String FROM_MAIL;
    private static final String MAIL_SEND_EXCEPTION_MESSAGE = "메일 전송 중 오류";

    private final JavaMailSender mailSender;
    private final AuthCodeRepository authCodeRepository;


    public void sendAuthenticationCode(String subject, String email) {
        String toMail = email;
        String randomCode = generateRandomCode();
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "utf-8");
            messageHelper.setFrom(FROM_MAIL);
            messageHelper.setTo(toMail);
            messageHelper.setSubject(subject);
            messageHelper.setText(randomCode);

            mailSender.send(message);
            authCodeRepository.save(email, randomCode);
        } catch (MailSendException e) {
            throw new InvalidMailException(MAIL_SEND_EXCEPTION_MESSAGE);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateRandomCode() {
        Random random = new Random();
        int n = random.nextInt(1000000);
        return String.format("%06d", n);
    }


}
