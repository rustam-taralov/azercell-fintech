package az.azercellfintech.emailservice.service;

import az.azercellfintech.emailservice.model.dto.MailContentDto;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(MailContentDto mailContentDto){

        var newMessage=new SimpleMailMessage();

        newMessage.setFrom("ailContentDto.()");
        newMessage.setSubject(mailContentDto.subject());
        newMessage.setTo(mailContentDto.toEmail());
        newMessage.setText(mailContentDto.message());

        mailSender.send(newMessage);
    }
}
