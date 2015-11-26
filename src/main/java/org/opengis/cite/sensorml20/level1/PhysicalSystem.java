package org.opengis.cite.sensorml20.level1;

import org.opengis.cite.sensorml20.BaseFixture;
import org.testng.annotations.Test;

public class PhysicalSystem extends BaseFixture{
	@Test(description = "Requirement 33" , groups  = "PhysicalSystem" , dependsOnMethods  = { "Definition" , "DependencyCore" })
	public void PackageFullyImplemented()
	{
		//Dependency All PhysicalSystem Tests
	}
	
	@Test(description = "Requirement 34" , groups  = "PhysicalSystem" , dependsOnMethods  = { "DependencyCore" })
	public void Definition()
	{
		
	}
	
	@Test(description = "Requirement 35" , groups  = "PhysicalSystem" , dependsOnGroups  = { "PhysicalComponent" })
	public void DependencyCore()
	{
		//Dependency PhysicalComponent
	}
	
	
}
