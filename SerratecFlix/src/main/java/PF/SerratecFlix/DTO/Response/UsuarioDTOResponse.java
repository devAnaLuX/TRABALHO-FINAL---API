package PF.SerratecFlix.DTO.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.UUID;
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

    public UsuarioDTOResponse() {
    }

    @Schema(description = "Construtor para criar um objeto UsuarioDTOResponse")
    public UsuarioDTOResponse(UUID id, String nome, String email, String username, String fotoPerfilUrl, LocalDateTime dataCriacao) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.username = username;
        this.fotoPerfilUrl = fotoPerfilUrl;
        this.dataCriacao = dataCriacao;
    }

    // Getters e Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
