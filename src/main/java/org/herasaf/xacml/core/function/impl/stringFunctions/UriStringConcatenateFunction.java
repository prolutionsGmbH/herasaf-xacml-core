/*
 * Copyright 2008 - 2013 HERAS-AF (www.herasaf.org)
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

package org.herasaf.xacml.core.function.impl.stringFunctions;

import java.net.URI;

import org.herasaf.xacml.core.function.AbstractFunction;
import org.herasaf.xacml.core.function.FunctionProcessingException;

/**
 * <p>
 * The implementation of the
 * urn:oasis:names:tc:xacml:2.0:function:uri-string-concatenate function.
 * </p>
 * <p>
 * See: Apendix A.3 of the <a href=
 * "http://www.oasis-open.org/committees/tc_home.php?wg_abbrev=xacml#XACML20">
 * OASIS eXtensible Access Control Markup Langugage (XACML) 2.0, Errata, 29 January 2008</a> page 121, for further information.
 * </p>
 * 
 * @author Sacha Dolski
 */
public class UriStringConcatenateFunction extends AbstractFunction {

	/** XACML function ID. */
	public static final String ID = "urn:oasis:names:tc:xacml:2.0:function:uri-string-concatenate";

	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc} <br>
	 * <br>
	 * The first argument must be of type URI and the others of type string. The
	 * return type is the concatenation of the arguments in the order they
	 * appear.
	 */
	@Override
	public Object handle(Object... args) throws FunctionProcessingException {
		try {
			if (args.length < 2) {
				throw new FunctionProcessingException(
						"Invalid number of parameters");
			}
			String returnString = ((URI) args[0]).toString();
			for (int i = 1; i < args.length; i++) {
				String val = (String) args[i];
				returnString = returnString.concat(val);
			}
			return new URI(returnString);
		} catch (ClassCastException e) {
			throw new FunctionProcessingException(
					"The arguments were of the wrong datatype.", e);
		} catch (FunctionProcessingException e) {
			throw e;
		} catch (Exception e) {
			throw new FunctionProcessingException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getFunctionId() {
		return ID;
	}
}