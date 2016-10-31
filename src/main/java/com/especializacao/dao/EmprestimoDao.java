package com.especializacao.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.especializacao.model.Emprestimo;
import com.especializacao.util.EntityManagerProducer;

public class EmprestimoDao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private EntityManager manager;
	private EntityManagerProducer entityManagerProducer = new EntityManagerProducer();
	
	public EmprestimoDao() {
		this.manager = entityManagerProducer.createEntityManager();
	}
	
	public List<Emprestimo> todos() {
		TypedQuery<Emprestimo> query = manager.createQuery("from Emprestimo", Emprestimo.class);
		return query.getResultList();
	}
	
	public void salvar(Emprestimo emprestimo) {
		this.manager.persist(emprestimo);
	}
	
	public void guardar(Emprestimo emprestimo){
		this.manager.merge(emprestimo);
	}
	
	public void remover(Emprestimo emprestimo){
		manager.remove(emprestimo);
	}
	
	public void removerPorId(Long id){
		try {
			manager.remove(findById(id));
			manager.flush();	
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	public void editar(Emprestimo emprestimo){
		this.manager.merge(emprestimo);
	}
	
	public Emprestimo findById(Long id) {
		return manager.find(Emprestimo.class, id);
	}
	
	public EntityManager getManager() {
		return manager;
	}

}
