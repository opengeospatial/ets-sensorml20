package org.opengis.cite.sensorml20.level1;

import java.util.ArrayList;

import org.opengis.cite.sensorml20.BaseFixture;
import org.opengis.cite.sensorml20.util.DocumentTools;
import org.testng.SkipException;
import org.testng.annotations.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <p>
 * ConfigurableProcesses class.
 * </p>
 *
 */
public class ConfigurableProcesses extends BaseFixture {

	/**
	 * <p>
	 * DependencyCore.
	 * </p>
	 */
	@Test(description = "A.8.1 - Requirement 38", groups = "ConfigurableProcesses",
			dependsOnGroups = { "CoreAbstractProcess" })
	public void DependencyCore() {
		Boolean configurableNessary = true;
		NodeList configurationList = this.testSubject.getDocumentElement().getElementsByTagName("sml:configuration");
		if (configurationList.getLength() == 0) {
			configurableNessary = false;
		}
		NodeList typeofList = this.testSubject.getDocumentElement().getElementsByTagName("sml:typeOf");
		if (typeofList.getLength() == 0) {
			configurableNessary = false;
		}

		Boolean configurableOptional = false;

		int valueCount = 0;
		int allowValueCount = 0;
		NodeList parametersList = this.testSubject.getDocumentElement().getElementsByTagName("sml:parameters");
		for (int parametersCount = 0; parametersCount < parametersList.getLength(); parametersCount++) {
			Element parametersNode = (Element) parametersList.item(parametersCount);
			ArrayList<Node> valueNodes = DocumentTools.GetElementByLocalName(parametersNode, "value");
			valueCount += valueNodes.size();

			NodeList allowValuesNodes = parametersNode.getElementsByTagName("swe:AllowedValues");
			allowValueCount += allowValuesNodes.getLength();
		}

		// Optional Test A
		if (parametersList.getLength() > 0 && valueCount == 0) {
			configurableOptional = true;
		}
		// Optional Test B
		if (parametersList.getLength() > 0 && allowValueCount > 0) {
			configurableOptional = true;
		}
		// Optional Test C
		ArrayList<String> refTurnName = new ArrayList<String>();

		NodeList configurationNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:configuration");
		for (int configurationCount = 0; configurationCount < configurationNodes.getLength(); configurationCount++) {
			Element configurationNode = (Element) configurationNodes.item(configurationCount);
			NodeList setModeTemp = configurationNode.getElementsByTagName("sml:setMode");
			for (int tempModeCount = 0; tempModeCount < setModeTemp.getLength(); tempModeCount++) {
				refTurnName.add(setModeTemp.item(tempModeCount).getTextContent());
			}
		}

		if (refTurnName.size() > 0) {
			ArrayList<String> targetTurnName = new ArrayList<String>();
			NodeList modesNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:modes");
			for (int modesCount = 0; modesCount < modesNodes.getLength(); modesCount++) {
				Element modesNode = (Element) modesNodes.item(modesCount);

				NodeList modeList = modesNode.getElementsByTagName("sml:Mode");

				for (int modeCount = 0; modeCount < modeList.getLength(); modeCount++) {
					Element mode = (Element) modeList.item(modeCount);
					String gmlId = mode.getAttribute("gml:id");
					if (gmlId != null && !gmlId.trim().isEmpty()) {
						targetTurnName.add(gmlId);
					}
				}
			}

			Boolean refTurnCkeck = true;
			for (int refCount = 0; refCount < refTurnName.size(); refCount++) {
				if (!targetTurnName.contains(refTurnName.get(refCount))) {
					refTurnCkeck = false;
				}
			}
			if (refTurnCkeck) {
				configurableOptional = true;
			}
		}
		// Optional Test D

		if (!(configurableNessary && configurableOptional)) {
			throw new SkipException("Not a Configurable Process");
		}
	}

	/**
	 * <p>
	 * PackageFullyImplemented.
	 * </p>
	 */
	@Test(description = "A.8.2 - Requirement 39", groups = "ConfigurableProcesses",
			dependsOnMethods = { "DependencyCore", "TwoModesRequired", "SettingsProperty", "SetValueRestriction",
					"SetArrayValueRestriction", "SetConstraintRestriction" })
	public void PackageFullyImplemented() {
		// Dependency All ConfigurableProcesses Tests
	}

	/**
	 * <p>
	 * TwoModesRequired.
	 * </p>
	 */
	@Test(description = "A.8.3 - Requirement 40", groups = "ConfigurableProcesses",
			dependsOnMethods = { "DependencyCore" })
	public void TwoModesRequired() {
		NodeList modeChoiceNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:ModeChoice");

		for (int choiceCount = 0; choiceCount < modeChoiceNodes.getLength(); choiceCount++) {
			Element modeChoice = (Element) modeChoiceNodes.item(choiceCount);
			NodeList modeNodes = modeChoice.getElementsByTagName("sml:Mode");
			if (modeNodes.getLength() < 2) {
				throw new AssertionError("requires two or more Mode !!");
			}
		}
	}

	/**
	 * <p>
	 * SettingsProperty.
	 * </p>
	 */
	@Test(description = "A.8.4 - Requirement 41", groups = "ConfigurableProcesses",
			dependsOnMethods = { "DependencyCore" })
	public void SettingsProperty() {
		NodeList configurationNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:configuration");
		if (configurationNodes.getLength() > 0) {
			for (int configurationCount = 0; configurationCount < configurationNodes
				.getLength(); configurationCount++) {
				Element configurationNode = (Element) configurationNodes.item(configurationCount);
				NodeList modeNodes = configurationNode.getElementsByTagName("sml:Settings");
				if (modeNodes.getLength() < 1) {
					throw new AssertionError("configuration need inclued Settings");
				}
			}
		}
		else {
			throw new AssertionError("Shall inclued configuration property");
		}
	}

	/**
	 * <p>
	 * SetValueRestriction.
	 * </p>
	 */
	@Test(description = "A.8.5 - Requirement 42", groups = "ConfigurableProcesses",
			dependsOnMethods = { "DependencyCore" })
	public void SetValueRestriction() {
		NodeList setValueNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:setValue");

		if (setValueNodes.getLength() > 0) {
			ArrayList<String> parameterFields = new ArrayList<String>();
			NodeList parametersNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:parameters");
			for (int parametersCount = 0; parametersCount < parametersNodes.getLength(); parametersCount++) {
				Element parameters = (Element) parametersNodes.item(parametersCount);
				NodeList fieldNodes = parameters.getElementsByTagName("swe:field");
				for (int fieldCount = 0; fieldCount < fieldNodes.getLength(); fieldCount++) {
					Element field = (Element) fieldNodes.item(fieldCount);
					String name = field.getAttribute("name");
					if (name != null && !name.trim().isEmpty()) {
						parameterFields.add(name);
					}
				}
			}

			for (int setValueCount = 0; setValueCount < setValueNodes.getLength(); setValueCount++) {
				Element setValueNode = (Element) setValueNodes.item(setValueCount);
				String setValueRef = setValueNode.getAttribute("ref");
				if (setValueRef != null && !setValueRef.trim().isEmpty()) {
					String[] refSplit = setValueRef.split("/");
					String targetField = "";
					if (refSplit.length > 0) {
						targetField = refSplit[refSplit.length - 1];
					}

					if (!parameterFields.contains(targetField)) {
						throw new AssertionError("value not defined with parameters");
					}
				}
			}
		}
	}

	/**
	 * <p>
	 * SetArrayValueRestriction.
	 * </p>
	 */
	@Test(description = "A.8.6 - Requirement 43", groups = "ConfigurableProcesses",
			dependsOnMethods = { "DependencyCore" })
	public void SetArrayValueRestriction() {
		NodeList setArrayValuesNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:setArrayValues");

		if (setArrayValuesNodes.getLength() > 0) {
			ArrayList<String> parameterFields = new ArrayList<String>();
			NodeList parametersNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:parameters");
			for (int parametersCount = 0; parametersCount < parametersNodes.getLength(); parametersCount++) {
				Element parameters = (Element) parametersNodes.item(parametersCount);
				NodeList dataArrayNodes = parameters.getElementsByTagName("swe:DataArray");

				for (int arrayCount = 0; arrayCount < dataArrayNodes.getLength(); arrayCount++) {
					Element arrayNode = (Element) dataArrayNodes.item(arrayCount);

					NodeList fieldNodes = arrayNode.getElementsByTagName("swe:field");
					for (int fieldCount = 0; fieldCount < fieldNodes.getLength(); fieldCount++) {
						Element field = (Element) fieldNodes.item(fieldCount);
						String name = field.getAttribute("name");
						if (name != null && !name.trim().isEmpty()) {
							parameterFields.add(name);
						}
					}
				}
			}

			for (int setArrayValuesCount = 0; setArrayValuesCount < setArrayValuesNodes
				.getLength(); setArrayValuesCount++) {
				Element setArrayValuesNode = (Element) setArrayValuesNodes.item(setArrayValuesCount);
				String setValueRef = setArrayValuesNode.getAttribute("ref");
				if (setValueRef != null && !setValueRef.trim().isEmpty()) {
					String[] refSplit = setValueRef.split("/");
					String targetField = "";
					if (refSplit.length > 0) {
						targetField = refSplit[refSplit.length - 1];
					}

					if (!parameterFields.contains(targetField)) {
						throw new AssertionError("array value not defined with parameters");
					}
				}
			}
		}
	}

	/**
	 * <p>
	 * SetConstraintRestriction.
	 * </p>
	 */
	@Test(description = "A.8.7 - Requirement 44", groups = "ConfigurableProcesses",
			dependsOnMethods = { "DependencyCore" })
	public void SetConstraintRestriction() {
		NodeList setConstraintNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:setConstraint");

		if (setConstraintNodes.getLength() > 0) {
			ArrayList<String> parameterFields = new ArrayList<String>();
			NodeList parametersNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:parameters");
			for (int parametersCount = 0; parametersCount < parametersNodes.getLength(); parametersCount++) {
				Element parameters = (Element) parametersNodes.item(parametersCount);
				NodeList fieldNodes = parameters.getElementsByTagName("swe:field");
				for (int fieldCount = 0; fieldCount < fieldNodes.getLength(); fieldCount++) {
					Element field = (Element) fieldNodes.item(fieldCount);
					String name = field.getAttribute("name");
					if (name != null && !name.trim().isEmpty()) {
						parameterFields.add(name);
					}
				}
			}

			for (int setConstraintCount = 0; setConstraintCount < setConstraintNodes
				.getLength(); setConstraintCount++) {
				Element setConstraintNode = (Element) setConstraintNodes.item(setConstraintCount);
				String setValueRef = setConstraintNode.getAttribute("ref");
				if (setValueRef != null && !setValueRef.trim().isEmpty()) {
					String[] refSplit = setValueRef.split("/");
					String targetField = "";
					if (refSplit.length > 0) {
						targetField = refSplit[refSplit.length - 1];
					}

					if (!parameterFields.contains(targetField)) {
						throw new AssertionError("setConstraint value not defined with parameters");
					}
				}
			}
		}
	}

}
