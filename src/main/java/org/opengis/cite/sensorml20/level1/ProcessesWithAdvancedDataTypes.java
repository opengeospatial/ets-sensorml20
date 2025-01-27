package org.opengis.cite.sensorml20.level1;

import org.opengis.cite.sensorml20.BaseFixture;
import org.testng.annotations.Test;

/**
 * <p>
 * ProcessesWithAdvancedDataTypes class.
 * </p>
 *
 */
public class ProcessesWithAdvancedDataTypes extends BaseFixture {

	/**
	 * <p>
	 * DependencyCore.
	 * </p>
	 */
	@Test(description = "A.7.1 - Requirement 36", groups = "ProcessesWithAdvancedDataTypes",
			dependsOnGroups = { "CoreAbstractProcess" })
	public void DependencyCore() {
		// Dependency CoreAbstractProcess
	}

	/**
	 * <p>
	 * PackageFullyImplemented.
	 * </p>
	 */
	@Test(description = "A.7.2 - Requirement 37", groups = "ProcessesWithAdvancedDataTypes",
			dependsOnMethods = { "DependencyCore" })
	public void PackageFullyImplemented() {
		// Dependency All ProcessesWithAdvancedDataTypes Tests
	}

}
