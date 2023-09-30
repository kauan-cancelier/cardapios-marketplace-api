package br.com.senai.cardapiosmktplaceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.senai.cardapiosmktplaceapi.entity.Usuario;
import jakarta.transaction.Transactional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
	
	@Query(value = "SELECT u FROM Usuario u WHERE u.login = :login")
	public Usuario buscarPor(String login);
	
	@Modifying @Transactional
	@Query(value = "UPDATE Usuario u SET u.nome = :nome, u.senha = :senha WHERE u.login = :login")
	public void atualizarPor( String login, String nome, String senha);

	@Modifying @Transactional
	@Query(value = "UPDATE Usuario u SET u.senha = :senha WHERE u.login = :login")
	public void atualizarPor(String login,String senha);
	
}
