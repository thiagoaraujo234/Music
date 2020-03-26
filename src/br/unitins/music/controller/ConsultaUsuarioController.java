package br.unitins.music.controller;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import br.unitins.music.dao.UsuarioDAO;
import br.unitins.music.model.Usuario;

@Named
@ViewScoped
public class ConsultaUsuarioController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1519924873802660972L;
	private String nome;
	private List<Usuario> listaUsuario = null;

	public void pesquisar() {
		listaUsuario = null;

	}

	public String editar(int id) {
		UsuarioDAO dao = new UsuarioDAO();
		// buscando um usuario pelo id
		Usuario usuario = dao.findId(id);
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("usuarioFlash", usuario);

		return "usuario.xhtml?faces-redirect=true";
	}

	public List<Usuario> getListaUsuario() {
		if (listaUsuario == null) {
			UsuarioDAO dao = new UsuarioDAO();
			listaUsuario = dao.findByNome(getNome());
			if (listaUsuario == null)
				listaUsuario = new ArrayList<Usuario>();
			dao.closeConnection();
		}
		return listaUsuario;
	}
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
