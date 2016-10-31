package com.especializacao.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.New;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityTransaction;

import com.especializacao.dao.PessoaDao;
import com.especializacao.exception.NegocioException;
import com.especializacao.model.Pessoa;
import com.especializacao.transaction.Transactional;

@Named
@ViewScoped
public class PessoaBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject @New
	private Pessoa pessoa;
	@Inject
	private PessoaDao pessoaDao;
	
	private Pessoa pessoaSelecionada;
	private List<Pessoa> pessoas = new ArrayList<Pessoa>();
	
	@PostConstruct
	public void init(){
		pessoas = pessoaDao.todos();
	}
	
	@Transactional
	public String novo(){
		pessoa = new Pessoa();
		return "/cadastrarPessoa?faces-redirect=true";
	}
	
	public void salvar(Pessoa pessoa) throws NegocioException{
		pessoaDao.salvar(pessoa);
	}
	
	public void editar(Pessoa pessoa) throws NegocioException{
		pessoaDao.editar(pessoa);
	}
	
	@Transactional
	public String salvar(){
		EntityTransaction trx = pessoaDao.getManager().getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			trx.begin();
			if(pessoa.getIdPessoa() == null){
				salvar(pessoa);
				context.addMessage(null, new FacesMessage("Usuário salvo com sucesso!"));
			}else{
				editar(pessoa);
				context.addMessage(null, new FacesMessage("Usuário alterado com sucesso!"));
			}
			trx.commit();
		}catch (NegocioException e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, mensagem);
		}finally {
			pessoaDao.getManager().close();
			clean();
		}
		return "consultaPessoas";
	}
	
	public void excluir(Pessoa pessoa) throws NegocioException{
		if(pessoa.getIdPessoa() != null){
			pessoaDao.remover(pessoa);
		}
	}
	
	@Transactional
	public void excluir(){
		EntityTransaction trx = pessoaDao.getManager().getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			trx.begin();
			excluir(pessoaSelecionada);
			context.addMessage(null, new FacesMessage("Pessoa excluída com sucesso!"));
			trx.commit();
		}catch (NegocioException e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, mensagem);
		} finally {
			pessoaDao.getManager().close();
			clean();
		}
	}
	
	
	public void clean() {
		pessoa = new Pessoa();
		pessoaSelecionada = new Pessoa();
		pessoaDao = new PessoaDao();
		pessoas.clear();
		init();
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}


	public List<Pessoa> getPessoas() {
		return pessoas;
	}


	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public Pessoa getPessoaSelecionada() {
		return pessoaSelecionada;
	}

	public void setPessoaSelecionada(Pessoa pessoaSelecionada) {
		this.pessoaSelecionada = pessoaSelecionada;
	}


}
