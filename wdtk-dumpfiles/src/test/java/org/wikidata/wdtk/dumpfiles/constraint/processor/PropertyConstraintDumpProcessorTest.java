package org.wikidata.wdtk.dumpfiles.constraint.processor;

import org.junit.Assert;
import org.junit.Test;

/*
 * #%L
 * Wikidata Toolkit RDF
 * %%
 * Copyright (C) 2014 Wikidata Toolkit Developers
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

/**
 * Test class for {@link PropertyConstraintDumpProcessor}.
 * 
 * @author Julian Mendez
 *
 */
public class PropertyConstraintDumpProcessorTest {

	public PropertyConstraintDumpProcessorTest() {
	}

	@Test
	public void testEscapeChars() {
		PropertyConstraintDumpProcessor processor = new PropertyConstraintDumpProcessor();
		Assert.assertEquals("", processor.escapeChars(""));
		Assert.assertEquals("&lt;test>", processor.escapeChars("<test>"));
		Assert.assertEquals("&amp;lt;", processor.escapeChars("&lt;"));
		Assert.assertEquals("&quot;test&quot;",
				processor.escapeChars("\"test\""));
		Assert.assertEquals("unit  test", processor.escapeChars("unit\ntest"));
	}

}