package PF.SerratecFlix.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PF.SerratecFlix.Domain.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {
	
	Optional<Categoria> findByNomeIgnoreCase(String nome);
}
