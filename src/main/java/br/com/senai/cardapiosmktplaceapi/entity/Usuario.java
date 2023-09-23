package br.com.senai.cardapiosmktplaceapi.entity;

import br.com.senai.cardapiosmktplaceapi.entity.enums.Papel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {
	
	@Column(name = "login")
	@NotBlank(message = "O login do usuario é obrigatório")
	@Size(max = 50, message = "O login do usuario não deve conter mais que 50 caracteres")
	@EqualsAndHashCode.Include
	@Id
	private String login;
	
	@Column(name = "senha")
	@NotBlank(message = "A senha do usuario é obrigatória")
	private String senha;

	@Column(name = "nome")
	@NotBlank(message = "O nome é obrigatório")
	@Size(max = 150, message = "O nome do usuario não deve conter mais do que 120 caracteres")
	private String nome;
	
	@Column(name = "papel")
	@Enumerated(value = EnumType.STRING)
	@NotNull(message = "O papel é obrigatório")
	private Papel papel;

}
