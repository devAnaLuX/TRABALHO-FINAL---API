package PF.SerratecFlix.DTO.Response;

import PF.SerratecFlix.Domain.Filme;
import PF.SerratecFlix.Domain.ListaFavoritos;
import PF.SerratecFlix.Domain.Serie;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({
        "id",
        "nomeLista",
        "privada",
        "dataCriacao",
        "usuarioId",
        "filmes",
        "series"
})
public class ListaFavoritosDTOResponse {

    private UUID id;
    private String nomeLista;
    private Boolean privada;
    private LocalDateTime dataCriacao;
    private UUID usuarioId;
    private Set<String> filmes = new HashSet<>();
    private Set<String> series = new HashSet<>();

    public ListaFavoritosDTOResponse(ListaFavoritos listaFavoritos) {
        this.id = listaFavoritos.getId();
        this.nomeLista = listaFavoritos.getNomeLista();
        this.privada = listaFavoritos.getPrivada();
        this.dataCriacao = listaFavoritos.getDataCriacao();
        this.usuarioId = listaFavoritos.getUsuario().getId();
        this.filmes = listaFavoritos.getFilmes()
                .stream()
                .map(Filme::getTitulo)
                .collect(Collectors.toCollection(HashSet::new));
        this.series = listaFavoritos.getSeries()
                .stream()
                .map(Serie::getTitulo)
                .collect(Collectors.toCollection(HashSet::new));
    }
}

