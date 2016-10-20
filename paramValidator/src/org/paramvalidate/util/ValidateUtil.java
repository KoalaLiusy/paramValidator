package org.paramvalidate.util;

import org.paramvalidate.base.validator.*;

import java.util.*;

public class ValidateUtil {
	public static final Map<String, List<AbstractParamValidator>> servletValidators = new HashMap<String, List<AbstractParamValidator>>();
	public static String returnType = "xml";
	public static String errorResultClass=null;
}
