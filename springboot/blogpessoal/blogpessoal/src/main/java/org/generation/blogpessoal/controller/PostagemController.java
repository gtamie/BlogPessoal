package org.generation.blogpessoal.controller;

import java.util.List;

import org.generation.blogpessoal.model.Postagem;
import org.generation.blogpessoal.repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/postagens")
public class PostagemController {
	
	@Autowired 
	private PostagemRepository postagemRepository;
	
	@GetMapping
	public ResponseEntity<List<Postagem>> getAll (){
		return ResponseEntity.ok(postagemRepository.findAll());
	}
	
}
