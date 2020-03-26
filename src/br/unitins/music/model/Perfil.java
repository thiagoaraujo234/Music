package br.unitins.music.model;

public enum Perfil {
	ADMINISTRADOR(1, "Administrador"), 
	FUNCIONARIO(2, "Funcionário"), 
	CLIENTE(3, "Cliente");
	
	private int value;
	private String label;
	
	Perfil(int value, String label) {
		this.value = value;
		this.label = label;
	}

	public int getValue() {
		return value;
	}

	public String getLabel() {
		return label;
	}
	
	public static Perfil valueOf(int value) {
		
		for (Perfil perfil : Perfil.values()) {
			if (perfil.getValue() == value) 
				return perfil;
		}
		return null;
	}
	

}
