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

package org.herasaf.xacml.core.policy.combiningAlgorithm.mock;

import java.util.ArrayList;
import java.util.List;

import org.herasaf.xacml.core.combiningAlgorithm.policy.PolicyCombiningAlgorithm;
import org.herasaf.xacml.core.context.RequestInformation;
import org.herasaf.xacml.core.context.StatusCode;
import org.herasaf.xacml.core.context.impl.DecisionType;
import org.herasaf.xacml.core.context.impl.MissingAttributeDetailType;
import org.herasaf.xacml.core.context.impl.RequestType;
import org.herasaf.xacml.core.policy.Evaluatable;

public class PolicyCombiningAlgMock implements PolicyCombiningAlgorithm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 644980341269055301L;
	public DecisionType decision;
	public StatusCode statusCode;
	public MissingAttributeDetailType missingAttr;
	public boolean returnTargetFailure;

	public PolicyCombiningAlgMock(DecisionType decision,
			StatusCode statusCode, MissingAttributeDetailType missingAttr) {
		this.decision = decision;
		this.statusCode = statusCode;
		this.missingAttr = missingAttr;
	}

	public PolicyCombiningAlgMock(DecisionType decision,
			StatusCode statusCode, MissingAttributeDetailType missingAttr, boolean returnTargetFailure) {
		this(decision, statusCode, missingAttr);
		this.returnTargetFailure = returnTargetFailure;
	}

	public DecisionType evaluate(RequestType request,
			Evaluatable evals, RequestInformation requestInfo) {
		List<MissingAttributeDetailType> missingAttributes = new ArrayList<MissingAttributeDetailType>();
		if (missingAttr != null) {
			missingAttributes.add(missingAttr);
		}
		requestInfo.setMissingAttributes(missingAttributes);
		requestInfo.updateStatusCode(statusCode);
		requestInfo.setTargetMatched(returnTargetFailure);
		return decision;
	}

	public DecisionType evaluateEvaluatableList(RequestType request,
			List<Evaluatable> possibleEvaluatables,
			RequestInformation requestInfo) {
		List<MissingAttributeDetailType> missingAttributes = new ArrayList<MissingAttributeDetailType>();
		if (missingAttr != null) {
			missingAttributes.add(missingAttr);
		}
		requestInfo.setMissingAttributes(missingAttributes);
		requestInfo.updateStatusCode(statusCode);
		return decision;
	}

}
