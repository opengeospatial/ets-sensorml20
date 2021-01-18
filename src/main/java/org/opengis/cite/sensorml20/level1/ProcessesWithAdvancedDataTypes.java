package org.opengis.cite.sensorml20.level1;

import org.opengis.cite.sensorml20.BaseFixture;
import org.testng.annotations.Test;

public class ProcessesWithAdvancedDataTypes extends BaseFixture{
	@Test(description = "A.7.1 - Requirement 36" , groups  = "ProcessesWithAdvancedDataTypes" , dependsOnGroups  = { "CoreAbstractProcess" })
	public void DependencyCore()
	{
		//Dependency CoreAbstractProcess
	}

	@Test(description = "A.7.2 - Requirement 37" , groups  = "ProcessesWithAdvancedDataTypes" , dependsOnMethods  = { "DependencyCore" })
	public void PackageFullyImplemented()
	{
		//Dependency All ProcessesWithAdvancedDataTypes Tests
	}
}
