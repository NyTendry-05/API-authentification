package mg.itu.api.controllers;

import mg.itu.api.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

    @Autowired
    private MailService mailService;

    @GetMapping("/send-mail")
    public ResponseEntity<String> sendMail(
        @RequestParam String to,
        @RequestParam String subject,
        @RequestParam String body
    ) {
        boolean success = mailService.sendEmail(to, subject, body, true);
        if (success) {
            return ResponseEntity.ok("Email sent successfully!");
        } else {
            return ResponseEntity.status(500).body("Failed to send email.");
        }
    }
}
