package com.especializacao.util;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.especializacao.dao.UsuarioDao;
import com.especializacao.model.UsuarioSession;

@WebFilter("*.xhtml")
public class AutorizacaoFilter implements Filter {
	

	@Inject
	private UsuarioSession usuarioSession;
	
	@Inject 
	private UsuarioDao usuarioDao;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		if (!usuarioDao.isLogado(usuarioSession.getUsername(), usuarioSession.getSenha())	&& !request.getRequestURI().endsWith("/login.xhtml") && !request.getRequestURI().contains("/javax.faces.resource/")) {
			response.sendRedirect(request.getContextPath() + "/login.xhtml");
		} else {
			chain.doFilter(req, res);
		}
	}
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		
	}
	
	@Override
	public void destroy() {
		
	}
}
