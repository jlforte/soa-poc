package com.gnw.credit_omni.soap.actions;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.jboss.soa.esb.actions.AbstractActionLifecycle;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import com.gnw.credit_omni.soap.utils.SOAPUtils;


public class WebServicesAction extends AbstractActionLifecycle
{

	private static final String TEST_PUBLIC_WEBSITE_URL = "http://www.equifax.com/home/en_us";
	protected ConfigTree config;
	private static final String SCORE_TYPE_ATTR_NAME = "scoreType";
	
	private static Logger log = Logger.getLogger(WebServicesAction.class);

	//Constructor
	public WebServicesAction(ConfigTree config){
		this.config = config;
	}

	//The Process Method
	public Message process(Message message) throws ActionProcessingException,FileNotFoundException,IOException 
	{
		log.info("WebServicesAction.process:  entry: scoreType=" + SOAPUtils.getValueOfAttributeInMessage(message, "scoreType") +
				 " messageId=" + SOAPUtils.getValueOfAttributeInMessage(message, "messageID") +
				 " processedFlag=" + SOAPUtils.getValueOfAttributeInMessage(message, "processedFlag"));
		
		
		log.debug("WebServicesAction.process: entry: message: " + SOAPUtils.messageToString(message) + 
				  " header: "  + message.getHeader() + 
				  " context: " + message.getContext());
		
		String response = getWebsiteURL(TEST_PUBLIC_WEBSITE_URL);
		log.info("WebServicesAction.process: response from website  " + WebServicesAction.TEST_PUBLIC_WEBSITE_URL + 
				  ": " + response);
		
		log.info("WebServicesAction.process: return: scoreType=" + SOAPUtils.getValueOfAttributeInMessage(message, "scoreType") +
				 " messageId="     + SOAPUtils.getValueOfAttributeInMessage(message, "messageID") +
				 " processedFlag=" + SOAPUtils.getValueOfAttributeInMessage(message, "processedFlag"));
		
		return message;
	}
	
	/**
	 * @param url
	 * @return
	 */
	public String getWebsiteURL(String url) {
		String response = "<no response>";
		
	    // Create an instance of HttpClient.
	    HttpClient client = new HttpClient();
	    
		HttpMethod method = new GetMethod(WebServicesAction.TEST_PUBLIC_WEBSITE_URL);
		   try {
			   
			      // Execute the method.
			      int statusCode = client.executeMethod(method);

			      if (statusCode != HttpStatus.SC_OK) {
			    	  response = "WebServicesAction.getWebsiteURL: method failed: " + method.getStatusLine();
			      }

			      // Read the response body.
			      byte[] responseBody = method.getResponseBody();

			      // Deal with the response.
			      // Use caution: ensure correct character encoding and is not binary data
			      response = new String(responseBody);

			    } catch (HttpException e) {
			      response = "WebServicesAction.getWebsiteURL: fatal protocol violation: " + e.getMessage();
			    } catch (IOException e) {
			    	response = "WebServicesAction.getWebsiteURL: fatal transport error: " + e.getMessage();
			    } finally {
			      // Release the connection.
			      method.releaseConnection();
			    }  

		return response;
	}


	/**
	 * default success handler
	 * @param message
	 */
	public void processSuccess(Message message) {
		log.info("WebServicesAction.processSuccess: scoreType=" + SOAPUtils.getValueOfAttributeInMessage(message, SOAPUtils.SCORE_TYPE_ATTR_NAME) +
				 " messageId=" + SOAPUtils.getValueOfAttributeInMessage(message, "messageID") +
				 " processedFlag=" + SOAPUtils.getValueOfAttributeInMessage(message, "processedFlag")); 
	}
	
	/**
	 * @param message
	 * @param th
	 */
	public void processException(Message message, java.lang.Throwable th) {
		log.info("WebServicesAction.processException: scoreType=" + SOAPUtils.getValueOfAttributeInMessage(message, SOAPUtils.SCORE_TYPE_ATTR_NAME) +
				 " messageId=" + SOAPUtils.getValueOfAttributeInMessage(message, "messageID") +
				 " processedFlag=" + SOAPUtils.getValueOfAttributeInMessage(message, "processedFlag") +
				 " exception: " + th + 
				 " exception message: " + th.getMessage()); 
	}

}