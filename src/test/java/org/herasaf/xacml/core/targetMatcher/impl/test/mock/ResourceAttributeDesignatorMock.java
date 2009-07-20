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

package org.herasaf.xacml.core.targetMatcher.impl.test.mock;

import java.util.Arrays;

import org.herasaf.xacml.core.context.RequestInformation;
import org.herasaf.xacml.core.context.impl.RequestType;
import org.herasaf.xacml.core.dataTypeAttribute.DataTypeAttribute;
import org.herasaf.xacml.core.dataTypeAttribute.impl.StringDataTypeAttribute;
import org.herasaf.xacml.core.policy.ExpressionProcessingException;
import org.herasaf.xacml.core.policy.impl.ResourceAttributeDesignatorType;

public class ResourceAttributeDesignatorMock extends ResourceAttributeDesignatorType {
	private static final long serialVersionUID = 888802881998266493L;
	private String[] reqValues;
	private boolean exception;

	public ResourceAttributeDesignatorMock(boolean exception) {
		this.reqValues = new String[0];
		this.exception = exception;
	}

	@Override
	public Object handle(RequestType request, RequestInformation reqInfo)
			throws ExpressionProcessingException {
		if(exception){
			throw new ExpressionProcessingException();
		}
		return Arrays.asList(reqValues);
	}

	@Override
	public DataTypeAttribute<?> getDataType() {
		return new StringDataTypeAttribute();
	}

	public void extendValues(String[] values){
		String[] newValues = new String[values.length + reqValues.length];
		for(int i = 0; i < newValues.length; i++){
			if(i < reqValues.length){
				newValues[i] = reqValues[i];
			}
			else {
				newValues[i] = values[i - reqValues.length];
			}
		}
		reqValues = newValues;
	}
}