package org.opengis.cite.sensorml20.level1;

import org.testng.Assert;
import org.testng.SkipException;
import org.opengis.cite.sensorml20.BaseFixture;
import org.testng.annotations.Test;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * <p>
 * PhysicalComponent class.
 * </p>
 *
 */
public class PhysicalComponent extends BaseFixture {

	/**
	 * <p>
	 * PackageFullyImplemented.
	 * </p>
	 */
	@Test(description = "A.5.1 - Requirement 26", groups = "PhysicalComponent", dependsOnMethods = { "DependencyCore",
			"ByPointOrLocation", "ByPostion", "ByTrajectory", "ByProcess", "Definition" })
	public void PackageFullyImplemented() {
		// Dependency All PhysicalComponent Tests
	}

	/**
	 * <p>
	 * DependencyCore.
	 * </p>
	 */
	@Test(description = "A.5.2 - Requirement 27", groups = "PhysicalComponent",
			dependsOnGroups = { "CoreAbstractProcess" })
	public void DependencyCore() {
		String rootName = this.testSubject.getDocumentElement().getNodeName();

		if (!rootName.equals("sml:PhysicalComponent")) {
			throw new SkipException("Not a PhysicalComponent Process");
		}
	}

	private Boolean ValidateSpatialRule(Element node) {
		String[] ruleNames = new String[] { "swe:Text", "gml:Point", "swe:Vector", "swe:DataRecord", "swe:DataArray",
				"sml:AbstractProcess" };
		for (int ruleCount = 0; ruleCount < ruleNames.length; ruleCount++) {
			String ruleName = ruleNames[ruleCount];
			NodeList list = node.getElementsByTagName(ruleName);
			if (list.getLength() > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <p>
	 * ByPointOrLocation.
	 * </p>
	 */
	@Test(description = "A.5.3 - Requirement 28", groups = "PhysicalComponent", dependsOnMethods = { "DependencyCore" })
	public void ByPointOrLocation() {
		NodeList positionList = this.testSubject.getDocumentElement().getElementsByTagName("sml:position");

		for (int positionCount = 0; positionCount < positionList.getLength(); positionCount++) {
			Element positionNode = (Element) positionList.item(positionCount);

			Assert.assertTrue(ValidateSpatialRule(positionNode), "position element shall define content");

			NodeList pointList = positionNode.getElementsByTagName("gml:Point");
			if (pointList.getLength() == 0) {
				throw new SkipException("location or point is not defined");
			}
		}
	}

	/**
	 * <p>
	 * ByPostion.
	 * </p>
	 */
	@Test(description = "A.5.4 - Requirement 29", groups = "PhysicalComponent", dependsOnMethods = { "DependencyCore" })
	public void ByPostion() {
		NodeList positionList = this.testSubject.getDocumentElement().getElementsByTagName("sml:position");

		for (int positionCount = 0; positionCount < positionList.getLength(); positionCount++) {
			Element positionNode = (Element) positionList.item(positionCount);

			Assert.assertTrue(ValidateSpatialRule(positionNode), "position element shall define content");

			NodeList dataList = positionNode.getElementsByTagName("swe:DataRecord");
			if (dataList.getLength() == 0) {
				throw new SkipException("location data set is not defined");
			}
		}
	}

	/**
	 * <p>
	 * ByTrajectory.
	 * </p>
	 */
	@Test(description = "A.5.5 - Requirement 30", groups = "PhysicalComponent", dependsOnMethods = { "DependencyCore" })
	public void ByTrajectory() {
		NodeList positionList = this.testSubject.getDocumentElement().getElementsByTagName("sml:position");

		for (int positionCount = 0; positionCount < positionList.getLength(); positionCount++) {
			Element positionNode = (Element) positionList.item(positionCount);

			Assert.assertTrue(ValidateSpatialRule(positionNode), "position element shall define content");

			NodeList dataList = positionNode.getElementsByTagName("swe:DataArray");
			if (dataList.getLength() == 0) {
				throw new SkipException("time-tagged dynamic state information is not defined");
			}
		}
	}

	/**
	 * <p>
	 * ByProcess.
	 * </p>
	 */
	@Test(description = "A.5.6 - Requirement 31", groups = "PhysicalComponent", dependsOnMethods = { "DependencyCore" })
	public void ByProcess() {
		NodeList positionList = this.testSubject.getDocumentElement().getElementsByTagName("sml:position");

		for (int positionCount = 0; positionCount < positionList.getLength(); positionCount++) {
			Element positionNode = (Element) positionList.item(positionCount);

			Assert.assertTrue(ValidateSpatialRule(positionNode), "position element shall define content");

			NodeList dataList = positionNode.getElementsByTagName("sml:AbstractProcess");
			if (dataList.getLength() == 0) {
				throw new SkipException("positional information is not defined");
			}
		}
	}

	/**
	 * <p>
	 * Definition.
	 * </p>
	 */
	@Test(description = "A.5.7 - Requirement 32", groups = "PhysicalComponent", dependsOnMethods = { "DependencyCore" })
	public void Definition() {
		NodeList componens = this.testSubject.getDocumentElement().getElementsByTagName("sml:components");
		int componensCount = componens.getLength();

		if (componensCount > 0) {
			throw new AssertionError("No intent to further divide the device description into sub-­process components");
		}

		NodeList inputs = this.testSubject.getDocumentElement().getElementsByTagName("sml:inputs");
		int inputsCount = inputs.getLength();

		if (inputsCount == 0) {
			throw new AssertionError("No inputs");
		}

		NodeList outputs = this.testSubject.getDocumentElement().getElementsByTagName("sml:outputs");
		int outputsCount = outputs.getLength();

		if (outputsCount == 0) {
			throw new AssertionError("No outputs");
		}
	}

}
