package org.paramvalidate.validator;

import org.paramvalidate.base.validator.AbstractParamValidator;
import org.paramvalidate.util.StringUtil;

/**
 * match the regex pattern
 * @author lulonglong
 *
 */
public class RegexValidator extends AbstractParamValidator {

	private String pattern=null;
	
	@Override
	protected boolean isError(String content) throws Exception {
		return StringUtil.isNotNull(content)&&!content.matches(pattern);
	}
	
	/**set regex
	 * @param pattern the pattern to set
	 */
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}
