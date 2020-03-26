package br.unitins.music.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.music.application.Util;
import br.unitins.music.dao.DAO;
import br.unitins.music.dao.UsuarioDAO;
import br.unitins.music.model.Endereco;
import br.unitins.music.model.Perfil;
import br.unitins.music.model.Telefone;
import br.unitins.music.model.Usuario;
@Named
@ViewScoped
//dontpad.com/sisunitins_topicos1_20192
public class UsuarioController implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4774267733499210016L;

	private Usuario usuario;
	
	private List<Usuario> listaUsuario;
	
	public List<Usuario> getListaUsuario() {
		if (listaUsuario == null) {
			DAO<Usuario> dao = new UsuarioDAO();
			listaUsuario = dao.findAll();
			if (listaUsuario == null)
				listaUsuario = new ArrayList<Usuario>();
		} 
		return listaUsuario;
	}	
	public UsuarioController() {
		Flash flash = FacesContext.
				getCurrentInstance().
				getExternalContext().getFlash();
		flash.keep("usuarioFlash");
		usuario = (Usuario) flash.get("usuarioFlash");
	}

	public void incluir() {
		if (validarDados()) {
			DAO<Usuario> dao = new UsuarioDAO();
			// faz a inclusao no banco de dados
			try {
				
				getUsuario().setSenha(Util.hashSHA256(getUsuario().getSenha()));
				
				dao.create(getUsuario());
				dao.getConnection().commit();
				Util.addMessageInfo("Inclusão realizada com sucesso.");
				limpar();
				listaUsuario = null;
			} catch (SQLException e) {
				dao.rollbackConnection();
				dao.closeConnection();
				Util.addMessageInfo("Erro ao incluir o Usuario no Banco de Dados.");
				e.printStackTrace();
			}
		}
	}
	
	public void alterar() {
		if (validarDados()) {
			DAO<Usuario> dao = new UsuarioDAO();
			// faz a alteracao no banco de dados
			try {
				// gerando um hash da senha
				getUsuario().setSenha(Util.hashSHA256(getUsuario().getSenha()));
				dao.update(getUsuario());
				dao.getConnection().commit();
				Util.addMessageInfo("Alteração realizada com sucesso.");
				limpar();
				listaUsuario = null;
			} catch (SQLException e) {
				dao.rollbackConnection();
				dao.closeConnection();
				Util.addMessageInfo("Erro ao alterar o Usuario no Banco de Dados.");
				e.printStackTrace();
			}
				
		}
	}
	
	public void excluir() {
		if (excluir(getUsuario()))
			limpar();
	}
	
	public boolean excluir(Usuario usuario) {
		DAO<Usuario> dao = new UsuarioDAO();
		// faz a exclusao no banco de dados
		try {
			dao.delete(getUsuario().getId());
			dao.getConnection().commit();
			Util.addMessageInfo("Exclusão realizada com sucesso.");
			return true;
		} catch (SQLException e) {
			dao.rollbackConnection();
			Util.addMessageInfo("Erro ao excluir o Produto no Banco de Dados.");
			e.printStackTrace();
			return false;
		} finally {
			dao.closeConnection();
		}
	}

	private boolean validarDados() {
//		if (getUsuario().getSenha().isBlank()) {
//			Util.addMessageWarn("O campo senha deve ser informado.");
//			return false;
//		}
		if (getUsuario().getSenha() == null || 
				getUsuario().getSenha().trim().equals("") ) {
			Util.addMessageError("O campo senha deve ser informado.");
			return false;
		}
		return true;
	}
	
	private int ultimoId() {
		int maior = 0;
		for (Usuario usuario : listaUsuario) {
			if (usuario.getId() > maior)
				maior = usuario.getId();
		}
		return maior;
	}
	
	public void editar(Usuario usuario) {
		UsuarioDAO dao = new UsuarioDAO();
		// buscando um usuario pelo id
		Usuario usu = dao.findId(usuario.getId());
		setUsuario(usu);
//		setUsuario(dao.findId(usuario.getId()));
	}

	public Usuario getUsuario() {
		if (usuario == null) {
			usuario = new Usuario();
			usuario.setTelefone(new Telefone());
			usuario.setEndereco(new Endereco());
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void limpar() {
		usuario = null;
	}
	
	public Perfil[] getListaPerfil() {
		return Perfil.values();
	}
	
}
