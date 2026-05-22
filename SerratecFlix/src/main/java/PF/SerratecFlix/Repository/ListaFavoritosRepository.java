package PF.SerratecFlix.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ListaFavoritosRepository extends JpaRepository<ListaFavoritosRepository, UUID> {
}
