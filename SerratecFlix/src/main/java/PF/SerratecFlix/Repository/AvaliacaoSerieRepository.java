package PF.SerratecFlix.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import PF.SerratecFlix.Domain.AvaliacaoSerie;

@Repository
public interface AvaliacaoSerieRepository extends JpaRepository<AvaliacaoSerie, UUID> {
	
	List<AvaliacaoSerie> findBySerieId(UUID serieId);
	 
    List<AvaliacaoSerie> findByUsuarioId(UUID usuarioId);
 
    boolean existsByUsuarioIdAndSerieId(UUID usuarioId, UUID serieId);
    
    @Query("SELECT SUM(a.nota) FROM AvaliacaoSerie a WHERE a.serie.id = :serieId")
	Double somarNotasDaSerie(@Param("serieId") UUID serieId);
    
    @Query("SELECT COUNT(a) FROM AvaliacaoSerie a WHERE a.serie.id = :serieId")
	Long contarAvaliacoesDaSerie(@Param("serieId") UUID serieId);
}
