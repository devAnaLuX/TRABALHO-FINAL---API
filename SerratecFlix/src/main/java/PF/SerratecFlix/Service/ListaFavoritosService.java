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

    @Autowired
    private GroqService groqService;

    @Autowired
    private EmailService emailService;

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


    public String recomendar(UUID id){
        ListaFavoritos listaFavoritos = listaFavoritosRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada."));

        List<String> filmes = listaFavoritos.getFilmes().stream()
                .map(Filme::getTitulo).toList();

        List<String> series = listaFavoritos.getSeries().stream()
                .map(Serie::getTitulo).toList();

        String prompt = String.format("""
            Sou um usuário de uma plataforma de streaming.
            Minha lista de filmes favoritos: %s
            Minha lista de séries favoritas: %s
            Com base nisso, me recomende 3 filmes e 3 séries similares que eu possa gostar.
            Seja objetivo e explique brevemente o motivo de cada recomendação.
            """, filmes, series);

        String recomendacao = groqService.obterRecomendacoes(prompt);

        String html = String.format("""
    <div style="font-family: 'Poppins', sans-serif; max-width: 600px; margin: auto; background: #1a1a2e; color: #ffffff; padding: 30px; border-radius: 10px;">
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
        <h1 style="color: #e94560; text-align: center; font-weight: 600;">🎬 SerratecFlix</h1>
        <h2 style="color: #ffffff; text-align: center; font-weight: 400;">Suas Recomendações Personalizadas</h2>
        <hr style="border-color: #e94560;">
        <div style="background: #16213e; padding: 20px; border-radius: 8px; margin-top: 20px;">
            <p style="color: #a8a8b3; font-size: 14px; font-weight: 300;">Com base nos seus favoritos, separamos isso para você:</p>
            <p style="color: #ffffff; line-height: 1.8; white-space: pre-line;">%s</p>
        </div>
        <p style="text-align: center; color: #a8a8b3; font-size: 12px; margin-top: 20px;">SerratecFlix © 2026</p>
    </div>
    """, recomendacao);

        try{
            String emailUsuario = listaFavoritos.getUsuario().getEmail();
            emailService.enviarEmail("luisamelo2807@gmail.com",
                    "\uD83C\uDFAC Suas recomendações SerratecFlix!",
                    html);
        } catch (Exception e) {
            System.out.println("Erro ao enviar e-mail: "+e.getMessage());
        }

        return recomendacao;

    }
}
