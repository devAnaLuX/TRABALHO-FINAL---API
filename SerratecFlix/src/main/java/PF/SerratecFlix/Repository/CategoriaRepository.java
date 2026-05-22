package PF.SerratecFlix.Repository;

import PF.SerratecFlix.Domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {
}
