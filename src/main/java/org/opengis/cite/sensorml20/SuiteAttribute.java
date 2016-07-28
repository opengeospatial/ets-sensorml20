package org.opengis.cite.sensorml20;

import com.sun.jersey.api.client.Client;

import java.io.File;
import java.net.URI;
import java.util.Set;

import org.apache.xerces.xs.XSModel;
import org.w3c.dom.Document;

/**
 * An enumerated type defining ISuite attributes that may be set to constitute a
 * shared test fixture.
 */
@SuppressWarnings("rawtypes")
public enum SuiteAttribute {

	/**
     * A client component for interacting with HTTP endpoints.
     */
    CLIENT("httpClient", Client.class),
    /**
     * A {@code Set<URI>} specifying the locations of XML Schema grammars.
     */
    SCHEMA_LOC_SET("schema-loc-set", Set.class),
    /**
     * Contains the XML Schema components comprising an application schema.
     */
    XSMODEL("xsmodel", XSModel.class),
    /**
     * A File containing SensorML data.
     */
    XML("xml-data", File.class),
    /**
     * An absolute URI referring to a Schematron schema.
     */
    SCHEMATRON("schematron", URI.class),
    /**
     * A DOM Document representation of the test subject or metadata about it.
     */
    TEST_SUBJECT("testSubject", Document.class);
	
    private final Class attrType;
    private final String attrName;

    private SuiteAttribute(String attrName, Class attrType) {
        this.attrName = attrName;
        this.attrType = attrType;
    }

    public Class getType() {
        return attrType;
    }

    public String getName() {
        return attrName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(attrName);
        sb.append('(').append(attrType.getName()).append(')');
        return sb.toString();
    }
}
