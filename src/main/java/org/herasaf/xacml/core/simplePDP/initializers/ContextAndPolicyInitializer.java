/*
 * Copyright 2009 HERAS-AF (www.herasaf.org)
 * Holistic Enterprise-Ready Application Security Architecture Framework
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.herasaf.xacml.core.simplePDP.initializers;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.herasaf.xacml.core.simplePDP.InitializationException;
import org.herasaf.xacml.core.utils.ContextAndPolicy;
import org.herasaf.xacml.core.utils.ContextAndPolicyConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO JAVADOC!!
 * 
 * @author Florian Huonder
 * @author Ren� Eggenschwiler
 * 
 */
public class ContextAndPolicyInitializer implements Initializer {
	private static final boolean FRAGMENT = true;
	private static final boolean VALIDATE = false;
	private static final boolean VALIDATE_WRITING = false;
	private static final boolean FORMATTED_OUTPUT = true;
	private static final String POLICY_PACKAGE = "org.herasaf.xacml.core.policy.impl";
	private static final String CONTEXT_PACKAGE = "org.herasaf.xacml.core.context.impl";
	private final Logger logger = LoggerFactory.getLogger(ContextAndPolicyInitializer.class);
	
	/**
	 * {@inheritDoc}
	 */
	public void run() {
		ContextAndPolicy
				.setRequestCtxProfile(createContextAndPolicyConfiguration(CONTEXT_PACKAGE));
		ContextAndPolicy
				.setResponseCtxProfile(createContextAndPolicyConfiguration(CONTEXT_PACKAGE));
		ContextAndPolicy
				.setPolicyProfile(createContextAndPolicyConfiguration(POLICY_PACKAGE));
		
		logger.info("JAXB settings for context (request, response) and policy are initialized.");
	}
	
	/**
	 * TODO JAVADOC
	 */
	private ContextAndPolicyConfiguration createContextAndPolicyConfiguration(String contextPath){
		ContextAndPolicyConfiguration config = new ContextAndPolicyConfiguration();
		
		try {
			config.setContext(JAXBContext.newInstance(contextPath));
		} catch (JAXBException e) {
			logger.error("Unable to initialize JAXB. Verify Context-Path settings.", e);
			throw new InitializationException(e);
		}
		config.setFormatted_output(FORMATTED_OUTPUT);
		config.setValidateWriting(VALIDATE_WRITING);
		config.setValidateParsing(VALIDATE);
		config.setFragment(FRAGMENT);
		
		return config;
	}
}