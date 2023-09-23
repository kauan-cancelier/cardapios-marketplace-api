package br.com.senai.cardapiosmktplaceapi.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Confirmacao;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.repository.OpcoesRepository;
import br.com.senai.cardapiosmktplaceapi.service.OpcaoService;

@Service
public class OpcaoServiceimpl implements OpcaoService{
	
	@Autowired
	OpcoesRepository opcoesRepository;

	@Override
	public Opcao salvar(Opcao opcao) {
		validar(opcao);
		if (opcao.isPersistido()) {
			return null;
		}else {
			return this.opcoesRepository.save(opcao);
		}
	}

	@Override
	public Opcao excluirPor(Integer id) {
		return null;
	}

	@Override
	public void atualizarStatusPor(Integer id, Status status) {
	}

	@Override
	public Page<Opcao> listarPor(String nome, Categoria categoria, Restaurante restaurante) {
		return null;
	}
	
	private void validar(Opcao opcao) { 
		Preconditions.checkNotNull(opcao);
		Preconditions.checkArgument(opcao.getStatus() == Status.A || opcao.getStatus() == Status.I,
				"O status da opção só pode receber os valores 'I' para inativo ou 'A' para ativo. ");
		
		Preconditions.checkArgument(opcao.getPromocao() == Confirmacao.N || opcao.getPromocao() == Confirmacao.S, 
				"A promoção só pode receber os valores 'S' para Sim ou 'N' para Não.");
	
		if (opcao.isEmPromocao()) {
			Preconditions.checkNotNull(opcao.getPercentualDeDesconto(), "Quando em promoção o percentual de desconto deve ser informado. ");
			Preconditions.checkArgument(opcao.getPercentualDeDesconto().doubleValue() > 0, 
					"Quando em promoção o valor do percentual de desconto deve ser maior que 0.");
		}
		
		Preconditions.checkNotNull(opcao.getCategoria(), "A categoria é obrigatória");
		Preconditions.checkArgument(opcao.getCategoria().getStatus() == Status.A, "A categoria informada está inativa. ");
		
		Preconditions.checkNotNull(opcao.getRestaurante(), "O restaurante é obrigatório");
		Preconditions.checkArgument(opcao.getRestaurante().getStatus() == Status.A, "O restaurante informado está inativo. ");
		
		Integer qtde = opcoesRepository.contarVinculadosPor(opcao.getNome(), opcao.getRestaurante());
		Preconditions.checkArgument(qtde == 0, "Já existe uma opção com o mesmo nome para o restaurante informado");
		System.out.println("\n\n\nPassou tudo\n\n\\n");
	}
	
	

}
