package PF.SerratecFlix.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import PF.SerratecFlix.DTO.Request.FilmeDTORequest;
import PF.SerratecFlix.DTO.Response.FilmeDTOResponse;
import PF.SerratecFlix.Domain.Categoria;
import PF.SerratecFlix.Domain.Filme;
import PF.SerratecFlix.Exception.ResourceNotFoundException;
import PF.SerratecFlix.Repository.CategoriaRepository;
import PF.SerratecFlix.Repository.FilmeRepository;

@Service
public class FilmeService {
 
    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;
 
    public List<FilmeDTOResponse> listar() {
        return filmeRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
 
    public FilmeDTOResponse buscarPorId(UUID id) {
        return toResponse(filmeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Filme com ID " + id + " não encontrado")));
    }
 
    public List<FilmeDTOResponse> buscarPorTitulo(String titulo) {
        return filmeRepository.findByTituloContainingIgnoreCase(titulo)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public Page<FilmeDTOResponse> listarPaginado(Pageable pageable) {
        return filmeRepository.findAll(pageable)
                .map(FilmeDTOResponse::new);
    }
 
    @Transactional
    public FilmeDTOResponse cadastrar(FilmeDTORequest request) {
        Filme f = new Filme();
        preencherDados(f, request);
        return toResponse(filmeRepository.save(f));
    }
 
    @Transactional
    public FilmeDTOResponse atualizar(UUID id, FilmeDTORequest request) {
         Filme f = filmeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Filme com ID " + id + " não encontrado"));
        preencherDados(f, request);
        return toResponse(filmeRepository.save(f));
    }
 
    public void apagar(UUID id) {
        if (!filmeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Filme com ID " + id + " não encontrado");
        }
        filmeRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public List<FilmeDTOResponse> listarPorCategoria(UUID categoriaId) {
        return filmeRepository.findByCategorias_Id(categoriaId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private void preencherDados(Filme f, FilmeDTORequest request) {
        f.setTitulo(request.getTitulo());
        f.setDescricao(request.getDescricao());
        f.setDuracao(request.getDuracao());
        f.setDataLancamento(request.getDataLancamento());
        f.setClassificacaoIndicativa(request.getClassificacaoIndicativa());
        f.setNotaMedia(request.getNotaMedia());

        if (request.getCategoriaIds() != null && !request.getCategoriaIds().isEmpty()) {
            Set<Categoria> categorias = request.getCategoriaIds().stream()
                    .map(categoriaId -> categoriaRepository.findById(categoriaId)
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Categoria com ID " + categoriaId + " não encontrada")))
                    .collect(Collectors.toCollection(HashSet::new));
            f.setCategorias(categorias);
        }
    }
 
    private FilmeDTOResponse toResponse(Filme f) {
        return new FilmeDTOResponse(f);
    }
}
