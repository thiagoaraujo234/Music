package br.unitins.music.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.unitins.music.application.Session;
import br.unitins.music.application.Util;
import br.unitins.music.dao.UsuarioDAO;
import br.unitins.music.model.Usuario;

@Named
@RequestScoped
public class LoginController {
	
	private Usuario usuario;

	
	public String logar() {
		UsuarioDAO dao = new UsuarioDAO();
		String hashSenha = Util.hashSHA256(getUsuario().getSenha());
		Usuario usuario = 
			dao.login(getUsuario().getLogin(), hashSenha);
		
		if (usuario != null) {
			// armazenando um usuario na sessao
			Session.getInstance().setAttribute("usuarioLogado", usuario);
			return "inicio.xhtml?faces-redirect=true";
		}
		Util.addMessageError("Usuario ou Senha Invalido.");
		return null;
	}
	
	public void limpar() {
		setUsuario(new Usuario());
//		usuario = new Usuario();
	}

	public Usuario getUsuario() {
		if (usuario == null)
			usuario = new Usuario();
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
