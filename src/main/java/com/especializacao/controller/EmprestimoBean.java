package com.especializacao.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.New;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityTransaction;

import com.especializacao.dao.EmprestimoDao;
import com.especializacao.dao.LivroDao;
import com.especializacao.dao.PeriodicoDao;
import com.especializacao.dao.PessoaDao;
import com.especializacao.exception.NegocioException;
import com.especializacao.model.Emprestimo;
import com.especializacao.model.Livro;
import com.especializacao.model.Periodico;
import com.especializacao.model.Pessoa;
import com.especializacao.transaction.Transactional;

@Named
@ViewScoped
public class EmprestimoBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject @New
	private Emprestimo emprestimo;
	@Inject
	private EmprestimoDao emprestimoDao;
	@Inject
	private LivroDao livroDao;
	@Inject
	private PeriodicoDao periodicoDao;
	@Inject
	private PessoaDao pessoaDao;
	private Emprestimo emprestimoSelecionado;
	private List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();
	private List<Pessoa> pessoas = new ArrayList<Pessoa>();
	private List<Livro> livros = new ArrayList<Livro>();
	private List<Periodico> periodicos = new ArrayList<Periodico>();
	
	@PostConstruct
	public void init(){
		pessoas = pessoaDao.todos();
		livros = livroDao.todos();
		periodicos = periodicoDao.todos();
		emprestimos = emprestimoDao.todos();
	}
	
	public void clean(){
		emprestimo = new Emprestimo();
		emprestimoSelecionado = null;
		emprestimoDao = new EmprestimoDao();
		pessoas.clear();
		livros.clear();
		periodicos.clear();
	}
	
	@Transactional
	public String novo(){
		emprestimo = new Emprestimo();
		return "/cadastrarEmprestimo?faces-redirect=true";
	}
	
	@Transactional
	public String salvar(){
		EntityTransaction trx = emprestimoDao.getManager().getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			trx.begin();
			if(emprestimo.getDataEmprestimo() != null && emprestimo.getDataEmprestimo().after(new Date())) { 
					throw new NegocioException("Data de empréstimo não pode ser uma data futura.");
			}
			if((emprestimo.getLivro() != null && emprestimo.getLivro().getIdLivro() == null) || (emprestimo.getPeriodico()!= null && emprestimo.getPeriodico().getIdPeriodico() == null)){
				throw new NegocioException("Selecione um Livro ou Periódico para realizar o empréstimo.");
			}
			if(emprestimo.getIdEmprestimo() == null){
				emprestimo.setDataCadastro(new Date());
				emprestimoDao.salvar(emprestimo);
				context.addMessage(null, new FacesMessage("Empréstimo salvo com sucesso!"));
			}else{
				emprestimoDao.editar(emprestimo);
				context.addMessage(null, new FacesMessage("Empréstimo alterado com sucesso!"));
			}
			trx.commit();
		} catch (NegocioException n) {
			trx.rollback();
			FacesMessage mensagem = new FacesMessage(n.getMessage());
			mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, mensagem);
			return "";
		}finally {
			emprestimoDao.getManager().close();
			clean();
		}
		return "consultaEmprestimos";
	}
	
	public void excluir(Emprestimo emprestimo) throws NegocioException{
		if(emprestimo.getIdEmprestimo() != null){
			emprestimoDao.remover(emprestimo);
		}
	}
	
	@Transactional
	public void excluir(){
		EntityTransaction trx = emprestimoDao.getManager().getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			trx.begin();
			excluir(emprestimoSelecionado);
			context.addMessage(null, new FacesMessage("Empréstimo excluído com sucesso!"));
			trx.commit();
		}catch (NegocioException e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, mensagem);
		} finally {
			emprestimoDao.getManager().close();
			clean();
			init();
		}
	}
	
	public String cancelar(){
		emprestimo = new Emprestimo();
		init();
		return "/consultaEmprestimos?faces-redirect=true";
	}

	public Emprestimo getEmprestimo() {
		return emprestimo;
	}

	public void setEmprestimo(Emprestimo emprestimo) {
		this.emprestimo = emprestimo;
	}

	public List<Emprestimo> getEmprestimos() {
		return emprestimos;
	}

	public void setEmprestimos(List<Emprestimo> emprestimos) {
		this.emprestimos = emprestimos;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public List<Livro> getLivros() {
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}

	public List<Periodico> getPeriodicos() {
		return periodicos;
	}

	public void setPeriodicos(List<Periodico> periodicos) {
		this.periodicos = periodicos;
	}

	public Emprestimo getEmprestimoSelecionado() {
		return emprestimoSelecionado;
	}

	public void setEmprestimoSelecionado(Emprestimo emprestimoSelecionado) {
		this.emprestimoSelecionado = emprestimoSelecionado;
	}

	
}
