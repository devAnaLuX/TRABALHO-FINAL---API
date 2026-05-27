package PF.SerratecFlix.Controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Filme", description = "Gerenciamento completo de filme — CRUD")
public class FilmeController {

    @Autowired
    private FilmeService filmeService;

    @GetMapping
    @Operation(summary = "Buscar filmes", description = "Busca todos os filmes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca concluída."),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Page<FilmeDTOResponse>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "titulo") String orderBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return ResponseEntity.ok(filmeService.listarPaginado(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar filme - ID", description = "Busca o filme pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca concluída."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "404", description = "Filme não encontrado."),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<FilmeDTOResponse> buscarPorId(@PathVariable UUID id){
        return ResponseEntity.ok(filmeService.buscarPorId(id));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar filme - Título", description = "Busca o filme pelo título")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca concluída."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "404", description = "Filme não encontrado."),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<List<FilmeDTOResponse>> buscarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(filmeService.buscarPorTitulo(titulo));
    }

    @PostMapping
    @Operation(summary = "Adicionar filme", description = "Adiciona filme")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<FilmeDTOResponse> cadastrar(@Valid @RequestBody FilmeDTORequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(filmeService.cadastrar(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar filme - ID", description = "Atualizar filme por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualizado."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Filme não encontrado."),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<FilmeDTOResponse> atualizar(@PathVariable UUID id, @Valid @RequestBody FilmeDTORequest request) {
        return ResponseEntity.ok(filmeService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar filme - ID", description = "Deletar o filme pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deletado."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Filme não encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Void> apagar(@PathVariable UUID id) {
        filmeService.apagar(id);
        return ResponseEntity.noContent().build();
    }
}
