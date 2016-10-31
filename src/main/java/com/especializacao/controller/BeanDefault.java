package com.especializacao.controller;

import java.io.Serializable;

import com.especializacao.model.Operacao;

public class BeanDefault implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Operacao operacao = Operacao.LISTANDO;
	
	public void setOperacaoListando() {
		this.operacao = Operacao.LISTANDO;
	}

	public void setOperacaoCadastrando() {
		this.operacao = Operacao.CADASTRANDO;
	}
	
	public void setOperacaoEditando() {
		this.operacao = Operacao.EDITANDO;
	}

	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}
	
	public boolean isListando() {
		return operacao == Operacao.LISTANDO;
	}

	public boolean isCadastrando() {
		return this.operacao == Operacao.CADASTRANDO;
	}

	public boolean isEditando() {
		return this.operacao == Operacao.EDITANDO;
	}

}
