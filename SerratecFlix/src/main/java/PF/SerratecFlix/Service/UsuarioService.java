package PF.SerratecFlix.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    
    @Value("${app.upload.dir}")
    private String uploadDir;


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

    // Foto de perfil
    public UsuarioDTOResponse adicionarFotoPerfil(
            UUID id,
            MultipartFile foto) {

        validarArquivoImagem(foto);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Usuário não encontrado com id: " + id));

        // Remove foto antiga
        if (usuario.getFotoPerfilUrl() != null) {
            deletarArquivoDoDisco(usuario.getFotoPerfilUrl());
        }

        // Nome único
        String extensao = obterExtensao(foto.getOriginalFilename());

        String nomeArquivo =
                UUID.randomUUID().toString() + "." + extensao;

        // Salvar no disco
        salvarArquivoNoDisco(foto, nomeArquivo);

        // Atualiza URL
        usuario.setFotoPerfilUrl("/fotos-perfil/" + nomeArquivo);

        usuario = usuarioRepository.save(usuario);

        return toDTOResponse(usuario);
    }

    // Métodos auxiliares
    private void validarArquivoImagem(MultipartFile foto) {
        if (foto == null || foto.isEmpty()) {
            throw new IllegalArgumentException("O arquivo não pode ser vazio");
        }
 
        String contentType = foto.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("O arquivo deve ser uma imagem (JPEG, PNG, etc.)");
        }
 
        // Valida extensão permitida
        String extensao = obterExtensao(foto.getOriginalFilename());
        if (!List.of("jpg", "jpeg", "png", "webp").contains(extensao.toLowerCase())) {
            throw new IllegalArgumentException("Formato não permitido. Use: JPG, JPEG, PNG ou WEBP");
        }
    }
 
    private void salvarArquivoNoDisco(MultipartFile foto, String nomeArquivo) {
        try {
            Path diretorio = Paths .get(uploadDir);
 
            // Cria o diretório se não existir
            if (!Files.exists(diretorio)) {
                Files.createDirectories(diretorio);
            }
 
            Path destino = diretorio.resolve(nomeArquivo);
            Files.copy(foto.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
 
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar o arquivo: " + e.getMessage());
        }
    }
 
    private void deletarArquivoDoDisco (String fotoPerfilUrl) {
        try {
            // Extrai o nome do arquivo da URL
            String nomeArquivo = fotoPerfilUrl.replace("/fotos-perfil/", "");
            Path arquivo = Paths.get(uploadDir).resolve(nomeArquivo);
 
            Files.deleteIfExists(arquivo);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao deletar o arquivo: " + e.getMessage());
        }
    }
 
    private String obterExtensao(String nomeArquivo) {
        if (nomeArquivo == null || !nomeArquivo.contains(".")) {
            throw new IllegalArgumentException("Arquivo sem extensão válida");
        }
        return nomeArquivo.substring(nomeArquivo.lastIndexOf(".") + 1);
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

    