package PF.SerratecFlix.Repository;

import PF.SerratecFlix.Domain.ListaFavoritos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ListaFavoritosRepository extends JpaRepository<ListaFavoritos, UUID> {
}
