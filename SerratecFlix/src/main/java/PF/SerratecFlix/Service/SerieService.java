package PF.SerratecFlix.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PF.SerratecFlix.DTO.Request.SerieDTORequest;
import PF.SerratecFlix.DTO.Response.SerieDTOResponse;
import PF.SerratecFlix.Domain.Serie;
import PF.SerratecFlix.Exception.ResourceNotFoundException;
import PF.SerratecFlix.Repository.SerieRepository;

@Service
public class SerieService {

    @Autowired
    private SerieRepository serieRepository;

    public SerieDTOResponse criar(SerieDTORequest dto) {
        Serie serie = new Serie();
        serie.setTitulo(dto.getTitulo());
        serie.setDescricao(dto.getDescricao());
        serie.setTemporadas(dto.getTemporadas());
        serie.setEpisodios(dto.getEpisodios());
        serie.setDataLancamento(dto.getDataLancamento());

        return new SerieDTOResponse(serieRepository.save(serie));
    }

    public List<SerieDTOResponse> listarTodos() {
        return serieRepository.findAll()
                .stream()
                .map(SerieDTOResponse::new)
                .collect(Collectors.toList());
    }

    public SerieDTOResponse buscarPorId(UUID id) {
        Serie serie = serieRepository.findById(id)
        		.orElseThrow(() -> new ResourceNotFoundException("Série não encontrada com id: " + id));
        return new SerieDTOResponse(serie);
    }

    public SerieDTOResponse atualizar(UUID id, SerieDTORequest dto) {
        Serie serie = serieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Série não encontrada com id: " + id));

        serie.setTitulo(dto.getTitulo());
        serie.setDescricao(dto.getDescricao());
        serie.setTemporadas(dto.getTemporadas());
        serie.setEpisodios(dto.getEpisodios());
        serie.setDataLancamento(dto.getDataLancamento());

        return new SerieDTOResponse(serieRepository.save(serie));
    }

    public void deletar(UUID id) {
        if (!serieRepository.existsById(id)) {
            throw new ResourceNotFoundException("Série não encontrada com id: " + id);
        }
        serieRepository.deleteById(id);
    }
}
