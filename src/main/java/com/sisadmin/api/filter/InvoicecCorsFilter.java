package com.sisadmin.api.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class InvoicecCorsFilter implements Filter {

	
	//@Autowired
	//protected GlobalConfig globalEmitter;
	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		//if (globalEmitter.getCors().equals("1")) { 
			
			HttpServletResponse response = (HttpServletResponse) res;
			//response.setHeader("Access-Control-Allow-Origin", "http://100.99.163.239");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
			response.setHeader("Access-Control-Max-Age", "3600");
			response.setHeader("Access-Control-Allow-Headers", "x-requested-with, origin, content-type, accept, authorization");
			response.setHeader("Tomcat-Mario-Header", "jojolete bla bla");
		
		//}
			chain.doFilter(req, res);
	}

	public void init(FilterConfig filterConfig) {}

	public void destroy() {}

}
