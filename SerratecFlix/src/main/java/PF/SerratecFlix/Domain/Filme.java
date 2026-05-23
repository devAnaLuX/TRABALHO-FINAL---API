package PF.SerratecFlix.Domain;

import PF.SerratecFlix.Enumerated.ClassificacaoIndicativa;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "filmes")
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private Integer duracao;

    @Column(name = "data_lancamento")
    private LocalDate dataLancamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "classificacao_indicativa")
    private ClassificacaoIndicativa classificacaoIndicativa;

    @Column(name = "nota_media")
    private Double notaMedia;

    @OneToMany(mappedBy = "filme", cascade = CascadeType.ALL)
    private List<AvaliacaoFilme> avaliacoes = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "filme_categoria",
            joinColumns = @JoinColumn(name = "filme_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private Set<Categoria> categorias = new HashSet<>();

    @ManyToMany(mappedBy = "filmes")
    private List<ListaFavoritos> listasFavoritos = new ArrayList<>();

    public Filme() {
    }

    public Filme(UUID id, String titulo, String descricao, Integer duracao, LocalDate dataLancamento,
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