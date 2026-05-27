package PF.SerratecFlix.Domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "Identificador único de usuário")
    private UUID id;


    @Column(nullable = false)
    @Schema(description = "Nome do usuário")
    private String nome;


    @Email
    @Column(nullable = false, unique = true)
    @Schema(description = "Email do usuário")
    private String email;

    
    @Column(nullable = false, unique = true)
    @Schema(description = "Username do usuário")
    private String username;

    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    @Column(nullable = false)
    @Schema(description = "Senha do usuário")
    private String senha;

    @Column(name = "foto_perfil")
    @Schema(description = "Foto de perfil do usuário")
    private String fotoPerfil;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    @Schema(description = "Data de criação do usuário")
    private LocalDateTime dataCriacao;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @Schema(description = "Avaliações de filme feitas pelo usuário")
    private List<AvaliacaoFilme> avaliacoesFilme = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @Schema(description = "Avaliações de série feitas pelo usuário")
    private List<AvaliacaoSerie> avaliacoesSerie = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @Schema(description = "Listas do usuário")
    private List<ListaFavoritos> listasFavoritos = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
    }

    public String getFotoPerfilUrl() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFotoPerfilUrl'");
    }

    public void setFotoPerfilUrl(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setFotoPerfilUrl'");
    }

}