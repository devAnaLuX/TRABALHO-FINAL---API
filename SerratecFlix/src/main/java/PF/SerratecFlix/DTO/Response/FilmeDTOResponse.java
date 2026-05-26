package PF.SerratecFlix.DTO.Response;

import java.util.UUID;
import java.time.LocalDate;
import java.util.*;

import PF.SerratecFlix.Domain.Categoria;
import PF.SerratecFlix.Domain.ListaFavoritos;
import PF.SerratecFlix.Enumerated.ClassificacaoIndicativa;
import PF.SerratecFlix.Domain.AvaliacaoFilme;

public class FilmeDTOResponse {

    private UUID id;
    private String titulo;
    private String descricao;
    private Integer duracao;
    private LocalDate dataLancamento;
    private ClassificacaoIndicativa classificacaoIndicativa;
    private Double notaMedia;
    private List<AvaliacaoFilme> avaliacoes = new ArrayList<>();
    private Set<Categoria> categorias = new HashSet<>();
    private List<ListaFavoritos> listasFavoritos = new ArrayList<>();

    public FilmeDTOResponse() {
    }

    public FilmeDTOResponse(UUID id, String titulo, String descricao, Integer duracao, LocalDate dataLancamento,
                 ClassificacaoIndicativa classificacaoIndicativa, Double notaMedia,
                 List<AvaliacaoFilme> avaliacoes, Set<Categoria> categorias, List<ListaFavoritos> listasFavoritos) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.duracao = duracao;
        this.dataLancamento = dataLancamento;
        this.classificacaoIndicativa = classificacaoIndicativa;
        this.notaMedia = notaMedia;
        this.avaliacoes = avaliacoes;
        this.categorias = categorias;
        this.listasFavoritos = listasFavoritos;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public ClassificacaoIndicativa getClassificacaoIndicativa() {
        return classificacaoIndicativa;
    }

    public void setClassificacaoIndicativa(ClassificacaoIndicativa classificacaoIndicativa) {
        this.classificacaoIndicativa = classificacaoIndicativa;
    }

    public Double getNotaMedia() {
        return notaMedia;
    }

    public void setNotaMedia(Double notaMedia) {
        this.notaMedia = notaMedia;
    }

    public List<AvaliacaoFilme> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<AvaliacaoFilme> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public Set<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(Set<Categoria> categorias) {
        this.categorias = categorias;
    }

    public List<ListaFavoritos> getListasFavoritos() {
        return listasFavoritos;
    }

    public void setListasFavoritos(List<ListaFavoritos> listasFavoritos) {
        this.listasFavoritos = listasFavoritos;
    }

}

