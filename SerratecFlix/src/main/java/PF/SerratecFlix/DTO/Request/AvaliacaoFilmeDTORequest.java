package PF.SerratecFlix.DTO.Request;

import java.util.UUID;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoFilmeDTORequest {
	@NotNull(message = "A nota é obrigatória.")
	@DecimalMin(value = "0.0", message = "A nota mínima é 0.0")
	@DecimalMax(value = "10.0", message = "A nota máxima é 10.0")
	private Double nota;

	@NotBlank(message = "O comentário não pode estar em branco.")
	@Size(max = 400, message = "O comentário não pode passar de 400 caracteres.")
	private String comentario;

	@NotNull(message = "O ID do usuário é obrigatório.")
	private UUID usuarioId;

	@NotNull(message = "O ID do filme é obrigatório.")
	private UUID filmeId;

	
}
