package br.unitins.music.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.music.application.Session;
import br.unitins.music.application.Util;
import br.unitins.music.dao.VendaDAO;
import br.unitins.music.model.ItemVenda;
import br.unitins.music.model.Usuario;
import br.unitins.music.model.Venda;

@Named
@ViewScoped
public class CarrinhoController	 implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1713948559100046427L;
	private Venda venda;

	public Venda getVenda() {
		if (venda == null)
			venda = new Venda();

		// obtendo o carrinho da sessao
		List<ItemVenda> carrinho = (ArrayList<ItemVenda>) Session.getInstance().getAttribute("carrinho");

		// adicionando os itens do carrinho na venda
		if (carrinho == null)
			carrinho = new ArrayList<ItemVenda>();
		venda.setListaItemVenda(carrinho);

		return venda;
	}

	public void remover(int idProduto) {
		List<ItemVenda> carrinho = (ArrayList<ItemVenda>) Session.getInstance().getAttribute("carrinho");
		venda.getListaItemVenda().remove(idProduto);
		Util.addMessageWarn("Produto removido!");
	}

	public void finalizar() {
		Usuario usuario = (Usuario) Session.getInstance().getAttribute("usuarioLogado");
		if (usuario == null) {
			Util.addMessageWarn("Faça Login para finalizar!");
			return;
		}
		// montar a venda
		Venda venda = new Venda();
		venda.setData(LocalDate.now());
		venda.setUsuario(usuario);
		List<ItemVenda> carrinho = (ArrayList<ItemVenda>) Session.getInstance().getAttribute("carrinho");
		venda.setListaItemVenda(carrinho);
		// salvar no banco
		VendaDAO dao = new VendaDAO();
		try {
			dao.create(venda);
			dao.getConnection().commit();
			Util.addMessageInfo("Venda realizada com sucesso.");
			// limpando o carrinho
			Session.getInstance().setAttribute("carrinho", null);
		} catch (SQLException e) {
			dao.rollbackConnection();
			dao.closeConnection();
			Util.addMessageInfo("Erro ao finalizar a Venda.");
			e.printStackTrace();
		}

	}

	public void setVenda(Venda venda) {

		this.venda = venda;
	}

}
