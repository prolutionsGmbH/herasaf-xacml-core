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

package org.herasaf.xacml.core.dataTypeAttribute.impl;

import org.herasaf.xacml.core.SyntaxException;
import org.herasaf.xacml.core.types.RFC822Name;

/**
 * This data type represents a urn:oasis:names:tc:xacml:1.0:data-type:rfc822Name. See: <a href=
 * "http://www.oasis-open.org/committees/tc_home.php?wg_abbrev=xacml#XACML20"> OASIS eXtensible Access Control Markup
 * Langugage (XACML) 2.0, Errata, 29 January 2008</a> page 110, for further information.
 */
public class RFC822NameDataTypeAttribute extends AbstractDataTypeAttribute<RFC822Name> {
	public static final String ID = "urn:oasis:names:tc:xacml:1.0:data-type:rfc822Name";
	private static final long serialVersionUID = 1L;

	/** {@inheritDoc} */
	@Override
	public String getDatatypeURI() {
		return ID;
	}

	/** {@inheritDoc} */
	@Override
	public RFC822Name convertTo(String jaxbRepresentation) throws SyntaxException {
		try {
			RFC822Name rfc822Name = new RFC822Name(jaxbRepresentation);
			return rfc822Name;
		} catch (IllegalArgumentException e) {
			throw new SyntaxException(e);
		}
	}
}