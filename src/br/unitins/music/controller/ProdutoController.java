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
import br.unitins.music.dao.ProdutoDAO;
import br.unitins.music.model.Perfil;
import br.unitins.music.model.Produto;
import br.unitins.music.model.Telefone;
@Named
@ViewScoped
public class ProdutoController implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1879690143558561201L;
	private Produto produto;
	
	public ProdutoController() {
		Flash flash = FacesContext.
				getCurrentInstance().
				getExternalContext().getFlash();
		flash.keep("produtoFlash");
		produto = (Produto) flash.get("produtoFlash");
	}
	
	public void incluir() {
		DAO<Produto> dao = new ProdutoDAO();
		// faz a inclusao no banco de dados
		try {
			dao.create(getProduto());
			dao.getConnection().commit();
			Util.addMessageInfo("Inclusão realizada com sucesso.");
			limpar();
		} catch (SQLException e) {
			dao.rollbackConnection();
			dao.closeConnection();
			Util.addMessageInfo("Erro ao incluir o Produto no Banco de Dados.");
			e.printStackTrace();
		}
	}
	
	public void alterar() {
		DAO<Produto> dao = new ProdutoDAO();
		// faz a alteracao no banco de dados
		try {
			dao.update(getProduto());
			dao.getConnection().commit();
			Util.addMessageInfo("Alteração realizada com sucesso.");
			limpar();
		} catch (SQLException e) {
			dao.rollbackConnection();
			dao.closeConnection();
			Util.addMessageInfo("Erro ao alterar o Usuario no Banco de Dados.");
			e.printStackTrace();
		}
	}
	
	
	public void excluir() {
		DAO<Produto> dao = new ProdutoDAO();
		// faz a exclusao no banco de dados
		try {
			dao.delete(getProduto().getId());
			dao.getConnection().commit();
			Util.addMessageInfo("Exclusão realizada com sucesso.");
			limpar();
		} catch (SQLException e) {
			dao.rollbackConnection();
			Util.addMessageInfo("Erro ao excluir o Produto no Banco de Dados.");
			e.printStackTrace();
		} finally {
			dao.closeConnection();
		}
	}


	public Produto getProduto() {
		if (produto == null) {
			produto = new Produto();
		}
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public void limpar() {
		produto = null;
	}
	
}
