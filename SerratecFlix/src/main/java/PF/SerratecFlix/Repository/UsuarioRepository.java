package PF.SerratecFlix.Repository;

import PF.SerratecFlix.Domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import java.util.Optional;
import java.util.UUID;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    // Métodos de consulta personalizados usando JPQL

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByNome(String nome);

}
