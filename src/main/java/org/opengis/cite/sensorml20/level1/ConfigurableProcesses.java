package org.opengis.cite.sensorml20.level1;

import org.opengis.cite.sensorml20.BaseFixture;
import org.testng.annotations.Test;

public class ConfigurableProcesses extends BaseFixture{
	@Test(description = "Requirement 38" , groups  = "ConfigurableProcesses" , dependsOnGroups  = { "CoreAbstractProcess" })
	public void DependencyCore()
	{
		//Dependency CoreAbstractProcess
	}
	
	@Test(description = "Requirement 39" , groups  = "ConfigurableProcesses" , dependsOnMethods  = { "DependencyCore" , "TwoModesRequired" , "SettingsProperty" , "SetValueRestriction" , "SetArrayValueRestriction" , "SetConstraintRestriction"})
	public void PackageFullyImplemented()
	{
		//Dependency All ConfigurableProcesses Tests
	}
	
	@Test(description = "Requirement 40" , groups  = "ConfigurableProcesses" , dependsOnMethods  = { "DependencyCore" })
	public void TwoModesRequired()
	{
		
	}
	
	@Test(description = "Requirement 41" , groups  = "ConfigurableProcesses" , dependsOnMethods  = { "DependencyCore" })
	public void SettingsProperty()
	{
		
	}
	
	@Test(description = "Requirement 42" , groups  = "ConfigurableProcesses" , dependsOnMethods  = { "DependencyCore" })
	public void SetValueRestriction()
	{
		
	}
	
	@Test(description = "Requirement 43" , groups  = "ConfigurableProcesses" , dependsOnMethods  = { "DependencyCore" })
	public void SetArrayValueRestriction()
	{
		
	}
	
	@Test(description = "Requirement 44" , groups  = "ConfigurableProcesses" , dependsOnMethods  = { "DependencyCore" })
	public void SetConstraintRestriction()
	{
		
	}
}
