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

package org.herasaf.xacml.core.function.impl.nonNumericComparisonFunction;

import static org.testng.Assert.assertEquals;

import org.herasaf.xacml.core.function.Function;
import org.herasaf.xacml.core.function.impl.nonNumericComparisonFunctions.StringGreaterThanFunction;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tests if the StringGreaterThan function works properly.
 * 
 * @author Florian Huonder
 */
public class TestStringGreaterThan {
	private Function ia;

	/**
	 * Creates various test cases.
	 * @return The created test cases.
	 */
	@DataProvider(name="args")
	public Object[][] createArgs(){
		return new Object[][]{
			new Object[] { "1", "1", false},
			new Object[] { "Hallo", "Hello", false},
			new Object[] { "Ha", "Hello", false},
			new Object[] { "He", "Hallo", true},
			new Object[] { "Test", "Auto", true},
			new Object[] { "Hello", "Ha", true},
			new Object[] { "He", "Hel", false},
		};
	}

	/**
	 * Initializes the function.
	 */
	@BeforeMethod
	public void init(){
		ia = new StringGreaterThanFunction();
	}

	/**
	 * Tests if the StringGreaterThan function works properly.
	 * 
	 * @param i1 The first string argument.
	 * @param i2 The second string argument.
	 * @param result The expected result.
	 * @throws Exception If an error occurs.
	 */
	@Test(dataProvider="args")
	public void testArgs(String i1, String i2, Boolean result) throws Exception {
		assertEquals(ia.handle(i1, i2), result);
	}

	/**
	 * Tests if the StringGreaterThan function returns the proper ID.
	 */
	@Test
	public void testID(){
		assertEquals(ia.toString(), "urn:oasis:names:tc:xacml:1.0:function:string-greater-than");
	}
}