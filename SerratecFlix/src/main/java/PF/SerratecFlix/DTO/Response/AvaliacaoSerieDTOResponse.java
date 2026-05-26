package PF.SerratecFlix.DTO.Response;

import java.time.LocalDateTime;
import java.util.UUID;

import PF.SerratecFlix.Domain.AvaliacaoSerie;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class AvaliacaoSerieDTOResponse {
	
	
	private UUID id;
    private Double nota;
    private String comentario;
    private LocalDateTime dataAvaliacao;
    private UUID usuarioId;
    private String nomeUsuario;
    private UUID serieId;
    private String tituloSerie;
    

	public AvaliacaoSerieDTOResponse(AvaliacaoSerie a) {
		super();
		this.id = a.getId();
		this.nota = a.getNota();
		this.comentario = a.getComentario();
		this.dataAvaliacao = a.getDataAvaliacao();
		this.usuarioId = a.getUsuario().getId();
		this.nomeUsuario = a.getComentario();
		this.serieId = a.getSerie().getId();
		this.tituloSerie = a.getSerie().getTitulo();
	}
	
	
    
}
