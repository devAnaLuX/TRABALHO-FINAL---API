package PF.SerratecFlix.Controller;

import PF.SerratecFlix.DTO.Request.ListaFavoritosDTORequest;
import PF.SerratecFlix.DTO.Response.ListaFavoritosDTOResponse;
import PF.SerratecFlix.Service.ListaFavoritosService;
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
    public ResponseEntity<List<ListaFavoritosDTOResponse>> buscar(){
        return ResponseEntity.ok(listaFavoritosService.buscar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListaFavoritosDTOResponse> buscarPorId(@PathVariable UUID id){
        return ResponseEntity.ok(listaFavoritosService.buscarPorId(id));
    }

    @PostMapping
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
    public ResponseEntity<ListaFavoritosDTOResponse> atualizar(@PathVariable UUID id, @Valid @RequestBody ListaFavoritosDTORequest listaFavoritosDTO){
        return ResponseEntity.ok(listaFavoritosService.atualizar(id, listaFavoritosDTO));
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deletar(@PathVariable UUID id){
        listaFavoritosService.deletar(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/recomendacoes")
    public ResponseEntity<String> recomendar(@PathVariable UUID id){
        return ResponseEntity.ok(listaFavoritosService.recomendar(id));
    }
}
