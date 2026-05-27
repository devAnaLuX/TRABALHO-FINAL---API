package PF.SerratecFlix.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import PF.SerratecFlix.DTO.Request.AvaliacaoSerieDTORequest;
import PF.SerratecFlix.DTO.Response.AvaliacaoSerieDTOResponse;
import PF.SerratecFlix.Domain.AvaliacaoSerie;
import PF.SerratecFlix.Domain.Serie;
import PF.SerratecFlix.Domain.Usuario;
import PF.SerratecFlix.Exception.ResourceNotFoundException;
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
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + dto.getUsuarioId()));

        Serie serie = serieRepository.findById(dto.getSerieId())
                .orElseThrow(() -> new ResourceNotFoundException("Série não encontrada com id: " + dto.getSerieId()));

        AvaliacaoSerie avaliacao = new AvaliacaoSerie();
        avaliacao.setNota(dto.getNota());
        avaliacao.setComentario(dto.getComentario());
        avaliacao.setUsuario(usuario);
        avaliacao.setSerie(serie);

        return new AvaliacaoSerieDTOResponse(avaliacaoSerieRepository.save(avaliacao));
    }

    public List<AvaliacaoSerieDTOResponse> listarTodos() {
        return avaliacaoSerieRepository.findAll()
                .stream()
                .map(AvaliacaoSerieDTOResponse::new)
                .collect(Collectors.toList());
    }

    public AvaliacaoSerieDTOResponse buscarPorId(UUID id) {
        AvaliacaoSerie avaliacao = avaliacaoSerieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação não encontrada com id: " + id));
        return new AvaliacaoSerieDTOResponse(avaliacao);
    }

    public List<AvaliacaoSerieDTOResponse> listarPorSerie(UUID serieId) {
        return avaliacaoSerieRepository.findBySerieId(serieId)
                .stream()
                .map(AvaliacaoSerieDTOResponse::new)
                .collect(Collectors.toList());
    }

    public List<AvaliacaoSerieDTOResponse> listarPorUsuario(UUID usuarioId) {
        return avaliacaoSerieRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(AvaliacaoSerieDTOResponse::new)
                .collect(Collectors.toList());
    }

    public AvaliacaoSerieDTOResponse atualizar(UUID id, AvaliacaoSerieDTORequest dto) {
        AvaliacaoSerie avaliacao = avaliacaoSerieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação não encontrada com id: " + id));

        avaliacao.setNota(dto.getNota());
        avaliacao.setComentario(dto.getComentario());

        return new AvaliacaoSerieDTOResponse(avaliacaoSerieRepository.save(avaliacao));
    }

    public void deletar(UUID id) {
        if (!avaliacaoSerieRepository.existsById(id)) {
            throw new ResourceNotFoundException("Avaliação não encontrada com id: " + id);
        }
        avaliacaoSerieRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
	public Double obterNotaMediaDoSerie(UUID serieId) {
		if (!serieRepository.existsById(serieId)) {
			throw new ResourceNotFoundException("Série não encontrada com id: " + serieId);
		}
		
		Long totalAvaliacoes = avaliacaoSerieRepository.contarAvaliacoesDaSerie(serieId);
		
		if (totalAvaliacoes == 0) {
			return 0.0;
		}
		
		Double somaNotas = avaliacaoSerieRepository.somarNotasDaSerie(serieId);
		
		return somaNotas / totalAvaliacoes;
	}
    
}