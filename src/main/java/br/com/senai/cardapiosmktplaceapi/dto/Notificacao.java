package br.com.senai.cardapiosmktplaceapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Notificacao {
	
	@NotBlank(message = "O destinatário é obrigatório. ")
	@Email(message = "O email é inválido. ")
	@Size(max = 100, message = "O email não deve conter mais do que 100 caracteres. ")
	private String destinatario;
	
	@NotBlank(message = "O título é obrigatório. ")
	@Size(max = 100, message = "O título não deve conter mais do que 100 caracteres. ")
	private String titulo;
	
	@NotBlank(message = "A mensagem é obrigatória. ")
	private String mensagem;

	
}
