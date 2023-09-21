package br.com.senai.cardapiosmktplaceapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.entity.enums.TipoDeCategoria;
import br.com.senai.cardapiosmktplaceapi.repository.CategoriasRepository;
import br.com.senai.cardapiosmktplaceapi.repository.RestaurantesRepository;
import br.com.senai.cardapiosmktplaceapi.service.CategoriaService;

@Service
public class CategoriaServiceImpl implements CategoriaService {
	
	@Autowired
	private CategoriasRepository categoriaRepository;
	
	@Autowired
	private RestaurantesRepository restaurantesRepository;
	
	@Override
	public Categoria salvar(Categoria categoria) {
		Categoria categoriaEncontrada = categoriaRepository.buscarPor(categoria.getNome(), categoria.getTipo());
		if (categoriaEncontrada != null) {
			if (categoriaEncontrada.isPersistido()) {
				Preconditions.checkArgument(categoriaEncontrada.equals(categoria), "O nome da categória já está em uso");
			}
		}
		return categoriaRepository.save(categoria);
	}

	@Override
	public void atualizarStatusPor(Integer id, Status status) {
		Categoria categoriaEncontrada = categoriaRepository.findById(id).get();
		Preconditions.checkNotNull(categoriaEncontrada, "Nenhuma categoria foi encontrada com o id informado. ");
		Preconditions.checkArgument(categoriaEncontrada.getStatus() != status, "O status já está salvo para a categoria. ");
		categoriaRepository.atualizarPor(id, status);
	}

	@Override
	public Page<Categoria> listarPor(String nome, Status status, TipoDeCategoria tipo, Pageable pageable) {
		return categoriaRepository.listarPor(nome + "%", status, tipo, pageable);
	}

	@Override
	public Categoria buscarPor(Integer id) {
		Categoria categoriaEncontrada = categoriaRepository.buscarPor(id);
		Preconditions.checkNotNull(categoriaEncontrada, "Não foi encontrada nenhuma categoria com o id informado. ");
		Preconditions.checkArgument(categoriaEncontrada.isAtiva(), "A categoria está inativa");
		return categoriaRepository.findById(id).get();
	}

	@Override
	public Categoria excluirPor(Integer id) {
		Categoria categoriaParaExclusao = buscarPor(id);
		Long qtdeDeRestaurantesVinculados = restaurantesRepository.contarPor(id);
		Preconditions.checkArgument(qtdeDeRestaurantesVinculados == 0, "Não é possivel remover pois existem restaurantes vinculadas a categoria");
		categoriaRepository.deleteById(categoriaParaExclusao.getId());
		return categoriaParaExclusao;
	}

}
