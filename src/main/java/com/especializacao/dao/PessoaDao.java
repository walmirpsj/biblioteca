package com.especializacao.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.especializacao.model.Pessoa;
import com.especializacao.util.EntityManagerProducer;

public class PessoaDao implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EntityManager manager;
	private EntityManagerProducer entityManagerProducer = new EntityManagerProducer();
	
	public PessoaDao() {
		this.manager = entityManagerProducer.createEntityManager();
	}
	
	public Pessoa findById(Long id) {
		return manager.find(Pessoa.class, id);
	}
	
	public List<Pessoa> todos() {
		TypedQuery<Pessoa> query = manager.createQuery("from Pessoa", Pessoa.class);
		return query.getResultList();
	}
	
	public void salvar(Pessoa pessoa) {
		this.manager.persist(pessoa);
	}
	
	public void remover(Pessoa pessoa){
		this.manager.remove(pessoa);
	}
	
	public void editar(Pessoa pessoa){
		this.manager.merge(pessoa);
	}

	public EntityManager getManager() {
		return manager;
	}
	
	
	
}