package PF.SerratecFlix.DTO.Request;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import PF.SerratecFlix.Domain.AvaliacaoFilme;
import PF.SerratecFlix.Domain.Categoria;
import PF.SerratecFlix.Domain.ListaFavoritos;
import io.swagger.v3.oas.annotations.media.Schema;
import PF.SerratecFlix.Enumerated.ClassificacaoIndicativa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilmeDTORequest {

    @NotBlank(message = "Título é obrigatório")
    @Schema(description = "Título do filme")
    private String titulo;

    @NotBlank(message = "Descrição é obrigatória")
	@Size(max = 1000, message = "A descrição deve ter no máximo 1000 caracteres")
    @Schema(description = "Descrição do filme")
    private String descricao;

    @Schema(description = "Tempo de duração do filme")
    private Integer duracao;

    @Schema(description = "Data de lançamento")
    private LocalDate dataLancamento;

    @Schema(description = "Classificação Indicativa")
    private ClassificacaoIndicativa classificacaoIndicativa;

    @Positive(message = "A nota para o filmes deve ser maior que 0")
    @Schema(description = "Nota para o filme de 0 a 10")
    private Double notaMedia;

    private List<AvaliacaoFilme> avaliacoes = new ArrayList<>();
    private Set<Categoria> categorias = new HashSet<>();
    private List<ListaFavoritos> listasFavoritos = new ArrayList<>();
}