package PF.SerratecFlix.DTO.Response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoSerieDTOResponse {
	
	private UUID id;
    private Double nota;
    private String comentario;
    private LocalDateTime dataAvaliacao;
    private UUID usuarioId;
    private String nomeUsuario;
    private UUID serieId;
    private String tituloSerie;
 
    public AvaliacaoSerieDTOResponse() {
    }
 
}
