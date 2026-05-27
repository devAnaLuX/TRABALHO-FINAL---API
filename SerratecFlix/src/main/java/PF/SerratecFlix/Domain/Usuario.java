package PF.SerratecFlix.Domain;

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
    private UUID id;


    @Column(nullable = false)
    private String nome;


    @Email
    @Column(nullable = false, unique = true)
    private String email;

    
    @Column(nullable = false, unique = true)
    private String username;

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

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
    }

    public String getFotoPerfilUrl() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFotoPerfilUrl'");
    }

}