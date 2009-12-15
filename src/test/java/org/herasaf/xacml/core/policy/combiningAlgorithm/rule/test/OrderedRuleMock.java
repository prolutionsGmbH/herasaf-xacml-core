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

package org.herasaf.xacml.core.policy.combiningAlgorithm.rule.test;

import java.util.List;

import org.herasaf.xacml.core.combiningAlgorithm.rule.RuleOrderedCombiningAlgorithm;
import org.herasaf.xacml.core.context.RequestInformation;
import org.herasaf.xacml.core.context.StatusCode;
import org.herasaf.xacml.core.context.impl.DecisionType;
import org.herasaf.xacml.core.context.impl.MissingAttributeDetailType;
import org.herasaf.xacml.core.context.impl.RequestType;
import org.herasaf.xacml.core.policy.combiningAlgorithm.mock.TargetMatcherMock;
import org.herasaf.xacml.core.policy.impl.RuleType;
import org.herasaf.xacml.core.policy.impl.TargetType;

/**
 * This is a mock of the {@link RuleOrderedCombiningAlgorithm}.
 *
 * @author Florian Huonder
 */
public class OrderedRuleMock extends RuleOrderedCombiningAlgorithm {
	public DecisionType targetDecision;
	public StatusCode targetStatusCode;
	public MissingAttributeDetailType targetMissingAttribute;

	/**
	 * Creates a new mock that contains a {@link TargetMatcherMock}.
	 */
	public OrderedRuleMock() {
		super.setTargetMatcher(new TargetMatcherMock());
	}

	/**
	 * Creates a new mock.
	 * 
	 * @param targetDecision The {@link DecisionType} of the combining algorithm.
	 * @param targetStatusCode The {@link StatusCode} of the combing algorithm.
	 * @param targetMissingAttribute The {@link MissingAttributeDetailType} of the combining algorithm.
	 */
	public OrderedRuleMock(DecisionType targetDecision,
			StatusCode targetStatusCode,
			MissingAttributeDetailType targetMissingAttribute) {
		this();
		this.targetDecision = targetDecision;
		this.targetStatusCode = targetStatusCode;
		this.targetMissingAttribute = targetMissingAttribute;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Returns always null because this method is not needed.
	 */
	@Override
	public DecisionType evaluateRule(RequestType request,
			RuleType rule, RequestInformation requestInfo) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Returns the predefined {@link DecisionType}.
	 */
	@Override
	protected DecisionType matchTarget(RequestType request,
			TargetType target, RequestInformation requestInfo) {
		requestInfo.resetStatus();
		if (targetMissingAttribute != null) {
			requestInfo.addMissingAttributes(targetMissingAttribute);
		}
		requestInfo.updateStatusCode(targetStatusCode);
		return targetDecision;
	}

	/**
	 * Returns always permit.
	 */
	@Override
	public DecisionType evaluateRuleList(RequestType request,
			List<RuleType> possiblePolicies, RequestInformation requestInfos) {
		return DecisionType.PERMIT;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCombiningAlgorithmId() {
		return "mockCombiningAlgorithm";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isOrderedCombiningAlgorithm() {
		return true;
	}
}