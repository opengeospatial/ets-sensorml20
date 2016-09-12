package org.opengis.cite.sensorml20.level1;
import org.testng.SkipException;
import org.opengis.cite.sensorml20.BaseFixture;
import org.testng.annotations.Test;
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
	
	@Test(description = "Requirement 28" , groups  = "PhysicalComponent" , dependsOnMethods  = { "DependencyCore" })
	public void ByPointOrLocation()
	{
		NodeList pointList = this.testSubject.getDocumentElement().getElementsByTagName("sml:byPoint");
		int pointCount = pointList.getLength();
		
		NodeList locationList = this.testSubject.getDocumentElement().getElementsByTagName("sml:byLocation");
		int locationCount = locationList.getLength();
		
		if(pointCount == 0 && locationCount == 0)
		{
			throw new AssertionError("location or point does not define !!");
		}
	}
	
	@Test(description = "Requirement 29" , groups  = "PhysicalComponent" , dependsOnMethods  = { "DependencyCore" })
	public void ByPostion()
	{
		NodeList postionList = this.testSubject.getDocumentElement().getElementsByTagName("sml:byPostion");
		int postionCount = postionList.getLength();
		
		if(postionCount == 0)
		{
			throw new AssertionError("postion does not define !!");
		}
	}
	
	@Test(description = "Requirement 30" , groups  = "PhysicalComponent" , dependsOnMethods  = { "DependencyCore" })
	public void ByTrajectory()
	{
		NodeList trajectoryList = this.testSubject.getDocumentElement().getElementsByTagName("sml:byTrajectory");
		int trajectoryCount = trajectoryList.getLength();
		
		if(trajectoryCount == 0)
		{
			throw new AssertionError("trajectory does not define !!");
		}
	}
	
	@Test(description = "Requirement 31" , groups  = "PhysicalComponent" , dependsOnMethods  = { "DependencyCore" })
	public void ByProcess()
	{
		NodeList processList = this.testSubject.getDocumentElement().getElementsByTagName("sml:byProcess");
		int processCount = processList.getLength();
		
		if(processCount == 0)
		{
			throw new AssertionError("Process does not define !!");
		}
	}
	
	@Test(description = "Requirement 32" , groups  = "PhysicalComponent" , dependsOnMethods  = { "DependencyCore" })
	public void Definition()
	{
		if(this.testSubject.getFirstChild().getNodeName() != "sml:PhyscialComponent")
		{
			throw new AssertionError("Not PhyscialComponent Procsee");
		}
		
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
