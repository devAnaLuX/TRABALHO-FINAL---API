package PF.SerratecFlix.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import PF.SerratecFlix.DTO.Request.CategoriaDTORequest;
import PF.SerratecFlix.DTO.Response.CategoriaDTOResponse;
import PF.SerratecFlix.Domain.Categoria;
import PF.SerratecFlix.Repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public List<CategoriaDTOResponse> findAll() {
		List<Categoria> categorias = categoriaRepository.findAll();
		List<CategoriaDTOResponse> dtos = new ArrayList<>();
		for (Categoria categoria : categorias) {
			dtos.add(new CategoriaDTOResponse(categoria));
		}
		return dtos;
	}
	
	public CategoriaDTOResponse buscarPorId(UUID id) {
		Categoria categoria = categoriaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não encontramos o ID desta categoria ."));
		return new CategoriaDTOResponse(categoria);
	}
	
	@Transactional
	public CategoriaDTOResponse inserir(CategoriaDTORequest dto) {
		Optional<Categoria> categoriaExistente = categoriaRepository.findByNomeIgnoreCase(dto.getNome());
		if (categoriaExistente.isPresent()) {
			throw new DuplicateEntryException("ERRO: Já existe uma categoria cadastrada com o nome. ");
		}

		Categoria categoria = new Categoria();
		preencherDados(categoria, dto);
		categoria = categoriaRepository.save(categoria);
		return new CategoriaDTOResponse(categoria);
	}
	
	@Transactional
	public CategoriaDTOResponse atualizar(UUID id, CategoriaDTORequest dto) {
		Categoria categoriaExistente = categoriaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada para atualização."));
		
		Optional<Categoria> outraCategoria = categoriaRepository.findByNomeIgnoreCase(dto.getNome());
		if (outraCategoria.isPresent() && !outraCategoria.get().getId().equals(id)) {
			throw new DuplicateEntryException("Não foi possível fazer a atualização. Existe outra categoria com este nome: " + dto.getNome());
		}

		preencherDados(categoriaExistente, dto);
		categoriaExistente = categoriaRepository.save(categoriaExistente);
		return new CategoriaDTOResponse(categoriaExistente);
	}

	@Transactional
	public void remover(UUID id) {
		if (!categoriaRepository.existsById(id)) {
			throw new ResourceNotFoundException("ID não encontrado. Não é possível deletar.");
		}
		categoriaRepository.deleteById(id);
	}
	private void preencherDados(Categoria categoria, CategoriaDTORequest dto) {
	    categoria.setNome(dto.getNome());
	    categoria.setDescricao(dto.getDescricao());
	}
}
