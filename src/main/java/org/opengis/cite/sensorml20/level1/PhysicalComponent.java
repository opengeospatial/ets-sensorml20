package org.opengis.cite.sensorml20.level1;

import org.opengis.cite.sensorml20.BaseFixture;
import org.testng.annotations.Test;

public class PhysicalComponent extends BaseFixture{
	
	@Test(description = "Requirement 26" , groups  = "PhysicalComponent" , dependsOnMethods  = { "DependencyCore" })
	public void PackageFullyImplemented()
	{
		//Dependency All PhysicalComponent Tests
	}
	
	@Test(description = "Requirement 27" , groups  = "PhysicalComponent" , dependsOnGroups  = { "CoreAbstractProcess" })
	public void DependencyCore()
	{
		//Dependency CoreAbstractProcess
	}
}
