
package org.paramvalidate.base.validator;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * AbstractParamValidator
 * 
 * @version 2013-6-14
 * @author lulonglong
 * @since JDK1.6
 * 
 */
public abstract class AbstractParamValidator {
	private Logger logger;
	
	protected AbstractParamValidator(){
		logger=Logger.getLogger(this.getClass());
	}
	
	protected Map<String, String> paramsMap = new HashMap<String, String>();

	/**
	 * add param and errorCode
	 * 
	 * @param paramName
	 * @param errorCode
	 */
	public void addParam(String paramName, String errorCode) {
		paramsMap.put(paramName, errorCode);
	}

	/**
	 * use this validator validate request params
	 * 
	 * @param req
	 * @return errorCodes
	 * @throws Exception
	 */
	public List<String> validate(HttpServletRequest req) throws Exception {
		List<String> errorCodes = new ArrayList<String>();

		for (String paramName : paramsMap.keySet()) {
			if (isError(req.getParameter(paramName))) {
				logger.info(MessageFormat.format("Param is illegal-- key:{0} value:{1} errorCode:{2} validator:{3}",paramName,req.getParameter(paramName),paramsMap.get(paramName),this.getClass()));
				errorCodes.add(paramsMap.get(paramName));
			}
		}

		return errorCodes;
	}

	/**
	 * is this param error
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	protected abstract boolean isError(String content) throws Exception;
}
