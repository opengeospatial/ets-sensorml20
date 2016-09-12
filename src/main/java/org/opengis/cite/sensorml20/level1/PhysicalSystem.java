package org.opengis.cite.sensorml20.level1;

import org.opengis.cite.sensorml20.BaseFixture;
import org.testng.SkipException;
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
		if(this.testSubject.getDocumentElement().getElementsByTagName("sml:inputs").getLength() == 0)
		{
			throw new AssertionError("Inputs dose not Define !!");
		}
		if(this.testSubject.getDocumentElement().getElementsByTagName("sml:outputs").getLength() == 0)
		{
			throw new AssertionError("Outputs dose not Define !!");
		}
		if(this.testSubject.getDocumentElement().getElementsByTagName("sml:position").getLength() == 0)
		{
			throw new AssertionError("Location dose not Define !!");
		}
		if(this.testSubject.getDocumentElement().getElementsByTagName("sml:components").getLength() == 0)
		{
			throw new AssertionError("Components dose not Define !!");
		}
	}
	
	@Test(description = "Requirement 35" , groups  = "PhysicalSystem" , dependsOnGroups  = { "PhysicalComponent" })
	public void DependencyCore()
	{
		String rootName = this.testSubject.getDocumentElement().getNodeName();

		if(!rootName.equals("sml:PhysicalSystem"))
		{
			throw new SkipException("TODO: Not a PhysicalSystem Process");	
		}
	}
	
	
}
