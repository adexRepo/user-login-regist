package adexRepo.userloginregist.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender{

    private final static Logger LOGGER = LoggerFactory
            .getLogger(EmailService.class);

    @Autowired
    private final JavaMailSender mailSender;

    @Override
    @Async
    public void send(String toEmail,String subject, String body) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom("your@gmail.com");
            msg.setTo(toEmail);
            msg.setSubject(subject);
            msg.setText(body);
    
            mailSender.send(msg);
            
            LOGGER.info("Email Successfully!");
        } catch (Exception e) {
            LOGGER.error("Email Errorfully");
            throw new IllegalStateException("Error Guys!",e);
        }
    }
}