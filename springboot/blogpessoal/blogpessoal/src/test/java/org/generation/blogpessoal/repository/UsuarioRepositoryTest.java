package org.generation.blogpessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.generation.blogpessoal.model.Usuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start() {
		
	usuarioRepository.save(new Usuario(0L,"Lucas Silva","lucas@gmail.com","12345678","abcs"));
	usuarioRepository.save(new Usuario(0L,"Lucas Souza","lucsouza@gmail.com","12345678","abcs"));
	usuarioRepository.save(new Usuario(0L,"Lucas Santos","lucsantos@gmail.com","12345678","abcs"));
	usuarioRepository.save(new Usuario(0L,"Gabriela Montes","gab@gmail.com","12345678","abcs"));
	}
	
	@Test
	@DisplayName("Retorna 1 usuário")
	public void deveRetornarUmUsuario() {
		Optional <Usuario> usuario = usuarioRepository.findByUsuario("lucas@gmail.com");
		assertTrue(usuario.get().getUsuario().equals("lucas@gmail.com"));
	}
	
	@Test
	@DisplayName("Retorna 3 usuários")
	public void deveRetornarTresUsuario() {
		List <Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Lucas");
		assertEquals(3, listaDeUsuarios.size());
		assertTrue(listaDeUsuarios.get(0).getNome().equals("Lucas Silva"));
		assertTrue(listaDeUsuarios.get(1).getNome().equals("Lucas Souza"));
		assertTrue(listaDeUsuarios.get(2).getNome().equals("Lucas Santos"));
	}
	
	


}
