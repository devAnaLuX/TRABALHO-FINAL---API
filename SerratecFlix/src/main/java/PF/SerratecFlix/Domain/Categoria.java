package PF.SerratecFlix.Domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "Identificador único da categoria", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @NotBlank
    @Column(nullable = false, unique = true, length = 50)
    @Schema(description = "Nome da categoria", example = "Ação")
    private String nome;

    @Column(nullable = true, length = 250)
    @Schema(description = "Descrição da categoria", example = "Filmes e séries com muita adrenalina e aventura")
    private String descricao;

    @ManyToMany(mappedBy = "categorias")
    @Schema(description = "Filmes associados a esta categoria")
    private Set<Filme> filmes = new HashSet<>();

    @ManyToMany(mappedBy = "categorias")
    @Schema(description = "Séries associadas a esta categoria")
    private Set<Serie> series = new HashSet<>();

}