package PF.SerratecFlix.Controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import PF.SerratecFlix.Service.FilmeService;
import PF.SerratecFlix.DTO.Response.FilmeDTOResponse;
import PF.SerratecFlix.DTO.Request.FilmeDTORequest;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    @Autowired
    private FilmeService filmeService;

    @GetMapping
    @Operation(summary = "Listar filmes")
    public ResponseEntity<List<FilmeDTOResponse>> listar() {
        return ResponseEntity.ok(filmeService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar filme por ID")
    public ResponseEntity<FilmeDTOResponse> buscarPorId(@PathVariable UUID id){
        return ResponseEntity.ok(filmeService.buscarPorId(id));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar filme por nome")
    public ResponseEntity<List<FilmeDTOResponse>> buscarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(filmeService.buscarPorTitulo(titulo));
    }

    @PostMapping
    @Operation(summary = "Cadastrar filme")
    public ResponseEntity<FilmeDTOResponse> cadastrar(@Valid @RequestBody FilmeDTORequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(filmeService.cadastrar(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar filme")
    public ResponseEntity<FilmeDTOResponse> atualizar(@PathVariable UUID id, @Valid @RequestBody FilmeDTORequest request) {
        return ResponseEntity.ok(filmeService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar filme")
    public ResponseEntity<Void> apagar(@PathVariable UUID id) {
        filmeService.apagar(id);
        return ResponseEntity.noContent().build();
    }
}
