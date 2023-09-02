package br.com.senai.cardapiosmktplaceapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Embeddable
@Data
public class Endereco {
	
	@Column(name = "cidade")
	@NotBlank(message = "A cidade da categoria é obrigatória")
	@Size(max = 80, message = "O nome da cidade não pode ter mais que 80 caracteres")
	private String cidade;
	
	@Column(name = "logradouro")
	@NotBlank(message = "O logradouro da categoria é obrigatório")
	@Size(max = 200, message = "O nome do logradouro não pode ter mais que 200 caracteres")
	private String logradouro;
	
	@Column(name = "bairro")
	@NotBlank(message = "O bairro da categoria é obrigatório")
	@Size(max = 50, message = "O nome do bairro não pode ter mais que 50 caracteres")
	private String bairro;
	
	@Column(name = "complemento")
	private String complemento;
	
}
