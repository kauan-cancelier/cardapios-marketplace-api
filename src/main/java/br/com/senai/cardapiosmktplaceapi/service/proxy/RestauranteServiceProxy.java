package br.com.senai.cardapiosmktplaceapi.service.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.service.RestauranteService;

@Service
public class RestauranteServiceProxy implements RestauranteService{
	
	@Autowired
	@Qualifier("restauranteServiceImpl")
	private RestauranteService restauranteService;

	@Override
	public Restaurante salvar(Restaurante restaurante) {
		return this.restauranteService.salvar(restaurante);
	}

	@Override
	public void atualizarStatusPor(Integer id, Status status) {
		this.restauranteService.atualizarStatusPor(id, status);
	}

	@Override
	public Page<Restaurante> listarPor(String nome, Categoria categoria, Pageable pageable) {
		return this.restauranteService.listarPor(nome, categoria, pageable);
	}

	@Override
	public Restaurante buscarPor(Integer id) {
		return this.restauranteService.buscarPor(id);
	}

	@Override
	public Restaurante excluirPor(Integer id) {
		return this.restauranteService.excluirPor(id);
	}

}
