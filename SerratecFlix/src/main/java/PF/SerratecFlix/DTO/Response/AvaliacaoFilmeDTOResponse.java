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
@JsonPropertyOrder
({ "id", "nota", "comentario", "dataAvaliacao", "usuario", "filme" })
public class AvaliacaoFilmeDTOResponse {
	
	private UUID id;
	private Double nota;
	private String comentario;
	private LocalDateTime dataAvaliacao;
	private String usuario;
	private String filme;
	
	public AvaliacaoFilmeDTOResponse(AvaliacaoFilme avaliacao) {
		this.id = avaliacao.getId();
		this.nota = avaliacao.getNota();
		this.comentario = avaliacao.getComentario();
		this.dataAvaliacao = avaliacao.getDataAvaliacao();
		this.usuario = avaliacao.getUsuario().getNome();
		this.filme = avaliacao.getFilme().getTitulo();
	}
}
