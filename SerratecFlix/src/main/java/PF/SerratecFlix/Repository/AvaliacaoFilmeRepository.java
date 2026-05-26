package PF.SerratecFlix.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PF.SerratecFlix.Domain.AvaliacaoFilme;

@Repository
public interface AvaliacaoFilmeRepository extends JpaRepository<AvaliacaoFilme, UUID> {
}
