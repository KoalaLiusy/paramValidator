package org.paramvalidate.validator;

import org.paramvalidate.base.validator.AbstractParamValidator;
import org.paramvalidate.util.StringUtil;

/**
 * 验证是否为null或空
 * 
 * @version 2013-6-14
 * @author lulonglong
 * @since JDK1.6
 * 
 */
public class NullValidator extends AbstractParamValidator {


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * locationshare.validator.AbstractParamValidator#isError(java.lang.String)
	 */
	@Override
	protected boolean isError(String content) throws Exception {
		return StringUtil.isNullOrWhiteSpace(content);
	}

}
