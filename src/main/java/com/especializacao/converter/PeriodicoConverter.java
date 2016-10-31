package com.especializacao.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.especializacao.dao.PeriodicoDao;
import com.especializacao.model.Periodico;
import com.especializacao.util.CDILocator;

@FacesConverter(forClass = Periodico.class)
public class PeriodicoConverter implements Converter {
	
	private PeriodicoDao periodicoDao;
	public PeriodicoConverter() {
		this.periodicoDao = CDILocator.getBean(PeriodicoDao.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context,	UIComponent component, String value) {
		Periodico retorno = null;
		if (value != null) {
			retorno = this.periodicoDao.findById(new Long(value));
		}
		return retorno;
	}
	
	@Override
	public String getAsString(FacesContext context,	UIComponent component, Object value) {
		if (value != null) {
			Periodico periodico = ((Periodico) value);
			return periodico.getIdPeriodico() == null ? null : periodico.getIdPeriodico().toString();
		}
		return null;
	}
	
}