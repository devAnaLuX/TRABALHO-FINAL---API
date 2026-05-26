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

import PF.SerratecFlix.DTO.Request.AvaliacaoFilmeDTORequest;
import PF.SerratecFlix.DTO.Response.AvaliacaoFilmeDTOResponse;
import PF.SerratecFlix.Service.AvaliacaoFilmeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/avaliacoes_filme")
public class AvaliacaoFilmeController {
	
	@Autowired
	private AvaliacaoFilmeService avaliacaoFilmeService;

	@GetMapping
	public ResponseEntity<List<AvaliacaoFilmeDTOResponse>> listarTodas() {
		List<AvaliacaoFilmeDTOResponse> lista = avaliacaoFilmeService.findAll();
		return ResponseEntity.ok(lista);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AvaliacaoFilmeDTOResponse> buscarPorId(@PathVariable UUID id) {
		AvaliacaoFilmeDTOResponse response = avaliacaoFilmeService.buscarPorId(id);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping
	public ResponseEntity<AvaliacaoFilmeDTOResponse> cadastrar(@Valid @RequestBody AvaliacaoFilmeDTORequest dto) {
		AvaliacaoFilmeDTOResponse response = avaliacaoFilmeService.inserir(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<AvaliacaoFilmeDTOResponse> atualizar(@PathVariable UUID id, @Valid @RequestBody AvaliacaoFilmeDTORequest dto) {
		AvaliacaoFilmeDTOResponse response = avaliacaoFilmeService.atualizar(id, dto);
		return ResponseEntity.ok(response);
		
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable UUID id) {
		avaliacaoFilmeService.remover(id);
		return ResponseEntity.noContent().build();
	}
}