package com.sisadmin.api.filter;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.minidev.json.JSONObject;

import org.joda.time.Instant;
import org.springframework.stereotype.Component;

import com.sisadmin.service.crypto.SisfeJowtService;


@Component
public class AuthFilter implements Filter {

	
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
				HttpServletRequest request = (HttpServletRequest) req;
				HttpServletResponse response = (HttpServletResponse) res;
				
				/**if(request.getRequestURI().endsWith("digest")){					
					request.setAttribute("junk", true);
				}**/
				
				boolean isLogin = request.getRequestURI().endsWith("login"); 
				boolean isApi = false;
				boolean isHtml = request.getRequestURI().endsWith("html");
				if(isLogin || isHtml) {
							System.out.println("pego a una pagina");
				}
				//isLogin = isLogin || isHtml;
				//si son llamadas las apis rest aplicar seguridad
				//TODO reemplazar por metodo de config.
				
				
				if (request.getRequestURI().contains("api/v1")   && !(request.getRequestURI().endsWith("/combo/documenttype/list") || request.getRequestURI().endsWith("/combo/emitter/list") || request.getRequestURI().endsWith("/combo/customer/list")) ) {
					isApi = true;
				}
				
				
				String uri = request.getRequestURI();
				if(uri.endsWith("html") &&  !(uri.endsWith("db.html") || uri.endsWith("intro.html")  || uri.endsWith("emisor_login.html")  || uri.endsWith("cliente_login.html") || uri.endsWith("index.html") || uri.endsWith("consulta_publica.html") )){
						String token = request.getHeader("Authorization");
						if (token == null) {
							response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED,"No Autorizado");
						} else {
							try {
								if (tokenInvalid(token)) {
									response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED,"No Autorizado");
								}
							} catch (Exception e) {
								e.printStackTrace();
								response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED,"No Autorizado");
							}
						}
				}
				/**
				if ( true && !request.getMethod().trim().equals("OPTIONS") && !isLogin && isApi) {
					String token = request.getHeader("Authorization");
					if (token == null) {
						response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"No Autorizado");
					} else {
						try {
							if (tokenInvalid(token)) {
								response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"No Autorizado");
							}
						} catch (Exception e) {
							e.printStackTrace();
							response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"No Autorizado");
						}
					}
				}**/
				
				
				
				
				chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	} 

	public boolean tokenInvalid(String token) {
		SisfeJowtService authHelper = new SisfeJowtService();
		try {
			JSONObject jo = authHelper.verify(token);
			if (jo == null ) return true;
			Calendar cal = Calendar.getInstance();
			String sExpiredAt = (String) jo.get("expiresAt");
			Instant today = new org.joda.time.Instant(cal.getTimeInMillis());
			Instant expiredAt = new org.joda.time.Instant();
			expiredAt = Instant.parse(sExpiredAt);
			if (today.isAfter(expiredAt)) return true;
		} catch (Exception e) {
			System.err.println("Error al verificar token");
			e.printStackTrace();
			return true;
		}
		return false;
	}
}
