package PF.SerratecFlix.Controller;

import java.util.List;
import java.util.UUID;

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
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;

	@GetMapping
	public ResponseEntity<List<CategoriaDTOResponse>> listarTodas() {
		List<CategoriaDTOResponse> lista = categoriaService.findAll();
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoriaDTOResponse> buscarPorId(@PathVariable UUID id) {
		CategoriaDTOResponse response = categoriaService.buscarPorId(id);
		return ResponseEntity.ok(response);
	}
	
		@PostMapping
		public ResponseEntity<CategoriaDTOResponse> cadastrar(@Valid @RequestBody CategoriaDTORequest dto) {
			CategoriaDTOResponse response = categoriaService.inserir(dto);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}
		@PutMapping("/{id}")
		public ResponseEntity<CategoriaDTOResponse> atualizar(@PathVariable UUID id, @Valid @RequestBody CategoriaDTORequest dto) {
			CategoriaDTOResponse response = categoriaService.atualizar(id, dto);
			return ResponseEntity.ok(response);
		}
		@DeleteMapping("/{id}")
		public ResponseEntity<Void> remover(@PathVariable UUID id) {
			categoriaService.remover(id);
			return ResponseEntity.noContent().build();
		}
			
}
