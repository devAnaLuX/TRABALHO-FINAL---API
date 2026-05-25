package PF.SerratecFlix.DTO.Response;

import java.time.LocalDate;
import java.util.UUID;

import PF.SerratecFlix.Domain.Serie;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SerieDTOResponse {

    private UUID id;
    private String titulo;
    private String descricao;
    private Integer temporadas;
    private Integer episodios;
    private LocalDate dataLancamento;
    private Double notaMedia;

    public SerieDTOResponse(Serie s) {
        this.id = s.getId();
        this.titulo = s.getTitulo();
        this.descricao = s.getDescricao();
        this.temporadas = s.getTemporadas();
        this.episodios = s.getEpisodios();
        this.dataLancamento = s.getDataLancamento();
        this.notaMedia = s.getNotaMedia();
    }
}