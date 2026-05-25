package PF.SerratecFlix.Controller;

import java.util.List;
import java.util.UUID;

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
import org.springframework.web.multipart.MultipartFile;

import PF.SerratecFlix.DTO.Request.UsuarioDTORequest;
import PF.SerratecFlix.DTO.Response.UsuarioDTOResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Operation(summary = "Criar um novo usuário")
    @PostMapping
    public ResponseEntity<UsuarioDTOResponse> criarUsuario(@Valid @RequestBody UsuarioDTORequest usuarioDTORequest) {
        // Lógica para criar um novo usuário
        UsuarioDTOResponse usuarioDTOResponse = new UsuarioDTOResponse();
         // Retornar a resposta com o usuário criado
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDTOResponse);
    }

     @Operation(summary = "Listar todos os usuários")
    @GetMapping
    public ResponseEntity<List<UsuarioDTOResponse>> listarUsuarios() {
        // Lógica para listar todos os usuários
        List<UsuarioDTOResponse> usuarios = new List.of();
        // Adicionar usuários à lista
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Obter um usuário por ID")
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTOResponse> obterUsuarioPorId(@PathVariable UUID id) {
        // Lógica para obter um usuário por ID
        UsuarioDTOResponse usuarioDTOResponse = new UsuarioDTOResponse();
        // Retornar a resposta com o usuário encontrado
        return ResponseEntity.ok(usuarioDTOResponse);
    }

    @Operation(summary = "Atualizar um usuário")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTOResponse> atualizarUsuario(@PathVariable UUID id, @Valid @RequestBody UsuarioDTORequest usuarioDTORequest) {
        // Lógica para atualizar um usuário existente
        UsuarioDTOResponse usuarioDTOResponse = new UsuarioDTOResponse();
        // Retornar a resposta com o usuário atualizado
        return ResponseEntity.ok(usuarioDTOResponse);
    }

    @Operation(summary = "Deletar um usuário")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable UUID id) {
        // Lógica para deletar um usuário por ID
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Adicionar foto de perfil")
    @PostMapping("/{id}/foto-perfil")
    public ResponseEntity<Void> adicionarFotoPerfil(@PathVariable UUID id, @RequestParam("foto") MultipartFile foto) {
        // Lógica para adicionar ou atualizar a foto de perfil do usuário
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Remover foto de perfil")
    @DeleteMapping("/{id}/foto-perfil")
    public ResponseEntity<Void> removerFotoPerfil(@PathVariable UUID id) {
        // Lógica para remover a foto de perfil do usuário
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Criar lista de favoritos")
    @PostMapping("/{id}/listas-favoritos")
    public ResponseEntity<Void> criarListaDeFavoritos(@PathVariable UUID id) {
        // Lógica para criar uma lista de favoritos para o usuário
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
        
    
}
