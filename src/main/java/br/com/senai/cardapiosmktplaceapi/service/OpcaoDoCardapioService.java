package br.com.senai.cardapiosmktplaceapi.service;

import org.springframework.stereotype.Service;

import br.com.senai.cardapiosmktplaceapi.entity.OpcaoDoCardapio;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Service
public interface OpcaoDoCardapioService {

	public OpcaoDoCardapio inserirNova(
			@Valid
			@NotNull(message = "A opcão do cardápio não pode ser nula. ")
			OpcaoDoCardapio opcaoDoCardapio);
}
