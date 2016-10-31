package com.especializacao.model;

public enum TipoPeriodico {
	
	REVISTA(1,"Revista"),
	JORNAL(2,"Jornal"),
	DIARIO(3,"Diário"),
	BOLETIM_INFORMATIVO(4,"Boletim Informativo"),
	BOLETIM_DIVULGACAO(5,"Boletim de Divulgação"),
	ANUARIO(6,"Anuário");
	
	private Integer id;
	private String descricao;
	
	TipoPeriodico(Integer id, String descricao) {
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
