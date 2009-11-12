/*
 * Copyright 2008 HERAS-AF (www.herasaf.org)
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

package org.herasaf.xacml.core.function.impl.higherOrderBagFunctions;

import java.util.List;

import org.herasaf.xacml.core.function.Function;
import org.herasaf.xacml.core.function.FunctionProcessingException;

/**
 * The implementation of the urn:oasis:names:tc:xacml:1.0:function:all-of-all
 * function. See: Apendix A.3 of the <a href=
 * "http://www.oasis-open.org/committees/tc_home.php?wg_abbrev=xacml#XACML20">
 * OASIS eXtensible Access Control Markup Langugage (XACML) 2.0, Errata 29 June
 * 2006</a> page 122, for further information.
 * 
 * @author Sacha Dolski (sdolski@solnet.ch)
 * @version 1.0
 */
public class AllOfAllFunction implements Function {

	private static final long serialVersionUID = 7426295464640973108L;
	private static final String ID = "urn:oasis:names:tc:xacml:1.0:function:all-of-all";

	/**
	 * {@inheritDoc} Takes a {@link Function} returning a {@link Boolean} value
	 * as first argument and two {@link List}s as second and thirt arguments.
	 * Returnes a {@link Boolean} which says wheter the function returnes true
	 * if applied to all values of the first {@link List} with every value of
	 * the second {@link List}.
	 */
	public Object handle(Object... args) throws FunctionProcessingException {
		try {
			if (args.length != 3) {
				throw new FunctionProcessingException("Invalid number of parameters");
			}
			Function function = (Function) args[0];
			List<?> list1 = (List<?>) args[1];
			List<?> list2 = (List<?>) args[2];
			if (list1.size() != list2.size()) {
				return false;
			}
			for (Object obj1 : list1) {
				for (Object obj2 : list2) {
					if (!(Boolean) function.handle(obj1, obj2)) {
						return false;
					}
				}
			}

			return true;
		} catch (ClassCastException e) {
			throw new FunctionProcessingException(e);
		} catch (FunctionProcessingException e) {
			throw e;
		} catch (Exception e) {
			throw new FunctionProcessingException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ID;
	}

}
