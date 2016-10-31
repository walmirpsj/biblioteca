package com.especializacao.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.especializacao.model.Livro;
import com.especializacao.util.EntityManagerProducer;

public class LivroDao {
	
	private EntityManager manager;
	private EntityManagerProducer entityManagerProducer = new EntityManagerProducer();
	
	public LivroDao(){
		this.manager = entityManagerProducer.createEntityManager();
	}
	
	public Livro findById(Long idLivro){
		return manager.find(Livro.class, idLivro);
	}
	
	public void salvar(Livro livro){
		manager.persist(livro);
	}
	
	public void editar(Livro livro){
		manager.merge(livro);
	}
	
	public void remover(Livro livro){
		manager.remove(livro);
	}
	
	public List<Livro> todos() {
		TypedQuery<Livro> query = manager.createQuery("from Livro", Livro.class);
		return query.getResultList();
	}

	public EntityManager getManager() {
		return manager;
	}

	@SuppressWarnings("unchecked")
	public List<Livro> listarLivrosPorParametros(String titulo, String autor, String isbn, String assunto, String editora, Integer anoPublicacao) {
		String sql = "Select l from Livro l where l.idLivro <> null";
		if(titulo != ""){
			sql += " and upper(l.titulo) like upper(:titulo)";
		}
		if(autor != ""){
			sql += " and upper(l.autor) like upper(:autor)";
		}
		if(isbn != ""){
			sql += " and l.isbn = :isbn";
		}
		if(assunto != ""){
			sql += " and upper(l.assunto) like upper(:assunto)";
		}
		if(editora != ""){
			sql += " and upper(l.editora) like upper(:editora)";
		}
		if(anoPublicacao != 0){
			sql += " and l.anoPublicacao = :anoPublicacao";
		}
		sql += " order by l.titulo";
		Query q = manager.createQuery(sql);
		if(titulo != ""){
			q.setParameter("titulo", "%" +titulo+ "%");
		}
		if(autor != ""){
			q.setParameter("autor", "%" +autor+ "%");
		}
		if(isbn != ""){
			q.setParameter("isbn", isbn);
		}
		if(assunto != ""){
			q.setParameter("assunto", "%" +assunto+ "%");
		}
		if(editora != ""){
			q.setParameter("editora", "%" +editora+ "%");
		}
		if(anoPublicacao != 0){
			q.setParameter("anoPublicacao", anoPublicacao);
		}
		return q.getResultList();
	}
	
	
	
}
