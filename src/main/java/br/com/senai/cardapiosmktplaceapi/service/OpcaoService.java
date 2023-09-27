package br.com.senai.cardapiosmktplaceapi.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
public interface OpcaoService {
	
	public Opcao salvar(
			@NotNull(message = "A opção é obrigatória para persisti-la")
			Opcao opcao);
	
	public Opcao excluirPor(
			@NotNull(message = "O id para exclusão é obrigatória")
			@Positive(message = "O id para exclusão deve ser positivo")
			Integer id);
	
	
	public Opcao buscarPor(
			@NotNull(message = "A opcão é obrigatória para busca. ")
			@Positive(message = "O id deve ser positivo para busca. ")
			Integer id);
	
	public void atualizarStatusPor(
			@NotNull(message = "O id é obrigatório. ")
			@Positive(message = "O id para atualização deve conter um valor positivo. ")
			Integer id,
			@NotNull(message = "O status é obrigatório. ")
			Status status);
	
	public Page<Opcao> listarPor(
			@NotBlank(message = "O nome para listagem é obrigatório. ")
			String nome,
 			Categoria categoria,
			Restaurante restaurante);

}
