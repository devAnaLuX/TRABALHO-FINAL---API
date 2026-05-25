package PF.SerratecFlix.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PF.SerratecFlix.DTO.Request.SerieDTORequest;
import PF.SerratecFlix.DTO.Response.SerieDTOResponse;
import PF.SerratecFlix.Domain.Serie;
import PF.SerratecFlix.Repository.SerieRepository;
import jakarta.validation.Valid;

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
 
        return toResponseDTO(serieRepository.save(serie));
    }
 
    public List<Object> listarTodos(){
        return serieRepository.findAll()
                .stream()
                .map(this::toDTOResponse)
                .collect(Collectors.toList());
    }
 
    public SerieDTOResponse buscarPorId(UUID id) {
        Serie serie = serieRepository.findById(id)
                .orElseThrow(() -> new RecurseNotFoundException("Série", id));
        return toResponseDTO(serie);
    }
 
    public SerieDTOResponse atualizar(UUID id, SerieDTORequest dto) {
        Serie serie = serieRepository.findById(id)
                .orElseThrow(() -> new RecurseNotFoundException("Série", id));
 
        serie.setTitulo(dto.getTitulo());
        serie.setDescricao(dto.getDescricao());
        serie.setTemporadas(dto.getTemporadas());
        serie.setEpisodios(dto.getEpisodios());
        serie.setDataLancamento(dto.getDataLancamento());
 
        return toResponseDTO(serieRepository.save(serie));
    }
   
    public void deletar(UUID id) {
        if (!serieRepository.existsById(id)) {
            throw new RecurseNotFoundException("Série", id);
        }
        serieRepository.deleteById(id);
    }
 
    private SerieDTOResponse toResponseDTO(Serie s) {
        return new SerieDTOResponse(
                s.getId(),
                s.getTitulo(),
                s.getDescricao(),
                s.getTemporadas(),
                s.getEpisodios(),
                s.getDataLancamento(),
                s.getNotaMedia(),
                s.getCategorias().stream()
                        .map(c -> new CategoriaResponseDTO(c.getId(), c.getNome()))
                        .collect(Collectors.toSet())
        );
    }
    
}
