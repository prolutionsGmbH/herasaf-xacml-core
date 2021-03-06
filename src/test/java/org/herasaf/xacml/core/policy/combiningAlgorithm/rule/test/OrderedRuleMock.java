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

package org.herasaf.xacml.core.policy.combiningAlgorithm.rule.test;

import java.util.List;

import org.herasaf.xacml.core.combiningAlgorithm.rule.RuleOrderedCombiningAlgorithm;
import org.herasaf.xacml.core.context.EvaluationContext;
import org.herasaf.xacml.core.context.StatusCode;
import org.herasaf.xacml.core.context.impl.DecisionType;
import org.herasaf.xacml.core.context.impl.MissingAttributeDetailType;
import org.herasaf.xacml.core.context.impl.RequestType;
import org.herasaf.xacml.core.policy.impl.RuleType;
import org.herasaf.xacml.core.policy.impl.TargetType;
import org.herasaf.xacml.core.targetMatcher.TargetMatchingResult;

/**
 * This is a mock of the {@link RuleOrderedCombiningAlgorithm}.
 * 
 * @author Florian Huonder
 */
public class OrderedRuleMock extends RuleOrderedCombiningAlgorithm {
	private static final long serialVersionUID = 1L;
	public TargetMatchingResult targetDecision;
	public StatusCode targetStatusCode;
	public MissingAttributeDetailType targetMissingAttribute;

	/**
	 * Creates a new {@link OrderedRuleMock}.
	 */
	public OrderedRuleMock() {
	}

	/**
	 * Creates a new mock.
	 * 
	 * @param targetDecision
	 *            The {@link DecisionType} of the combining algorithm.
	 * @param targetStatusCode
	 *            The {@link StatusCode} of the combing algorithm.
	 * @param targetMissingAttribute
	 *            The {@link MissingAttributeDetailType} of the combining
	 *            algorithm.
	 */
	public OrderedRuleMock(TargetMatchingResult targetDecision,
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
	public DecisionType evaluateRule(RequestType request, RuleType rule,
			EvaluationContext evaluationContext) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Returns the predefined {@link DecisionType}.
	 */
	@Override
	protected TargetMatchingResult matchTarget(RequestType request, TargetType target,
			EvaluationContext evaluationContext) {
		evaluationContext.resetStatus();
		if (targetMissingAttribute != null) {
			evaluationContext.addMissingAttributes(targetMissingAttribute);
		}
		evaluationContext.updateStatusCode(targetStatusCode);
		return targetDecision;
	}

	/**
	 * Returns always permit.
	 */
	@Override
	public DecisionType evaluateRuleList(RequestType request,
			List<RuleType> possiblePolicies,
			EvaluationContext evaluationContexts) {
		return DecisionType.PERMIT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCombiningAlgorithmId() {
		return "mockCombiningAlgorithm";
	}
}