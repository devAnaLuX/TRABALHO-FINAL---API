package PF.SerratecFlix.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import PF.SerratecFlix.Exception.ResourceNotFoundException;
import PF.SerratecFlix.DTO.Request.AvaliacaoFilmeDTORequest;
import PF.SerratecFlix.DTO.Response.AvaliacaoFilmeDTOResponse;
import PF.SerratecFlix.Domain.AvaliacaoFilme;
import PF.SerratecFlix.Domain.Filme;
import PF.SerratecFlix.Domain.Usuario;
import PF.SerratecFlix.Repository.AvaliacaoFilmeRepository;
import PF.SerratecFlix.Repository.FilmeRepository;
import PF.SerratecFlix.Repository.UsuarioRepository;

@Service
public class AvaliacaoFilmeService {
	

		@Autowired
		private AvaliacaoFilmeRepository avaliacaoFilmeRepository;

		@Autowired
		private UsuarioRepository usuarioRepository;

		@Autowired
		private FilmeRepository filmeRepository;
		
		public List<AvaliacaoFilmeDTOResponse> findAll() {
			List<AvaliacaoFilme> avaliacoes = avaliacaoFilmeRepository.findAll();
			List<AvaliacaoFilmeDTOResponse> dtos = new ArrayList<>();
			for (AvaliacaoFilme avaliacao : avaliacoes) {
				dtos.add(new AvaliacaoFilmeDTOResponse(avaliacao));
			}
			return dtos;
		}
		public AvaliacaoFilmeDTOResponse buscarPorId(UUID id) {
			AvaliacaoFilme avaliacao = avaliacaoFilmeRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Avaliação do filme não encontrada."));
			return new AvaliacaoFilmeDTOResponse(avaliacao);
		}
		@Transactional
		public AvaliacaoFilmeDTOResponse inserir(AvaliacaoFilmeDTORequest dto) {
			AvaliacaoFilme avaliacao = new AvaliacaoFilme();
			preencherDados(avaliacao, dto);
			avaliacao = avaliacaoFilmeRepository.save(avaliacao);
			return new AvaliacaoFilmeDTOResponse(avaliacao);
		}
		
		@Transactional
		public AvaliacaoFilmeDTOResponse atualizar(UUID id, AvaliacaoFilmeDTORequest dto) {
			AvaliacaoFilme avaliacaoExistente = avaliacaoFilmeRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Avaliação não encontrada para alteração."));
	
			preencherDados(avaliacaoExistente, dto);
			avaliacaoExistente = avaliacaoFilmeRepository.save(avaliacaoExistente);
			return new AvaliacaoFilmeDTOResponse(avaliacaoExistente);
		}
			
			
			@Transactional
			public void remover(UUID id) {
				if (!avaliacaoFilmeRepository.existsById(id)) {
					throw new ResourceNotFoundException("Não foi possível deletar. ID da avaliação não encontrado.");
				}
				
				avaliacaoFilmeRepository.deleteById(id);
			}
			
			@Transactional(readOnly = true)
			public Double obterNotaMediaDoFilme(UUID filmeId) {
				if (!filmeRepository.existsById(filmeId)) {
					throw new ResourceNotFoundException("Filme não encontrado para calcular a média.");
				}
				Long totalAvaliacoes = avaliacaoFilmeRepository.contarAvaliacoesDoFilme(filmeId);
				
				if (totalAvaliacoes == 0) {
					return 0.0;
				}
				
				Double somaNotas = avaliacaoFilmeRepository.somarNotasDoFilme(filmeId);
				
				return somaNotas / totalAvaliacoes;
			}
			
			private void preencherDados(AvaliacaoFilme avaliacao, AvaliacaoFilmeDTORequest dto) {
				avaliacao.setNota(dto.getNota());
				avaliacao.setComentario(dto.getComentario());
				
				Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
						.orElseThrow(() -> new ResourceNotFoundException("Erro: Usuário não encontrado."));
				avaliacao.setUsuario(usuario);
				
				Filme filme = filmeRepository.findById(dto.getFilmeId())
						.orElseThrow(() -> new ResourceNotFoundException("Erro: Filme não encontrado."));
				avaliacao.setFilme(filme);
			}
}

