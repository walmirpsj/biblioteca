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

import com.especializacao.dao.LivroDao;
import com.especializacao.exception.NegocioException;
import com.especializacao.model.Livro;
import com.especializacao.transaction.Transactional;

@Named
@ViewScoped
public class LivroBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject @New
	private Livro livro;
	@Inject
	private LivroDao livroDao;
	
	private Livro livroSelecionado;
	private List<Livro> livros = new ArrayList<Livro>();
	
	@PostConstruct
	public void init(){
		
	}
	
	@Transactional
	public String novo(){
		livro = new Livro();
		return "/cadastrarLivro?faces-redirect=true";
	}
	
	public void salvar(Livro livro) throws NegocioException{
		livroDao.salvar(livro);
	}
	
	public void editar(Livro livro) throws NegocioException{
		livroDao.editar(livro);
	}
	
	@Transactional
	public String salvar() {
		EntityTransaction trx = livroDao.getManager().getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			trx.begin();
			if(livro.getIdLivro() == null){
				salvar(livro);
				context.addMessage(null, new FacesMessage("Livro salvo com sucesso!"));
			}else{
				editar(livro);
				context.addMessage(null, new FacesMessage("Livro Alterado com sucesso!"));
			}
			trx.commit();
		}catch (NegocioException e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, mensagem);
		}finally {
			livroDao.getManager().close();
			clean();
		}
		return "";
	}
	
	public void pesquisar(){
		FacesContext context = FacesContext.getCurrentInstance();
		livros = livroDao.listarLivrosPorParametros(livro.getTitulo(), livro.getAutor(), livro.getIsbn(), livro.getAssunto(), livro.getEditora(), livro.getAnoPublicacao());
		if(livros.isEmpty()){
			FacesMessage mensagem = new FacesMessage("A pesquisa não retornou registros");
			mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, mensagem);
		}
	}
	
	public void limpar(){
		livro = new Livro();
		livros.clear();
	}
	
	public void excluir(Livro livro) throws NegocioException{
		if(livro.getIdLivro() != null){
			livroDao.remover(livro);
		}
	}
	
	@Transactional
	public void excluir(){
		EntityTransaction trx = livroDao.getManager().getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			trx.begin();
			excluir(livroSelecionado);
			context.addMessage(null, new FacesMessage("Livro excluído com sucesso!"));
			trx.commit();
		}catch (NegocioException e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, mensagem);
		} finally {
			livroDao.getManager().close();
			clean();
			init();
		}
	}
	
	public void clean(){
		livro = new Livro();
		livroSelecionado = new Livro();
		livroDao = new LivroDao();
		livros.clear();
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	
	public List<Livro> getLivros() {
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}

	public Livro getLivroSelecionado() {
		return livroSelecionado;
	}

	public void setLivroSelecionado(Livro livroSelecionado) {
		this.livroSelecionado = livroSelecionado;
	}

	

}
