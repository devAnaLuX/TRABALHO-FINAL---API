package PF.SerratecFlix.DTO.Response;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import PF.SerratecFlix.Domain.Filme;
import PF.SerratecFlix.Domain.Serie;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import PF.SerratecFlix.Domain.Categoria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder
({ "id", "nome", "descricao", "filmes", "series"})
public class CategoriaDTOResponse {
	
    private UUID id;
    private String nome;
    private String descricao;
    private Set<String> filmes = new HashSet<>();
    private Set<String> series = new HashSet<>();
    
    public CategoriaDTOResponse(Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
        this.descricao = categoria.getDescricao();
        this.filmes = categoria.getFilmes()
                .stream()
                .map(Filme::getTitulo)
                .collect(Collectors.toCollection(HashSet::new));
        this.series = categoria.getSeries()
                .stream()
                .map(Serie::getTitulo)
                .collect(Collectors.toCollection(HashSet::new));
    }
}
