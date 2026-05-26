package PF.SerratecFlix.Repository;

import PF.SerratecFlix.DTO.Request.SerieDTORequest;
import PF.SerratecFlix.Domain.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface SerieRepository extends JpaRepository<Serie, UUID> {

	Collection<SerieDTORequest> findByCategoriaId(UUID categoriaId);
}
