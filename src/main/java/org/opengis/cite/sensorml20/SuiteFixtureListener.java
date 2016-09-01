package org.opengis.cite.sensorml20;

import com.sun.jersey.api.client.Client;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import javax.xml.stream.XMLStreamException;
import javax.xml.transform.stream.StreamSource;

import org.opengis.cite.sensorml20.SuiteAttribute;
import org.opengis.cite.sensorml20.util.ValidationUtils;
import org.opengis.cite.sensorml20.TestRunArg;
import org.opengis.cite.sensorml20.util.ClientUtils;
import org.opengis.cite.sensorml20.util.XMLUtils;
import org.opengis.cite.sensorml20.util.TestSuiteLogger;
import org.opengis.cite.sensorml20.util.URIUtils;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.w3c.dom.Document;

/**
 * A listener that performs various tasks before and after a test suite is run,
 * usually concerned with maintaining a shared test suite fixture. Since this
 * listener is loaded using the ServiceLoader mechanism, its methods will be
 * called before those of other suite listeners listed in the test suite
 * definition and before any annotated configuration methods.
 *
 * Attributes set on an ISuite instance are not inherited by constituent test
 * group contexts (ITestContext). However, suite attributes are still accessible
 * from lower contexts.
 *
 * @see org.testng.ISuite ISuite interface
 */
public class SuiteFixtureListener implements ISuiteListener {

    @Override
    public void onStart(ISuite suite) {
        processIUTParameter(suite);
        processXmlReference(suite);
        processSchematronSchema(suite);
        registerClientComponent(suite);
    }

    @Override
    public void onFinish(ISuite suite) {
    }

    /**
     * Processes test suite arguments and sets suite attributes accordingly. The
     * entity referenced by the {@link TestRunArg#IUT iut} argument is parsed
     * and the resulting Document is set as the value of the "testSubject"
     * attribute.
     * 
     * @param suite
     *            An ISuite object representing a TestNG test suite.
     */
    void processIUTParameter(ISuite suite) {
    	Map<String, String> params = suite.getXmlSuite().getParameters();
		String iutRef = params.get(TestRunArg.IUT.toString());
		if (null == iutRef || iutRef.isEmpty()) {
			return;
		}
		try {
			File iutFile = URIUtils.resolveURIAsFile(URI.create(iutRef));
			if (XMLUtils.isXMLSchema(iutFile)) {
				params.put(TestRunArg.XSD.toString(), iutRef);
			} else {
				params.put(TestRunArg.XML.toString(), iutRef);				
			}
		} catch (Exception x) {
			throw new RuntimeException(
					"Failed to read resource from " + iutRef, x);
		}
		params.remove(TestRunArg.IUT.toString());
    }

    /**
	 * Extracts schema references from the XML resource identified by the
	 * supplied test run argument. If this is a XML instance document, the value
	 * of the standard xsi:schemaLocation attribute is used to locate the
	 * application schema(s). The schema references are added as the suite
	 * attribute {@link SuiteAttribute#SCHEMA_LOC_SET SCHEMA_LOC_SET} (of type
	 * Set&lt;URI&gt;).
	 * 
	 * @param suite
	 *            An ISuite object representing a TestNG test suite.
	 */
	void processXmlReference(ISuite suite) {
		Map<String, String> params = suite.getXmlSuite().getParameters();
		TestSuiteLogger.log(Level.CONFIG,
				String.format("Suite parameters:\n %s", params));
		Set<URI> schemaURIs = new HashSet<URI>();
		String xsdURI = params.get(TestRunArg.XSD.toString());
		if (null != xsdURI && !xsdURI.isEmpty()) {
			// was submitted as iut argument value via POST
			schemaURIs.add(URI.create(xsdURI));
			suite.setAttribute(SuiteAttribute.SCHEMA_LOC_SET.getName(),
					schemaURIs);
			return;
		}
		String xmlURI = params.get(TestRunArg.XML.toString());
		if (null == xmlURI || xmlURI.isEmpty()) {
			throw new IllegalArgumentException(
					"Missing XML resource (document or application schema).");
		}
		File xmlFile = null;
		try {
			xmlFile = URIUtils.resolveURIAsFile(URI.create(xmlURI));
			if (null == xmlFile || !xmlFile.exists()) {
				throw new IllegalArgumentException(
						"Failed to dereference URI: " + xmlURI);
			}
			if (XMLUtils.isXMLSchema(xmlFile)) {
				params.put(TestRunArg.XSD.toString(), xmlURI);
				schemaURIs.add(URI.create(xmlURI));
			} else {
				schemaURIs.addAll(ValidationUtils.extractSchemaReferences(
						new StreamSource(xmlFile), xmlURI));
				suite.setAttribute(SuiteAttribute.XML.getName(), xmlFile);
				
				Document iutDoc = null;
		        try {
		            iutDoc = URIUtils.parseURI(xmlFile.toURI());
		        } catch (Exception x) {
		            throw new RuntimeException("Failed to parse resource retrieved from "
		                    + xmlURI, x);
		        }
		        suite.setAttribute(SuiteAttribute.TEST_SUBJECT.getName(), iutDoc);
		        TestSuiteLogger.log(Level.FINE, "Wrote XML document to "
						+ xmlFile.getAbsolutePath());
			}
		} catch (IOException iox) {
			throw new RuntimeException("Failed to read resource obtained from "
					+ xmlURI, iox);
		} catch (XMLStreamException xse) {
			throw new RuntimeException(
					"Failed to find schema reference in source: "
							+ xmlFile.getAbsolutePath(), xse);
		}
		suite.setAttribute(SuiteAttribute.SCHEMA_LOC_SET.getName(), schemaURIs);
		TestSuiteLogger.log(Level.FINE,
				String.format("Schema references: %s", schemaURIs));
	}
	/**
	 * Adds a URI reference specifying the location of a Schematron schema.
	 * 
	 * @param suite
	 *            An ISuite object representing a TestNG test suite.
	 */
	void processSchematronSchema(ISuite suite) {
		Map<String, String> params = suite.getXmlSuite().getParameters();
		String schRef = params.get(TestRunArg.SCH.toString());
		if ((schRef != null) && !schRef.isEmpty()) {
			URI schURI = URI.create(params.get(TestRunArg.SCH.toString()));
			suite.setAttribute(SuiteAttribute.SCHEMATRON.getName(), schURI);
		}
	}
    /**
     * A client component is added to the suite fixture as the value of the
     * {@link SuiteAttribute#CLIENT} attribute; it may be subsequently accessed
     * via the {@link org.testng.ITestContext#getSuite()} method.
     *
     * @param suite The test suite instance.
     */
    void registerClientComponent(ISuite suite) {
        Client client = ClientUtils.buildClient();
        if (null != client) {
            suite.setAttribute(SuiteAttribute.CLIENT.getName(), client);
        }
    }
}
