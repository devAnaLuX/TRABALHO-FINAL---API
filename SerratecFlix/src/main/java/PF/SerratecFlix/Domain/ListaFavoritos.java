package PF.SerratecFlix.Domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "listas_favoritos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListaFavoritos {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "Identificador único de lista de favoritos")
    private UUID id;

    @NotBlank
    @Column(name = "nome_lista", nullable = false)
    @Schema(description = "Nome da lista de favoritos")
    private String nomeLista;

    @Column(nullable = false)
    @Schema(description = "Lista privada ou não")
    private Boolean privada;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    @Schema(description = "Data de criação da lista")
    private LocalDateTime dataCriacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @Schema(description = "Usuário que a lista pertence")
    private Usuario usuario;

    @ManyToMany
    @JoinTable(
            name = "lista_filme",
            joinColumns = @JoinColumn(name = "lista_id"),
            inverseJoinColumns = @JoinColumn(name = "filme_id")
    )
    @Schema(description = "Filmes que estão na lista")
    private Set<Filme> filmes = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "lista_serie",
            joinColumns = @JoinColumn(name = "lista_id"),
            inverseJoinColumns = @JoinColumn(name = "serie_id")
    )
    @Schema(description = "Séries que estão na lista")
    private Set<Serie> series = new HashSet<>();

}
