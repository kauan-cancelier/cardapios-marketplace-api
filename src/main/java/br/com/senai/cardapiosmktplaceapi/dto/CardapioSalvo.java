package br.com.senai.cardapiosmktplaceapi.dto;

import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

public class CardapioSalvo {

	@Positive(message = "O id do cardápio deve ser positivo. ")
	@NotNull(message = "O id é obrigatório. ")
	private Integer id;
	
	@NotBlank(message = "O nome é obrigatório. ")
	@Size(max = 100, message = "O nome não deve conter mais que 100. ")
	private String nome;
	
	@NotBlank(message = "A descrição é obrigatória. ")
	private String descricao;
	
	@NotNull(message = "O restaurante é obrigatório")
	private Restaurante restaurante;

	@NotNull(message = "O status é obrigatório")
	private Status status;
	
}
