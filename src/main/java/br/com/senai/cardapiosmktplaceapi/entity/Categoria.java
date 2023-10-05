package br.com.senai.cardapiosmktplaceapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.entity.enums.TipoDeCategoria;
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

@Table(name = "categorias")
@Entity(name = "Categoria")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Categoria {
	
	@Id
	@Column(name = "id")
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nome")
	@NotBlank(message = "O nome da categoria é obrigatório")
	@Size(max = 100, message = "O nome da categoria deve conter entre 2 e 100 caracteres")
	private String nome;
	
	@Column(name = "status")
	@Enumerated(value = EnumType.STRING)
	@NotNull(message = "O status da categoria é obrigatório")
	private Status status;
	
	@Column(name = "tipo")
	@Enumerated(value = EnumType.STRING)
	@NotNull(message = "O tipo da categoria é obrigatório")
	private TipoDeCategoria tipo;
	
	public  Categoria() {
		this.status = Status.A;
	}
	
	@JsonIgnore
	@Transient
	public boolean isPersistido() {
		return getId() != null && getId() > 0;
	}
	
	@JsonIgnore
	@Transient
	public boolean isAtiva() {
		return getStatus() == Status.A;
	}
}
