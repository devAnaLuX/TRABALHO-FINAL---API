package PF.SerratecFlix.Service;

import PF.SerratecFlix.DTO.Request.ListaFavoritosDTORequest;
import PF.SerratecFlix.DTO.Response.ListaFavoritosDTOResponse;
import PF.SerratecFlix.Domain.Filme;
import PF.SerratecFlix.Domain.ListaFavoritos;
import PF.SerratecFlix.Domain.Serie;
import PF.SerratecFlix.Domain.Usuario;
import PF.SerratecFlix.Exception.ResourceNotFoundException;
import PF.SerratecFlix.Repository.FilmeRepository;
import PF.SerratecFlix.Repository.ListaFavoritosRepository;
import PF.SerratecFlix.Repository.SerieRepository;
import PF.SerratecFlix.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ListaFavoritosService {

    @Autowired
    private ListaFavoritosRepository listaFavoritosRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private SerieRepository serieRepository;

    public List<ListaFavoritosDTOResponse> buscar(){
        return listaFavoritosRepository.findAll()
                .stream()
                .map(ListaFavoritosDTOResponse::new)
                .toList();
    }

    public ListaFavoritosDTOResponse buscarPorId(UUID id){
        return listaFavoritosRepository.findById(id)
                .map(ListaFavoritosDTOResponse::new)
                .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrado com id: "+id));
    }

    public ListaFavoritosDTOResponse adicionar(ListaFavoritosDTORequest listaFavoritosNew){

        Usuario usuario = usuarioRepository.findById(listaFavoritosNew.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: "+listaFavoritosNew.getUsuarioId()));

        Set<Filme> filmes = listaFavoritosNew.getFilmes()
                .stream()
                .map(filmeId -> filmeRepository.findById(filmeId)
                        .orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado.")))
                .collect(Collectors.toCollection(HashSet::new));

        Set<Serie> series = listaFavoritosNew.getSeries()
                .stream()
                .map(serieId -> serieRepository.findById(serieId)
                        .orElseThrow(() -> new ResourceNotFoundException("Serie não encontrada.")))
                .collect(Collectors.toCollection(HashSet::new));

        ListaFavoritos listaFavoritos = new ListaFavoritos();
        listaFavoritos.setNomeLista(listaFavoritosNew.getNomeLista());
        listaFavoritos.setPrivada(listaFavoritosNew.getPrivada());
        listaFavoritos.setUsuario(usuario);
        listaFavoritos.setFilmes(filmes);
        listaFavoritos.setSeries(series);

        listaFavoritosRepository.save(listaFavoritos);
        return new ListaFavoritosDTOResponse(listaFavoritos);
    }

    public ListaFavoritosDTOResponse atualizar(UUID id, ListaFavoritosDTORequest listaFavoritosDTO){
        ListaFavoritos listaFavoritos = listaFavoritosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrado com id: "+id));

        Usuario usuario = usuarioRepository.findById(listaFavoritosDTO.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: "+listaFavoritosDTO.getUsuarioId()));

        Set<Filme> filmes = listaFavoritosDTO.getFilmes()
                .stream()
                .map(filmeId -> filmeRepository.findById(filmeId)
                        .orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado.")))
                .collect(Collectors.toCollection(HashSet::new));

        Set<Serie> series = listaFavoritosDTO.getSeries()
                .stream()
                .map(serieId -> serieRepository.findById(serieId)
                        .orElseThrow(() -> new ResourceNotFoundException("Serie não encontrada.")))
                .collect(Collectors.toCollection(HashSet::new));

        listaFavoritos.setNomeLista(listaFavoritosDTO.getNomeLista());
        listaFavoritos.setPrivada(listaFavoritosDTO.getPrivada());
        listaFavoritos.setUsuario(usuario);
        listaFavoritos.setFilmes(filmes);
        listaFavoritos.setSeries(series);

        listaFavoritosRepository.save(listaFavoritos);
        return new ListaFavoritosDTOResponse(listaFavoritos);
    }

    public void deletar(UUID id){
        if (!listaFavoritosRepository.existsById(id)){
            throw new ResourceNotFoundException("Lista não encontrado com id: "+id);
        }
        listaFavoritosRepository.deleteById(id);
    }

}
