package PF.SerratecFlix.Repository;

import PF.SerratecFlix.Domain.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Collection;
import java.util.UUID;

@Repository
public interface SerieRepository extends JpaRepository<Serie, UUID> {

	Collection<Serie> findByCategorias_Id(UUID categoriaId);
	
	Page<Serie> findAll(Pageable pageable);
}
