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

package org.herasaf.xacml.core.combiningAlgorithm.policy.impl;

import java.util.ArrayList;
import java.util.List;

import org.herasaf.xacml.core.combiningAlgorithm.CombiningAlgorithm;
import org.herasaf.xacml.core.combiningAlgorithm.policy.PolicyOrderedCombiningAlgorithm;
import org.herasaf.xacml.core.context.EvaluationContext;
import org.herasaf.xacml.core.context.StatusCode;
import org.herasaf.xacml.core.context.XACMLDefaultStatusCode;
import org.herasaf.xacml.core.context.impl.DecisionType;
import org.herasaf.xacml.core.context.impl.RequestType;
import org.herasaf.xacml.core.policy.Evaluatable;
import org.herasaf.xacml.core.policy.impl.EffectType;
import org.herasaf.xacml.core.policy.impl.IdReferenceType;
import org.herasaf.xacml.core.policy.impl.ObligationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * The implementation of the default XACML 2.0 <i>policy ordered deny overrides
 * algorithm</i>.<br />
 * See: <a href=
 * "http://www.oasis-open.org/committees/tc_home.php?wg_abbrev=xacml#XACML20">
 * OASIS eXtensible Access Control Markup Langugage (XACML) 2.0, Errata 29.
 * January 2008</a> page 148, for further information.
 * 
 * @author Stefan Oberholzer
 * @author Florian Huonder
 * @author René Eggenschwiler
 */
public class PolicyOrderedDenyOverridesAlgorithm extends
		PolicyOrderedCombiningAlgorithm {

	/** XACMLcombining algorithm ID. */
	public static final String ID = "urn:oasis:names:tc:xacml:1.1:policy-combining-algorithm:ordered-deny-overrides";

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory
			.getLogger(PolicyOrderedDenyOverridesAlgorithm.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DecisionType evaluateEvaluatableList(final RequestType request,
			final List<Evaluatable> possiblePolicies,
			final EvaluationContext evaluationContext) {

		if (possiblePolicies == null) {
			// It is an illegal state if the list containing the policies is
			// null.
			logger
					.error("The possiblePolicies list was null. This is an illegal state.");
			evaluationContext
					.updateStatusCode(XACMLDefaultStatusCode.SYNTAX_ERROR);
			return DecisionType.INDETERMINATE;
		}

		List<ObligationType> obligationsOfApplicableEvals = new ArrayList<ObligationType>();
		List<StatusCode> statusCodes = new ArrayList<StatusCode>();

		boolean atLeastOnePermit = false;
		boolean atLeastOneDeny = false;
		boolean atLeastOneError = false;

		/*
		 * If the list of evaluatables contains no values, the for-loop is
		 * skipped and a NOT_APPLICABLE is returned.
		 */
		for (int i = 0; i < possiblePolicies.size(); i++) {
		        Evaluatable eval = possiblePolicies.get(i);
		        if (eval instanceof IdReferenceType) {
		                eval = evaluationContext.getPolicyRetrievalPoint().getEvaluatable(
		                                                                                  eval.getId());
		        }

			if (eval == null) {
				// It is an illegal state if the list contains any
				// null.
				logger
						.error("The list of possible policies must not contain any null values.");
				evaluationContext
						.updateStatusCode(XACMLDefaultStatusCode.SYNTAX_ERROR);
				return DecisionType.INDETERMINATE;
			}

			if (atLeastOneDeny
					&& evaluationContext.isRespectAbandonedEvaluatables()
					&& !eval.hasObligations()) {
				/*
				 * If a decision is already made (atLeastOneDeny == true) and
				 * the abandoned Obligations must be taken into account
				 * (respectAbandonedEvaluatables == true) and the evaluatable to
				 * evaluate (and its sub evaluatables) do not have Obligations,
				 * then this iteration can be skipped.
				 */
				break;
			}

			DecisionType decision;
			// Resets the status to go sure, that the returned statuscode is
			// the one of the evaluation.
			evaluationContext.resetStatus();

			if (logger.isDebugEnabled()) {
				MDC.put(MDC_EVALUATABLE_ID, eval.getId().toString());
				logger
						.debug("Starting evaluation of: {}", eval.getId()
								.toString());
			}

			CombiningAlgorithm combiningAlg = eval.getCombiningAlg();
			if (combiningAlg == null) {
				logger.error(
						"Unable to locate combining algorithm for policy {}",
						eval.getId());
				evaluationContext
						.updateStatusCode(XACMLDefaultStatusCode.SYNTAX_ERROR);
				decision = DecisionType.INDETERMINATE;
			} else {
				decision = combiningAlg.evaluate(request, eval,
						evaluationContext);
			}

			if (logger.isDebugEnabled()) {
				MDC.put(MDC_EVALUATABLE_ID, eval.getId().toString());
				logger.debug("Evaluation of {} was: {}", eval.getId().toString(),
						decision.toString());
				MDC.remove(MDC_EVALUATABLE_ID);
			}

			if (decision == DecisionType.PERMIT
					|| decision == DecisionType.DENY) {
				obligationsOfApplicableEvals.addAll(eval
						.getContainedObligations(EffectType.fromValue(decision
								.toString())));
				obligationsOfApplicableEvals.addAll(evaluationContext
						.getObligations().getObligations());
			}
			switch (decision) {
			case DENY:
				if (!evaluationContext.isRespectAbandonedEvaluatables()) { // if
																			// abandoned
					// evaluatables should
					// not be included then
					// the first deny
					// finishes the
					// evaluation
					evaluationContext.clearObligations();
					evaluationContext.addObligations(
							obligationsOfApplicableEvals, EffectType.DENY);
					return DecisionType.DENY;
				} else {
					atLeastOneDeny = true;
				}
				break;
			case PERMIT:
				atLeastOnePermit = true;
				break;
			case INDETERMINATE:
				statusCodes.add(evaluationContext.getStatusCode());
				atLeastOneError = true;
				break;
			case NOT_APPLICABLE:
				break;
			}
			evaluationContext.clearObligations();
		}

		if (atLeastOneDeny) {
			evaluationContext.resetStatus();
			evaluationContext.addObligations(obligationsOfApplicableEvals,
					EffectType.DENY); // To
			// filter
			// all
			// PERMIT-Obligations
			// that
			// were collected so far
			return DecisionType.DENY;
		} else if (atLeastOneError) {
			evaluationContext.resetStatus();
			return DecisionType.DENY;
		} else if (atLeastOnePermit) {
			evaluationContext.addObligations(obligationsOfApplicableEvals,
					EffectType.PERMIT); // The
			// decision
			// is
			// made
			// and
			// all
			// DENY-Obligations can be filtered out

			/*
			 * If the result is permit, the statuscode is always ok.
			 */
			evaluationContext.resetStatus();
			return DecisionType.PERMIT;
		}
		evaluationContext.clearObligations();
		return DecisionType.NOT_APPLICABLE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCombiningAlgorithmId() {
		return ID;
	}
}