package PF.SerratecFlix.DTO.Request;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SerieDTORequest {
	
	@NotBlank
    
	@NotBlank(message = "Título é obrigatório")
    @Size(min = 3, max = 100, message = "Título deve ter entre 3 e 100 caracteres")
    @Schema(description = "Título da série", example = "The Office")
    private String titulo;

	@NotBlank(message = "Descrição da série é obrigatório")
	@Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
    @Schema(description = "Descrição da série")
    private String descricao;

	@NotNull(message = "O número de temporadas é obrigatório")
    @Min(value = 1, message = "A série deve ter ao menos 1 temporada")
    private Integer temporadas;

    @NotNull(message = "O número de episódios é obrigatório")
    @Min(value = 1, message = "A série deve ter ao menos 1 episódio")
    private Integer episodios;

    @Schema(description = "Data de lançamento", example = "2026-06-01")
    private LocalDate dataLancamento;

    @Positive(message = "Nota deve ser positivo")
    @Schema(description = "Nota para a série de 0 a 10", example = "9")
    private Double notaMedia;
    
    private Set<UUID> categorias = new HashSet<>();
    
}
