package PF.SerratecFlix.Controller;

import PF.SerratecFlix.DTO.Request.ListaFavoritosDTORequest;
import PF.SerratecFlix.DTO.Response.ListaFavoritosDTOResponse;
import PF.SerratecFlix.Service.ListaFavoritosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/listas")
public class ListaFavoritosController {

    @Autowired
    private ListaFavoritosService listaFavoritosService;

    @GetMapping
    @Operation(summary = "Buscar lista de favoritos", description = "Busca todas as listas de favoritos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca concluída."),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<List<ListaFavoritosDTOResponse>> buscar(){
        return ResponseEntity.ok(listaFavoritosService.buscar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar lista de favoritos - ID", description = "Busca todas as listas de favoritos pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca concluída."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "404", description = "Lista de favoritos não encontrado."),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<ListaFavoritosDTOResponse> buscarPorId(@PathVariable UUID id){
        return ResponseEntity.ok(listaFavoritosService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Adicionar lista de favoritos", description = "Adiciona lista de favoritos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<ListaFavoritosDTOResponse> adicionar(@Valid @RequestBody ListaFavoritosDTORequest listaFavoritosNew){

        ListaFavoritosDTOResponse listaFavoritosDTO = listaFavoritosService.adicionar(listaFavoritosNew);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(listaFavoritosDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(listaFavoritosDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar lista de favoritos - ID", description = "Atualizar lista de favoritos por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualizado."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Lista de favoritos não encontrado."),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<ListaFavoritosDTOResponse> atualizar(@PathVariable UUID id, @Valid @RequestBody ListaFavoritosDTORequest listaFavoritosDTO){
        return ResponseEntity.ok(listaFavoritosService.atualizar(id, listaFavoritosDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar filme - ID", description = "Deletar o filme pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deletado."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Lista de favoritos não encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public  ResponseEntity<Void> deletar(@PathVariable UUID id){
        listaFavoritosService.deletar(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/recomendacoes")
    @Operation(summary = "Recomedações", description = "Cria uma lista de recomendações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recomendação feita."),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<String> recomendar(@PathVariable UUID id){
        return ResponseEntity.ok(listaFavoritosService.recomendar(id));
    }
}
