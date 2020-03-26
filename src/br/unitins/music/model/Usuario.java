package br.unitins.music.model;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

public class Usuario implements Cloneable {

	private Integer id;
	
	@NotEmpty(message = "O campo Nome não pode ser vazio")
	@Size(max = 60, message = "O campo Nome deve conter no maximo 60 caracteres")
	private String nome;
	@Email
	private String login;
	@Size(min = 6, max = 30, message = "A senha deve conter entre 6 e 30 caracteres")
	private String senha;
	@Past
	private LocalDate dataAniversario;
	private Boolean ativo = Boolean.TRUE;
	private Telefone telefone;
	private Perfil perfil;
	private Endereco endereco;
	
	public Usuario() {
		super();
	}

	public Usuario(Integer id, String nome, String login, String senha, Boolean ativo, LocalDate data, Telefone telefone, Endereco endereco) {
		super();
		this.id = id;
		this.nome = nome;
		this.login = login;
		this.senha = senha;
		this.ativo = ativo;
		this.dataAniversario = data;
		this.telefone = telefone;
		this.endereco = endereco;
	}

	@Override
	public Usuario clone() {
		try {
			return (Usuario) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			System.out.println("Erro ao clonar.");
		}
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public LocalDate getDataAniversario() {
		return dataAniversario;
	}

	public void setDataAniversario(LocalDate dataAniversario) {
		this.dataAniversario = dataAniversario;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

}
