package com.especializacao.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.especializacao.dao.EmprestimoDao;
import com.especializacao.model.Emprestimo;
import com.especializacao.util.CDILocator;

@FacesConverter(forClass = Emprestimo.class)
public class EmprestimoConverter implements Converter {
	
	private EmprestimoDao emprestimoDao;
	public EmprestimoConverter() {
		this.emprestimoDao = CDILocator.getBean(EmprestimoDao.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context,	UIComponent component, String value) {
		Emprestimo retorno = null;
		if (value != null) {
			retorno = this.emprestimoDao.findById(new Long(value));
		}
		return retorno;
	}
	
	@Override
	public String getAsString(FacesContext context,	UIComponent component, Object value) {
		if (value != null) {
			Emprestimo emprestimo = ((Emprestimo) value);
			return emprestimo.getIdEmprestimo() == null ? null : emprestimo.getIdEmprestimo().toString();
		}
		return null;
	}
	
}