package br.com.senai.cardapiosmktplaceapi.entity;

import java.math.BigDecimal;

import br.com.senai.cardapiosmktplaceapi.entity.enums.Confirmacao;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
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
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(name = "opcoes")
@Entity(name = "Opcao")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Opcao {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	
	@Column(name = "nome")
	@NotBlank(message = "O nome da opcao não pode ser vazio")
	@Size(min = 3, max = 100, message = "O nome da opção deve conter entre 3 e 100 caracteres")
	private String nome;
	
	@Column(name = "descricao")
	private String descricao;
	
	@Column(name = "status")
	@NotNull(message = "O status da opção é obrigatória")
	@Enumerated(value = EnumType.STRING)
	private Status status;
	
	@Column(name = "promocao")
	@NotNull(message = "O inidicador de promoção é obrigatório")
	@Enumerated(value = EnumType.STRING)
	private Confirmacao promocao;
	
	@Column(name = "percentual_desconto")
	@Digits(integer = 2, fraction = 2, message = "O percentual de desconto deve seguir o formato: 'NN.NN' ")
	@DecimalMax(value = "100.0", inclusive = false, message = "O percentual de desconto não pode ser superior a 100.00% ")
	@DecimalMin(value = "0.0", inclusive = false, message = "O percentual de desconto não pode ser inferir a 0.01% ")
	private BigDecimal percentualDeDesconto;
	
	@JoinColumn(name = "id_categoria")
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull(message = "A categoria é obrigatória")
	private Categoria categoria;
	
	@JoinColumn(name = "id_restaurante")
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull(message = "O restaurante é obrigatório")
	private Restaurante restaurante;
	
	public Opcao() {
		this.status = Status.A;
	}
	
	@Transient
	public boolean isPersistido() {
		return getId() != null && getId() > 0;
	}
	
	@Transient
	public boolean isAtiva() {
		return getStatus() == Status.A;
	}

}
