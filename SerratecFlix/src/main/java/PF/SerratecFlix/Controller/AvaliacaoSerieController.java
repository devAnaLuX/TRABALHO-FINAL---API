package PF.SerratecFlix.Controller;


import java.net.URI;
import java.util.UUID;

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
import PF.SerratecFlix.DTO.Request.SerieDTORequest;
import PF.SerratecFlix.DTO.Response.AvaliacaoSerieDTOResponse;
import PF.SerratecFlix.Service.AvaliacaoSerieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/avaliacaoserie")
@Tag(name = "Avaliação série")
public class AvaliacaoSerieController {
	
	@Autowired
    private AvaliacaoSerieService avaliacaoserieService;
	
	@GetMapping
    @Operation(summary = "Listar as avaliações das séries")
    public ResponseEntity<Object> listarTodos() {
        return ResponseEntity.ok(avaliacaoserieService.listarTodos());
    }
	
	@GetMapping("/{id}")
    @Operation(summary = "Buscar avaliação de série")
    public ResponseEntity<Object> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(avaliacaoserieService.buscarPorId(id));
    }
	
	@PostMapping
    @Operation(summary = "Cadastrar série")
    public ResponseEntity<AvaliacaoSerieDTOResponse> criar(@Valid @RequestBody SerieDTORequest dto) {
		AvaliacaoSerieDTOResponse response = avaliacaoserieService.criar(dto);
    	
    	URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        
        return ResponseEntity.created(uri).body(response);
    }
	
	@PutMapping("/{id}")
    @Operation(summary = "Atualizar serie")
    public ResponseEntity<AvaliacaoSerieDTOResponse> atualizar(@PathVariable UUID id, @Valid @RequestBody AvaliacaoSerieDTORequest dto) {
        return ResponseEntity.ok(avaliacaoserieService.atualizar(id, dto));
    }
	
	@DeleteMapping("/{id}")
    @Operation(summary = "Remover serie")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
		avaliacaoserieService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
