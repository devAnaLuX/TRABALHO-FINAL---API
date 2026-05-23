package PF.SerratecFlix.Repository;

import PF.SerratecFlix.Domain.AvaliacaoSerie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AvaliacaoFilme extends JpaRepository<AvaliacaoSerie, UUID> {
}
