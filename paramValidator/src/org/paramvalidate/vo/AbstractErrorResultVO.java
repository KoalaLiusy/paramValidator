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
public abstract class AbstractErrorResultVO {
	protected List<String> errorCodeList = null;

	/**
	 * set errorCodes
	 * 
	 * @param errorCodes
	 */
	public abstract void setErrorCodes(List<String> errorCodes);

	public abstract String toXmlString();

	/**
	 * get json result
	 * 
	 * @return
	 */
	public abstract String toJsonString();
}
