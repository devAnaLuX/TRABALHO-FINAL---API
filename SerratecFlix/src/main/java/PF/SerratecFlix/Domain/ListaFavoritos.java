package PF.SerratecFlix.Domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "listas_favoritos")
public class ListaFavoritos {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(name = "nome_lista", nullable = false)
    private String nomeLista;

    @Column(nullable = false)
    private Boolean privada = false;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToMany
    @JoinTable(
            name = "lista_filme",
            joinColumns = @JoinColumn(name = "lista_id"),
            inverseJoinColumns = @JoinColumn(name = "filme_id")
    )
    private Set<Filme> filmes = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "lista_serie",
            joinColumns = @JoinColumn(name = "lista_id"),
            inverseJoinColumns = @JoinColumn(name = "serie_id")
    )
    private Set<Serie> series = new HashSet<>();


    public ListaFavoritos() {
    }

    public ListaFavoritos(UUID id, String nomeLista, Boolean privada, LocalDateTime dataCriacao, Usuario usuario, Set<Filme> filmes, Set<Serie> series) {
        this.id = id;
        this.nomeLista = nomeLista;
        this.privada = privada;
        this.dataCriacao = dataCriacao;
        this.usuario = usuario;
        this.filmes = filmes;
        this.series = series;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNomeLista() {
        return nomeLista;
    }

    public void setNomeLista(String nomeLista) {
        this.nomeLista = nomeLista;
    }

    public Boolean getPrivada() {
        return privada;
    }

    public void setPrivada(Boolean privada) {
        this.privada = privada;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Set<Filme> getFilmes() {
        return filmes;
    }

    public void setFilmes(Set<Filme> filmes) {
        this.filmes = filmes;
    }

    public Set<Serie> getSeries() {
        return series;
    }

    public void setSeries(Set<Serie> series) {
        this.series = series;
    }
}
