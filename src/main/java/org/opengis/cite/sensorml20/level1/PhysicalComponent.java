package org.opengis.cite.sensorml20.level1;
import org.testng.Assert;
import org.testng.SkipException;
import org.opengis.cite.sensorml20.BaseFixture;
import org.testng.annotations.Test;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class PhysicalComponent extends BaseFixture{
	
	
	@Test(description = "Requirement 26" , groups  = "PhysicalComponent" , dependsOnMethods  = {  "DependencyCore" , "ByPointOrLocation" , "ByPostion" , "ByTrajectory" , "ByProcess" , "Definition"})
	public void PackageFullyImplemented()
	{
		//Dependency All PhysicalComponent Tests
	}
	
	@Test(description = "Requirement 27" , groups  = "PhysicalComponent" , dependsOnGroups  = { "CoreAbstractProcess" })
	public void DependencyCore()
	{
		String rootName = this.testSubject.getDocumentElement().getNodeName();

		if(!rootName.equals("sml:PhysicalComponent"))
		{
			throw new SkipException("TODO: Not a PhysicalComponent Process");	
		}
	}
	
	private Boolean ValidateSpatialRule(Element node)
	{
		String[] ruleNames = new String[]{"swe:Text", "gml:Point", "swe:Vector", "swe:DataRecord", "swe:DataArray" , "sml:AbstractProcess"};
		for(int ruleCount = 0 ; ruleCount < ruleNames.length ; ruleCount++)
		{
			String ruleName = ruleNames[ruleCount];
			NodeList list = node.getElementsByTagName(ruleName);
			if(list.getLength() > 0)
			{
				return true;
			}
		}
		return false;
	}
	
	@Test(description = "Requirement 28" , groups  = "PhysicalComponent" , dependsOnMethods  = { "DependencyCore" })
	public void ByPointOrLocation()
	{
		NodeList positionList = this.testSubject.getDocumentElement().getElementsByTagName("sml:position");
		
		for(int positionCount = 0 ; positionCount < positionList.getLength();positionCount++)
		{
			Element positionNode = (Element)positionList.item(positionCount);
			
			Assert.assertTrue(ValidateSpatialRule(positionNode), "position element shall defind content" );
			
			NodeList pointList = positionNode.getElementsByTagName("gml:Point");
			if(pointList.getLength() == 0)
			{
				throw new SkipException("location or point does not define");
			}
		}
	}
	
	@Test(description = "Requirement 29" , groups  = "PhysicalComponent" , dependsOnMethods  = { "DependencyCore" })
	public void ByPostion()
	{
		NodeList positionList = this.testSubject.getDocumentElement().getElementsByTagName("sml:position");
		
		for(int positionCount = 0 ; positionCount < positionList.getLength();positionCount++)
		{
			Element positionNode = (Element)positionList.item(positionCount);
			
			Assert.assertTrue(ValidateSpatialRule(positionNode), "position element shall defind content" );
			
			NodeList dataList = positionNode.getElementsByTagName("swe:DataRecord");
			if(dataList.getLength() == 0)
			{
				throw new SkipException("location data set does not define");
			}
		}	
	}
	
	@Test(description = "Requirement 30" , groups  = "PhysicalComponent" , dependsOnMethods  = { "DependencyCore" })
	public void ByTrajectory()
	{
		NodeList positionList = this.testSubject.getDocumentElement().getElementsByTagName("sml:position");
		
		for(int positionCount = 0 ; positionCount < positionList.getLength();positionCount++)
		{
			Element positionNode = (Element)positionList.item(positionCount);
			
			Assert.assertTrue(ValidateSpatialRule(positionNode), "position element shall defind content" );
			
			NodeList dataList = positionNode.getElementsByTagName("swe:DataArray");
			if(dataList.getLength() == 0)
			{
				throw new SkipException("time-tagged dynamic state information does not define");
			}
		}	
	}
	
	@Test(description = "Requirement 31" , groups  = "PhysicalComponent" , dependsOnMethods  = { "DependencyCore" })
	public void ByProcess()
	{
		NodeList positionList = this.testSubject.getDocumentElement().getElementsByTagName("sml:position");
		
		for(int positionCount = 0 ; positionCount < positionList.getLength();positionCount++)
		{
			Element positionNode = (Element)positionList.item(positionCount);
			
			Assert.assertTrue(ValidateSpatialRule(positionNode), "position element shall defind content" );
			
			NodeList dataList = positionNode.getElementsByTagName("sml:AbstractProcess");
			if(dataList.getLength() == 0)
			{
				throw new SkipException("positional information does not define");
			}
		}	
	}
	
	@Test(description = "Requirement 32" , groups  = "PhysicalComponent" , dependsOnMethods  = { "DependencyCore" })
	public void Definition()
	{
		NodeList componens = this.testSubject.getDocumentElement().getElementsByTagName("sml:components");
		int componensCount = componens.getLength();
		
		if(componensCount > 0)
		{
			throw new AssertionError("No intent to further divide the device description into sub-­process components");
		}
		
		NodeList inputs = this.testSubject.getDocumentElement().getElementsByTagName("sml:inputs");
		int inputsCount = inputs.getLength();
		
		if(inputsCount == 0)
		{
			throw new AssertionError("No inputs");
		}
		
		NodeList outputs = this.testSubject.getDocumentElement().getElementsByTagName("sml:outputs");
		int outputsCount = outputs.getLength();
		
		if(outputsCount == 0)
		{
			throw new AssertionError("No outputs");
		}
	}
}