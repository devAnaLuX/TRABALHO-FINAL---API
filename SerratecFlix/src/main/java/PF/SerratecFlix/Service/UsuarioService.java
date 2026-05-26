package PF.SerratecFlix.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
 
import PF.SerratecFlix.DTO.Request.UsuarioDTORequest;
import PF.SerratecFlix.DTO.Response.UsuarioDTOResponse;
import PF.SerratecFlix.Domain.Usuario;
import PF.SerratecFlix.Exception.ResourceNotFoundException;
import PF.SerratecFlix.Repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    // Métodos para criar, atualizar, excluir e buscar usuários
    
    //retorna todos os usuários
    public List<UsuarioDTOResponse> findAll() {
       return usuarioRepository.findAll()
       .stream()
       .map(this::toDTOResponse)
       .collect(Collectors.toList());
    }

    //adição de um novo usuario
    public UsuarioDTOResponse save(UsuarioDTORequest usuarioDTORequest) {
        Usuario usuario = toEntity(usuarioDTORequest);

         // Criptografa senha
        encryptPassword(usuario, usuarioDTORequest);

        usuario = usuarioRepository.save(usuario);

        return toDTOResponse(usuario);
    }


    //atualização de um usuario existente
       public UsuarioDTOResponse update(UUID id, UsuarioDTORequest usuarioDTORequest) {
        Usuario usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> 
        new ResourceNotFoundException("Usuário não encontrado" + id));
        usuario.setNome(usuarioDTORequest.getNome());
        usuario.setEmail(usuarioDTORequest.getEmail());
        usuario.setUsername(usuarioDTORequest.getUsername());
        usuario.setSenha(usuarioDTORequest.getSenha());


    //Atualização da senha criptografada
        encryptPassword(usuario, usuarioDTORequest);

            usuario = usuarioRepository.save(usuario);

         return toDTOResponse(usuario);
    }

     // Método para criptografar a senha do usuário
    private void encryptPassword(Usuario usuario, UsuarioDTORequest dto) {
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
    
    }

    //exclusão de um usuario existente
    public void deleteById(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + id));
        usuarioRepository.delete(usuario);
    }

    // Converte Entity → DTO Response
    private UsuarioDTOResponse toDTOResponse(Usuario usuario) {
        return UsuarioDTOResponse.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .username(usuario.getUsername())
                // fotoPerfilUrl não disponível no modelo Usuario; omitido
                .dataCriacao(usuario.getDataCriacao())
                .build();
    }
 
    // Converte DTO Request → Entity
    private Usuario toEntity(UsuarioDTORequest dto) {
        return Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .username(dto.getUsername())
                .senha(dto.getSenha())
                .build();
    }

    
}

    