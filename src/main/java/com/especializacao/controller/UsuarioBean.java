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

import com.especializacao.dao.UsuarioDao;
import com.especializacao.exception.NegocioException;
import com.especializacao.model.Usuario;
import com.especializacao.transaction.Transactional;

@Named
@ViewScoped
public class UsuarioBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject @New
	private Usuario usuario;
	@Inject
	private UsuarioDao usuarioDao;
	
	private Usuario usuarioSelecionado;
	private List<Usuario> usuarios = new ArrayList<Usuario>();
	
	@PostConstruct
	public void init(){
		usuarios = usuarioDao.todos();
	}
	
	@Transactional
	public String novo(){
		usuario = new Usuario();
		return "/cadastrarUsuario?faces-redirect=true";
	}
	
	public void salvar(Usuario usuario) throws NegocioException{
		usuarioDao.salvarUsuario(usuario);
	}
	
	public void editar(Usuario usuario) throws NegocioException{
		usuarioDao.editarUsuario(usuario);
	}
	
	@Transactional
	public String salvar() {
		EntityTransaction trx = usuarioDao.getManager().getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			trx.begin();
			if(usuario.getIdUsuario() == null){
				usuario.setDataLogin(new Date());
				salvar(usuario);
				context.addMessage(null, new FacesMessage("Usuário salvo com sucesso!"));
			}else{
				editar(usuario);
				context.addMessage(null, new FacesMessage("Usuário alterado com sucesso!"));
			}
			trx.commit();
		}catch (NegocioException e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, mensagem);
		}finally {
			usuarioDao.getManager().close();
			clean();
		}
		return "consultaUsuarios";
	}
	
	public void excluir(Usuario usuario) throws NegocioException{
		if(usuario.getIdUsuario() != null){
			usuarioDao.removerUsuario(usuario);
		}
	}
	
	@Transactional
	public void excluir(){
		EntityTransaction trx = usuarioDao.getManager().getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			trx.begin();
			excluir(usuarioSelecionado);
			context.addMessage(null, new FacesMessage("Usuário excluído com sucesso!"));
			trx.commit();
		}catch (NegocioException e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, mensagem);
		} finally {
			usuarioDao.getManager().close();
			clean();
			init();
		}
	}
	
	public void clean() {
		usuario = new Usuario();
		usuarioSelecionado = new Usuario();
		usuarioDao = new UsuarioDao();
		usuarios.clear();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}
	

}
