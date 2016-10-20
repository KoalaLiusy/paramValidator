package org.paramvalidate.vo;

import java.util.List;

/**
 * Descriptions
 * 
 * @version 2013-6-20
 * @author lulnglong
 * @since JDK1.6
 * 
 */
public class ErrorResultVO extends AbstractErrorResultVO {

	@Override
	/**
	 * set errorCodes
	 * 
	 * @param errorCodes
	 */
	public void setErrorCodes(List<String> errorCodes) {
		if (errorCodes == null || errorCodes.isEmpty()) {
			return;
		}
		errorCodeList = errorCodes;
	}

	@Override
	/**
	 * get xml error result
	 */
	public String toXmlString() {

		StringBuilder midString = new StringBuilder();

		for (String errorCode : errorCodeList) {
			midString.append("<error code=\"" + errorCode + "\"/>");
		}

		return toXmlStringPrefix() + midString + toXmlStringSuffix();
	}

	public String toXmlStringPrefix() {
		return "<result code=\"1\">";
	}

	public String toXmlStringSuffix() {
		return "</result>";
	}
	
	@Override
	/**
	 * get json result
	 * 
	 * @return
	 */
	public  String toJsonString() {

		if (errorCodeList == null || errorCodeList.isEmpty())
			return null;

		StringBuilder midString = new StringBuilder();

		for (String errorCode : errorCodeList) {
			midString.append(",").append("\"" + errorCode + "\"");
		}

		midString.deleteCharAt(0);

		return "{\"code\":\"1\",\"errorCode\":[" + midString + "]}";
	}
}
