package PF.SerratecFlix.Service;

import java.util.stream.Collectors;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PF.SerratecFlix.DTO.Request.FilmeDTORequest;
import PF.SerratecFlix.DTO.Response.FilmeDTOResponse;
import PF.SerratecFlix.Domain.Filme;
import PF.SerratecFlix.Exception.ResourceNotFoundException;
import PF.SerratecFlix.Repository.FilmeRepository;

@Service
public class FilmeService {
 
    @Autowired
    private FilmeRepository filmeRepository;
 
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
 
    public FilmeDTOResponse cadastrar(FilmeDTORequest request) {
        Filme f = new Filme();
        f.setTitulo(request.getTitulo());
        f.setDescricao(request.getDescricao());
        f.setDuracao(request.getDuracao());
        f.setDataLancamento(request.getDataLancamento());
        f.setClassificacaoIndicativa(request.getClassificacaoIndicativa());
        f.setNotaMedia(request.getNotaMedia());
        return toResponse(filmeRepository.save(f));
    }
 
    public FilmeDTOResponse atualizar(UUID id, FilmeDTORequest request) {
         Filme f = filmeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Filme com ID " + id + " não encontrado"));
        f.setTitulo(request.getTitulo());
        f.setDescricao(request.getDescricao());
        f.setDuracao(request.getDuracao());
        f.setDataLancamento(request.getDataLancamento());
        f.setClassificacaoIndicativa(request.getClassificacaoIndicativa());
        f.setNotaMedia(request.getNotaMedia());
        return toResponse(filmeRepository.save(f));
    }
 
    public void apagar(UUID id) {
        if (!filmeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Filme com ID " + id + " não encontrado");
        }
        filmeRepository.deleteById(id);
    }
 
    private FilmeDTOResponse toResponse(Filme f) {
        return new FilmeDTOResponse(
            f.getId(),
            f.getTitulo(),
            f.getDescricao(),
            f.getDuracao(),
            f.getDataLancamento(),
            f.getClassificacaoIndicativa(),
            f.getNotaMedia(),
            f.getAvaliacoes(),
            f.getCategorias(),
            f.getListasFavoritos()
        );
    }
}
