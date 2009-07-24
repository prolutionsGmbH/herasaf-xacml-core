/*
 * Copyright 2009 HERAS-AF (www.herasaf.org)
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
package org.herasaf.xacml.core.simplePDP.initializers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.herasaf.xacml.core.combiningAlgorithm.rule.AbstractRuleCombiningAlgorithm;
import org.herasaf.xacml.core.combiningAlgorithm.rule.RuleCombiningAlgorithm;
import org.herasaf.xacml.core.converter.URNToRuleCombiningAlgorithmConverter;
import org.herasaf.xacml.core.targetMatcher.TargetMatcher;
import org.herasaf.xacml.core.targetMatcher.impl.TargetMatcherImpl;

/**
 * TODO JAVADOC!!
 * 
 * @author Florian Huonder
 * @author Ren� Eggenschwiler
 * 
 */
public class RuleCombiningAlgorithmsInitializer extends
		AbstractInitializer<AbstractRuleCombiningAlgorithm> {

	private final static String SEARCH_CONTEXT = "org.herasaf.xacml.core.combiningAlgorithm.rule.impl";
	private final static String SEARCH_CONTEXT_PATH = "org/herasaf/xacml/core/combiningAlgorithm/rule/impl";
	private final static Class<AbstractRuleCombiningAlgorithm> TARGET_CLASS = AbstractRuleCombiningAlgorithm.class;
	private final TargetMatcher targetMatcher = new TargetMatcherImpl();

	@Override
	protected void furtherInitializations(
			List<AbstractRuleCombiningAlgorithm> instances) {
		for (AbstractRuleCombiningAlgorithm algorithm : instances) {
			algorithm.setTargetMatcher(targetMatcher);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getSearchContext() {
		return SEARCH_CONTEXT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getSearchContextPath() {
		return SEARCH_CONTEXT_PATH;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getURIFromType(AbstractRuleCombiningAlgorithm instance) {
		return instance.getCombiningAlgorithmId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setInstancesIntoConverter(
			Map<String, AbstractRuleCombiningAlgorithm> instancesMap) {
		Map<String, RuleCombiningAlgorithm> instances = new HashMap<String, RuleCombiningAlgorithm>();
		instances.putAll(instancesMap);
		URNToRuleCombiningAlgorithmConverter.setCombiningAlgorithms(instances);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.herasaf.xacml.core.simplePDP.initializers.AbstractInitializer#
	 * getTargetClass()
	 */
	@Override
	protected Class<AbstractRuleCombiningAlgorithm> getTargetClass() {
		return TARGET_CLASS;
	}
}
