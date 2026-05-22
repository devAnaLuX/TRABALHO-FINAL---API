package PF.SerratecFlix.Repository;

import PF.SerratecFlix.Domain.Filme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FilmeRepository extends JpaRepository<Filme, UUID> {
}
