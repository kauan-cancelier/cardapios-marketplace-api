package br.com.senai.cardapiosmktplaceapi.entity;

import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(name = "secoes")
@Entity(name = "Secao")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Secao {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	
	@Column(name = "nome")
	@NotBlank(message = "O nome da seção é obrigatório")
	@Size(min = 3, max = 100,message = "O nome deve conter entre 3 e 100 caracteres")
	private String nome;
	
	@Column(name = "status")
	@NotNull(message = "O status da seção é obrigatório")
	@Enumerated(EnumType.STRING)
	private Status status;

	public Secao() {
		status = Status.A;
	}
	
	@Transient
	public boolean isPersistida() {
		return getId() != null && getId() > 0;
	}
	
	@Transient
	public boolean isAtiva() {
		return getStatus() == Status.A;
	}

}
