package PF.SerratecFlix.DTO.Response;

import java.util.UUID;
import java.time.LocalDate;
import java.util.*;

import PF.SerratecFlix.Domain.Categoria;
import PF.SerratecFlix.Domain.ListaFavoritos;
import PF.SerratecFlix.Enumerated.ClassificacaoIndicativa;
import PF.SerratecFlix.Domain.AvaliacaoFilme;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilmeDTOResponse {

    private UUID id;
    private String titulo;
    private String descricao;
    private Integer duracao;
    private LocalDate dataLancamento;
    private ClassificacaoIndicativa classificacaoIndicativa;
    private Double notaMedia;
    private List<AvaliacaoFilme> avaliacoes = new ArrayList<>();
    private Set<Categoria> categorias = new HashSet<>();
    private List<ListaFavoritos> listasFavoritos = new ArrayList<>();

}

