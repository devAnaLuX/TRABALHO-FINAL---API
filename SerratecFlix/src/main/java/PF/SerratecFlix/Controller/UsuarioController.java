package PF.SerratecFlix.Controller;

import java.util.List;
import java.util.UUID;
 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
 
import PF.SerratecFlix.DTO.Request.UsuarioDTORequest;
import PF.SerratecFlix.DTO.Response.UsuarioDTOResponse;
import PF.SerratecFlix.Service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Criar um novo usuário")
    @PostMapping
   public ResponseEntity<UsuarioDTOResponse> criarUsuario(@Valid @RequestBody UsuarioDTORequest usuarioDTORequest) {
        // Lógica para criar um novo usuário
        UsuarioDTOResponse usuarioDTOResponse = usuarioService.save(usuarioDTORequest);
        // Retornar a resposta com o usuário criado
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDTOResponse);
    }

     @Operation(summary = "Listar todos os usuários")
    @GetMapping
    public ResponseEntity<List<UsuarioDTOResponse>> listarUsuarios() {
        List<UsuarioDTOResponse> usuarios = usuarioService.findAll();

    return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Obter um usuário por ID")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTOResponse> obterUsuarioPorId(@PathVariable UUID id) {
        UsuarioDTOResponse usuarioDTOResponse = usuarioService.findById(id);
        return ResponseEntity.ok(usuarioDTOResponse);
    }

    @Operation(summary = "Atualizar um usuário")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTOResponse> atualizarUsuario(
            @PathVariable UUID id,
            @Valid @RequestBody UsuarioDTORequest usuarioDTORequest) {
        UsuarioDTOResponse usuarioDTOResponse = usuarioService.update(id, usuarioDTORequest);
        return ResponseEntity.ok(usuarioDTOResponse);
    }

    @Operation(summary = "Deletar um usuário")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable UUID id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Adicionar ou atualizar foto de perfil")
    @PostMapping("/{id}/foto-perfil")
    public ResponseEntity<UsuarioDTOResponse> adicionarFotoPerfil(
            @PathVariable UUID id,
            @RequestParam("foto") MultipartFile foto) {
        UsuarioDTOResponse usuario = usuarioService.adicionarFotoPerfil(id, foto);
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Remover foto de perfil")
    @DeleteMapping("/{id}/foto-perfil")
    public ResponseEntity<Void> removerFotoPerfil(@PathVariable UUID id) {
        usuarioService.removerFotoPerfil(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Criar lista de favoritos")
    @PostMapping("/{id}/listas-favoritos")
    public ResponseEntity<Void> criarListaDeFavoritos(@PathVariable UUID id) {
        // Lógica para criar uma lista de favoritos para o usuário
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
        
    
}
