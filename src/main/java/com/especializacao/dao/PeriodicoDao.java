package com.especializacao.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.especializacao.model.Periodico;
import com.especializacao.util.EntityManagerProducer;

public class PeriodicoDao {
	
	private EntityManager manager;
	private EntityManagerProducer entityManagerProducer = new EntityManagerProducer();
	
	public PeriodicoDao(){
		this.manager = entityManagerProducer.createEntityManager();
	}
	
	public Periodico findById(Long idPeriodico){
		return manager.find(Periodico.class, idPeriodico);
	}
	
	public void salvar(Periodico periodico){
		manager.persist(periodico);
	}
	
	public void editar(Periodico periodico){
		manager.merge(periodico);
	}
	
	public void remover(Periodico periodico){
		manager.remove(periodico);
	}
	
	public List<Periodico> todos() {
		TypedQuery<Periodico> query = manager.createQuery("from Periodico", Periodico.class);
		return query.getResultList();
	}

	public EntityManager getManager() {
		return manager;
	}

	
	
}
