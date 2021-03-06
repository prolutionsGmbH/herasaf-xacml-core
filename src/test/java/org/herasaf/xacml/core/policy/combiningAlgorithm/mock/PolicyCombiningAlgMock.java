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

package org.herasaf.xacml.core.policy.combiningAlgorithm.mock;

import java.util.ArrayList;
import java.util.List;

import org.herasaf.xacml.core.combiningAlgorithm.policy.PolicyCombiningAlgorithm;
import org.herasaf.xacml.core.context.EvaluationContext;
import org.herasaf.xacml.core.context.StatusCode;
import org.herasaf.xacml.core.context.impl.DecisionType;
import org.herasaf.xacml.core.context.impl.MissingAttributeDetailType;
import org.herasaf.xacml.core.context.impl.RequestType;
import org.herasaf.xacml.core.policy.Evaluatable;

/**
 * This is a mock object of a policy combining algorithm. This mock can be fed
 * with a {@link DecisionType}, a {@link StatusCode}, a
 * {@link MissingAttributeDetailType} and the target evaluation failed.
 * 
 * @author Florian Huonder
 */
public class PolicyCombiningAlgMock implements PolicyCombiningAlgorithm {
    private static final long serialVersionUID = 1L;
    public DecisionType decision;
	public StatusCode statusCode;
	public MissingAttributeDetailType missingAttr;
	public boolean returnTargetFailure;
	
	/** {@inheritDoc} */
	@Override
	public String getCombiningAlgorithmId() {
		return "CombiningAlgorithmMock";
	}

	/**
	 * Creates a new {@link PolicyCombiningAlgMock}.
	 * 
	 * @param decision
	 *            The {@link DecisionType} that this combining algorithm shall
	 *            return.
	 * @param statusCode
	 *            The {@link StatusCode} that this combining algorithm shall
	 *            return.
	 * @param missingAttr
	 *            The {@link MissingAttributeDetailType} that this combining
	 *            algorithm shall return.
	 */
	public PolicyCombiningAlgMock(DecisionType decision, StatusCode statusCode, MissingAttributeDetailType missingAttr) {
		this.decision = decision;
		this.statusCode = statusCode;
		this.missingAttr = missingAttr;
	}

	/**
	 * Creates a new {@link PolicyCombiningAlgMock}.
	 * 
	 * @param decision
	 *            The {@link DecisionType} that this combining algorithm shall
	 *            return.
	 * @param statusCode
	 *            The {@link StatusCode} that this combining algorithm shall
	 *            return.
	 * @param missingAttr
	 *            The {@link MissingAttributeDetailType} that this combining
	 *            algorithm shall return.
	 * @param returnTargetFailure
	 *            True if the target evaluation shall fail, false otherwise.
	 */
	public PolicyCombiningAlgMock(DecisionType decision, StatusCode statusCode, MissingAttributeDetailType missingAttr,
			boolean returnTargetFailure) {
		this(decision, statusCode, missingAttr);
		this.returnTargetFailure = returnTargetFailure;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Returns a {@link DecisionType} containing the predefined
	 * {@link StatusCode}, {@link DecisionType} and
	 * {@link MissingAttributeDetailType}.
	 */
	@Override
	public DecisionType evaluate(RequestType request, Evaluatable evals, EvaluationContext evaluationContext) {
		List<MissingAttributeDetailType> missingAttributes = new ArrayList<MissingAttributeDetailType>();
		if (missingAttr != null) {
			missingAttributes.add(missingAttr);
		}
		evaluationContext.setMissingAttributes(missingAttributes);
		evaluationContext.updateStatusCode(statusCode);
		evaluationContext.setTargetMatched(returnTargetFailure);
		return decision;
	}

	/**
	 *{@inheritDoc}
	 * 
	 * Returns a {@link DecisionType} containing the predefined
	 * {@link StatusCode}, {@link DecisionType} and
	 * {@link MissingAttributeDetailType}.
	 */
	@Override
	public DecisionType evaluateEvaluatableList(RequestType request, List<Evaluatable> possibleEvaluatables,
			EvaluationContext evaluationContext) {
		List<MissingAttributeDetailType> missingAttributes = new ArrayList<MissingAttributeDetailType>();
		if (missingAttr != null) {
			missingAttributes.add(missingAttr);
		}
		evaluationContext.setMissingAttributes(missingAttributes);
		evaluationContext.updateStatusCode(statusCode);
		return decision;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.herasaf.xacml.core.combiningAlgorithm.policy.PolicyCombiningAlgorithm
	 * #isRespectAbandonedEvaluatables()
	 */
	public boolean isRespectAbandonedEvaluatables() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.herasaf.xacml.core.combiningAlgorithm.policy.PolicyCombiningAlgorithm
	 * #setRespectAbandondEvaluatables(boolean)
	 */
	public void setRespectAbandondEvaluatables(boolean respectAbandondEvaluatables) {

	}

	/**
	 * This mock shall be an unordered combining algorithm.
	 */
	public boolean isOrderedCombiningAlgorithm() {
		return false;
	}

	/**
	 * nothing to set.
	 */
	public void setOrderedCombiningAlgorithm(boolean isOrderedCombiningAlgorithm) {
		// nop. because its unordered.

	}

}
