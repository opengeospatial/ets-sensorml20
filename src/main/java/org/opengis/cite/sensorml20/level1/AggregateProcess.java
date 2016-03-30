package org.opengis.cite.sensorml20.level1;

import org.opengis.cite.sensorml20.BaseFixture;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Element;
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
		String rootName = this.testSubject.getDocumentElement().getTagName();
		
		if(rootName == "sml:AggregateProcess")
		{			
			if(this.testSubject.getElementsByTagName("sml:inputs").getLength() == 0)
			{
				throw new AssertionError("AggregateProcess Must Define Inputs !!");
			}
			
			if(this.testSubject.getElementsByTagName("sml:outputs").getLength() == 0)
			{
				throw new AssertionError("AggregateProcess Must Define Outputs !!");
			}
			
			if(this.testSubject.getElementsByTagName("sml:components").getLength() == 0)
			{
				throw new AssertionError("AggregateProcess Must Define Sub Process !!");
			}
		}

		
	}	
	
	@Test(description = "Requirement 25" , groups  = "AggregateProcess" , dependsOnMethods  = { "DependencyCore" })
	public void Components()
	{
		NodeList componentsList = this.testSubject.getDocumentElement().getElementsByTagName("sml:components");
		if(componentsList.getLength() > 0)
		{		
			NodeList componentList = componentsList.item(1).getChildNodes();
			if(componentList.getLength() == 0)
			{
				throw new AssertionError("requires one or more Process !!");
			}
			else
			{
				for(int i=0 ; i<componentList.getLength() ; i++)
				{
					Element item = (Element)componentList.item(i);

				    Assert.assertTrue(item.getTagName() != "sml:SimpleProcess", "Sub Process Not SimpleProcess!!" );
				}
			}
		}
		else
		{
			throw new AssertionError("Components does not exist !!");
		}
	}
}
