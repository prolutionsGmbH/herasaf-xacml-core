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

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1.5-b01-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.10.03 at 07:56:30 AM CEST 
//

package org.herasaf.xacml.core.context.impl;

import org.herasaf.xacml.core.dataTypeAttribute.impl.DateDataTypeAttribute;
import org.herasaf.xacml.core.dataTypeAttribute.impl.DateTimeDataTypeAttribute;
import org.herasaf.xacml.core.dataTypeAttribute.impl.TimeDataTypeAttribute;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Java class for RequestType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="RequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:oasis:names:tc:xacml:2.0:context:schema:os}Subject" maxOccurs="unbounded"/>
 *         &lt;element ref="{urn:oasis:names:tc:xacml:2.0:context:schema:os}Resource" maxOccurs="unbounded"/>
 *         &lt;element ref="{urn:oasis:names:tc:xacml:2.0:context:schema:os}Action"/>
 *         &lt;element ref="{urn:oasis:names:tc:xacml:2.0:context:schema:os}Environment"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * See: <a href=
 * "http://www.oasis-open.org/committees/tc_home.php?wg_abbrev=xacml#XACML20">
 * OASIS eXtensible Access Control Markup Langugage (XACML) 2.0, Errata 29.
 * January 2008</a> page 71, for further information.
 * 
 * @version 1.0
 * @author <i>generated</i>
 */
@XmlRootElement
@XmlType(name = "RequestType", propOrder = { "subjects", "resources", "action",
		"environment" })
public class RequestType implements Serializable {

	private static final String CURRENT_DATETIME_DATATYPEID = "urn:oasis:names:tc:xacml:1.0:environment:current-dateTime";
	private static final String CURRENT_DATE_DATATYPEID = "urn:oasis:names:tc:xacml:1.0:environment:current-date";
	private static final String CURRENT_TIME_DATATYPEID = "urn:oasis:names:tc:xacml:1.0:environment:current-time";

	private static final long serialVersionUID = 1L;

	@XmlElement(name = "Subject", required = true)
	private List<SubjectType> subjects;
	@XmlElement(name = "Resource", required = true)
	private List<ResourceType> resources;
	@XmlElement(name = "Action", required = true)
	private ActionType action;
	@XmlElement(name = "Environment", required = true)
	private EnvironmentType environment;

	/**
	 * Sets the following attributes to the according value of the given creationTime,
	 * if the request does not already contain values for these attributes
	 * <ul>
	 * <li>urn:oasis:names:tc:xacml:1.0:environment:current-dateTime</li>
	 * <li>urn:oasis:names:tc:xacml:1.0:environment:current-date</li>
	 * <li>urn:oasis:names:tc:xacml:1.0:environment:current-time</li>
	 * </ul>
	 * The XACML 2.0 specification. See: <a href=
	 * "http://www.oasis-open.org/committees/tc_home.php?wg_abbrev=xacml#XACML20"
	 * > OASIS eXtensible Access Control Markup Langugage (XACML) 2.0, Errata
	 * 29. January 2008</a> appendix B.8. Environment attributes, requires these attributes to be set on each request.
	 * With this method a caller is able to add a date, time and datetime.
	 * 
	 * @param creationTime The creationTime to set to this request.
	 */
	public void setCreationTime(OffsetDateTime creationTime) {
	        boolean foundCurrentTime = false;
	        boolean foundCurrentDate = false;
	        boolean foundCurrentDateTime = false;
	        for (AttributeType attribute : environment.getAttributes()) {
	                if (CURRENT_TIME_DATATYPEID.equals(attribute.getAttributeId())
                                && attribute.getAttributeValues() != null && !attribute.getAttributeValues().isEmpty()) {
                                foundCurrentTime = true;   
                        } else if (CURRENT_DATE_DATATYPEID.equals(attribute.getAttributeId())
                                && attribute.getAttributeValues() != null && !attribute.getAttributeValues().isEmpty()) {
                                foundCurrentDate = true;   
                        } else if (CURRENT_DATETIME_DATATYPEID.equals(attribute.getAttributeId())
                                && attribute.getAttributeValues() != null && !attribute.getAttributeValues().isEmpty()) {
                                foundCurrentDateTime = true;   
                        }
	        }
	        if (!foundCurrentTime) {
	                environment.getAttributes().add(createCurrentTime(creationTime));
	        }
	        if (!foundCurrentDate) {
	                environment.getAttributes().add(createCurrentDate(creationTime));
	        }
	        if (!foundCurrentDateTime) {
	                environment.getAttributes().add(createCurrentDateTime(creationTime));
	        }
	}
	
	/**
	 * Sets the following attributes to the current time, if the request does not 
	 * already contain values for these attributes
	 * <ul>
	 * <li>urn:oasis:names:tc:xacml:1.0:environment:current-dateTime</li>
	 * <li>urn:oasis:names:tc:xacml:1.0:environment:current-date</li>
	 * <li>urn:oasis:names:tc:xacml:1.0:environment:current-time</li>
	 * </ul>
	 * The XACML 2.0 specification. See: <a href=
	 * "http://www.oasis-open.org/committees/tc_home.php?wg_abbrev=xacml#XACML20"
	 * > OASIS eXtensible Access Control Markup Langugage (XACML) 2.0, Errata
	 * 29. January 2008</a> appendix B.8. Environment attributes, requires these attributes to be set on each request.
	 * With this method a caller is able to add the current date, time and datetime.
	 */
	public void ensureThatCreationTimeIsSet() {
	    OffsetDateTime dateTime = OffsetDateTime.now();
		setCreationTime(dateTime);
	}

	/**
	 * Creates a new {@link AttributeType} containing the current time.
	 * 
	 * @return The {@link AttributeType} containing the current time.
	 */
	private AttributeType createCurrentTime(OffsetDateTime dateTime) {
		AttributeType currentTimeAttr = new AttributeType();
		currentTimeAttr.setAttributeId(CURRENT_TIME_DATATYPEID);
		currentTimeAttr.setDataType(new TimeDataTypeAttribute());
		currentTimeAttr.getAttributeValues().add(createDateTime(dateTime, DateTimeFormatter.ISO_DATE));

		return currentTimeAttr;
	}

	/**
	 * Creates a new {@link AttributeType} containing the current date.
	 * 
	 * @return The {@link AttributeType} containing the current date.
	 */
	private AttributeType createCurrentDate(OffsetDateTime dateTime) {
		AttributeType currentDateAttr = new AttributeType();
		currentDateAttr.setAttributeId(CURRENT_DATE_DATATYPEID);
		currentDateAttr.setDataType(new DateDataTypeAttribute());
		currentDateAttr.getAttributeValues().add(createDateTime(dateTime, DateTimeFormatter.ISO_DATE));

		return currentDateAttr;
	}

	/**
	 * Creates a new {@link AttributeType} containing the current dateTime.
	 * 
	 * @return The {@link AttributeType} containing the current dateTime.
	 */
	private AttributeType createCurrentDateTime(OffsetDateTime dateTime) {
		AttributeType currentDateTimeAttr = new AttributeType();
		currentDateTimeAttr.setAttributeId(CURRENT_DATETIME_DATATYPEID);
		currentDateTimeAttr.setDataType(new DateTimeDataTypeAttribute());
		currentDateTimeAttr.getAttributeValues().add(
				createDateTime(dateTime, DateTimeFormatter.ISO_DATE_TIME));

		return currentDateTimeAttr;
	}

	/**
	 * Creates a new {@link AttributeValueType} containing the current dateTime
	 * with the given pattern.
	 * 
	 * @return The current dateTime.
	 */
	private AttributeValueType createDateTime(OffsetDateTime dateTime, DateTimeFormatter format) {
		
		String value = dateTime.atZoneSameInstant(ZoneOffset.UTC)
				.format(format);

		AttributeValueType attrValue = new AttributeValueType();
		attrValue.getContent().add(value);

		return attrValue;
	}

	/**
	 * Gets the value of the subjects property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the subjects property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getSubjects().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link SubjectType }
	 * 
	 * 
	 */
	public List<SubjectType> getSubjects() {
		if (subjects == null) {
			subjects = new ArrayList<SubjectType>();
		}
		return this.subjects;
	}

	/**
	 * Gets the value of the resources property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the resources property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getResources().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link ResourceType }
	 * 
	 * 
	 */
	public List<ResourceType> getResources() {
		if (resources == null) {
			resources = new ArrayList<ResourceType>();
		}
		return this.resources;
	}

	/**
	 * Gets the value of the action property.
	 * 
	 * @return possible object is {@link ActionType }
	 * 
	 */
	public ActionType getAction() {
		return action;
	}

	/**
	 * Sets the value of the action property.
	 * 
	 * @param value
	 *            allowed object is {@link ActionType }
	 * 
	 */
	public void setAction(ActionType value) {
		this.action = value;
	}

	/**
	 * Gets the value of the environment property.
	 * 
	 * @return possible object is {@link EnvironmentType }
	 * 
	 */
	public EnvironmentType getEnvironment() {
		return environment;
	}

	/**
	 * Sets the value of the environment property.
	 * 
	 * @param value
	 *            allowed object is {@link EnvironmentType }
	 * 
	 */
	public void setEnvironment(EnvironmentType value) {
		this.environment = value;
	}
}