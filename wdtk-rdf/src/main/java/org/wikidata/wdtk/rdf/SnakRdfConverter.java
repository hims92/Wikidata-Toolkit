package org.wikidata.wdtk.rdf;

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
import org.openrdf.model.Value;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.rio.RDFHandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wikidata.wdtk.datamodel.interfaces.NoValueSnak;
import org.wikidata.wdtk.datamodel.interfaces.Snak;
import org.wikidata.wdtk.datamodel.interfaces.SnakVisitor;
import org.wikidata.wdtk.datamodel.interfaces.SomeValueSnak;
import org.wikidata.wdtk.datamodel.interfaces.ValueSnak;

public class SnakRdfConverter implements SnakVisitor<Void> {

	static final Logger logger = LoggerFactory
			.getLogger(SnakRdfConverter.class);

	final ValueRdfConverter valueRdfConverter;

	final ValueFactory factory = ValueFactoryImpl.getInstance();
	final RdfWriter rdfWriter;

	String currentSubjectUri;
	PropertyContext currentPropertyContext;

	public SnakRdfConverter(RdfWriter rdfWriter,
			ValueRdfConverter valueRdfConverter) {
		this.rdfWriter = rdfWriter;
		this.valueRdfConverter = valueRdfConverter;
	}

	public void writeSnak(Snak snak, String subjectUri,
			PropertyContext propertyContext) {
		this.currentSubjectUri = subjectUri;
		this.currentPropertyContext = propertyContext;
		snak.accept(this);
	}

	public void setSnakContext(String subjectUri,
			PropertyContext propertyContext) {
		this.currentSubjectUri = subjectUri;
		this.currentPropertyContext = propertyContext;
	}

	@Override
	public Void visit(ValueSnak snak) {
		String propertyUri = Vocabulary.getPropertyUri(snak.getPropertyId(),
				this.currentPropertyContext);
		Value value = valueRdfConverter.getRdfValueForWikidataValue(
				snak.getValue(), snak.getPropertyId());
		if (value == null) {
			logger.error("Could not serialize snak: missing value (Snak: "
					+ snak.toString() + ")");
			return null;
		}

		try {
			this.rdfWriter.writeTripleValueObject(this.currentSubjectUri,
					propertyUri, value);
		} catch (RDFHandlerException e) {
			throw new RuntimeException(e.toString(), e);
		}
		return null;
	}

	@Override
	public Void visit(SomeValueSnak snak) {
		// TODO
		return null;
	}

	@Override
	public Void visit(NoValueSnak snak) {
		// TODO
		return null;
	}

}
