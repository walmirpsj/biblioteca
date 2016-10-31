package com.especializacao.model;

public enum FrequenciaPeriodico{
	
	SEMANAL(1,"Frequência de publicação Semanal"),
	MENSAL(2,"Frequência de publicação Mensal"),
	BIMESTRAL(3,"Frequência de publicação Bimestral"),
	TRIMESTRAL(4,"Frequência de publicação Trimestral"),
	SEMESTRAL(5,"Frequência de publicação Semestral"),
	ANUAL(6,"Frequência de publicação Anual");
	
	private Integer id;
	private String descricao;

	FrequenciaPeriodico(Integer id, String descricao){
		this.setId(id);
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
