/*
 * Copyright 2009 - 2012 HERAS-AF (www.herasaf.org)
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

import org.herasaf.xacml.core.combiningAlgorithm.AbstractCombiningAlgorithm;

/**
 * This class handles all commonality of the policy combining algorithms. This
 * is if a combining algorithm shall respect abandoned evaluatables.
 * 
 * @author Stefan Oberholzer
 * @author Florian Huonder
 * @author René Eggenschwiler
 */
public abstract class AbstractPolicyCombiningAlgorithm extends AbstractCombiningAlgorithm implements
		PolicyCombiningAlgorithm {
    private static final long serialVersionUID = 1L;
    protected static final String MDC_EVALUATABLE_ID = "org:herasaf:xacml:evaluation:evaluatableid";

}
