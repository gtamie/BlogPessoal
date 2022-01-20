package org.generation.blogpessoal.controller;

import java.util.List;

import javax.validation.Valid;

import org.generation.blogpessoal.model.Tema;
import org.generation.blogpessoal.repository.PostagemRepository;
import org.generation.blogpessoal.repository.TemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/tema")
public class TemaController {
	
	@Autowired
	private TemaRepository temaRepository;
	
	@GetMapping
	public ResponseEntity<List<Tema>> getAllTema(){
		return ResponseEntity.ok(temaRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Tema> getByIdTema(@PathVariable long id){
		return temaRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/descricao/{descricao}")
	public ResponseEntity<List<Tema>> getByDescricao(@PathVariable String descricao) {
		return ResponseEntity.ok(temaRepository.findAllByDescricaoContainingIgnoreCase(descricao));
		
		
	}
	
	@PostMapping
	public ResponseEntity<Tema> postTema(@RequestBody @Valid Tema tema){
		return ResponseEntity.status(HttpStatus.CREATED).body(temaRepository.save(tema));
	}
	
	@PutMapping
	public ResponseEntity<Tema> putTema(@RequestBody @Valid Tema tema){
		return temaRepository.findById(tema.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.CREATED)
						.body(temaRepository.save(tema)))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTema(@PathVariable long id){
		return temaRepository.findById(id).map(resposta -> {temaRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();})
				.orElse(ResponseEntity.notFound().build());
	}
				
		
	

	
}
