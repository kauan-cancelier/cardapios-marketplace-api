package br.com.senai.cardapiosmktplaceapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import br.com.senai.cardapiosmktplaceapi.entity.Cardapio;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.Secao;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.repository.CardapiosRepository;
import br.com.senai.cardapiosmktplaceapi.repository.CategoriasRepository;
import br.com.senai.cardapiosmktplaceapi.repository.RestaurantesRepository;
import br.com.senai.cardapiosmktplaceapi.repository.SecoesRepository;



@SpringBootApplication
public class InitApp {

	@Autowired
	private CategoriasRepository categoriasRepository;
	
	@Autowired
	private RestaurantesRepository restaurantesRepository;
	
	@Autowired 
	private CardapiosRepository cardapiosRepository;
	
	@Autowired
	private SecoesRepository secoesRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(InitApp.class, args);
	}
	 
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			Cardapio cardapio = new Cardapio();
			cardapio.setNome("pizzaria do kauan");
			cardapio.setDescricao("descr");
			cardapio.setStatus(Status.A);
			Restaurante restaurante = restaurantesRepository.findById(9).get();
			cardapio.setRestaurante(restaurante);
//			cardapiosRepository.save(cardapio);
			
			Secao secao = new Secao();
			secao.setNome("Secao do kauan");
			secao.setStatus(Status.A);
			secoesRepository.save(secao);
			
			
			System.out.println("salvou");
		};
	}
}
