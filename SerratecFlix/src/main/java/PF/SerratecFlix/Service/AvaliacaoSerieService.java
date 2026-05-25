package PF.SerratecFlix.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PF.SerratecFlix.DTO.Request.AvaliacaoSerieDTORequest;
import PF.SerratecFlix.DTO.Response.AvaliacaoSerieDTOResponse;
import PF.SerratecFlix.Domain.AvaliacaoSerie;
import PF.SerratecFlix.Domain.Serie;
import PF.SerratecFlix.Domain.Usuario;
import PF.SerratecFlix.Repository.AvaliacaoSerieRepository;
import PF.SerratecFlix.Repository.SerieRepository;
import PF.SerratecFlix.Repository.UsuarioRepository;


@Service
public class AvaliacaoSerieService {

    @Autowired
    private AvaliacaoSerieRepository avaliacaoSerieRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SerieRepository serieRepository;

    public AvaliacaoSerieDTOResponse criar(AvaliacaoSerieDTORequest dto) { 
    	Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário", dto.getUsuarioId()));

        Serie serie = serieRepository.findById(dto.getSerieId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Série", dto.getSerieId()));

        AvaliacaoSerie avaliacao = new AvaliacaoSerie();
        avaliacao.setNota(dto.getNota());          
        avaliacao.setComentario(dto.getComentario());
        avaliacao.setUsuario(usuario);
        avaliacao.setSerie(serie);

        return toResponseDTO(avaliacaoSerieRepository.save(avaliacao));
    }

    public List<AvaliacaoSerieDTOResponse> listarTodos() {
        return avaliacaoSerieRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public AvaliacaoSerieDTOResponse buscarPorId(UUID id) {
        AvaliacaoSerie avaliacao = avaliacaoSerieRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Avaliação de série", id));
        return toResponseDTO(avaliacao);
    }

    public List<AvaliacaoSerieDTOResponse> listarPorSerie(UUID serieId) {
        return avaliacaoSerieRepository.findBySerieId(serieId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<AvaliacaoSerieDTOResponse> listarPorUsuario(UUID usuarioId) {
        return avaliacaoSerieRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public AvaliacaoSerieDTOResponse atualizar(UUID id, AvaliacaoSerieDTORequest dto) {
        AvaliacaoSerie avaliacao = avaliacaoSerieRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Avaliação de série", id));

        avaliacao.setNota(dto.getNota());
        avaliacao.setComentario(dto.getComentario());

        return toResponseDTO(avaliacaoSerieRepository.save(avaliacao));
    }

    public void deletar(UUID id) {
        if (!avaliacaoSerieRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Avaliação de série", id);
        }
        avaliacaoSerieRepository.deleteById(id);
    }

    private AvaliacaoSerieDTOResponse toResponseDTO(AvaliacaoSerie a) {
        return new AvaliacaoSerieDTOResponse(a);
        		
    
    }

    
}