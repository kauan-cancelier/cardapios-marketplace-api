package br.com.senai.cardapiosmktplaceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.senai.cardapiosmktplaceapi.entity.OpcaoDoCardapio;
import br.com.senai.cardapiosmktplaceapi.entity.composite.OpcaoDoCardapioId;

public interface OpcoesDoCardapioRepository extends JpaRepository<OpcaoDoCardapio, OpcaoDoCardapioId>{
	
	@Query(value = "SELECT Count(*) FROM OpcaoDoCardapio o WHERE o.opcao.id = :idDaOpcao")
	public Integer contarOpcoesPelo(Integer idDaOpcao);
	
	@Query(value = "SELECT oc FROM OpcaoDoCardapio oc "
			+ "JOIN FETCH oc.cardapio "
			+ "JOIN FETCH oc.opcao "
			+ "JOIN FETCH oc.secao "
			+ "WHERE oc.id = :id")
	public OpcaoDoCardapio buscarPelo(OpcaoDoCardapioId id);
	
	
}
