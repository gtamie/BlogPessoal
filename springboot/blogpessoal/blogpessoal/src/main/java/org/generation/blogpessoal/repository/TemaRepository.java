package org.generation.blogpessoal.repository;

import java.util.List;
import java.util.Optional;

import org.generation.blogpessoal.model.Postagem;
import org.generation.blogpessoal.model.Tema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface TemaRepository extends JpaRepository<Tema, Long> {

	
	public List<Tema> findAllByDescricaoContainingIgnoreCase (String descricao);
	

}
