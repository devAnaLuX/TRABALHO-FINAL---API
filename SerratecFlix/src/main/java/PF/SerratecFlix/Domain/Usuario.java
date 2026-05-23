package PF.SerratecFlix.Domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    @Column(nullable = false)
    private String senha;

    @Column(name = "foto_perfil")
    private String fotoPerfil;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<AvaliacaoFilme> avaliacoesFilme = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<AvaliacaoSerie> avaliacoesSerie = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<ListaFavoritos> listasFavoritos = new ArrayList<>();

    public Usuario() {
    }

    public Usuario(UUID id, String nome, String email, String username, String senha, String fotoPerfil, LocalDateTime dataCriacao, List<AvaliacaoFilme> avaliacoesFilme, List<AvaliacaoSerie> avaliacoesSerie, List<ListaFavoritos> listasFavoritos) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.username = username;
        this.senha = senha;
        this.fotoPerfil = fotoPerfil;
        this.dataCriacao = dataCriacao;
        this.avaliacoesFilme = avaliacoesFilme;
        this.avaliacoesSerie = avaliacoesSerie;
        this.listasFavoritos = listasFavoritos;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public List<AvaliacaoFilme> getAvaliacoesFilme() {
        return avaliacoesFilme;
    }

    public void setAvaliacoesFilme(List<AvaliacaoFilme> avaliacoesFilme) {
        this.avaliacoesFilme = avaliacoesFilme;
    }

    public List<AvaliacaoSerie> getAvaliacoesSerie() {
        return avaliacoesSerie;
    }

    public void setAvaliacoesSerie(List<AvaliacaoSerie> avaliacoesSerie) {
        this.avaliacoesSerie = avaliacoesSerie;
    }

    public List<ListaFavoritos> getListasFavoritos() {
        return listasFavoritos;
    }

    public void setListasFavoritos(List<ListaFavoritos> listasFavoritos) {
        this.listasFavoritos = listasFavoritos;
    }
}
