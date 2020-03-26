package br.unitins.music.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class AjaxController implements Serializable {

	private static final long serialVersionUID = 2247712473536860066L;
	
	private String nome = "Teste";
	
	public void imprimir() {
		System.out.println(nome);
		nome = "janio";
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
