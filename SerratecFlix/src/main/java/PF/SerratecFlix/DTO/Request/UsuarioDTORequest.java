package PF.SerratecFlix.DTO.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTORequest {

    @NotBlank(message = "O nome do usuário é obrigatório")
    private String nome;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "O email deve ser válido")
    private String email;

    @NotBlank(message = "O nome de usuário é obrigatório")
    private String username;
    
    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 8, message = "A senha deve conter no mínimo 8 caracteres")
    private String senha;
    

}