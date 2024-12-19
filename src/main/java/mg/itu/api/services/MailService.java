package mg.itu.api.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public boolean sendEmail(String to, String subject, String body, boolean isHtml) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, isHtml);

            mailSender.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sendMailValidator(String to, String code) throws FileNotFoundException,IOException{
        String htmlDatas=Files.readString(Path.of("verifierMail.html"));
        htmlDatas.replaceAll("{{code}}", code);
        sendEmail(to, "Code de validation de l'adresse mail", htmlDatas, true);
        return true;
    }

    public boolean sendAuthValidator(String to, String code) throws FileNotFoundException,IOException{
        String htmlDatas=Files.readString(Path.of("verifierLogin.html"));
        htmlDatas.replaceAll("{{code}}", code);
        sendEmail(to, "Code de validation de l'adresse mail", htmlDatas, true);
        return true;
    }
}
