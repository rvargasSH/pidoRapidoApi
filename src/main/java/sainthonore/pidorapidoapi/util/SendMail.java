package sainthonore.pidorapidoapi.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class SendMail {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void singleAddress(String address, String subject, String messagebody) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();

        message.setSubject(subject);
        MimeMessageHelper helper;
        helper = new MimeMessageHelper(message, true);
        helper.setFrom(fromEmail);
        helper.setTo(address);
        helper.setText(messagebody, true);
        javaMailSender.send(message);

    }
}