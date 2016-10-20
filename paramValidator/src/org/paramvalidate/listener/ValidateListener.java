package org.paramvalidate.listener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.paramvalidate.base.validator.AbstractParamValidator;
import org.paramvalidate.util.StringUtil;
import org.paramvalidate.util.ValidateUtil;

/**
 * Application Lifecycle Listener implementation class ValidateListener
 * 
 */
public class ValidateListener implements ServletContextListener {

	//need configure property before use logger
	private Logger logger = Logger.getLogger( this.getClass() );

	/**
	 * Default constructor.
	 */
	public ValidateListener() {
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	@SuppressWarnings("unchecked")
	public void contextInitialized( ServletContextEvent servletContextEvent ) {
		logger.info( "ValidateListener Start..." );

		// return format json or xml, default is xml
		String returnType = servletContextEvent.getServletContext().getInitParameter( "returnType" );
		ValidateUtil.returnType = StringUtil.isNullOrWhiteSpace( returnType ) ? ValidateUtil.returnType : returnType;

		// get validateParam.xml path
		String configLocation = servletContextEvent.getServletContext().getInitParameter( "validateConfigLocation" );
		if ( StringUtil.isNullOrWhiteSpace( configLocation ) ) {
			logger.error( "validateConfigLocation config is not found" );
			return;
		}

		// add namespace
		SAXReader reader = new SAXReader();
		Map<String, String> map = new HashMap<String, String>();
		map.put( "xmlns", "http://www.example.net/test" );
		reader.getDocumentFactory().setXPathNamespaceURIs( map );

		try {
			String configPath = servletContextEvent.getServletContext().getRealPath( configLocation );
			Document document = reader.read( new File( configPath ) );

			// get errorResultClass
			Node node = document.selectSingleNode( "/xmlns:validators/xmlns:errorResultClass" );
			ValidateUtil.errorResultClass = node == null ? "org.paramvalidate.vo.ErrorResultVO" : node.getText();

			// get all servlets
			List<Element> servletValidators = document.selectNodes( "/xmlns:validators/xmlns:servletValidator" );

			// read all servletValidators
			for ( Element servletValidator : servletValidators ) {

				String servletUrl = servletValidator.valueOf( "@servletUrl" );
				List<AbstractParamValidator> paramValidators = new ArrayList<AbstractParamValidator>();

				List<Element> validators = servletValidator.selectNodes( "xmlns:validator" );

				// read all validator in servletValidator
				for ( Element validator : validators ) {

					String validatorClassName = validator.valueOf( "@validatorClass" );
					Class<?> validatorClass = Class.forName( validatorClassName );
					Object validatorInstance = validatorClass.newInstance();

					// read attributes in this validator
					List<Attribute> attributes = validator.attributes();
					for ( Attribute attribute : attributes ) {

						String attributeName = attribute.getName();
						if ( attributeName.equals( "validatorClass" ) )
							continue;

						String attributeValue = attribute.getValue();

						Method method = validatorClass.getMethod( StringUtil.getSetMethodString( attributeName ),
								String.class );

						method.invoke( validatorInstance, attributeValue );

					}

					// read params in this validator
					List<Element> params = validator.selectNodes( "xmlns:validateParam" );
					for ( Element element : params ) {

						String nameString = element.valueOf( "@name" );
						String errorCode = element.valueOf( "@errorCode" );

						Method addParamMethod = validatorClass.getMethod( "addParam", String.class, String.class );

						addParamMethod.invoke( validatorInstance, nameString, errorCode );
					}

					// all paramValidators in the servlet
					paramValidators.add( (AbstractParamValidator) validatorInstance );

				}

				// all servlet validators
				ValidateUtil.servletValidators.put( servletUrl, paramValidators );

			}
			// error in jetty
			// FilterRegistration filterRegistration = servletContextEvent
			// .getServletContext().addFilter("ValidateFilter",
			// "org.paramvalidate.filter.ValidateFilter");
			// filterRegistration.addMappingForUrlPatterns(null, true, "/*");

		}
		catch ( DocumentException e ) {
			logger.error( StringUtil.getExceptionStack( e ) );
		}
		catch ( InstantiationException e ) {
			logger.error( StringUtil.getExceptionStack( e ) );
		}
		catch ( IllegalAccessException e ) {
			logger.error( StringUtil.getExceptionStack( e ) );
		}
		catch ( ClassNotFoundException e ) {
			logger.error( StringUtil.getExceptionStack( e ) );
		}
		catch ( SecurityException e ) {
			logger.error( StringUtil.getExceptionStack( e ) );
		}
		catch ( NoSuchMethodException e ) {
			logger.error( StringUtil.getExceptionStack( e ) );
		}
		catch ( IllegalArgumentException e ) {
			logger.error( StringUtil.getExceptionStack( e ) );
		}
		catch ( InvocationTargetException e ) {
			logger.error( StringUtil.getExceptionStack( e ) );
		}
		finally {
			logger.info( "ValidateListener End..." );
		}
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed( ServletContextEvent arg0 ) {
	}

}
