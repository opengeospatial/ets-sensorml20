package org.opengis.cite.sensorml20.level1;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import org.opengis.cite.sensorml20.BaseFixture;
import org.opengis.cite.sensorml20.ETSAssert;
import org.opengis.cite.sensorml20.util.DocumentTools;
import org.opengis.cite.sensorml20.util.ValidationUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * <p>
 * CoreConcepts class.
 * </p>
 *
 */
public class CoreConcepts extends BaseFixture {

	/**
	 * <p>
	 * CoreConceptUsed.
	 * </p>
	 * @throws org.xml.sax.SAXException if any.
	 * @throws java.io.IOException if any.
	 * @throws java.net.URISyntaxException if any.
	 */
	@Test(description = "A.1.1 - Requirement 1", groups = "CoreConcepts", priority = 1)
	public void CoreConceptUsed() throws SAXException, IOException, URISyntaxException {
		DocumentTools.MergeReference(this.testSubject, this.testSubjectUri);

		Schema schema = null;
		Node documentElement = this.testSubject.getDocumentElement();
		String documentName = documentElement.getNodeName();
		if (documentName.equals("sml:SimpleProcess")) {
			schema = ValidationUtils.CreateSchema("simple_process.xsd", null);
		}
		else if (documentName.equals("sml:PhysicalComponent")) {
			schema = ValidationUtils.CreateSchema("physical_component.xsd", null);
		}
		else if (documentName.equals("sml:AggregateProcess")) {
			schema = ValidationUtils.CreateSchema("aggregate_process.xsd", null);
		}
		else if (documentName.equals("sml:PhysicalSystem")) {
			schema = ValidationUtils.CreateSchema("physical_system.xsd", null);
		}
		Validator validator = schema.newValidator();
		Source source = new DOMSource(this.testSubject);
		ETSAssert.assertSchemaValid(validator, source);
	}

	/**
	 * <p>
	 * CoreConceptProcesses.
	 * </p>
	 */
	@Test(description = "A.1.2 - Requirement 2", groups = "CoreConcepts", priority = 2)
	public void CoreConceptProcesses() {
		ArrayList<String> result = new ArrayList<String>();

		if (this.testSubject.getElementsByTagName("sml:inputs").getLength() == 0) {
			result.add("Core Model Must Define Inputs");
		}

		if (this.testSubject.getElementsByTagName("sml:outputs").getLength() == 0) {
			result.add("Core Model Must Define Outputs");
		}

		if (this.testSubject.getElementsByTagName("sml:parameters").getLength() == 0) {
			result.add("Core Model Must Define Parameters");
		}

		String documentName = this.testSubject.getDocumentElement().getNodeName();
		if (documentName == "sml:AggregateProcess") {
			String process = documentName.replace("sml:", "");
			if (this.testSubject.getElementsByTagName("sml:connections").getLength() == 0) {
				result.add(process + " Must Define Methodology");
			}
		}

		Assert.assertTrue(result.size() == 0, GetArrayToString(result));
	}

	private String GetArrayToString(ArrayList<String> ary) {
		String ret = "";
		for (String str : ary) {
			ret += str + "\n";
		}
		return ret;
	}

	/**
	 * <p>
	 * CoreConceptUniqueId.
	 * </p>
	 */
	@Test(description = "A.1.3 - Requirement 3", groups = "CoreConcepts", priority = 3)
	public void CoreConceptUniqueId() {
		NodeList identifierList = this.testSubject.getDocumentElement().getElementsByTagName("gml:identifier");
		int listLength = identifierList.getLength();
		if (listLength == 0) {
			throw new AssertionError("Identifier Not Found !!");
		}
		else if (listLength > 1) {
			ArrayList<String> idList = new ArrayList<String>();
			for (int i = 0; i < listLength; i++) {
				Node item = identifierList.item(i);
				String id = item.getTextContent();
				if (idList.contains(id)) {
					throw new AssertionError("ID is not unique !!");
				}
				else {
					idList.add(id);
				}
			}
		}
	}

	/**
	 * <p>
	 * CoreConceptMetadata.
	 * </p>
	 */
	@Test(description = "A.1.4 - Requirement 4", priority = 4)
	public void CoreConceptMetadata() {
		// check element identification is included
		NodeList idList = this.testSubject.getDocumentElement().getElementsByTagName("sml:identification");
		boolean isIdIncluded = false;
		if (idList.getLength() != 0) {
			isIdIncluded = true;
		}
		Assert.assertTrue(isIdIncluded, "Process Shall include identification metadata element ");

		// check to meet one of the discovery
		boolean isAnyDiscoveryGroup = false;
		String[] metadataName = new String[] { "sml:keywords", "sml:classification" };
		for (String str : metadataName) {
			NodeList list = this.testSubject.getDocumentElement().getElementsByTagName(str);
			if (list.getLength() != 0) {
				isAnyDiscoveryGroup = true;
				break;
			}
		}
		Assert.assertTrue(isAnyDiscoveryGroup,
				"Process Shall include discovery metadata (one of the following elements: keywords, classification) ");

		// check to meet one of the qualifications
		metadataName = new String[] { "sml:characteristics", "sml:capabilities" };
		boolean isAnyQualificationGroup = false;
		for (String str : metadataName) {
			NodeList list = this.testSubject.getDocumentElement().getElementsByTagName(str);

			if (list.getLength() != 0) {
				isAnyQualificationGroup = true;
				break;
			}
		}
		Assert.assertTrue(isAnyQualificationGroup,
				"Process Shall include qualification metadata (one of the following elements: characteristics, capabilities) ");

	}

	/**
	 * <p>
	 * CoreConceptExecution.
	 * </p>
	 */
	@Test(description = "A.1.5 - Requirement 5", groups = "CoreConcepts", priority = 5)
	public void CoreConceptExecution() {
		ArrayList<String> result = new ArrayList<String>();

		if (this.testSubject.getDocumentElement().getElementsByTagName("sml:input").getLength() == 0) {
			result.add("Core Model Must Define Inputs");
		}
		if (this.testSubject.getDocumentElement().getElementsByTagName("sml:output").getLength() == 0) {
			result.add("Core Model Must Define Output");
		}
		if (this.testSubject.getDocumentElement().getElementsByTagName("sml:parameter").getLength() == 0) {
			result.add("Core Model Must Define Parameter");
		}
		if (this.testSubject.getDocumentElement().getElementsByTagName("sml:method").getLength() == 0) {
			// result.add("Core Model Must Define Method");
		}
		Assert.assertTrue(result.size() == 0, GetArrayToString(result));

		// 必須檢查input . output . parameter . method這四個Tag --2016/05/17

		// 檢查Description(3-30又覺得不太像是這樣做)
		/*
		 * if(this.testSubject.getElementsByTagName("gml:description").getLength() == 0) {
		 * throw new AssertionError("Description undefined !!"); }
		 */
	}

}
