package org.generation.blogpessoal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/* BasicSecurityConfig : configura o tipo de criptografia que será utilizada na senha,
 * qual o tipo de segurança que utilizaremos (basic), e quais End Point’s que serão 
 * liberados para que usuário possa acessar
 * 
 * @EnableWebSecurity : habilita a configuração de segurança padrão do Spring Security na nossa api.
 *
 * extends WebSecurityConfigurerAdapter : habilitar a segurança HTTP no Spring, 
 * fornece uma configuração padrão no método configure (HttpSecurity http). 
 * WebSecurityConfigurerAdapter is a convenience class that allows customization to both WebSecurity and HttpSecurity. 
 * 
 * Configuração padrão : garante que qualquer requisição enviada para a API 
 * seja autenticada com login baseado em formulário ou autenticação via Browser.
 */

@EnableWebSecurity              
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 *  Configure : cria uma nova instância da Classe AuthenticationManagerBuilder e 
	 *  define que o login será efetuado através dos usuários criados no Banco de dados.
	 *  Para recuperar os dados do usuário no Banco de Dados utilizaremos a Interface 
	 *  UserDetailsService.
	 *  O método é do tipo protected (permite acesso às classes filhas, mas proíbe a qualquer 
	 *  outro acesso externo) por definição da classe.
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		/**
		 *  auth : registra e cria uma nova instância do objeto userDetailsService
		 *  da interface UserDetailsService implementada na Classe UserDetailsServiceImpl
		 *  para recuperar os dados dos usuários gravados no Banco de dados.
		 */
		auth.userDetailsService(userDetailsService);
		
		//inMemoryAuthentication : autenticação baseada em usuário e senha e armazenada na memória
		auth.inMemoryAuthentication()
		.withUser("root")
		.password(passwordEncoder().encode("root"))
		.authorities("ROLE_USER");
	}
		
		
	/*  
	 * @Bean : transforma a instância retornada pelo método como um 
	 *  objeto gerenciado pelo Spring, desta forma, ele pode ser injetado em qualquer
	 *  classe, a qualquer momento que você precisar sem a necessidade de usar a 
	 *  annotation @Autowired
	 *  
	 *  passwordEncoder() : criptografa a senha do usuário utilizando o 
	 *  método hash Bcrypt.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	/*
	 *
	 */
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		/**
		 * http.authorizeHttpRequests() 
		 *.antMatchers("/usuarios/logar").permitAll()     -> Libera endpoints para o client sem que ele tenha que fornecer a chave token 
		 *.antMatchers("/usuarios/cadastrar").permitAll()
		 *.antMatchers(HttpMethod.OPTIONS).permitAll()    -> O parâmetro HttpMethod.OPTIONS permite que 
		 * 													o cliente (frontend), possa descobrir quais são as opções de 
		 * 													requisição permitidas para um determinado recurso em um servidor. 
		 *													Nesta implementação, está sendo liberada todas as opções das 
		 * 													requisições através do método permitAll().
		 *.anyRequest().authenticated()                   -> qualquer outro endpoint precisará do token
		 *.and().httpBasic()                              -> vamos utilizar o padrão Basic Security para gerar a chave token
		 *.and().sessionManagement()                      -> Cria um gerenciador de Sessões
		 *      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  -> Define como o Spring Secuiryt irá criar (ou não) as sessões
		 * 																	STATELESS : Nunca será criada uma sessão, ou seja, basta enviar
		 * 																	o token através do cabeçalho da requisição que a mesma será processada.
		 *.and().cors()                                   -> O compartilhamento de recursos de origem cruzada (CORS) surgiu 
		 * 													porquê os navegadores não permitem solicitações feitas por um domínio
		 * 													(endereço) diferente daquele de onde o site foi carregado. Desta forma o 
		 * 													Frontend da aplicação, por exemplo, obrigatoriamente teria que estar 
		 * 													no mesmo domínio que o Backend. Habilitando o CORS, o Spring desabilita 
		 * 													esta regra e permite conexões de outros domínios.
		 *.and().csrf().disable();                        -> CSRF : O cross-site request forgery (falsificação de solicitação entre 
		 *													sites), é um tipo de ataque no qual comandos não autorizados são 
		 *													transmitidos a partir de um usuário em quem a aplicação web confia. 
		 * 													csrf().disabled() : Esta opção de proteção é habilitada por padrão no 
		 * 													Spring Security, entretanto precisamos desabilitar, caso contrário, todos 
		 * 													os endpoints que respondem ao verbo POST não serão executados.
		 * 
		 */
		http.authorizeHttpRequests() 
		.antMatchers("/usuarios/logar").permitAll()      
		.antMatchers("/usuarios/cadastrar").permitAll()
		.antMatchers(HttpMethod.OPTIONS).permitAll()
		.anyRequest().authenticated()     
		.and().httpBasic()    
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) 
		.and().cors()
		.and().csrf().disable();
	}

	

}
