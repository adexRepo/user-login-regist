package adexRepo.userloginregist.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
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
    public void sendSimple(String toEmail,String subject, String body) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom("your@gmail.com");
            msg.setTo(toEmail);
            msg.setSubject(subject);
            msg.setText("Tralalala");
    
            mailSender.send(msg);
            
            LOGGER.info("Email Successfully!");
        } catch (Exception e) {
            LOGGER.error("Email Errorfully");
            throw new IllegalStateException("Error Guys!",e);
        }
    }

    @Override
    @Async
    public void sendMimeMsg(String to,String email) {
        try {
            MimeMessage mimeMsg = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMsg,"utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm Your Email");
            helper.setFrom("aeitiao@gmail.com");
            mailSender.send(mimeMsg);
        } catch (Exception e) {
            LOGGER.error("Email Errorfully");
            throw new IllegalStateException("Error Guys!",e);
        }
    }
}
