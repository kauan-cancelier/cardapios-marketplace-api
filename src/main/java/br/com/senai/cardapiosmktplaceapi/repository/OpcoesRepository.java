package br.com.senai.cardapiosmktplaceapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import jakarta.transaction.Transactional;

@Repository
public interface OpcoesRepository extends JpaRepository<Opcao, Integer>{
	
	
	@Query("SELECT o FROM Opcao o " +
		       "LEFT JOIN FETCH o.categoria " +
		       "LEFT JOIN FETCH o.restaurante r " +
		       "WHERE (o.nome LIKE :nome) " +
		       "AND (o.categoria = :categoria OR :categoria IS NULL) " +
		       "AND (r = :restaurante OR :restaurante IS NULL) " +
		       "ORDER BY o.nome")
	public Page<Opcao> listarPor(String nome, Categoria categoria, Restaurante restaurante, Pageable pageable);
	
	@Query(value = "SELECT o FROM Opcao o "
            + "JOIN FETCH o.categoria "
            + "JOIN FETCH o.restaurante r "
            + "WHERE o.id = :id")
	public Opcao buscarPorId(Integer id);
	
	
	@Query(value = "SELECT Count(o) "
			+ "FROM Opcao o "
			+ "WHERE o.nome = :nome "
			+ "AND o.restaurante = :restaurante")
	public Integer contarVinculadosPor(String nome, Restaurante restaurante);
	
	@Query(value = "SELECT COUNT(DISTINCT c.id) "
			+ "	FROM Opcao o "
			+ "	JOIN OpcaoDoCardapio oc ON o.id = oc.opcao.id "
			+ "	JOIN Cardapio c ON oc.cardapio.id = c.id "
			+ "	WHERE o.id = :id")
	public Integer contarRestaurantesVinculadosPela(Integer id);
	
	@Transactional
	@Modifying
	@Query(value = 
			"UPDATE Opcao o SET o.status = :status "
			+ "WHERE o.id = :id")
	public void atualizarStatusPor(Integer id, Status status);
	
}
