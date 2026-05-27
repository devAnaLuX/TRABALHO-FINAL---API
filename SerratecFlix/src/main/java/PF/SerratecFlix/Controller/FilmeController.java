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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
    public ResponseEntity<Page<FilmeDTOResponse>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "titulo") String orderBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return ResponseEntity.ok(filmeService.listarPaginado(pageable));
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
    
    @GetMapping("/categoria/{categoriaId}")
    @Operation(summary = "Listar filmes por categoria")
    public ResponseEntity<List<FilmeDTOResponse>> listarPorCategoria(@PathVariable UUID categoriaId) {
        List<FilmeDTOResponse> lista = filmeService.listarPorCategoria(categoriaId);
        return ResponseEntity.ok(lista);
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
