package PF.SerratecFlix.DTO.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({"id", "nome", "email", "username", "fotoPerfilUrl", "dataCriacao"})
public class UsuarioDTOResponse {
    
    @Schema(description = "ID do usuário")
    private UUID id;
    
    @Schema(description = "Nome do usuário")
    private String nome;
    
    @Schema(description = "Email do usuário")
    private String email;
    
    @Schema(description = "Nome de usuário")
    private String username;
    
    @Schema(description = "URL da foto do perfil")
    private String fotoPerfilUrl;

    @Schema(description = "Data de criação da conta")
    private LocalDateTime dataCriacao;



}