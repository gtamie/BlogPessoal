package org.generation.blogpessoal.security;

import java.util.Collection;
import java.util.List;
import org.generation.blogpessoal.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class UserDetailsImpl implements UserDetails{
	
	private static final long serialVersionUID = 1L;

	private String userName;
	private String password;
	private List<GrantedAuthority> authorities;
	
	//Método construtor com parâmetros
	public UserDetailsImpl(Usuario usuario) {
		
		this.userName = usuario.getUsuario();
		this.password = usuario.getSenha();
	}
	
	
	//Método construtor sem parâmetros
	public UserDetailsImpl() {	}

	
	/**
	 *  Retorna as Autorizações da conta do usuário. 
	 *  Nesta implementação, não há nenhuma autorização negada
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	
		return authorities;
	}
	

	@Override
	public String getPassword() {
		
		return password;
	}

	@Override
	public String getUsername() {
		
		return userName;
	}

	
	
	// Indica se a conta do usuário expirou.
	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}
	
	
	//Indica se o usuário está bloqueado ou desbloqueado.
	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	
	//Indica se as credenciais do usuário (senha) expiraram.
	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	
	//Indica se o usuário está habilitado ou desabilitado. Se mudar para false nenhum usuário conseguirá logar.
	@Override
	public boolean isEnabled() {
		
		return true;
	}
	
	
	

}
