package PF.SerratecFlix.Controller;

import PF.SerratecFlix.DTO.Request.EmailDTORequest;
import PF.SerratecFlix.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/enviar")
    public ResponseEntity<String> enviar(@RequestBody EmailDTORequest request) {
        try {
            emailService.enviarEmail(
                    request.getDestinatario(),
                    request.getAssunto(),
                    request.getCorpo()
            );
            return ResponseEntity.ok("E-mail enviado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao enviar e-mail: " + e.getMessage());
        }
    }
}
