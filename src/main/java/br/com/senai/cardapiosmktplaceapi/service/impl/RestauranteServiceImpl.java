package br.com.senai.cardapiosmktplaceapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.repository.CardapiosRepository;
import br.com.senai.cardapiosmktplaceapi.repository.RestaurantesRepository;
import br.com.senai.cardapiosmktplaceapi.service.CategoriaService;
import br.com.senai.cardapiosmktplaceapi.service.RestauranteService;

@Service
public class RestauranteServiceImpl implements RestauranteService{

	@Autowired
	private RestaurantesRepository restaurantesRepository;
	
	@Autowired
	private CardapiosRepository cardapiosRepository;

	@Autowired
	@Qualifier("categoriaServiceImpl")
	private CategoriaService categoriaService;
	
	@Override
	public Restaurante salvar(Restaurante restaurante) {
		Restaurante restauranteEncontrado = restaurantesRepository.buscarPor(restaurante.getNome());
		if (restauranteEncontrado != null) {
			if (restauranteEncontrado.isPersistido()) {
				Preconditions.checkArgument(restauranteEncontrado.equals(restaurante), "O nome do restaurante já está em uso");
			}
		}
		categoriaService.buscarPor(restaurante.getCategoria().getId());
		Restaurante restauranteSalvo = restaurantesRepository.save(restaurante);
		return restauranteSalvo;
	}

	@Override
	public void atualizarStatusPor(Integer id, Status status) {
		Restaurante restauranteEncontrado = restaurantesRepository.buscarPor(id);
		Preconditions.checkNotNull(restauranteEncontrado, "O id não está vinculado a um restaurante. ");
		Preconditions.checkArgument(restauranteEncontrado.getStatus() != status, "O status já está salvo para o restaurante. ");
		restaurantesRepository.atualizarPor(id, status);
	}

	@Override
	public Page<Restaurante> listarPor(String nome,	Categoria categoria, Pageable pageable) {
		return restaurantesRepository.listarPor("%" + nome + "%", categoria, pageable);
	}
	
	@Override
	public Restaurante buscarPor(Integer id) {
		Restaurante restauranteEncontrado = restaurantesRepository.buscarPor(id);
		Preconditions.checkNotNull(restauranteEncontrado, "Não foi encontrado nenhum restaurante para o id informado. ");
		Preconditions.checkArgument(restauranteEncontrado.isAtivo(), "O restaurante está inátivo");
		return restauranteEncontrado;
	}

	@Override
	public Restaurante excluirPor(Integer id) {
		Restaurante restauranteEnconRestaurante = buscarPor(id);
		Long qtdeCardapioVinculados = cardapiosRepository.contarPor(id);
		Preconditions.checkArgument(qtdeCardapioVinculados == 0,
				"O restaurante não pode ser removido pois tem cardápios vinculados a esse restaurante. ");
		restaurantesRepository.deleteById(id);
		return restauranteEnconRestaurante;
	}
	
}
