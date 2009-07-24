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

package org.herasaf.xacml.core.combiningAlgorithm.rule.impl;

import java.util.ArrayList;
import java.util.List;

import org.herasaf.xacml.core.combiningAlgorithm.policy.PolicyCombiningAlgorithm;
import org.herasaf.xacml.core.combiningAlgorithm.rule.RuleOrderedCombiningAlgorithm;
import org.herasaf.xacml.core.context.RequestInformation;
import org.herasaf.xacml.core.context.StatusCode;
import org.herasaf.xacml.core.context.impl.DecisionType;
import org.herasaf.xacml.core.context.impl.MissingAttributeDetailType;
import org.herasaf.xacml.core.context.impl.RequestType;
import org.herasaf.xacml.core.policy.impl.EffectType;
import org.herasaf.xacml.core.policy.impl.RuleType;

/**
 * <p>
 * The implementation of the {@link PolicyCombiningAlgorithm} with the
 * Ordered-Deny-Override strategy.
 * </p>
 * <p>
 * The Implementation of the Ordered-Deny-Override implementation oriented at
 * the sample implementation in the XACML 2.0 specification.
 * </p>
 *
 * <p>
 * See <a
 * href="http://www.oasis-open.org/committees/tc_home.php?wg_abbrev=xacml#XACML20">
 * OASIS eXtensible Access Control Markup Langugage (XACML) 2.0, Errata 29 June
 * 2006</a> page 133-134, for further information.
 * </p>
 *
 * @author Stefan Oberholzer
 * @version 1.0
 */
public class RuleOrderedDenyOverridesAlgorithm extends
		RuleOrderedCombiningAlgorithm {
	private static final long serialVersionUID = 13812608137844813L;
	// XACML Name of the Combining Algorithm
	private static final String COMBALGOID = "urn:oasis:names:tc:xacml:1.1:rule-combining-algorithm:ordered-deny-overrides";


	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCombiningAlgorithmId() {
		return COMBALGOID;
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see org.herasaf.core.combiningAlgorithm.rule.RuleOrderedCombiningAlgorithm#evaluateRuleList(org.herasaf.core.context.impl.RequestType,
	 *      java.util.List, org.herasaf.core.dataTypes.RequestInformation,
	 *      java.util.Map)
	 */
	@Override
	protected DecisionType evaluateRuleList(RequestType request,
			List<RuleType> rules, RequestInformation requestInfo) {
		/*
		 * keeps the actual state and missing attributes of this combining
		 * process.
		 */
		List<MissingAttributeDetailType> missingAttributes = new ArrayList<MissingAttributeDetailType>();
		List<StatusCode> statusCodes = new ArrayList<StatusCode>();

		boolean atLeastOnePermit = false;
		boolean potentialDeny = false;
		boolean atLeastOneError = false;

		for (int i = 0; i < rules.size(); i++) {
			RuleType rule = rules.get(i);
			// Resets the status to go sure, that the returned statuscode is
			// the one of the evaluation.
			requestInfo.resetStatus();
			DecisionType decision = this.evaluateRule(request, rule,
					requestInfo);
			switch (decision) {
			case DENY:
				return DecisionType.DENY;
			case INDETERMINATE:
				/*
				 * Adds the missing attributes and of the rule evaluation.
				 */
				missingAttributes.addAll(requestInfo.getMissingAttributes());
				statusCodes.add(requestInfo.getStatusCode());
				atLeastOneError = true;
				/*
				 * If the effect is deny if the evaluation results in true, the
				 * result is potentialy deny if an error occures.
				 */
				if (rule.getEffect() == EffectType.DENY) {
					potentialDeny = true;
				}
				break;
			case PERMIT:
				atLeastOnePermit = true;
				break;
			}
		}
		if (potentialDeny) {
			requestInfo.resetStatus();
			requestInfo.setMissingAttributes(missingAttributes);
			requestInfo.updateStatusCode(statusCodes);
			return DecisionType.INDETERMINATE;
		}
		if (atLeastOnePermit) {
			requestInfo.resetStatus();
			return DecisionType.PERMIT;
		}
		if (atLeastOneError) {
			requestInfo.resetStatus();
			requestInfo.setMissingAttributes(missingAttributes);
			requestInfo.updateStatusCode(statusCodes);
			return DecisionType.INDETERMINATE;
		}
		return DecisionType.NOT_APPLICABLE;

	}
}