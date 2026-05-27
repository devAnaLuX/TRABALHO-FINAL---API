package PF.SerratecFlix.Controller;


import java.net.URI;
import java.util.UUID;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import PF.SerratecFlix.DTO.Request.AvaliacaoSerieDTORequest;
import PF.SerratecFlix.DTO.Response.AvaliacaoSerieDTOResponse;
import PF.SerratecFlix.Service.AvaliacaoSerieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/avaliacaoserie")
@Tag(name = "Avaliação série", description = "Gerenciamento completo de avaliações série — CRUD")
public class AvaliacaoSerieController {
	
	@Autowired
    private AvaliacaoSerieService avaliacaoserieService;
	
	@GetMapping
    @Operation(summary = "Buscar avaliações", description = "Busca todos as avaliações de série")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca concluída."),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Object> listarTodos() {
        return ResponseEntity.ok(avaliacaoserieService.listarTodos());
    }


	@GetMapping("/{id}")
    @Operation(summary = "Buscar avaliação - ID", description = "Busca a avaliação da série pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca concluída."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada."),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Object> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(avaliacaoserieService.buscarPorId(id));
    }
	
	@PostMapping
    @Operation(summary = "Adicionar avaliação", description = "Adiciona avaliação em uma série")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<AvaliacaoSerieDTOResponse> criar(@Valid @RequestBody AvaliacaoSerieDTORequest dto) {
		AvaliacaoSerieDTOResponse response = avaliacaoserieService.criar(dto);
    	
    	URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        
        return ResponseEntity.created(uri).body(response);
    }
	
	@PutMapping("/{id}")
    @Operation(summary = "Atualizar avaliação - ID", description = "Atualizar avaliação por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualizado."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada."),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<AvaliacaoSerieDTOResponse> atualizar(@PathVariable UUID id, @Valid @RequestBody AvaliacaoSerieDTORequest dto) {
        return ResponseEntity.ok(avaliacaoserieService.atualizar(id, dto));
    }
	
	@DeleteMapping("/{id}")
    @Operation(summary = "Remover serie")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
		avaliacaoserieService.deletar(id);
        return ResponseEntity.noContent().build();
    }
	
	@GetMapping("/serie/{serieId}/media")
    @Operation(summary = "Buscar nota média - ID", description = "Busca a nota média pelo ID da série")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca concluída."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada."),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Double> buscarNotaMediaDoSerie(@PathVariable UUID serieId) {
        Double media = avaliacaoserieService.obterNotaMediaDoSerie(serieId);
        return ResponseEntity.ok(media);
    }
}
