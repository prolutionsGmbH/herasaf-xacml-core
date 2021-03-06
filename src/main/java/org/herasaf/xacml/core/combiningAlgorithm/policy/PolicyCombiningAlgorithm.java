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

package org.herasaf.xacml.core.combiningAlgorithm.policy;

import java.util.List;

import org.herasaf.xacml.core.combiningAlgorithm.CombiningAlgorithm;
import org.herasaf.xacml.core.context.EvaluationContext;
import org.herasaf.xacml.core.context.impl.DecisionType;
import org.herasaf.xacml.core.context.impl.RequestType;
import org.herasaf.xacml.core.policy.Evaluatable;

/**
 * All policy combining algorithms must implement this interface. It provides
 * the entry point (
 * {@link #evaluateEvaluatableList(RequestType, List, EvaluationContext)}) for
 * starting to evaluate sub-evaluatables.
 * 
 * @author Sacha Dolski
 * @author Florian Huonder
 */
public interface PolicyCombiningAlgorithm extends CombiningAlgorithm {
	/**
	 * This method is the entry point for a policy combining algorithm to start
	 * evaluating its sub-evaluatables.
	 * 
	 * @param request
	 *            The request that has to be evaluated.
	 * @param possibleEvaluatables
	 *            List of the sub-evaluatables that have to be evaluated.
	 * @param evaluationContext
	 *            The context of this request evaluation.
	 * @return The combined decision of the evaluation of the request.
	 */
	DecisionType evaluateEvaluatableList(RequestType request, List<Evaluatable> possibleEvaluatables,
			EvaluationContext evaluationContext);
}