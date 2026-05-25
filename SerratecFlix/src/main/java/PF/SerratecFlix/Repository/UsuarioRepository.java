package PF.SerratecFlix.Repository;

import PF.SerratecFlix.Domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Métodos de consulta personalizados usando JPQL

    @Param("email")
    @Return
    Operational<Usuario> findByEmail(String email);

    @Param("nome")
    @Return
    Operational<Usuario> findByNome(String nome);

}
