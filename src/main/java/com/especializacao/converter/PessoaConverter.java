package com.especializacao.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.especializacao.dao.PessoaDao;
import com.especializacao.model.Pessoa;
import com.especializacao.util.CDILocator;


@FacesConverter(forClass = Pessoa.class)
public class PessoaConverter implements Converter {
	
	private PessoaDao pessoaDao;
	public PessoaConverter() {
		pessoaDao = CDILocator.getBean(PessoaDao.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context,	UIComponent component, String value) {
		Pessoa retorno = null;
		if (value != null) {
			retorno = this.pessoaDao.findById(new Long(value));
		}
		return retorno;
	}
	
	@Override
	public String getAsString(FacesContext context,	UIComponent component, Object value) {
		if (value != null) {
			Pessoa pessoa = ((Pessoa) value);
			return pessoa.getIdPessoa() == null ? null : pessoa.getIdPessoa().toString();
		}
		return null;
	}
	
}