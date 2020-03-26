package br.unitins.music.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.music.dao.ProdutoDAO;
import br.unitins.music.model.Produto;

@Named
@ViewScoped
public class ConsultaProdutoController implements Serializable {

	private static final long serialVersionUID = -5361541934905883966L;

	private String nome;
	private List<Produto> listaProduto = null;
	
	public void pesquisar() {
		listaProduto = null;
		
	}
	
	public String editar(int id) {
		ProdutoDAO dao = new ProdutoDAO();
		// buscando um usuario pelo id
		Produto produto = dao.findById(id);
		Flash flash = FacesContext.
				getCurrentInstance().
				getExternalContext().getFlash();
		flash.put("produtoFlash", produto);
		
		return "produto.xhtml?faces-redirect=true";
	}

	public List<Produto> getListaProduto() {
		if (listaProduto == null) {
			ProdutoDAO dao = new ProdutoDAO();
			listaProduto = dao.findByNome(getNome());
			if (listaProduto == null)
				listaProduto = new ArrayList<Produto>();
			dao.closeConnection();
		}
		return listaProduto;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


}
