package br.com.senai.cardapiosmktplaceapi;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Confirmacao;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.repository.CardapiosRepository;
import br.com.senai.cardapiosmktplaceapi.repository.CategoriasRepository;
import br.com.senai.cardapiosmktplaceapi.repository.OpcoesRepository;
import br.com.senai.cardapiosmktplaceapi.repository.RestaurantesRepository;
import br.com.senai.cardapiosmktplaceapi.repository.SecoesRepository;



@SpringBootApplication
public class InitApp {

	@SuppressWarnings("unused")
	@Autowired
	private CategoriasRepository categoriasRepository;
	
	@SuppressWarnings("unused")
	@Autowired
	private RestaurantesRepository restaurantesRepository;
	
	@SuppressWarnings("unused")
	@Autowired 
	private CardapiosRepository cardapiosRepository;
	
	@SuppressWarnings("unused")
	@Autowired
	private SecoesRepository secoesRepository;
	
	@SuppressWarnings("unused")
	@Autowired
	private OpcoesRepository opcoesRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(InitApp.class, args);
	}
	 
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			Opcao opcao = new Opcao();
			opcao.setNome("Teste");
			opcao.setDescricao("Descricao");
			opcao.setPercentualDeDesconto(BigDecimal.TEN);
			opcao.setPromocao(Confirmacao.S);
			opcao.setStatus(Status.A);
			opcao.setCategoria(categoriasRepository.findById(45).get());
			opcao.setRestaurante(restaurantesRepository.findById(10).get());
			opcoesRepository.save(opcao);
			System.out.println("subiu");
		};
	}
}
