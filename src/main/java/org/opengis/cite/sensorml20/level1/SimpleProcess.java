package org.opengis.cite.sensorml20.level1;

import org.opengis.cite.sensorml20.BaseFixture;
import org.testng.annotations.Test;
import org.w3c.dom.NodeList;

public class SimpleProcess extends BaseFixture{
	
	@Test(description = "Requirement 18" , groups  = "SimpleProcess" , dependsOnGroups  = { "CoreAbstractProcess" })
	public void DependencyCore()
	{
		//Dependency SimpleProcess
	}
	
	@Test(description = "Requirement 19" , groups  = "SimpleProcess" , dependsOnMethods  = { "DependencyCore" , "Definition" , "Method" })
	public void PackageFullyImplement()
	{
		//Dependency All SimpleProcess Tests
	}
	
	@Test(description = "Requirement 20" , groups  = "SimpleProcess" , dependsOnMethods  = { "DependencyCore" })
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
		if(this.testSubject.getDocumentElement().getElementsByTagName("sml:components").getLength() > 0)
		{
			throw new AssertionError("Components should not be Defined !!");
		}
	}
	
	@Test(description = "Requirement 21" , groups  = "SimpleProcess" , dependsOnMethods  = { "DependencyCore" })
	public void Method()
	{
		NodeList methodList = this.testSubject.getDocumentElement().getElementsByTagName("sml:method");
		int listLength = methodList.getLength();
		if(listLength == 0)
		{
			throw new AssertionError("Method property does not exist !!");
		}		
		
	}
}
