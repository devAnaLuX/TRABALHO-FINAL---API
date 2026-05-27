package PF.SerratecFlix.DTO.Response;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import PF.SerratecFlix.Domain.AvaliacaoSerie;
import PF.SerratecFlix.Domain.Categoria;
import PF.SerratecFlix.Domain.ListaFavoritos;
import PF.SerratecFlix.Domain.Serie;
import PF.SerratecFlix.Enumerated.ClassificacaoIndicativa;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonPropertyOrder({
        "categorias",
        "titulo",
        "notaMedia",
        "descricao",
        "temporadas",
        "episodios",
        "dataLancamento",
        "classificacaoIndicativa",
        "avaliacoes",
        "listasFavoritos",
        "id"
})
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
    private List<Double> avaliacoes = new ArrayList<>();
    private Set<String> categorias = new HashSet<>();
    private List<String> listasFavoritos = new ArrayList<>();
    private ClassificacaoIndicativa classificacaoIndicativa;

    public SerieDTOResponse(Serie s) {
        this.id = s.getId();
        this.titulo = s.getTitulo();
        this.descricao = s.getDescricao();
        this.temporadas = s.getTemporadas();
        this.episodios = s.getEpisodios();
        this.dataLancamento = s.getDataLancamento();
        this.notaMedia = s.getNotaMedia();
        this.classificacaoIndicativa = s.getClassificacaoIndicativa();
        this.avaliacoes = s.getAvaliacoes()
                .stream()
                .map(AvaliacaoSerie::getNota)
                .toList();
        this.categorias = s.getCategorias()
                .stream()
                .map(Categoria::getNome)
                .collect(Collectors.toCollection(HashSet::new));
        this.listasFavoritos = s.getListasFavoritos()
                .stream()
                .map(ListaFavoritos::getNomeLista)
                .toList();
    }
}