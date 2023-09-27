package br.com.senai.cardapiosmktplaceapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class OpcaoServiceimpl implements OpcaoService {
	
	@Autowired
	OpcoesRepository opcoesRepository;

	@Override
	public Opcao salvar(Opcao opcao) {
		Preconditions.checkNotNull(opcao);
		if (opcao.isPersistido()) {
			validarAlterar(opcao);
			return this.opcoesRepository.save(opcao);
		}else {
			validarInserir(opcao);
			return this.opcoesRepository.save(opcao);
		}
	}

	@Override
	public Opcao excluirPor(Integer id) {
		Opcao opcaoEncontrada = validarExcluir(id);
		this.opcoesRepository.deleteById(id);
		return opcaoEncontrada;
	}

	@Override
	public void atualizarStatusPor(Integer id, Status status) {
		Preconditions.checkNotNull(id, "O id deve ser informado para atualização de status");
		Preconditions.checkArgument(id > 0, "O id deve ser maior que 0");
		Preconditions.checkNotNull(status, "O status deve ser informado para atualização de status");
		Opcao opcao = this.opcoesRepository.buscarPorId(id);
		Preconditions.checkNotNull(opcao, "Não foi encontrada nenhuma opcão para o id informado. ");
		Preconditions.checkArgument(opcao.getStatus() != status, "O status informado é o mesmo já cadastrado no banco. ");
		this.opcoesRepository.atualizarStatusPor(id, status);
		System.out.println("atualizou");
	}

	@Override
	public Page<Opcao> listarPor(String nome, Categoria categoria, Restaurante restaurante) {
		Preconditions.checkNotNull(nome, "O nome é obrigatório para listagem");
		Preconditions.checkArgument(categoria != null || restaurante != null, "Não é possivel listar com apenas o nome da opção. Deve conter a categoria ou restaurante. ");
		Preconditions.checkArgument(nome.length() >= 3, "O nome para listagem deve conter ao menos 3 caracteres. ");
		return this.opcoesRepository.listarPor(nome + "%", categoria, restaurante, Pageable.ofSize(15));
	}
	
	@Override
	public Opcao buscarPor(Integer id) {
		Preconditions.checkNotNull(id, "O id é obrigatório para busca. ");
		Preconditions.checkArgument(id > 0, "O id deve ser maior que zero para busca. ");
		Opcao opcaoEncontrada = this.opcoesRepository.buscarPorId(id);
		Preconditions.checkNotNull(opcaoEncontrada, "Não foi encontrada nenhuma opcão para o id informado. ");
		Preconditions.checkArgument(opcaoEncontrada.getStatus() == Status.A, "A opcão informada está inativa. ");
		return opcaoEncontrada;
	}
	
	private void validarInserir(Opcao opcao) {
		Preconditions.checkNotNull(opcao);
		Preconditions.checkArgument(opcao.getStatus() == Status.A || opcao.getStatus() == Status.I,
				"O status da opção só pode receber os valores 'I' para inativo ou 'A' para ativo. ");
		
		Preconditions.checkArgument(opcao.getPromocao() == Confirmacao.N || opcao.getPromocao() == Confirmacao.S, 
				"A promoção só pode receber os valores 'S' para Sim ou 'N' para Não.");
	
		if (opcao.isEmPromocao()) {
			Preconditions.checkNotNull(opcao.getPercentualDeDesconto(), "Quando em promoção o percentual de desconto deve ser informado. ");
			Preconditions.checkArgument(opcao.getPercentualDeDesconto().doubleValue() > 0, "Quando em promoção o valor do percentual de desconto deve ser maior que 0.");
		}
		
		Preconditions.checkNotNull(opcao.getCategoria(), "A categoria é obrigatória");
		Preconditions.checkArgument(opcao.getCategoria().getStatus() == Status.A, "A categoria informada está inativa. ");
		
		Preconditions.checkNotNull(opcao.getRestaurante(), "O restaurante é obrigatório");
		Preconditions.checkArgument(opcao.getRestaurante().getStatus() == Status.A, "O restaurante informado está inativo. ");
		
		Integer qtde = opcoesRepository.contarVinculadosPor(opcao.getNome(), opcao.getRestaurante());
		Preconditions.checkArgument(qtde == 0, "Já existe uma opção com o mesmo nome para o restaurante informado");
	}
	
	private void validarAlterar(Opcao opcao) {
		Preconditions.checkNotNull(opcao.getId(), "O id deve ser informado para alteração. ");
		Preconditions.checkNotNull(opcao.getNome(), "O nome é obrigatório para alteração. ");
		Preconditions.checkNotNull(opcao.getDescricao(), "A descrição é obrigatória para alteração. ");
		Preconditions.checkNotNull(opcao.getStatus(), "O status é obrigatório para alteração. ");
		Integer vinculados = this.opcoesRepository.contarRestaurantesVinculadosPela(opcao.getId());
		Preconditions.checkArgument(vinculados == 0, "A opção informada está vinculada a: '" + vinculados + "' cardapios. E por isso não pode ser alterada.");
		Preconditions.checkArgument(opcao.getRestaurante() == null, "O restaurante não pode ser alterado. ");
	}
	
	private Opcao validarExcluir(Integer id) {
		Opcao opcaoEncontrada = this.buscarPor(id);
		Integer qtde = opcoesRepository.contarRestaurantesVinculadosPela(id);
		Preconditions.checkArgument(qtde == 0, "Não foi possivel excluir pois existem restaurantes vinculados a opção. ");
		return opcaoEncontrada;
	}

}
