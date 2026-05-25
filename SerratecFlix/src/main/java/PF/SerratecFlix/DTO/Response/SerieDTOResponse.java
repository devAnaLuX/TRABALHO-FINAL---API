package PF.SerratecFlix.DTO.Response;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SerieDTOResponse {
		 
	    private UUID id;
	    private String titulo;
	    private String descricao;
	    private Integer temporadas;
	    private Integer episodios;
	    private LocalDate dataLancamento;
	    private Double notaMedia;
	    private Set<CategoriaResponseDTO> categorias;
	 
	    public SerieDTOResponse() {
	    }

}

