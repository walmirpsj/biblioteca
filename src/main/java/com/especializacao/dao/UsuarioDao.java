package com.especializacao.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.especializacao.model.Usuario;
import com.especializacao.util.EntityManagerProducer;

public class UsuarioDao {
	
	private EntityManager manager;
	private EntityManagerProducer entityManagerProducer = new EntityManagerProducer();
	
	public UsuarioDao(){
		this.manager = entityManagerProducer.createEntityManager();
	}
	
	public List<Usuario> todos() {
		TypedQuery<Usuario> query = manager.createQuery("from Usuario", Usuario.class);
		return query.getResultList();
	}
	
	public Usuario porId(Long idUsuario){
		return manager.find(Usuario.class, idUsuario);
	}
	
	public void salvarUsuario(Usuario usuario){
		manager.persist(usuario);
	}
	
	public void editarUsuario(Usuario usuario){
		manager.merge(usuario);
	}
	
	public void removerUsuario(Usuario usuario){
		manager.remove(usuario);
	}
	
	public Boolean isLogado(String usuario, String senha){
		Boolean retorno = false;
		Query query = manager.createQuery("select username, senha from Usuario where username = :usuario and senha = :senha");
		query.setParameter("usuario", usuario);
		query.setParameter("senha", senha);
		if(!query.getResultList().isEmpty()){
			retorno = true;
		}
		return retorno;
	}
	
	public Usuario findByUsernameAndSenha(String username, String senha){
		Query query = manager.createQuery("select u from Usuario u where u.username = :usuario and u.senha = :senha");
		query.setParameter("usuario", username);
		query.setParameter("senha", senha);
		try {
			return (Usuario) query.getSingleResult();
		} catch (NoResultException e) {
			return new Usuario();
		}
	}

	public EntityManager getManager() {
		return manager;
	}
	
	
	
}
