package PF.SerratecFlix.Domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "series")
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @NotBlank
    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private Integer temporadas;

    @Column(nullable = false)
    private Integer episodios;

    @Column(name = "data_lancamento")
    private LocalDate dataLancamento;

    @Column(name = "nota_media")
    private Double notaMedia;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL)
    private List<AvaliacaoSerie> avaliacoes = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "serie_categoria",
            joinColumns = @JoinColumn(name = "serie_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private Set<Categoria> categorias = new HashSet<>();

    @ManyToMany(mappedBy = "series")
    private List<ListaFavoritos> listasFavoritos = new ArrayList<>();

    public Serie() {
    }

    public Serie(UUID id, String titulo, String descricao, Integer temporadas, Integer episodios,
                 LocalDate dataLancamento, Double notaMedia, List<AvaliacaoSerie> avaliacoes,
                 Set<Categoria> categorias, List<ListaFavoritos> listasFavoritos) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.temporadas = temporadas;
        this.episodios = episodios;
        this.dataLancamento = dataLancamento;
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

    public Integer getTemporadas() {
        return temporadas;
    }

    public void setTemporadas(Integer temporadas) {
        this.temporadas = temporadas;
    }

    public Integer getEpisodios() {
        return episodios;
    }

    public void setEpisodios(Integer episodios) {
        this.episodios = episodios;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public Double getNotaMedia() {
        return notaMedia;
    }

    public void setNotaMedia(Double notaMedia) {
        this.notaMedia = notaMedia;
    }

    public List<AvaliacaoSerie> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<AvaliacaoSerie> avaliacoes) {
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
