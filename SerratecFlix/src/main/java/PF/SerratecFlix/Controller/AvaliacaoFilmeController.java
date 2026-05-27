package PF.SerratecFlix.Controller;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.springframework.web.bind.annotation.RestController;

import PF.SerratecFlix.DTO.Request.AvaliacaoFilmeDTORequest;
import PF.SerratecFlix.DTO.Response.AvaliacaoFilmeDTOResponse;
import PF.SerratecFlix.Service.AvaliacaoFilmeService;
import jakarta.validation.Valid;

@Tag(name = "Avaliações Filme", description = "Gerenciamento completo de avaliações filme — CRUD")
@RestController
@RequestMapping("/avaliacoes_filme")
public class AvaliacaoFilmeController {
	
	@Autowired
	private AvaliacaoFilmeService avaliacaoFilmeService;

	@Operation(summary = "Buscar avaliações", description = "Busca todos as avaliações de filme")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Busca concluída."),
			@ApiResponse(responseCode = "403", description = "Não autorizado"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor")
	})
	@GetMapping
	public ResponseEntity<List<AvaliacaoFilmeDTOResponse>> listarTodas() {
		List<AvaliacaoFilmeDTOResponse> lista = avaliacaoFilmeService.findAll();
		return ResponseEntity.ok(lista);
	}

	@Operation(summary = "Buscar avaliação - ID", description = "Busca a avaliação pelo ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Busca concluída."),
			@ApiResponse(responseCode = "400", description = "Dados inválidos."),
			@ApiResponse(responseCode = "404", description = "Avaliação não encontrada."),
			@ApiResponse(responseCode = "403", description = "Não autorizado"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor")
	})
	@GetMapping("/{id}")
	public ResponseEntity<AvaliacaoFilmeDTOResponse> buscarPorId(@PathVariable UUID id) {
		AvaliacaoFilmeDTOResponse response = avaliacaoFilmeService.buscarPorId(id);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Adicionar avaliação", description = "Adiciona avaliação em um filme")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Criado."),
			@ApiResponse(responseCode = "400", description = "Dados inválidos."),
			@ApiResponse(responseCode = "403", description = "Não autorizado"),
			@ApiResponse(responseCode = "409", description = "Conflito de dados"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor")
	})
	@PostMapping
	public ResponseEntity<AvaliacaoFilmeDTOResponse> cadastrar(@Valid @RequestBody AvaliacaoFilmeDTORequest dto) {
		AvaliacaoFilmeDTOResponse response = avaliacaoFilmeService.inserir(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}


	@Operation(summary = "Atualizar avaliação - ID", description = "Atualizar avaliação por ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Atualizado."),
			@ApiResponse(responseCode = "400", description = "Dados inválidos."),
			@ApiResponse(responseCode = "403", description = "Não autorizado"),
			@ApiResponse(responseCode = "404", description = "Avaliação não encontrada."),
			@ApiResponse(responseCode = "409", description = "Conflito de dados"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor")
	})
	@PutMapping("/{id}")
	public ResponseEntity<AvaliacaoFilmeDTOResponse> atualizar(@PathVariable UUID id, @Valid @RequestBody AvaliacaoFilmeDTORequest dto) {
		AvaliacaoFilmeDTOResponse response = avaliacaoFilmeService.atualizar(id, dto);
		return ResponseEntity.ok(response);
		
	}

	@Operation(summary = "Deletar avaliação - ID", description = "Deletar o avaliação pelo ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Deletado."),
			@ApiResponse(responseCode = "400", description = "Dados inválidos."),
			@ApiResponse(responseCode = "403", description = "Não autorizado"),
			@ApiResponse(responseCode = "404", description = "Avaliação não encontrada."),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor")
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable UUID id) {
		avaliacaoFilmeService.remover(id);
		return ResponseEntity.noContent().build();
	}


	@Operation(summary = "Buscar nota média - ID", description = "Busca a nota média pelo ID do filme")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Busca concluída."),
			@ApiResponse(responseCode = "400", description = "Dados inválidos."),
			@ApiResponse(responseCode = "404", description = "Nota média não encontrada."),
			@ApiResponse(responseCode = "403", description = "Não autorizado"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor")
	})
	@GetMapping("/filme/{filmeId}/media")
	public ResponseEntity<Double> buscarNotaMediaDoFilme(@PathVariable UUID filmeId) {
		Double media = avaliacaoFilmeService.obterNotaMediaDoFilme(filmeId);
		return ResponseEntity.ok(media);
	}
}