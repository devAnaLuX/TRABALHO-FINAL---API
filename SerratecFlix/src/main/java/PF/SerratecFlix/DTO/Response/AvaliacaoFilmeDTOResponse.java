package PF.SerratecFlix.DTO.Response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import PF.SerratecFlix.Domain.AvaliacaoFilme;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder
({ "id", "nota", "comentario", "dataAvaliacao", "usuarioId", "filmeId" })
public class AvaliacaoFilmeDTOResponse {
	
	private UUID id;
	private Double nota;
	private String comentario;
	private LocalDateTime dataAvaliacao;
	private UUID usuarioId;
	private UUID filmeId;
	
	public AvaliacaoFilmeDTOResponse(AvaliacaoFilme avaliacao) {
		this.id = avaliacao.getId();
		this.nota = avaliacao.getNota();
		this.comentario = avaliacao.getComentario();
		this.dataAvaliacao = avaliacao.getDataAvaliacao();
		this.usuarioId = avaliacao.getUsuario().getId();
		this.filmeId = avaliacao.getFilme().getId();
	}
}
