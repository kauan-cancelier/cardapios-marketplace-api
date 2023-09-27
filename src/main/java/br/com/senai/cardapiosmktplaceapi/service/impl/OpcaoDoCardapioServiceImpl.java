package br.com.senai.cardapiosmktplaceapi.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.entity.OpcaoDoCardapio;
import br.com.senai.cardapiosmktplaceapi.repository.OpcoesDoCardapioRepository;
import br.com.senai.cardapiosmktplaceapi.service.OpcaoDoCardapioService;

@Service
public class OpcaoDoCardapioServiceImpl implements OpcaoDoCardapioService{

	@Autowired
	private OpcoesDoCardapioRepository opcoesDoCardapioRepository;
	
	
	@Override
	public OpcaoDoCardapio inserirNova(OpcaoDoCardapio opcaoDoCardapio) {
		this.validarInsercao(opcaoDoCardapio);
		return this.opcoesDoCardapioRepository.save(opcaoDoCardapio);
	}
	
	
	
	private void validarInsercao(OpcaoDoCardapio opcaoDoCardapio) {
		Preconditions.checkNotNull(opcaoDoCardapio, "A opcao do cardapio é obrigatória. ");
		
		Preconditions.checkNotNull(opcaoDoCardapio.getCardapio(), "O cardapio da opção do cardapio é obrigatória para inserção. ");
		Preconditions.checkArgument(opcaoDoCardapio.getCardapio().isAtivo(), "O cardápio informado está inativo. ");
		
		Preconditions.checkNotNull(opcaoDoCardapio.getOpcao(), "A opcão da opção do cardapio é obrigatório para inserção. ");
		Preconditions.checkArgument(opcaoDoCardapio.getOpcao().isAtiva(), "A opção infomada está inativa");
		
		Preconditions.checkArgument(opcaoDoCardapio.getPreco().doubleValue() > 0, "O preço deve ser maior que zero. ");
		
		Preconditions.checkNotNull(opcaoDoCardapio.getSecao(), "A seçãio da opção do cardápio é obrigatória. ");
		Preconditions.checkArgument(opcaoDoCardapio.getSecao().isAtiva(), "A seção deve estar ativa. ");
		
		if (opcaoDoCardapio.isEmPromocao()) {
			opcaoDoCardapio.setPreco(BigDecimal.valueOf((opcaoDoCardapio.getPreco().doubleValue() * opcaoDoCardapio.getOpcao().getPercentualDeDesconto().doubleValue()) / 100));
		}
		
		Integer i = opcoesDoCardapioRepository.contarOpcoesPelo(opcaoDoCardapio.getOpcao().getId());
		Preconditions.checkArgument(i == 0, "Foram encontadas opções para o mesmo código no cardápio. ");
		
		Preconditions.checkArgument(opcaoDoCardapio.getCardapio().getRestaurante().getId() == opcaoDoCardapio.getOpcao().getRestaurante().getId(),
				"A opção deve pertencer ao mesmo restaurante do cardápio");
	}

}
