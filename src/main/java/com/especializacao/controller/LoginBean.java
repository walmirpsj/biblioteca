package com.especializacao.controller;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.especializacao.dao.UsuarioDao;
import com.especializacao.model.Usuario;
import com.especializacao.model.UsuarioSession;

@Named
@RequestScoped
public class LoginBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Usuario usuario;
	@Inject
	private UsuarioSession usuarioSession;
	
	@Inject 
	private UsuarioDao usuarioDao;
	
	public String login() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			if (usuarioDao.isLogado(usuario.getUsername(), usuario.getSenha())) {
				usuario = usuarioDao.findByUsernameAndSenha(usuario.getUsername(), usuario.getSenha());
				usuarioSession.setNome(usuario.getNome());
				usuarioSession.setUsername(usuario.getUsername());
				usuarioSession.setSenha(usuario.getSenha());
				usuarioSession.setDataAcesso(new Date());
				return "/consultaEmprestimos?faces-redirect=true";
			} else {
				FacesMessage mensagem = new FacesMessage("Usuário/senha inválidos!");
				mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
				context.addMessage(null, mensagem);
			}
		} catch (Exception e) {
			FacesMessage m = new FacesMessage("Erro de login!");
			m.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(e.getMessage(), m);
		}
		return "";
	}
	
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/login?faces-redirect=true";
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public UsuarioSession getUsuarioSession() {
		return usuarioSession;
	}

	public void setUsuarioSession(UsuarioSession usuarioSession) {
		this.usuarioSession = usuarioSession;
	}
	
	

}
