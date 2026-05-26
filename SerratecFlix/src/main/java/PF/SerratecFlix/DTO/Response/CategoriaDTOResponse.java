package PF.SerratecFlix.DTO.Response;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import PF.SerratecFlix.Domain.Categoria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder
({ "id", "nome", "descricao"})
public class CategoriaDTOResponse {
	
    private UUID id;
    private String nome;
    private String descricao;
    
    public CategoriaDTOResponse(Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
        this.descricao = categoria.getDescricao();
    }
}
