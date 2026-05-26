package PF.SerratecFlix.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTORequest {
	
	@NotBlank(message = "O nome da categoria é obrigatório.")
	@Size(max = 50, message = "O nome da categoria não pode passar de 50 caracteres")
    private String nome;
    
	@Size(max = 250, message = "A descrição não pode passar de 250 caracteres")
    private String descricao;
}
