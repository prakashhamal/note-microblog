package com.note.note.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCorsFilter implements Filter
{
	private static final Logger log = LoggerFactory.getLogger(SimpleCorsFilter.class);

	private final Set<String> allowedOrigins = new HashSet<>(Arrays.asList("http://localhost:4230","http://www.aageno.com"));


	public SimpleCorsFilter() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
	{
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		
        String origin = request.getHeader("Origin");
		log.trace("CORS Filter - HTTP Header origin -> ", origin);
		response.setHeader("Access-Control-Allow-Origin", allowedOrigins.contains(origin) ? origin : "*");
		log.trace("CORS Filter set Header  Access-Control-Allow-Origin to -> ", response.getHeader("Access-Control-Allow-Origin"));
		response.setHeader("Vary", "Origin");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PUT");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, X-Auth-Token , Authorization");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		

		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			log.trace("CORS Filter request Method OPTIONS found so set STATUS to OK.");
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			log.trace("CORS Filter request Method OPTIONS not found doFilter chain.");
			chain.doFilter(req, res);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void destroy() {
	}
}

