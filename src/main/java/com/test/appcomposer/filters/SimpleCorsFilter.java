package com.test.appcomposer.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class SimpleCorsFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		if (true) {
//			log.info("ALLOWING");
			HttpServletResponse res = (HttpServletResponse) response;
			res.setHeader("Access-Control-Allow-Origin", "*");
			res.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
			res.setHeader("Access-Control-Max-Age", "3600");
//			res.setHeader("Access-Control-Allow-Headers", "x-requested-with");
			res.setHeader("Access-Control-Allow-Headers", "authorization, content-type, x-requested-with");
//			res.setHeader("Access-Control-Allow-Credentials", "true");
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}
}
