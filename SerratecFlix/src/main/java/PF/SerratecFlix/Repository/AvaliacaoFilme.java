package PF.SerratecFlix.Repository;

import PF.SerratecFlix.Domain.AvaliacaoSerie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AvaliacaoFilme extends JpaRepository<AvaliacaoSerie, UUID> {
}
