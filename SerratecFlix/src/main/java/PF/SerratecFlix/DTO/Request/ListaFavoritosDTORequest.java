package PF.SerratecFlix.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListaFavoritosDTORequest {

    private String nomeLista;
    private Boolean privada;
    private UUID usuarioId;
    private Set<UUID> filmes = new HashSet<>();
    private Set<UUID> series = new HashSet<>();

}
