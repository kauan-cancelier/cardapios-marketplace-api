package br.com.senai.cardapiosmktplaceapi.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.dto.CardapioSalvo;
import br.com.senai.cardapiosmktplaceapi.dto.NovaOpcaoDoCardapio;
import br.com.senai.cardapiosmktplaceapi.dto.NovoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Cardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.OpcaoDoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.Secao;
import br.com.senai.cardapiosmktplaceapi.entity.composite.OpcaoDoCardapioId;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.repository.CardapiosRepository;
import br.com.senai.cardapiosmktplaceapi.repository.OpcoesRepository;
import br.com.senai.cardapiosmktplaceapi.repository.RestaurantesRepository;
import br.com.senai.cardapiosmktplaceapi.repository.SecoesRepository;
import br.com.senai.cardapiosmktplaceapi.service.CardapioService;
import br.com.senai.cardapiosmktplaceapi.service.RestauranteService;

@Service
public class CardapioServiceImpl implements CardapioService {
	
	@Autowired
	private CardapiosRepository cardapiosRepository;
	
	@Autowired
	private OpcoesRepository opcoesRepository;

	@Autowired
	private SecoesRepository secoesRepository;
	
	@Autowired
	private RestaurantesRepository restaurantesRepository;
	
	@Autowired
	@Qualifier("restauranteServiceImpl")
	private RestauranteService restauranteService;

	private Restaurante getRestaurantePor(NovoCardapio novoCardapio) {
		Preconditions.checkNotNull(novoCardapio.getRestaurante(), "O restaurante é obrigatório. ");
		
		Restaurante restaurante = restaurantesRepository.buscarPor(novoCardapio.getRestaurante().getId());
		Preconditions.checkNotNull(restaurante,
				"O restaurante '" + novoCardapio.getRestaurante().getId() + "' não foi salvo. ");
		
		Preconditions.checkArgument(restaurante.isAtivo(), "O restaurante está inativo. ");
		
		return restaurante;
	}
	
	private Secao getSecaoPor(NovaOpcaoDoCardapio novaOpcaoDoCardapio) {
		Preconditions.checkNotNull(novaOpcaoDoCardapio.getSecao(), "A seção da opção é obrigatória. ");
		Secao secao = secoesRepository.findById(novaOpcaoDoCardapio.getSecao().getId()).get();
		
		Preconditions.checkNotNull(secao,
				"Não existe seção vinculada ao id '" + novaOpcaoDoCardapio.getSecao().getId() + "'.");
		
		Preconditions.checkArgument(secao.isAtiva(), "A seção está inativa. ");
		
		return secao;
	}
	
	private Opcao getOpcaoPor(Integer idDaOpcao, Restaurante restaurante) {
		Opcao opcao = opcoesRepository.buscarPorId(idDaOpcao);
		Preconditions.checkNotNull(opcao, "Não existe opção vinculada ao id '" + idDaOpcao + "'. ");
		
		Preconditions.checkArgument(opcao.isAtiva(), "A opção '" + opcao.getId() + "' está inativa. ");
		
		Preconditions.checkArgument(opcao.getRestaurante().equals(restaurante),
				"A opção '" + idDaOpcao + "' não pertence ao cardapio do restaurante. ");
		
		return opcao;
	}
	
	private void validarDuplicidadeEm(List<NovaOpcaoDoCardapio> opcoesDoCardapio) {
		for (NovaOpcaoDoCardapio novaOpcao: opcoesDoCardapio) {
			int qtdeOcorrencias = 0;
			for (NovaOpcaoDoCardapio outraOpcao : opcoesDoCardapio) {
				if (novaOpcao.getIdDaOpcao().equals(outraOpcao.getIdDaOpcao())) {
					qtdeOcorrencias++;
				}
			}
			Preconditions.checkArgument(qtdeOcorrencias == 1,
					"A opção '" + novaOpcao.getIdDaOpcao() + "' está duplicada no cardápio. ");
		}
	}
	
	
	@Override
	public Cardapio inserir(NovoCardapio novoCardapio) {
		Restaurante restaurante = getRestaurantePor(novoCardapio);
		Cardapio cardapio = new Cardapio();
		cardapio.setNome(novoCardapio.getNome());
		cardapio.setDescricao(novoCardapio.getDescricao());
		cardapio.setRestaurante(restaurante);
		Cardapio cardapioSalvo = cardapiosRepository.save(cardapio);
		
		this.validarDuplicidadeEm(novoCardapio.getOpcoes());
		
		for (NovaOpcaoDoCardapio novaOpcao : novoCardapio.getOpcoes()) {
			Opcao opcao = getOpcaoPor(novaOpcao.getIdDaOpcao(), restaurante);
			Secao secao = getSecaoPor(novaOpcao);
			OpcaoDoCardapioId id = new OpcaoDoCardapioId(cardapioSalvo.getId(), opcao.getId());
			OpcaoDoCardapio opcaoDoCardapio = new OpcaoDoCardapio();
			opcaoDoCardapio.setId(id);
			opcaoDoCardapio.setCardapio(cardapioSalvo);
			opcaoDoCardapio.setOpcao(opcao);
			opcaoDoCardapio.setSecao(secao);
			opcaoDoCardapio.setPreco(novaOpcao.getPreco());
			opcaoDoCardapio.setRecomendado(novaOpcao.getRecomendado());
			cardapioSalvo.getOpcoes().add(opcaoDoCardapio);
		}
		this.cardapiosRepository.saveAndFlush(cardapioSalvo);
		return cardapiosRepository.buscarPor(cardapioSalvo.getId());
	}

	@Override
	public Cardapio alterar(CardapioSalvo cardapioSalvo) {
		Restaurante restaurante = restauranteService.buscarPor(cardapioSalvo.getRestaurante().getId());
		Cardapio cardapio = cardapiosRepository.buscarPor(cardapioSalvo.getId());
		Preconditions.checkNotNull(cardapio, "Não foi encontrado nenhum cardapio com id: '" + cardapioSalvo.getId() + "'");
		Preconditions.checkArgument(restaurante.equals(cardapio.getRestaurante()), "O restaurante do cardapio não pode ser alterado");
		
		cardapio.setNome(cardapioSalvo.getNome());
		cardapio.setDescricao(cardapioSalvo.getDescricao());
		cardapio.setRestaurante(restaurante);
		cardapio.setStatus(cardapioSalvo.getStatus());
		
		Cardapio cardapioAtualizado = cardapiosRepository.saveAndFlush(cardapio);
		
		return buscarPor(cardapioAtualizado.getId());
	}
	
	private void atualizarPrecosDas(List<OpcaoDoCardapio> opcoesDoCardapio) {
		for (OpcaoDoCardapio opcaoDoCardapio : opcoesDoCardapio) {
			if (opcaoDoCardapio.getOpcao().isEmPromocao()) {
				BigDecimal divisor = new BigDecimal(100);
				BigDecimal percentualDeDesconto = opcaoDoCardapio.getOpcao().getPercentualDeDesconto();
				BigDecimal valorDescontado = 
						opcaoDoCardapio.getPreco().multiply(percentualDeDesconto).divide(divisor);
				BigDecimal preco = opcaoDoCardapio.getPreco().subtract(valorDescontado).setScale(2, RoundingMode.CEILING);
				opcaoDoCardapio.setPreco(preco);
			}
		}
	}

	@Override
	public Page<Cardapio> listarPor(Restaurante restaurante, Pageable pageable) {
		Page<Cardapio> cardapios = cardapiosRepository.listarPor(restaurante, pageable);
		for (Cardapio cardapio : cardapios.getContent()) {
			this.atualizarPrecosDas(cardapio.getOpcoes());
		}
		return cardapios;
	}

	@Override
	public Cardapio buscarPor(Integer id) {
		Cardapio cardapioEncontrado = cardapiosRepository.buscarPor(id);
		Preconditions.checkNotNull(cardapioEncontrado, "Não foi encontrado nenhum cardápio para o id informado. ");
		Preconditions.checkArgument(cardapioEncontrado.isAtivo(), "O cardápio está inativo. ");
		this.atualizarPrecosDas(cardapioEncontrado.getOpcoes());
		return cardapioEncontrado;
	}

	@Override
	public void atualizarStatusPor(Integer id, Status status) {
		Cardapio cardapio = cardapiosRepository.buscarPor(id);
		Preconditions.checkNotNull(cardapio, "Não foi encontrado nenhum cardápio para o id informado. ");
		Preconditions.checkArgument(cardapio.isAtivo(), "O cardápio está inativo. ");
		Preconditions.checkArgument(cardapio.getStatus() != status, "O status informado ja foi salvo anteriormente");
	}
	
}
