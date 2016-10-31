package com.especializacao.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.especializacao.dao.UsuarioDao;
import com.especializacao.model.Usuario;
import com.especializacao.util.CDILocator;


@FacesConverter(forClass = Usuario.class)
public class UsuarioConverter implements Converter{
	
	private UsuarioDao usuarioDao;
	public UsuarioConverter() {
		usuarioDao = CDILocator.getBean(UsuarioDao.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Usuario retorno = null;
		if (value != null) {
			retorno = usuarioDao.porId(new Long(value));
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Usuario usuario = ((Usuario) value);
			return usuario.getIdUsuario() == null ? null : usuario.getIdUsuario().toString();
		}
		return null;
	}

}
