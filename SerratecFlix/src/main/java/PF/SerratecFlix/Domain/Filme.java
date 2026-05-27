package PF.SerratecFlix.Domain;

import PF.SerratecFlix.Enumerated.ClassificacaoIndicativa;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "filmes")
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "Identificador único do filme", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @NotBlank
    @Column(nullable = false)
    @Schema(description = "Título do filme", example = "Inception")
    private String titulo;

    @Column(nullable = false)
    @Schema(description = "Descrição do filme", example = "Um ladrão que entra nos sonhos das pessoas para roubar segredos")
    private String descricao;

    @Column(nullable = false)
    @Schema(description = "Duração do filme em minutos", example = "148")
    private Integer duracao;

    @Column(name = "data_lancamento")
    @Schema(description = "Data de lançamento do filme", example = "2010-07-16")
    private LocalDate dataLancamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "classificacao_indicativa")
    @Schema(description = "Classificação indicativa do filme", example = "QUATORZE_ANOS")
    private ClassificacaoIndicativa classificacaoIndicativa;

    @Column(name = "nota_media")
    @Schema(description = "Nota média do filme baseada nas avaliações", example = "8.5")
    private Double notaMedia;

    @OneToMany(mappedBy = "filme", cascade = CascadeType.ALL)
    @Schema(description = "Lista de avaliações do filme")
    private List<AvaliacaoFilme> avaliacoes = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "filme_categoria",
            joinColumns = @JoinColumn(name = "filme_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    @Schema(description = "Categorias associadas ao filme")
    private Set<Categoria> categorias = new HashSet<>();

    @ManyToMany(mappedBy = "filmes")
    @Schema(description = "Listas de favoritos que contêm este filme")
    private List<ListaFavoritos> listasFavoritos = new ArrayList<>();

}