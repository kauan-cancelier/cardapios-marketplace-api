
package br.com.senai.cardapiosmktplaceapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	private MapConverter converter;
	
	@Autowired
	@Qualifier("categoriaServiceProxy")
	CategoriaService categoriaService;
	
	@GetMapping("/id/{id}")
	public ResponseEntity<?> buscarPor(@PathVariable("id") Integer id) {
		Categoria categoria = categoriaService.buscarPor(id);
		return ResponseEntity.ok(converter.toJsonMap(categoria));
	}
	
}
