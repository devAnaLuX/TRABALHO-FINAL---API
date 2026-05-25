package PF.SerratecFlix.Repository;

import PF.SerratecFlix.DTO.Response.SerieDTOResponse.SerieResponseDTO;
import PF.SerratecFlix.Domain.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface SerieRepository extends JpaRepository<Serie, UUID> {

	Collection<SerieResponseDTO> findByCategoriaId(UUID categoriaId);
}
