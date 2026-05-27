package PF.SerratecFlix.Domain;

import PF.SerratecFlix.Enumerated.ClassificacaoIndicativa;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "series")
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "Identificador único de série")
    private UUID id;

    @NotBlank
    @Column(nullable = false)
    @Schema(description = "Título da série")
    private String titulo;

    @Column(nullable = false)
    @Schema(description = "Descrição da série")
    private String descricao;

    @Column(nullable = false)
    @Schema(description = "Quantidade de temporadas da série")
    private Integer temporadas;

    @Column(nullable = false)
    @Schema(description = "Quantidade de episódios da série")
    private Integer episodios;

    @Column(name = "data_lancamento")
    @Schema(description = "Data de lançamento da série")
    private LocalDate dataLancamento;

    @Column(name = "nota_media")
    @Schema(description = "Nota média da série")
    private Double notaMedia;

    @Enumerated(EnumType.STRING)
    @Column(name = "classificacao_indicativa")
    @Schema(description = "Classificação indicativa da série", example = "DEZOITO")
    private ClassificacaoIndicativa classificacaoIndicativa;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL)
    @Schema(description = "Lista de avaliações da série")
    private List<AvaliacaoSerie> avaliacoes = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "serie_categoria",
            joinColumns = @JoinColumn(name = "serie_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    @Schema(description = "Categorias da série")
    private Set<Categoria> categorias = new HashSet<>();

    @ManyToMany(mappedBy = "series")
    @Schema(description = "Listas que a série pertence")
    private List<ListaFavoritos> listasFavoritos = new ArrayList<>();

}
