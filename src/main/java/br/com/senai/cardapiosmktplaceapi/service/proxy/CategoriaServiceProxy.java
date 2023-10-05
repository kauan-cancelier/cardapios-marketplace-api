package br.com.senai.cardapiosmktplaceapi.service.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.senai.cardapiosmktplaceapi.entity.Categoria;
import br.com.senai.cardapiosmktplaceapi.entity.enums.Status;
import br.com.senai.cardapiosmktplaceapi.entity.enums.TipoDeCategoria;
import br.com.senai.cardapiosmktplaceapi.service.CategoriaService;

@Service
public class CategoriaServiceProxy implements CategoriaService{

	@Autowired
	@Qualifier("categoriaServiceImpl")
	private CategoriaService categoriaService;
	
	@Override
	public Categoria salvar(Categoria categoria) {
		return categoriaService.salvar(categoria);
	}

	@Override
	public void atualizarStatusPor(Integer id,  Status status) {
		categoriaService.atualizarStatusPor(id, status);
	}

	@Override
	public Page<Categoria> listarPor(String nome, Status status, TipoDeCategoria tipo, Pageable pageable) {
		return categoriaService.listarPor(nome, status, tipo, pageable);
	}

	@Override
	public Categoria buscarPor(Integer id) {
		return categoriaService.buscarPor(id);
	}

	@Override
	public Categoria excluirPor(Integer id) {
		return categoriaService.excluirPor(id);
	}

}
