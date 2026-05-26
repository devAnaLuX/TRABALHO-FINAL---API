package PF.SerratecFlix.Service;
 
import PF.SerratecFlix.Domain.Usuario;
import PF.SerratecFlix.DTO.Request.LoginDTORequest;
import PF.SerratecFlix.DTO.Response.LoginDTOResponse;
import PF.SerratecFlix.Repository.UsuarioRepository;
import PF.SerratecFlix.Security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
 
@Service
@RequiredArgsConstructor
public class AuthService {
 
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
 
    public LoginDTOResponse login(LoginDTORequest dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha())
        );
 
        UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getEmail());
        String token = jwtService.gerarToken(userDetails);
 
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow();
 
        return new LoginDTOResponse(token, usuario.getEmail(), usuario.getNome());
    }
}