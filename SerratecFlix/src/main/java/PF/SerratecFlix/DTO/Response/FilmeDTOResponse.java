package PF.SerratecFlix.DTO.Response;

import java.util.UUID;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import PF.SerratecFlix.Domain.Categoria;
import PF.SerratecFlix.Domain.Filme;
import PF.SerratecFlix.Domain.ListaFavoritos;
import PF.SerratecFlix.Enumerated.ClassificacaoIndicativa;
import PF.SerratecFlix.Domain.AvaliacaoFilme;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@JsonPropertyOrder({
        "categorias",
        "titulo",
        "notaMedia",
        "descricao",
        "duracao",
        "dataLancamento",
        "classificacaoIndicativa",
        "avaliacoes",
        "listasFavoritos",
        "id"
})
@Getter
@Setter
@NoArgsConstructor
public class FilmeDTOResponse {

    private UUID id;
    private String titulo;
    private String descricao;
    private Integer duracao;
    private LocalDate dataLancamento;
    private ClassificacaoIndicativa classificacaoIndicativa;
    private Double notaMedia;
    private List<Double> avaliacoes = new ArrayList<>();
    private Set<String> categorias = new HashSet<>();
    private List<String> listasFavoritos = new ArrayList<>();

    public FilmeDTOResponse(Filme f) {
        this.id = f.getId();
        this.titulo = f.getTitulo();
        this.descricao = f.getDescricao();
        this.duracao = f.getDuracao();
        this.dataLancamento = f.getDataLancamento();
        this.classificacaoIndicativa = f.getClassificacaoIndicativa();
        this.notaMedia = f.getNotaMedia();
        this.avaliacoes = f.getAvaliacoes()
                .stream()
                .map(AvaliacaoFilme::getNota)
                .toList();
        this.categorias = f.getCategorias()
                .stream()
                .map(Categoria::getNome)
                .collect(Collectors.toCollection(HashSet::new));
        this.listasFavoritos = f.getListasFavoritos()
                .stream()
                .map(ListaFavoritos::getNomeLista)
                .toList();
    }
}

