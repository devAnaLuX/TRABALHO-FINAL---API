package PF.SerratecFlix.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import PF.SerratecFlix.DTO.Request.UsuarioDTORequest;
import PF.SerratecFlix.DTO.Response.UsuarioDTOResponse;
import PF.SerratecFlix.Domain.Usuario;
import PF.SerratecFlix.Repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    // Métodos para criar, atualizar, excluir e buscar usuários
    
    //retorna todos os usuários
    public List<UsuarioDTOResponse> findAll() {
       return usuaruoRepository.findAll()
       .stream()
       .map(this::toDTOResponse)
       .collect(Collectors.toList());
    }

    //adição de um novo usuario
    @Param
    @returns
    public UsuarioDTOResponse save(UsuarioDTORequest usuarioDTORequest) {
        usuario usuario = toEntity(usuarioDTORequest);
        usuario = usuarioRepository.save(usuario);
        return toDTOResponse(usuario);
    }

    //atualização de um usuario existente
    @Param id
    @Param usuarioDTORequest
    @Return

    public UsuarioDTOResponse update(Long id, UsuarioDTORequest usuarioDTORequest) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        usuario.setNome(usuarioDTORequest.getNome());
        usuario.setEmail(usuarioDTORequest.getEmail());
        usuario.setUsername(usuarioDTORequest.getUsername());
        usuario.setSenha(usuarioDTORequest.getSenha());

        usuario = usuarioRepository.save(usuario);
        return toDTOResponse(usuario);
    }

    //exclusão de um usuario existente
    @Param id
    @Throws ResourceNotFoundException

    public void deleteById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new UsuarioService().ResourceNotFoundException("Usuário não encontrado com id: " + id));
        usuarioRepository.delete(usuario);
    }

}

    