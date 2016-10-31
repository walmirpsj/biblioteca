package com.especializacao.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "periodico")
public class Periodico  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id_periodico", nullable = false)
	private Long idPeriodico;
	
	@Size(max = 120)
	@Column(length = 120)
	private String titulo;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_periodico")
	private TipoPeriodico tipoPeriodico;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "frequencia_periodico")
	private FrequenciaPeriodico frequenciaPeriodico;
	
	@Column(length = 100, nullable = true)
	private String assunto;
	
	@Column(length = 100, nullable = false)
	private String autor;
	
	@Column(length = 15, nullable = false)
	private String issn;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "data_publicacao", nullable = false)
	private Date dataPublicacao;
	
	
	public Periodico(){
		
	}

	public Long getIdPeriodico() {
		return idPeriodico;
	}

	public void setIdPeriodico(Long idPeriodico) {
		this.idPeriodico = idPeriodico;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public TipoPeriodico getTipoPeriodico() {
		return tipoPeriodico;
	}

	public void setTipoPeriodico(TipoPeriodico tipoPeriodico) {
		this.tipoPeriodico = tipoPeriodico;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}
	
	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public FrequenciaPeriodico getFrequenciaPeriodico() {
		return frequenciaPeriodico;
	}

	public void setFrequenciaPeriodico(FrequenciaPeriodico frequenciaPeriodico) {
		this.frequenciaPeriodico = frequenciaPeriodico;
	}
	
	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public Date getDataPublicacao() {
		return dataPublicacao;
	}

	public void setDataPublicacao(Date dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPeriodico == null) ? 0 : idPeriodico.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Periodico other = (Periodico) obj;
		if (idPeriodico == null) {
			if (other.idPeriodico != null)
				return false;
		} else if (!idPeriodico.equals(other.idPeriodico))
			return false;
		return true;
	}


}
