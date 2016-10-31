package com.especializacao.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.especializacao.dao.LivroDao;
import com.especializacao.model.Livro;
import com.especializacao.util.CDILocator;

@FacesConverter(forClass = Livro.class)
public class LivroConverter implements Converter {
	
	private LivroDao livroDao;
	public LivroConverter() {
		this.livroDao = CDILocator.getBean(LivroDao.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context,	UIComponent component, String value) {
		Livro retorno = null;
		if (value != null) {
			retorno = this.livroDao.findById(new Long(value));
		}
		return retorno;
	}
	
	@Override
	public String getAsString(FacesContext context,	UIComponent component, Object value) {
		if (value != null) {
			Livro livro = ((Livro) value);
			return livro.getIdLivro() == null ? null : livro.getIdLivro().toString();
		}
		return null;
	}
	
}