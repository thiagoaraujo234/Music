package br.unitins.music.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.music.application.Session;
import br.unitins.music.dao.VendaDAO;
import br.unitins.music.model.Produto;
import br.unitins.music.model.Usuario;
import br.unitins.music.model.Venda;
@Named
@ViewScoped
public class HistoricoVendaController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6376218574407114790L;
	private List<Venda> listaVenda = null;
	

	public List<Venda> getListaVenda() {
		if (listaVenda == null) {
			VendaDAO dao = new VendaDAO();
			Usuario usuario = (Usuario) Session.getInstance().getAttribute("usuarioLogado");
			listaVenda = dao.findByUsuario(usuario.getId());
			if (listaVenda == null)
				listaVenda = new ArrayList<Venda>();
			dao.closeConnection();
		}
		return listaVenda;
	}
	
	public String detalhes(Venda venda) {
		Flash flash = FacesContext.
				getCurrentInstance().
				getExternalContext().getFlash();
		flash.put("detalheVenda", venda);
		
		return "detalhesvenda.xhtml?faces-redirect=true";
	}
	
}
