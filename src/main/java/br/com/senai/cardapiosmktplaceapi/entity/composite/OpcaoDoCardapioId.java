package br.com.senai.cardapiosmktplaceapi.entity.composite;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpcaoDoCardapioId {
	
	@Column(name = "id_cardapio")
	private Integer idDoCardapio;
	
	@Column(name = "id_opcao")
	private Integer idDaOpcao;
	
}
