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

import PF.SerratecFlix.DTO.Request.CategoriaDTORequest;
import PF.SerratecFlix.DTO.Response.CategoriaDTOResponse;
import PF.SerratecFlix.Service.CategoriaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
@Tag(name = "Categoria", description = "Gerenciamento completo de categoria — CRUD")
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;

	@GetMapping
	@Operation(summary = "Buscar categorias", description = "Busca todos as categorias")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Busca concluída."),
			@ApiResponse(responseCode = "403", description = "Não autorizado"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor")
	})
	public ResponseEntity<List<CategoriaDTOResponse>> listarTodas() {
		List<CategoriaDTOResponse> lista = categoriaService.findAll();
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Buscar categoria - ID", description = "Busca a categoria pelo ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Busca concluída."),
			@ApiResponse(responseCode = "400", description = "Dados inválidos."),
			@ApiResponse(responseCode = "404", description = "Categoria não encontrada."),
			@ApiResponse(responseCode = "403", description = "Não autorizado"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor")
	})
	public ResponseEntity<CategoriaDTOResponse> buscarPorId(@PathVariable UUID id) {
		CategoriaDTOResponse response = categoriaService.buscarPorId(id);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping
	@Operation(summary = "Adicionar categoria", description = "Adiciona categoria")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Criado."),
			@ApiResponse(responseCode = "400", description = "Dados inválidos."),
			@ApiResponse(responseCode = "403", description = "Não autorizado"),
			@ApiResponse(responseCode = "409", description = "Conflito de dados"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor")
	})
	public ResponseEntity<CategoriaDTOResponse> cadastrar(@Valid @RequestBody CategoriaDTORequest dto) {
		CategoriaDTOResponse response = categoriaService.inserir(dto);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}


	@PutMapping("/{id}")
	@Operation(summary = "Atualizar categoria - ID", description = "Atualizar categoria por ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Atualizado."),
			@ApiResponse(responseCode = "400", description = "Dados inválidos."),
			@ApiResponse(responseCode = "403", description = "Não autorizado"),
			@ApiResponse(responseCode = "404", description = "Categoria não encontrada."),
			@ApiResponse(responseCode = "409", description = "Conflito de dados"),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor")
	})
	public ResponseEntity<CategoriaDTOResponse> atualizar(@PathVariable UUID id, @Valid @RequestBody CategoriaDTORequest dto) {
		CategoriaDTOResponse response = categoriaService.atualizar(id, dto);
			return ResponseEntity.ok(response);
	}


	@DeleteMapping("/{id}")
	@Operation(summary = "Deletar avaliação - ID", description = "Deletar o avaliação pelo ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Deletado."),
			@ApiResponse(responseCode = "400", description = "Dados inválidos."),
			@ApiResponse(responseCode = "403", description = "Não autorizado"),
			@ApiResponse(responseCode = "404", description = "Avaliação não encontrada."),
			@ApiResponse(responseCode = "500", description = "Erro interno no servidor")
	})
	public ResponseEntity<Void> remover(@PathVariable UUID id) {
		categoriaService.remover(id);
			return ResponseEntity.noContent().build();
	}
			
}
