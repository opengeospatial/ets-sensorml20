package org.opengis.cite.sensorml20.level1;

import org.opengis.cite.sensorml20.BaseFixture;
import org.testng.annotations.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AggregateProcess extends BaseFixture{
	@Test(description = "Requirement 22" , groups  = "AggregateProcess" , dependsOnGroups  = { "SimpleProcess" })
	public void DependencyCore()
	{
		//Dependency SimpleProcess
	}
	
	@Test(description = "Requirement 23" , groups  = "AggregateProcess" , dependsOnMethods  = { "DependencyCore" , "Definition" , "Components" })
	public void PackageFullyImplemented()
	{
		//Dependency All AggregateProcess Tests
	}
	
	@Test(description = "Requirement 24" , groups  = "AggregateProcess" , dependsOnMethods  = { "DependencyCore" })
	public void Definition()
	{
		NodeList inputList = this.testSubject.getDocumentElement().getElementsByTagName("sml:components");
		
		NodeList componentsList = this.testSubject.getDocumentElement().getElementsByTagName("sml:components");
		if(componentsList.getLength() > 0)
		{
			Node components = componentsList.item(1);
		}
	}	
	
	@Test(description = "Requirement 25" , groups  = "AggregateProcess" , dependsOnMethods  = { "DependencyCore" })
	public void Components()
	{
		NodeList componentsList = this.testSubject.getDocumentElement().getElementsByTagName("sml:components");
		if(componentsList.getLength() > 0)
		{
			NodeList subList = componentsList.item(1).getFirstChild().getChildNodes();
			if(subList.getLength() == 0)
			{
				throw new AssertionError("requires one or more components !!");
			}
		}
		else
		{
			throw new AssertionError("Components does not exist !!");
		}
	}
}
