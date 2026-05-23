package PF.SerratecFlix.Repository;

import PF.SerratecFlix.Domain.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, UUID> {
}
