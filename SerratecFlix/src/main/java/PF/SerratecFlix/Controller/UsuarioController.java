package PF.SerratecFlix.Controller;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


    @PostMapping
    @Operation(summary = "Adicionar usuário", description = "Adiciona usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
   public ResponseEntity<UsuarioDTOResponse> criarUsuario(@Valid @RequestBody UsuarioDTORequest usuarioDTORequest) {
        // Lógica para criar um novo usuário
        UsuarioDTOResponse usuarioDTOResponse = usuarioService.save(usuarioDTORequest);
        // Retornar a resposta com o usuário criado
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDTOResponse);
    }


    @GetMapping
    @Operation(summary = "Buscar usuários", description = "Busca todos os usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca concluída."),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<List<UsuarioDTOResponse>> listarUsuarios() {
        List<UsuarioDTOResponse> usuarios = usuarioService.findAll();

        return ResponseEntity.ok(usuarios);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário - ID", description = "Busca usuário pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca concluída."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrada."),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<UsuarioDTOResponse> obterUsuarioPorId(@PathVariable UUID id) {
        UsuarioDTOResponse usuarioDTOResponse = usuarioService.findById(id);
        return ResponseEntity.ok(usuarioDTOResponse);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário - ID", description = "Atualizar usuário por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualizado."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrada."),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<UsuarioDTOResponse> atualizarUsuario(
            @PathVariable UUID id,
            @Valid @RequestBody UsuarioDTORequest usuarioDTORequest) {
        UsuarioDTOResponse usuarioDTOResponse = usuarioService.update(id, usuarioDTORequest);
        return ResponseEntity.ok(usuarioDTOResponse);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário - ID", description = "Deletar usuário pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deletado."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Void> deletarUsuario(@PathVariable UUID id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{id}/foto-perfil")
    @Operation(summary = "Adicionar foto de perfil", description = "Adiciona foto de perfil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "409", description = "Conflito de dados"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<UsuarioDTOResponse> adicionarFotoPerfil(
            @PathVariable UUID id,
            @RequestParam("foto") MultipartFile foto) {
        UsuarioDTOResponse usuario = usuarioService.adicionarFotoPerfil(id, foto);
        return ResponseEntity.ok(usuario);
    }


    @DeleteMapping("/{id}/foto-perfil")
    @Operation(summary = "Deletar foto - ID", description = "Deletar foto pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deletado."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "403", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Foto não encontrada."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Void> removerFotoPerfil(@PathVariable UUID id) {
        usuarioService.removerFotoPerfil(id);
        return ResponseEntity.noContent().build();
    }


}
