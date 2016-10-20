package org.paramvalidate.util;

/**
 * Descriptions
 * 
 * @version 2013-6-20
 * @author lulonglong
 * @since JDK1.6
 * 
 */
public class StringUtil {

	public static final String Empty = "";

	public static boolean isNotNull(String str) {

		boolean result = false;
		if (str != null && str.trim().length() > 0) {
			result = true;
		}

		return result;
	}

	/**
	 * 是否为列表字符串，例如121,343,asd
	 * 
	 * @param str
	 * @param split
	 * @return
	 */
	public static boolean isListString(String str, String split) {

		if (isNullOrWhiteSpace(str) || str.endsWith(split)
				|| str.startsWith(split))
			return false;

		for (String item : str.split(split)) {
			if (isNullOrWhiteSpace(item))
				return false;
		}

		return true;
	}

	/**
	 * 是否为空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNullOrWhiteSpace(String str) {
		return !isNotNull(str);
	}

	/**
	 * 获取私有字段的标准set方法
	 * 
	 * @param methodName
	 * @return
	 */
	public static String getSetMethodString(String methodName) {
		if (isNullOrWhiteSpace(methodName))
			return null;
		return "set" + methodName.substring(0, 1).toUpperCase()
				+ methodName.substring(1);
	}
	
	/**
	 * 获取堆栈信息
	 * 
	 * @param e
	 * @return
	 */
	public static String getExceptionStack( Exception e ) {
		StackTraceElement[] stackTraceElements = e.getStackTrace();
		StringBuilder result = new StringBuilder( e.toString().replace( '\r', ' ' ).replace( '\n', ' ' ) + "@@@" );

		for ( int index = 0; index < stackTraceElements.length; index++ ) {
			result.append( "at " ).append( stackTraceElements[index].getClassName() ).append( "." );
			result.append( stackTraceElements[index].getMethodName() + "(" );
			result.append( stackTraceElements[index].getFileName() + ":" );
			result.append( stackTraceElements[index].getLineNumber() + ")@@@" );
		}

		return result.toString();
	}
}
