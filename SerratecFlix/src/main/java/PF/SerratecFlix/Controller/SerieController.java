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
import PF.SerratecFlix.DTO.Response.SerieDTOResponse;
import PF.SerratecFlix.DTO.Request.SerieDTORequest;
import PF.SerratecFlix.Service.SerieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/series")
@Tag(name = "Séries")
public class SerieController {
	
	@Autowired
    private SerieService serieService;
	
	@GetMapping
    @Operation(summary = "Listar séries")
	public ResponseEntity<Object> listarTodos() {
        return ResponseEntity.ok(serieService.listarTodos());
    }
	
	@GetMapping("/{id}")
    @Operation(summary = "Buscar série por ID")
    public ResponseEntity<SerieDTOResponse> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(serieService.buscarPorId(id));
    }
	
	@PostMapping
    @Operation(summary = "Cadastrar série")
    public ResponseEntity<SerieDTOResponse> criar(@Valid @RequestBody SerieDTORequest dto) {
		SerieDTOResponse response = serieService.criar(dto);
    	
    	URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        
        return ResponseEntity.created(uri).body(response);
    }
	
	@PutMapping("/{id}")
    @Operation(summary = "Atualizar serie")
    public ResponseEntity<SerieDTOResponse> atualizar(@PathVariable UUID id, @Valid @RequestBody SerieDTORequest dto) {
        return ResponseEntity.ok(serieService.atualizar(id, dto));
    }
	
	@DeleteMapping("/{id}")
    @Operation(summary = "Remover serie")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        serieService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
