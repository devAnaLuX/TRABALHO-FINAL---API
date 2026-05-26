package PF.SerratecFlix.Domain;

import PF.SerratecFlix.Enumerated.ClassificacaoIndicativa;
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

}