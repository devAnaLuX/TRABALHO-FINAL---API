package PF.SerratecFlix.DTO.Request;
 
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTORequest {
 
    @Email(message = "Email inválido")
    @NotBlank(message = "O email é obrigatório")
    private String email;
 
    @NotBlank(message = "A senha é obrigatória")
    private String senha;
}
 