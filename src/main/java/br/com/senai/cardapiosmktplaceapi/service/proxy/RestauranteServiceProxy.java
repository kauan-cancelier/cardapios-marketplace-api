package br.com.senai.cardapiosmktplaceapi.service.proxy;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.senai.cardapiosmktplaceapi.dto.Notificacao;
import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.Restaurante;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.service.RestauranteService;

@Service
public class RestauranteServiceProxy implements RestauranteService{
	
	@Autowired
	@Qualifier("restauranteServiceImpl")
	private RestauranteService restauranteService;
	
	@Autowired
	private ProducerTemplate toEmail;
	
	@Value("${email.endereco-admin}")
	private String enderecoDeEmail;

	@Override
	public Restaurante salvar(Restaurante restaurante) {
		Restaurante restauranteSalvo = this.restauranteService.salvar(restaurante);
		Notificacao notificacao = gerarNotificacaoPara(restauranteSalvo);
		this.toEmail.sendBody("direct:enviarEmail", notificacao);
		return restauranteSalvo;

	}
	
	
	private Notificacao gerarNotificacaoPara(Restaurante restaurante) {
		Notificacao notificacao = new Notificacao();
		notificacao.setDestinatario(enderecoDeEmail);
		notificacao.setTitulo("Restaurante criado/atualizado");
		StringBuilder text = new StringBuilder();
		text.append("<p>Olá, </p> <p>O restaurante <b>");
		text.append(restaurante.getNome() + "</b> Foi criado/atualizado no sistema </p>");
		text.append("<p> Esse e-mail é automatico. Não deve ser respondido. </p>");
		text.append("<p>Atenciosamente, <b>Marketplace SENAI</b></p>");
		notificacao.setMensagem(text.toString());		
		return notificacao;
	}

	@Override
	public void atualizarStatusPor(Integer id, Status status) {
		this.restauranteService.atualizarStatusPor(id, status);
	}

	@Override
	public Page<Restaurante> listarPor(String nome, Categoria categoria, Pageable pageable) {
		return this.restauranteService.listarPor(nome, categoria, pageable);
	}

	@Override
	public Restaurante buscarPor(Integer id) {
		return this.restauranteService.buscarPor(id);
	}

	@Override
	public Restaurante excluirPor(Integer id) {
		return this.restauranteService.excluirPor(id);
	}

}
