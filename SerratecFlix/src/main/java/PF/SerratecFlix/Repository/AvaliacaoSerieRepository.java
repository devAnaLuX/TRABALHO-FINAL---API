package PF.SerratecFlix.Repository;

import PF.SerratecFlix.Domain.AvaliacaoSerie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AvaliacaoSerieRepository extends JpaRepository<AvaliacaoSerie, UUID> {
	
	List<AvaliacaoSerie> findBySerieId(UUID serieId);
	 
    List<AvaliacaoSerie> findByUsuarioId(UUID usuarioId);
 
    boolean existsByUsuarioIdAndSerieId(UUID usuarioId, UUID serieId);
}
