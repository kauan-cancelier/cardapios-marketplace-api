package br.com.senai.cardapiosmktplaceapi.entity;

import java.util.ArrayList;
import java.util.List;

import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Table(name = "cardapios")
@Entity(name = "Cardapio")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cardapio {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	
	@Column(name = "nome")
	@Size(min = 3, max = 100, message = "O nome do cardápio deve conter entre 3 e 100 caracteres")
	@NotBlank(message = "O nome cardápio é obrigatório")
	private String nome;
	

	@Column(name = "descricao")
	@NotBlank(message = "A descrição do cardápio é obrigatório")
	private String descricao;
	
	@Column(name = "status")
	@NotNull(message = "O status do cardápio é obrigatório")
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@ToString.Exclude
	@JoinColumn(name = "id_restaurante")
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull(message =  "O restaurante do cardápio é obrigatório")
	private Restaurante restaurante;

	@OneToMany(mappedBy = "cardapio", fetch = FetchType.LAZY,
			cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OpcaoDoCardapio> opcoes;
	
	public Cardapio() {
		status = Status.A;
		this.opcoes = new ArrayList<>();
	}
	
	@Transient
	public boolean isPersistido() {
		return getId() != null && getId() > 0;
	}
	
	@Transient
	public boolean isAtivo() {
		return getStatus() == Status.A;
	}
	
}
