package PF.SerratecFlix.Repository;


import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import PF.SerratecFlix.Domain.AvaliacaoFilme;

@Repository
public interface AvaliacaoFilmeRepository extends JpaRepository<AvaliacaoFilme, UUID> {
	
	@Query("SELECT SUM(a.nota) FROM AvaliacaoFilme a WHERE a.filme.id = :filmeId")
	Double somarNotasDoFilme(@Param("filmeId") UUID filmeId);
	
	@Query("SELECT COUNT(a) FROM AvaliacaoFilme a WHERE a.filme.id = :filmeId")
	Long contarAvaliacoesDoFilme(@Param("filmeId") UUID filmeId);
	
}