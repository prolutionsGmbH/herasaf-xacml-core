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

package org.herasaf.xacml.core.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.herasaf.xacml.core.attributeFinder.AttributeFinder;
import org.herasaf.xacml.core.context.impl.MissingAttributeDetailType;
import org.herasaf.xacml.core.policy.Evaluatable;
import org.herasaf.xacml.core.policy.impl.EffectType;
import org.herasaf.xacml.core.policy.impl.ObjectFactory;
import org.herasaf.xacml.core.policy.impl.ObligationType;
import org.herasaf.xacml.core.policy.impl.ObligationsType;
import org.herasaf.xacml.core.policy.impl.Variable;

/**
 * TODO JAVADOC extend documentation a lot. This is one of the central classes of the implementation.
 * 
 * This data type is a collection which contains all local-{@link Evaluatable}s
 * and all remote-{@link Evaluatable}s.
 *
 * @author Florian Huonder
 * @author Ren� Eggenschwiler
 */
public class RequestInformation {
	private AttributeFinder attributeFinder;
	private StatusCode statusCode;
	private List<MissingAttributeDetailType> missingAttributes;
	private boolean targetMatched;
	private Map<String, Variable> variableDefinitions;
	private ObligationsType obligations;
	
	private static ObjectFactory objectFactory;

	/**
	 * Initializes the JAXB object factory.
	 */
	static {
		objectFactory = new ObjectFactory();
	}

	/**
	 * TODO JAVADOC
	 * 
	 * @param attributeFinder The Attribute finder to place into the {@link RequestInformation}.
	 */
	public RequestInformation(AttributeFinder attributeFinder) {
		this.attributeFinder = attributeFinder;
		statusCode = StatusCode.OK;
		missingAttributes = new ArrayList<MissingAttributeDetailType>();
		targetMatched = true;
		obligations = objectFactory.createObligationsType();
	}

	/**
	 * Returns the {@link StatusCode} set in this {@link RequestInformation}.
	 *
	 * @return The {@link StatusCode} of this {@link RequestInformation}.
	 */
	public StatusCode getStatusCode() {
		return statusCode;
	}

	/**
	 * Updates the {@link StatusCode} in this {@link RequestInformation}. This
	 * update is aware of the priority of a {@link StatusCode} what means that a
	 * weak one cannot override a strong one. Priorities:
	 * <ol>
	 * <li>{@link StatusCode#SYNTAX_ERROR}</li>
	 * <li>{@link StatusCode#MISSING_ATTRIBUTE}
	 * <li>
	 * <li>{@link StatusCode#PROCESSING_ERROR}</li>
	 * <li>{@link StatusCode#OK}</li>
	 * </ol>
	 *
	 * @param code
	 *            The prospective new {@link StatusCode}.
	 */
	public void updateStatusCode(StatusCode code) {

		if (statusCode == StatusCode.SYNTAX_ERROR) {
			return;
		}
		if (code == StatusCode.OK) {
			return;
		}
		if (statusCode == StatusCode.OK
				&& (code == StatusCode.MISSING_ATTRIBUTE || code == StatusCode.PROCESSING_ERROR)) {
			this.statusCode = code;
			return;
		}
		if (statusCode == StatusCode.PROCESSING_ERROR
				&& code == StatusCode.MISSING_ATTRIBUTE) {
			this.statusCode = code;
			return;
		}
		if (code == StatusCode.SYNTAX_ERROR) {
			this.statusCode = code;
			return;
		}
	}

	/**
	 * Sets the strongest {@link StatusCode} from the {@link List} into this
	 * {@link RequestInformation}.
	 *
	 * @param statusCodes
	 *            The {@link List} of {@link StatusCode}s.
	 */
	public void updateStatusCode(List<StatusCode> statusCodes) {
		for (StatusCode code : statusCodes) {
			updateStatusCode(code);
		}
	}

	/**
	 * Resets the {@link StatusCode}.
	 */
	public void resetStatus() {
		this.statusCode = StatusCode.OK;
		missingAttributes.clear();
		targetMatched = true;
	}

	/**
	 * Returns a {@link List} containing the {@link MissingAttributeDetailType}s.
	 *
	 * @return The {@link List} of {@link MissingAttributeDetailType}s.
	 */
	public List<MissingAttributeDetailType> getMissingAttributes() {
		return missingAttributes;
	}

	/**
	 * Sets the {@link MissingAttributeDetailType}s.
	 *
	 * @param missingAttributes
	 *            The {@link List} of {@link MissingAttributeDetailType}s.
	 */
	public void setMissingAttributes(
			List<MissingAttributeDetailType> missingAttributes) {
		this.missingAttributes = missingAttributes;
	}

	/**
	 * There are some combining algorithms which must be aware of the fact if an
	 * indeterminate has its source in a function or a target-match.
	 *
	 * @return True if the target-match was successful.
	 */
	public boolean isTargetMatched() {
		return targetMatched;
	}

	/**
	 * Sets if the target-match was successful.
	 *
	 * @param targetMatched
	 */
	public void setTargetMatched(boolean targetMatched) {
		this.targetMatched = targetMatched;
	}

	/**
	 * Adds a {@link MissingAttributeDetailType} to the {@link List} of
	 * {@link MissingAttributeDetailType}.
	 *
	 * @param missingAttribute
	 *            The {@link MissingAttributeDetailType} to add.
	 */
	public void addMissingAttributes(MissingAttributeDetailType missingAttribute) {
		this.missingAttributes.add(missingAttribute);

	}

	/**
	 * Gets the variable Definitions actually appended to the
	 * requestInformation.
	 *
	 * @return a Map with the VariableDefinitions. The key value is the
	 *         identifier of the variable.
	 */
	public Map<String, Variable> getVariableDefinitions() {
		return variableDefinitions;
	}

	/**
	 * Sets the variable definitions to the RequestInformation.
	 *
	 * @param variableDefinitions
	 *            The Map with the Variabledefinitions to set. The key value is
	 *            the identifier of the variable
	 */
	public void setVariableDefinitions(Map<String, Variable> variableDefinitions) {
		this.variableDefinitions = variableDefinitions;
	}

	/**
	 * Gets the AttributeFinder for this Request.
	 *
	 * @return Returns the AttributeFinder
	 */
	public AttributeFinder getAttributeFinder() {
		return attributeFinder;
	}
	
	/**
	 * Removes all Obligations from the list that to not match the {@link EffectType} provided.
	 * 
	 * @param effect The Obligation's {@link EffectType} that should be kept.
	 */
	
	public void addObligations(final List<ObligationType> obligations, final EffectType effect){
		List<ObligationType> obls = new ArrayList<ObligationType>();
		
		for(int i = 0; i < obligations.size(); i++){
			ObligationType obl = obligations.get(i);
			if(obl.getFulfillOn().equals(effect)){
				obls.add(obl);
			}
		}
		
		this.obligations.getObligations().addAll(obls);
	}
	
	public void clearObligations(){
		this.obligations.getObligations().clear();
	}
	
	/**
	 * Returns the {@link ObligationsType} contained in this {@link RequestInformation} object.
	 * 
	 * @return the {@link ObligationsType}.
	 */
	public ObligationsType getObligations(){
		return obligations;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder stringValue = new StringBuilder("RequestInformation[");
		stringValue.append("attributeFinder=");
		stringValue.append(attributeFinder);
		stringValue.append(", statusCode=");
		stringValue.append(statusCode);
		stringValue.append(", missingAttributes=");
		stringValue.append(missingAttributes);
		stringValue.append(", targetMatched=");
		stringValue.append(this.targetMatched);
		stringValue.append(", variableDefinitions=");
		stringValue.append(variableDefinitions);
		stringValue.append(", obligations=");
		stringValue.append(obligations);
		stringValue.append("]");

		return stringValue.toString();
	}
}