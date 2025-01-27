package org.opengis.cite.sensorml20.level1;

import org.opengis.cite.sensorml20.BaseFixture;
import org.testng.SkipException;
import org.testng.annotations.Test;
import org.w3c.dom.NodeList;

/**
 * <p>
 * SimpleProcess class.
 * </p>
 *
 */
public class SimpleProcess extends BaseFixture {

	/**
	 * <p>
	 * DependencyCore.
	 * </p>
	 */
	@Test(description = "A.3.1 - Requirement 18", groups = "SimpleProcess", dependsOnGroups = { "CoreAbstractProcess" })
	public void DependencyCore() {
		String rootName = this.testSubject.getDocumentElement().getNodeName();

		if (!rootName.equals("sml:SimpleProcess")) {
			throw new SkipException("Not a Simple Process");
		}
	}

	/**
	 * <p>
	 * PackageFullyImplemented.
	 * </p>
	 */
	@Test(description = "A.3.2 - Requirement 19", groups = "SimpleProcess",
			dependsOnMethods = { "DependencyCore", "Definition", "Method" })
	public void PackageFullyImplemented() {
		// Dependency All SimpleProcess Tests
	}

	/**
	 * <p>
	 * Definition.
	 * </p>
	 */
	@Test(description = "A.3.3 - Requirement 20", groups = "SimpleProcess", dependsOnMethods = { "DependencyCore" })
	public void Definition() {
		if (this.testSubject.getDocumentElement().getElementsByTagName("sml:inputs").getLength() == 0) {
			throw new AssertionError("Inputs dose not Define !!");
		}
		if (this.testSubject.getDocumentElement().getElementsByTagName("sml:outputs").getLength() == 0) {
			throw new AssertionError("Outputs dose not Define !!");
		}
		if (this.testSubject.getDocumentElement().getElementsByTagName("sml:components").getLength() > 0) {
			throw new AssertionError("Components should not be Defined !!");
		}
	}

	/**
	 * <p>
	 * Method.
	 * </p>
	 */
	@Test(description = "A.3.4 - Requirement 21", groups = "SimpleProcess", dependsOnMethods = { "DependencyCore" })
	public void Method() {
		NodeList methodList = this.testSubject.getDocumentElement().getElementsByTagName("sml:method");
		int listLength = methodList.getLength();
		if (listLength == 0) {
			throw new AssertionError("Method property does not exist !!");
		}

	}

}
