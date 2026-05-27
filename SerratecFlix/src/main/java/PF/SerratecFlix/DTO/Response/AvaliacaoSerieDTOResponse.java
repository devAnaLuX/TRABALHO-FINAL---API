package PF.SerratecFlix.DTO.Response;

import java.time.LocalDateTime;
import java.util.UUID;

import PF.SerratecFlix.Domain.AvaliacaoSerie;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@JsonPropertyOrder
		({ "id", "nota", "comentario", "dataAvaliacao", "usuario", "serie" })
public class AvaliacaoSerieDTOResponse {

	private UUID id;
    private Double nota;
    private String comentario;
    private LocalDateTime dataAvaliacao;
    private String usuario;
    private String serie;

	public AvaliacaoSerieDTOResponse(AvaliacaoSerie a) {
		super();
		this.id = a.getId();
		this.nota = a.getNota();
		this.comentario = a.getComentario();
		this.dataAvaliacao = a.getDataAvaliacao();
		this.usuario = a.getUsuario().getNome();
		this.serie = a.getSerie().getTitulo();
	}
	
	
    
}
