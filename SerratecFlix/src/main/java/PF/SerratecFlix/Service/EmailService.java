package PF.SerratecFlix.Service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmail(String destinatario, String assunto, String corpo) throws Exception {
        MimeMessage mensagem = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensagem, true, "UTF-8");

        helper.setFrom("hello@demomailtrap.co", "SerratecFlix");
        helper.setTo(destinatario);
        helper.setSubject(assunto);
        helper.setText(corpo, true);

        mailSender.send(mensagem);
    }
}
