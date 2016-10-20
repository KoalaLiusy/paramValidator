package org.paramvalidate.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.paramvalidate.base.validator.AbstractParamValidator;
import org.paramvalidate.util.StringUtil;
import org.paramvalidate.util.ValidateUtil;
import org.paramvalidate.vo.AbstractErrorResultVO;

/**
 * Servlet Filter implementation class ValidateFilter
 */
public class ValidateFilter implements Filter {

	private Logger logger=Logger.getLogger(this.getClass());
	/**
	 * Default constructor.
	 */
	public ValidateFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		logger.info( "ValidateFilter Start....." );
		
		// get request
		HttpServletRequest req = (HttpServletRequest) request;

		// get servlet validators
		String servletUrlString = req.getServletPath();
		List<AbstractParamValidator> validators = ValidateUtil.servletValidators
				.get(servletUrlString);

		if (validators != null) {
			List<String> errorCodes = new ArrayList<String>();
			// validate every
			for (AbstractParamValidator validator : validators) {
				try {
					errorCodes.addAll(validator.validate(req));
				} catch (Exception e) {
					logger.error( StringUtil.getExceptionStack( e ) );
				}
			}

			if (!errorCodes.isEmpty()) {
				AbstractErrorResultVO vo = null;
				try {
					vo = (AbstractErrorResultVO) Class.forName(
							ValidateUtil.errorResultClass).newInstance();
					vo.setErrorCodes(errorCodes);

				} catch (InstantiationException e) {
					logger.error( StringUtil.getExceptionStack( e ) );
				} catch (IllegalAccessException e) {
					logger.error( StringUtil.getExceptionStack( e ) );
				} catch (ClassNotFoundException e) {
					logger.error( StringUtil.getExceptionStack( e ) );
				}
				
				if (ValidateUtil.returnType == "json") {
					logger.info( vo.toJsonString() );
					response.getWriter().println(vo.toJsonString());
				} else {
					logger.info( vo.toXmlString() );
					response.getWriter().println(vo.toXmlString());
				}
				return;
			}
		}
		chain.doFilter(request, response);
		logger.info( "ValidateFilter End....." );
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
