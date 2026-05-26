package PF.SerratecFlix.DTO.Response;
 
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTOResponse {
 
    private String token;
    private String email;
    private String nome;
}