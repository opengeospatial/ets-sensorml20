package org.opengis.cite.sensorml20.level2;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;

import org.opengis.cite.sensorml20.level1.DataFixture;
import org.opengis.cite.sensorml20.ETSAssert;
import org.opengis.cite.sensorml20.ErrorMessage;
import org.opengis.cite.sensorml20.ErrorMessageKeys;
import org.opengis.cite.sensorml20.Namespaces;
import org.opengis.cite.sensorml20.SuiteAttribute;
import org.opengis.cite.sensorml20.TestRunArg;
import org.opengis.cite.sensorml20.util.XMLUtils;
import org.opengis.cite.sensorml20.util.TestSuiteLogger;
import org.opengis.cite.validation.SchematronValidator;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

/**
 * Verifies that a SensorML instance document adheres to the constraints defined in
 * Schematron schemas. An application-specific schema may be associated with the
 * instance document by either of the following means:
 * 
 * <ol>
 * <li>Specify the schema location using the <code>xml-model</code> processing
 * instruction, where the value of the "schematypens" data item is the name of
 * the Schematron namespace (see sample listing below).</li>
 * <li>Specify the schema location as the value of the {@link TestRunArg#SCH
 * sch} test run argument;</li>
 * 
 * </ol>
 * <p>
 * <strong>Using the xml-model PI to refer to a Schematron schema</strong>
 * </p>
 * 
 * <pre>
 * {@code
 * <?xml version="1.0" encoding="UTF-8"?>
 * <?xml-model href="http://example.org/constraints.sch" 
 *             schematypens="http://purl.oclc.org/dsdl/schematron" 
 *             phase="#ALL"?>
 * }
 * </pre>
 * 
 * The processing instruction takes precedence if multiple schema references are
 * found.
 * 
 * <p style="margin-bottom: 0.5em">
 * <strong>Sources</strong>
 * </p>
 * <ul>
 * <li><a href=
 * "http://standards.iso.org/ittf/PubliclyAvailableStandards/c040833_ISO_IEC_19757-3_2006(E).zip"
 * >ISO 19757-3:2006</a></li>
 * <li><a href="http://www.w3.org/TR/xml-model/">Associating Schemas with XML
 * documents 1.0 (Second Edition)</a></li>
 * </ul>
 * 
 */
public class SchematronTests extends DataFixture {

	private SchematronValidator dataValidator;

	/**
	 * Attempts to construct a Schematron validator from a schema reference
	 * given in (a) the SML data file, or (b) a test run argument (in the ISuite
	 * context).
	 * 
	 * @param testContext
	 *            The test set context.
	 */
	@BeforeClass
	public void createSchematronValidator(ITestContext testContext) {
		Map<String, String> piData = getXmlModelPIData(this.dataFile);
		String phase = "#ALL";
		URI schematronURI;
		Source schema = null;
		//schematron URI in PI
		if (isSchematronReference(piData)) {
			schematronURI = URI.create(piData.get("href"));
			if (!schematronURI.isAbsolute()) {
				// resolve relative URI against location of GML data
				String dataURI = testContext.getSuite().getParameter(
						TestRunArg.XML.toString());
				URI baseURI = URI.create(dataURI);
				schematronURI = baseURI.resolve(schematronURI);
			}
			schema = new StreamSource(schematronURI.toString());
			if (piData.containsKey("phase"))
				phase = piData.get("phase");
		}
		// look for suite attribute (test run argument)
		else { 
			Set<String> suiteAttrs = testContext.getSuite().getAttributeNames();
			if (suiteAttrs.contains(SuiteAttribute.SCHEMATRON.getName())) {
				schematronURI = (URI) testContext.getSuite().getAttribute(
						SuiteAttribute.SCHEMATRON.getName());
				schema = new StreamSource(schematronURI.toString());
			}
		}
		if (null != schema) {
			try {
				this.dataValidator = new SchematronValidator(schema, phase);
			} catch (Exception e) {
				Logger.getLogger(getClass()).warn(
						"Failed to create SchematronValidator.\n", e);
			}
		}
	}

	/**
	 * [{@code Test}] Verifies that a SensorML instance satisfies the additional
	 * Schematron constraints specified in the conformance classes of Core.
	 * 
	 * <p style="margin-bottom: 0.5em">
	 * <strong>Sources</strong>
	 * </p>
	 * <ul>
	 * <li><a
	 * href="http://schemas.opengis.net/sensorML/2.0/core.sch">
	 * Schematron constraints for conformance classes of Core in SensorML 2.0</a></li>
	 * </ul>
	 */
	@Test(description = "Checks Core Schematron rules")
	public void checkCoreConstraints() {
		URL schRef = this.getClass().getResource(
				"/org/opengis/cite/sensorml20/sch/core.sch");
		ETSAssert
				.assertSchematronValid(schRef, new StreamSource(this.dataFile));
	}
	/**
	 * [{@code Test}] Verifies that a SensorML instance satisfies the additional
	 * Schematron constraints specified in the conformance classes of SimpleProcess.
	 * 
	 * <p style="margin-bottom: 0.5em">
	 * <strong>Sources</strong>
	 * </p>
	 * <ul>
	 * <li><a
	 * href="http://schemas.opengis.net/sensorML/2.0/simple_process.sch">
	 * Schematron constraints for conformance classes of Core in SensorML 2.0</a></li>
	 * </ul>
	 */
	@Test(description = "Checks SimpleProcess Schematron rules")
	public void checkSimpleProcessConstraints() {
		URL schRef = this.getClass().getResource(
				"/org/opengis/cite/sensorml20/sch/simple_process.sch");
		ETSAssert
				.assertSchematronValid(schRef, new StreamSource(this.dataFile));
	}
	/**
	 * [{@code Test}] Verifies that a SensorML instance satisfies the additional
	 * Schematron constraints specified in the conformance classes of AggregateProcess.
	 * 
	 * <p style="margin-bottom: 0.5em">
	 * <strong>Sources</strong>
	 * </p>
	 * <ul>
	 * <li><a
	 * href="http://schemas.opengis.net/sensorML/2.0/aggregate_process.sch">
	 * Schematron constraints for conformance classes of Core in SensorML 2.0</a></li>
	 * </ul>
	 */
	@Test(description = "Checks SimpleProcess Schematron rules")
	public void checkAggregateProcessConstraints() {
		URL schRef = this.getClass().getResource(
				"/org/opengis/cite/sensorml20/sch/aggregate_process.sch");
		ETSAssert
				.assertSchematronValid(schRef, new StreamSource(this.dataFile));
	}
	/**
	 * [{@code Test}] Verifies that a SensorML instance satisfies the additional
	 * Schematron constraints specified in the conformance classes of PhysicalComponent.
	 * 
	 * <p style="margin-bottom: 0.5em">
	 * <strong>Sources</strong>
	 * </p>
	 * <ul>
	 * <li><a
	 * href="http://schemas.opengis.net/sensorML/2.0/physical_component.sch">
	 * Schematron constraints for conformance classes of Core in SensorML 2.0</a></li>
	 * </ul>
	 */
	@Test(description = "Checks PhysicalComponent Schematron rules")
	public void checkPhysicalComponentConstraints() {
		URL schRef = this.getClass().getResource(
				"/org/opengis/cite/sensorml20/sch/physical_component.sch");
		ETSAssert
				.assertSchematronValid(schRef, new StreamSource(this.dataFile));
	}
	/**
	 * [{@code Test}] Verifies that a SensorML instance satisfies the additional
	 * Schematron constraints specified in the conformance classes of PhysicalComponent.
	 * 
	 * <p style="margin-bottom: 0.5em">
	 * <strong>Sources</strong>
	 * </p>
	 * <ul>
	 * <li><a
	 * href="http://schemas.opengis.net/sensorML/2.0/physical_system.sch">
	 * Schematron constraints for conformance classes of Core in SensorML 2.0</a></li>
	 * </ul>
	 */
	@Test(description = "Checks PhysicalSystem Schematron rules")
	public void checkPhysicalSystemConstraints() {
		URL schRef = this.getClass().getResource(
				"/org/opengis/cite/sensorml20/sch/physical_system.sch");
		ETSAssert
				.assertSchematronValid(schRef, new StreamSource(this.dataFile));
	}
	/**
	 * [{@code Test}] Verifies that a GML instance satisfies the additional
	 * Schematron constraints specified in ISO 19136.
	 * 
	 * <p style="margin-bottom: 0.5em">
	 * <strong>Sources</strong>
	 * </p>
	 * <ul>
	 * <li><a
	 * href="http://schemas.opengis.net/gml/3.2.1/SchematronConstraints.xml">
	 * Schematron constraints for ISO 19136</a></li>
	 * </ul>
	 */
	@Test(description = "Checks general Schematron rules specified in ISO 19136")
	public void checkGMLSchematronConstraints() {
		URL schRef = this.getClass().getResource(
				"/org/opengis/cite/iso19136/sch/gml-3.2.1.sch");
		ETSAssert
				.assertSchematronValid(schRef, new StreamSource(this.dataFile));
	}	

	/**
	 * [{@code Test}] Validates a XML document against a set of Schematron
	 * constraints associated with it using either the {@code xml-model}
	 * processing instruction or the {@code sch} test run argument.
	 */
	@Test(description = "Checks application-specific Schematron rules")
	public void checkSchematronConstraints() {
		if (null == this.dataValidator) {
			throw new SkipException("Schematron schema reference not found.");
		}
		Source xmlSource = new StreamSource(this.dataFile);
		DOMResult result = dataValidator.validate(xmlSource);
		Assert.assertFalse(dataValidator.ruleViolationsDetected(), ErrorMessage
				.format(ErrorMessageKeys.NOT_SCHEMA_VALID,
						dataValidator.getRuleViolationCount(),
						XMLUtils.writeNodeToString(result.getNode())));
	}

	/**
	 * Indicates whether or not the given PI data includes a Schematron schema
	 * reference.
	 * 
	 * @param piData
	 *            A Map containing PI data (pseudo-attributes).
	 * @return {@code true} if the "schematypens" pseudo-attribute has the value
	 *         otherwise;
	 */
	boolean isSchematronReference(Map<String, String> piData) {
		if (null != piData && null != piData.get("schematypens")) {
			return piData.get("schematypens").equals(Namespaces.SCH);
		}
		return false;
	}

	/**
	 * Extracts the data items from the {@code xml-model} processing
	 * instruction. The PI must appear before the document element.
	 * 
	 * @param dataFile
	 *            A File containing the GML instance.
	 * @return A Map containing the supplied pseudo-attributes, or {@code null}
	 *         if the PI is not present.
	 */
	Map<String, String> getXmlModelPIData(File dataFile) {
		Map<String, String> piData = null;
		XMLStreamReader reader = null;
		FileInputStream input = null;
		try {
			input = new FileInputStream(dataFile);
			XMLInputFactory factory = XMLInputFactory.newInstance();
			reader = factory.createXMLStreamReader(input);
			int event = reader.getEventType();
			// Now in START_DOCUMENT state. Stop at document element.
			while (event != XMLStreamReader.START_ELEMENT) {
				event = reader.next();
				if (event == XMLStreamReader.PROCESSING_INSTRUCTION) {
					if (reader.getPITarget().equals("xml-model")) {
						String[] pseudoAttrs = reader.getPIData().split("\\s+");
						piData = new HashMap<String, String>();
						for (String pseudoAttr : pseudoAttrs) {
							String[] nv = pseudoAttr.split("=");
							piData.put(nv[0].trim(), nv[1].replace('"', ' ')
									.trim());
						}
						break;
					}
				}
			}
		} catch (Exception e) {
			TestSuiteLogger.log(Level.WARNING, "Failed to parse document at "
					+ dataFile.getAbsolutePath(), e);
			return null; // not an XML document
		} finally {
			try {
				if (null != reader)
					reader.close();
				if (null != input)
					input.close();
			} catch (Exception x) {
				TestSuiteLogger.log(Level.INFO, x.getMessage());
			}
		}
		return piData;
	}

}

