package br.com.senai.cardapiosmktplaceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;

@Repository
public interface OpcoesRepository extends JpaRepository<Opcao, Integer>{
	
	@Query(value = " SELECT o "
			+ "FROM Opcao o "
			+ "JOIN FETCH o.categoria "
			+ "JOIN FETCH o.restaurante r "
			+ "JOIN FETCH r.categoria "
			+ "WHERE o.id = :id")
	public Opcao buscarPorId(Integer id);
	
	
	@Query(value = "SELECT Count(o) "
			+ "FROM Opcao o "
			+ "WHERE o.nome = :nome "
			+ "AND o.restaurante = :restaurante")
	public Integer contarVinculadosPor(String nome, Restaurante restaurante);
	
	@Query(value = "UPDATE mktplace.opcoes o"
			+ "SET "
			+ "nome = 'op'"
			+ "descricao = 'desc'"
			+ "status = 'I'"
			+ "promocao = 'S'"
			+ "percentual_desconto = 40"
			+ "id_restaurante = 33"
			+ "WHERE"
			+ "o.id = 202")
	public Opcao alterar(Opcao opcao);
}
