package br.com.senai.cardapiosmktplaceapi.service.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.senai.cardapiosmktplaceapi.dto.CardapioSalvo;
import br.com.senai.cardapiosmktplaceapi.dto.NovoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Cardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.service.CardapioService;
import br.com.senai.cardapiosmktplaceapi.service.impl.CardapioServiceImpl;

@Service
public class CardapioServiceProxy implements CardapioService{
	
	@Autowired
	@Qualifier("cardapioServiceImpl")
	CardapioServiceImpl cardapioServiceImpl;
	
	@Override
	public Cardapio inserir(NovoCardapio novoCardapio) {
		return cardapioServiceImpl.inserir(novoCardapio);
	}

	@Override
	public Cardapio alterar(CardapioSalvo cardapioSalvo) {
		return this.cardapioServiceImpl.alterar(cardapioSalvo);
	}

	@Override
	public Page<Cardapio> listarPor(Restaurante restaurante, Pageable pageable) {
		return this.cardapioServiceImpl.listarPor(restaurante, pageable);
	}

	@Override
	public Cardapio buscarPor(Integer id) {
		return this.cardapioServiceImpl.buscarPor(id);
	}

	@Override
	public void atualizarStatusPor(Integer id, Status status) {
		this.cardapioServiceImpl.atualizarStatusPor(id, status);
	}

}
