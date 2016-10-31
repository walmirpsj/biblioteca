package com.especializacao.converter;

import java.text.SimpleDateFormat;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converterData")
public class ConverterData implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		try {
			if((!arg2.equals("")) && (arg2!=null)){
				if(arg2.length()!=10){
					// se o usuario digitou apenas os caracteres sem as barras, o sistema coloca as barras
					if(arg2.length()==8){
						arg2 = arg2.substring(0, 2)+"/"+arg2.substring(2, 4)+"/"+arg2.substring(4, 8);
						return new SimpleDateFormat("dd/MM/yyyy").parse(arg2);
					}else{
						return arg2;
					}
				}else{
					//Se o formato estiver invalido ele passara adiante para a validacao disparar o erro
					if(!arg2.substring(2, 3).equals("/")){
						return arg2;
					}else if(!arg2.substring(5, 6).equals("/")){
						return arg2;
					}else{
						return new SimpleDateFormat("dd/MM/yyyy").parse(arg2);
					}
				}
			}
		} catch (ConverterException e) {
			e.getMessage();
		}catch (Exception e) {
			e.getMessage();
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		try {
			if((!arg2.toString().equals("")) && (arg2!=null)){
				return new SimpleDateFormat("dd/MM/yyyy").format(arg2);
			}
		} catch (ConverterException e) {
			e.getMessage();
		}catch (Exception e) {
			e.getMessage();
		}
		return null;
	}

}

