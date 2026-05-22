package PF.SerratecFlix.Repository;

import PF.SerratecFlix.Domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
}
