package br.unitins.music.model;

import java.time.LocalDate;
import java.util.List;

public class Venda {

	private Integer id;
	private LocalDate data;
	private Usuario usuario;
	private List<ItemVenda> listaItemVenda;

	// campo calculado
	private Float totalVenda = 0.0f;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<ItemVenda> getListaItemVenda() {
		return listaItemVenda;
	}

	public void setListaItemVenda(List<ItemVenda> listaItemVenda) {
		this.listaItemVenda = listaItemVenda;
	}

	public Float getTotalVenda() {
		totalVenda = 0.0f;
		if (listaItemVenda != null)
			for (ItemVenda itemVenda : listaItemVenda)
				totalVenda += itemVenda.getValor();
		return totalVenda;
	}

	public void setTotalVenda(Float totalVenda) {
		this.totalVenda = totalVenda;
	}

}
