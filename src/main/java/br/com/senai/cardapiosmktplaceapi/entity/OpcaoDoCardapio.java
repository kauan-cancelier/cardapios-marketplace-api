package br.com.senai.cardapiosmktplaceapi.entity;

import java.math.BigDecimal;

import br.com.senai.cardapiosmktplaceapi.entity.composite.OpcaoDoCardapioId;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Confirmacao;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(name = "opcoes_cardapios")
@Entity(name = "OpcaoDoCardapio")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OpcaoDoCardapio {
	
	@EmbeddedId
	@NotNull(message = "O id da opcão do cardapio é obrigatório")
	@EqualsAndHashCode.Include
	private OpcaoDoCardapioId id;
	
	@JoinColumn(name = "id_cardapio")
	@MapsId("idDoCardapio")
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull(message = "O cardápio da opção do cardápio é obrigatório")
	private Cardapio cardapio;
	
	@JoinColumn(name = "id_opcao")
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull(message = "A opção da opção do cardápio é obrigatória")
	private Opcao opcao;
	
	@Column(name = "status")
	@Enumerated(value = EnumType.STRING)
	@NotNull(message = "O status da opção do cardápio é obrigatório")
	private Status status;
	
	@Column(name = "preco")
	@Digits(integer = 9, fraction = 2, message = "O preço deve possuir o formato: 'NNNNNNNNN:NN'")
	@DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser positivo")
	private BigDecimal preco;
	
	@Column(name = "recomendado")
	@NotNull(message = "O indicador de recomendação não pode ser nulo")
	@Enumerated(value = EnumType.STRING)
	private Confirmacao recomendado;
	
	@JoinColumn(name = "id_secao")
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull(message = "O id da seção do opção do cardápio")
	private Secao secao;
	
	private OpcaoDoCardapio() {
		this.status = Status.A;
	}
	
	@Transient
	public boolean isPersistido() {
		return getId() != null && getId().getIdDaOpcao() > 0
				&& getId().getIdDoCardapio() > 0;
	}
	
	@Transient
	public boolean isAtivo() {
		return getStatus() == Status.A;
	}
	
}
