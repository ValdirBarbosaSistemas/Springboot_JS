package br.com.springbootJS.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.springbootJS.model.Usuario;
import br.com.springbootJS.repository.UsuarioRepository;

@RestController
/* @RequestMapping("/usuarios") */
public class UsuarioController {

	@Autowired
	private UsuarioRepository repository;

	@PostMapping(value = "/novousuario")
	public ResponseEntity<Usuario> inserirUsuario(@RequestBody Usuario usuario) {
		Usuario user = repository.save(usuario);
		
		return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
	}

	@GetMapping("/listarTodos")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Usuario> obterUsuario() {
		List<Usuario> us = repository.findAll();
		return us;
	}

	@GetMapping("/buscarUsuarioId")
	@ResponseStatus(code = HttpStatus.OK)
	public Usuario obterUsuarioID(@RequestParam Long id) {

		Usuario usu = repository.findById(id).get();
		return usu;
	}
	
	@GetMapping("/buscarPorNome")
	public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam (name = "nome") String nome){
		
		List<Usuario> usuario = repository.buscarPorNome(nome);
		return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);
		
	}

	@PutMapping("/atualizarcliente")
	@ResponseBody
	public ResponseEntity<?> atualizarUsuario(@RequestBody Usuario usuario) {
		
		if(usuario.getId() == null) {
			return new ResponseEntity<String>("É necessário um ID para realizar a atualizacao", HttpStatus.CONFLICT);
		}
		Usuario user = repository.saveAndFlush(usuario);
		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteUsuario(@RequestParam Long id) {
		Optional<Usuario> usuarioDeletar = repository.findById(id);
		
		if (usuarioDeletar.isPresent()) {
			repository.delete(usuarioDeletar.get());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado");
		}
	}
}
