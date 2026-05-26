package PF.SerratecFlix.Security;
 
import PF.SerratecFlix.Domain.Usuario;
import PF.SerratecFlix.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
 
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
 
    private final UsuarioRepository usuarioRepository;
 
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + email));
 
        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .roles("USER")
                .build();
    }
}