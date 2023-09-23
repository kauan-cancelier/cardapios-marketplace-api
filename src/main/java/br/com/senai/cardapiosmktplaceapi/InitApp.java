package br.com.senai.cardapiosmktplaceapi;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;

import br.com.senai.cardapiosmktplaceapi.entity.Opcao;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Confirmacao;
import br.com.senai.cardapiosmktplaceapi.repository.CardapiosRepository;
import br.com.senai.cardapiosmktplaceapi.repository.CategoriasRepository;
import br.com.senai.cardapiosmktplaceapi.repository.OpcoesRepository;
import br.com.senai.cardapiosmktplaceapi.repository.RestaurantesRepository;
import br.com.senai.cardapiosmktplaceapi.repository.SecoesRepository;
import br.com.senai.cardapiosmktplaceapi.service.OpcaoService;
import br.com.senai.cardapiosmktplaceapi.service.impl.OpcaoServiceimpl;

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
	
	@Autowired
	OpcaoServiceimpl opcaoServiceimpl;
	
	public static void main(String[] args) {
		SpringApplication.run(InitApp.class, args);
	}
	
	@Bean
	public Hibernate5JakartaModule jsonHibernate5Module() {
		return new Hibernate5JakartaModule();
	}
	 
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			Opcao opcao = new Opcao();
			opcao.setCategoria(categoriasRepository.buscarPor(38));
			opcao.setDescricao("descricao");
			opcao.setNome("Opção 2");
			opcao.setRestaurante(restaurantesRepository.buscarPor(2));
			opcao.setPromocao(Confirmacao.S);
			opcao.setPercentualDeDesconto(BigDecimal.valueOf(1));
			opcaoServiceimpl.salvar(opcao);
		};
	}
}
