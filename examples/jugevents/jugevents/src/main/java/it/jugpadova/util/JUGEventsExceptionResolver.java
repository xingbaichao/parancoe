/**
 * 
 */
package it.jugpadova.util;

import it.jugpadova.exception.ParancoeAccessDeniedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.parancoe.web.ExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * Ovverride ExceptionResolver to define custom web application Exception.
 * Should be mapped in parancoe-servlet.xml
 * @author Enrico Giurin
 *
 */
public class JUGEventsExceptionResolver extends ExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse res, Object object, Exception e) {
		if(e instanceof ParancoeAccessDeniedException)
			throw (ParancoeAccessDeniedException)e;
		return super.resolveException(req, res, object, e);
	}

}
