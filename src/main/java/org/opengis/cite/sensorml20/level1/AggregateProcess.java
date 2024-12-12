package org.opengis.cite.sensorml20.level1;

import java.util.ArrayList;

import org.opengis.cite.sensorml20.BaseFixture;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * <p>
 * AggregateProcess class.
 * </p>
 *
 */
public class AggregateProcess extends BaseFixture {

	/**
	 * <p>
	 * DependencyCore.
	 * </p>
	 */
	@Test(description = "A.4.1 - Requirement 22", groups = "AggregateProcess", dependsOnGroups = { "SimpleProcess" })
	public void DependencyCore() {
		String rootName = this.testSubject.getDocumentElement().getNodeName();

		if (!rootName.equals("sml:AggregateProcess")) {
			throw new SkipException("Not an Aggregate Process");
		}
	}

	/**
	 * <p>
	 * PackageFullyImplemented.
	 * </p>
	 */
	@Test(description = "A.4.2 - Requirement 23", groups = "AggregateProcess",
			dependsOnMethods = { "DependencyCore", "Definition", "Components" })
	public void PackageFullyImplemented() {
		// Dependency All AggregateProcess Tests
	}

	/**
	 * <p>
	 * Definition.
	 * </p>
	 */
	@Test(description = "A.4.3 - Requirement 24", groups = "AggregateProcess", dependsOnMethods = { "DependencyCore" })
	public void Definition() {
		String rootName = this.testSubject.getDocumentElement().getNodeName();

		if (rootName == "sml:AggregateProcess") {
			if (this.testSubject.getElementsByTagName("sml:inputs").getLength() == 0) {
				throw new AssertionError("AggregateProcess Must Define Inputs !!");
			}

			if (this.testSubject.getElementsByTagName("sml:outputs").getLength() == 0) {
				throw new AssertionError("AggregateProcess Must Define Outputs !!");
			}

			if (this.testSubject.getElementsByTagName("sml:components").getLength() == 0) {
				throw new AssertionError("AggregateProcess Must Define Sub Process !!");
			}
		}
	}

	/**
	 * <p>
	 * Components.
	 * </p>
	 */
	@Test(description = "A.4.4 - Requirement 25", groups = "AggregateProcess", dependsOnMethods = { "DependencyCore" })
	public void Components() {
		String rootName = this.testSubject.getDocumentElement().getNodeName();

		if (rootName == "sml:AggregateProcess") {
			NodeList components = this.testSubject.getDocumentElement().getElementsByTagName("sml:components");
			if (components.getLength() > 0) {
				ArrayList<Element> componentsList = new ArrayList<Element>();

				NodeList tempList = components.item(0).getChildNodes();
				for (int i = 0; i < tempList.getLength(); i++) {
					Element item = (Element) tempList.item(i);
					if (item.getNodeName() == "sml:ComponentsList") {
						componentsList.add(item);
					}
				}

				if (componentsList.size() > 1) {
					NodeList component = componentsList.get(0).getChildNodes();
					if (component.getLength() == 0) {
						throw new AssertionError("requires one or more Process !!");
					}
					else {
						Boolean checkComponent = false;

						for (int i = 0; i < component.getLength(); i++) {
							Element item = (Element) component.item(i);
							if (item.getNodeName() == "sml:component") {
								checkComponent = true;
							}
							Assert.assertTrue(checkComponent, "Sub Process Not SimpleProcess!!");
						}
					}
				}
				else {
					throw new AssertionError("componentsList does not exist !!");
				}

			}
			else {
				throw new AssertionError("components does not exist !!");
			}

			if (this.testSubject.getDocumentElement().getElementsByTagName("sml:connections").getLength() == 0) {
				throw new AssertionError("connection does not exist !!");
			}
		}
	}

}
