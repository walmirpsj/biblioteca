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

import com.especializacao.dao.PeriodicoDao;
import com.especializacao.exception.NegocioException;
import com.especializacao.model.FrequenciaPeriodico;
import com.especializacao.model.Periodico;
import com.especializacao.model.TipoPeriodico;
import com.especializacao.transaction.Transactional;

@Named
@ViewScoped
public class PeriodicoBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject @New
	private Periodico periodico;
	@Inject
	private PeriodicoDao periodicoDao;
	
	private Periodico periodicoSelecionado;
	private List<Periodico> periodicos = new ArrayList<Periodico>();
	
	@PostConstruct
	public void init(){
		periodicos = periodicoDao.todos();
	}
	
	@Transactional
	public String novo(){
		periodico = new Periodico();
		return "/cadastrarPeriodico?faces-redirect=true";
	}
	
	public void salvar(Periodico periodico) throws NegocioException{
		periodicoDao.salvar(periodico);
	}
	
	public void editar(Periodico periodico) throws NegocioException{
		periodicoDao.editar(periodico);
	}
	
	@Transactional
	public String salvar() {
		EntityTransaction trx = periodicoDao.getManager().getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			trx.begin();
			if(periodico.getIdPeriodico() == null){
				salvar(periodico);
				context.addMessage(null, new FacesMessage("Periódico salvo com sucesso!"));
			}else{
				editar(periodico);
				context.addMessage(null, new FacesMessage("Periódico Alterado com sucesso!"));
			}
			trx.commit();
		}catch (NegocioException e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, mensagem);
		}finally {
			periodicoDao.getManager().close();
			clean();
		}
		return "consultaPeriodicos";
	}
	
	public void excluir(Periodico periodico) throws NegocioException{
		if(periodico.getIdPeriodico() != null){
			periodicoDao.remover(periodico);
		}
	}
	
	@Transactional
	public void excluir(){
		EntityTransaction trx = periodicoDao.getManager().getTransaction();
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			trx.begin();
			excluir(periodicoSelecionado);
			context.addMessage(null, new FacesMessage("Usuário excluído com sucesso!"));
			trx.commit();
		}catch (NegocioException e) {
			FacesMessage mensagem = new FacesMessage(e.getMessage());
			mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, mensagem);
		} finally {
			periodicoDao.getManager().close();
			clean();
			init();
		}
	}
	
	public void clean(){
		periodico = new Periodico();
		periodicoSelecionado = new Periodico();
		periodicoDao = new PeriodicoDao();
		periodicos.clear();
	}
	
	
	public TipoPeriodico[] getTiposPeriodicos() {
		return TipoPeriodico.values();
	}

	public FrequenciaPeriodico[] getFrequenciasPeriodicos() {
		return FrequenciaPeriodico.values();
	}
	
	public Periodico getPeriodico() {
		return periodico;
	}

	public void setPeriodico(Periodico periodico) {
		this.periodico = periodico;
	}

	public List<Periodico> getPeriodicos() {
		return periodicos;
	}

	public void setPeriodicos(List<Periodico> periodicos) {
		this.periodicos = periodicos;
	}

	public Periodico getPeriodicoSelecionado() {
		return periodicoSelecionado;
	}

	public void setPeriodicoSelecionado(Periodico periodicoSelecionado) {
		this.periodicoSelecionado = periodicoSelecionado;
	}

	
}
