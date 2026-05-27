package PF.SerratecFlix.Controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import PF.SerratecFlix.DTO.Request.SerieDTORequest;
import PF.SerratecFlix.DTO.Response.SerieDTOResponse;
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
	@Operation(summary = "Listar todas as séries")
	public ResponseEntity<Page<SerieDTOResponse>> listarTodos(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size,
	        @RequestParam(defaultValue = "titulo") String orderBy) {

	    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
	    return ResponseEntity.ok(serieService.listarTodos(pageable));
	}
	
	@GetMapping("/{id}")
    @Operation(summary = "Buscar série por ID")
    public ResponseEntity<SerieDTOResponse> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(serieService.buscarPorId(id));
    }
	
	@GetMapping("/categoria/{categoriaId}")
	@Operation(summary = "Listar séries por categoria")
	public ResponseEntity<List<SerieDTOResponse>> listarPorCategoria(@PathVariable UUID categoriaId) {
		List<SerieDTOResponse> lista = serieService.listarPorCategoria(categoriaId);
		return ResponseEntity.ok(lista);
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
