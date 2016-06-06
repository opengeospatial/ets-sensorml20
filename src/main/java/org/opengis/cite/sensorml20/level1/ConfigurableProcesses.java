package org.opengis.cite.sensorml20.level1;

import org.opengis.cite.sensorml20.BaseFixture;
import org.testng.annotations.Test;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

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
		NodeList modes = this.testSubject.getDocumentElement().getElementsByTagName("sml:modes");
		if(modes.getLength() > 0)
		{
			Element modeChoice = null;
			
			for(int i=0 ; i<modes.getLength() ; i++)
			{
				Element item = (Element)modes.item(i);
				if(item.getNodeName() == "sml:ModeChoice")
				{
					modeChoice = item;
					break;
				}
			}
			
			if(modeChoice != null)
			{
				int modeCount = 0;
				NodeList modeChoiceChilds = modeChoice.getChildNodes();
				for(int i=0 ; i<modeChoiceChilds.getLength() ; i++)
				{
					Element item = (Element)modeChoiceChilds.item(i);
					if(item.getNodeName() == "sml:Mode")
					{
						modeCount++;
					}
				}	
				if(modeCount < 2)
				{
					throw new AssertionError("requires two or more Mode !!");
				}
			}
			else
			{
				throw new AssertionError("requires two or more ModeChoice !!");
			}
		}
		else
		{
			throw new AssertionError("requires two or more modes !!");
		}
	}
	
	@Test(description = "Requirement 41" , groups  = "ConfigurableProcesses" , dependsOnMethods  = { "DependencyCore" })
	public void SettingsProperty()
	{
		NodeList modes = this.testSubject.getDocumentElement().getElementsByTagName("sml:modes");
		if(modes.getLength() > 0)
		{
			Element modeChoice = null;		
			for(int i=0 ; i<modes.getLength() ; i++)
			{
				Element item = (Element)modes.item(i);
				if(item.getNodeName() == "sml:ModeChoice")
				{
					modeChoice = item;
					break;
				}
			}
			
			if(modeChoice != null)
			{
				Element mode = null;
				NodeList modeChoiceChilds = modeChoice.getChildNodes();
				for(int i=0 ; i<modeChoiceChilds.getLength() ; i++)
				{
					Element item = (Element)modeChoiceChilds.item(i);
					if(item.getNodeName() == "sml:Mode")
					{
						mode = item;
						break;
					}
				}	
				if(mode != null)
				{
					Element configuration = null;
					NodeList modeChilds = mode.getChildNodes();
					for(int i=0 ; i<modeChilds.getLength() ; i++)
					{
						Element item = (Element)modeChilds.item(i);
						if(item.getNodeName() == "sml:configuration")
						{
							configuration = item;
							break;
						}
					}
					if(configuration != null)
					{
						Element settings = null;
						NodeList configurationChilds = configuration.getChildNodes();
						for(int i=0 ; i<configurationChilds.getLength() ; i++)
						{
							Element item = (Element)configurationChilds.item(i);
							if(item.getNodeName() == "sml:Settings")
							{
								settings = item;
								break;
							}
						}
						if(settings == null)
						{
							throw new AssertionError("mode's configuration need inclued Settings !!");
						}
					}
					else
					{
						throw new AssertionError("mode need inclued configuration !!");
					}
				}
				else
				{
					throw new AssertionError("requires two or more Mode !!");
				}
			}
			else
			{
				throw new AssertionError("requires two or more ModeChoice !!");
			}
		}
		else
		{
			throw new AssertionError("requires two or more modes !!");
		}		
	}
	
	@Test(description = "Requirement 42" , groups  = "ConfigurableProcesses" , dependsOnMethods  = { "DependencyCore" })
	public void SetValueRestriction()
	{
		NodeList modes = this.testSubject.getDocumentElement().getElementsByTagName("sml:modes");
		if(modes.getLength() > 0)
		{
			Element modeChoice = null;		
			for(int i=0 ; i<modes.getLength() ; i++)
			{
				Element item = (Element)modes.item(i);
				if(item.getNodeName() == "sml:ModeChoice")
				{
					modeChoice = item;
					break;
				}
			}
			
			if(modeChoice != null)
			{
				Element mode = null;
				NodeList modeChoiceChilds = modeChoice.getChildNodes();
				for(int i=0 ; i<modeChoiceChilds.getLength() ; i++)
				{
					Element item = (Element)modeChoiceChilds.item(i);
					if(item.getNodeName() == "sml:Mode")
					{
						mode = item;
						break;
					}
				}	
				if(mode != null)
				{
					Element configuration = null;
					NodeList modeChilds = mode.getChildNodes();
					for(int i=0 ; i<modeChilds.getLength() ; i++)
					{
						Element item = (Element)modeChilds.item(i);
						if(item.getNodeName() == "sml:configuration")
						{
							configuration = item;
							break;
						}
					}
					if(configuration != null)
					{
						Element settings = null;
						NodeList configurationChilds = configuration.getChildNodes();
						for(int i=0 ; i<configurationChilds.getLength() ; i++)
						{
							Element item = (Element)configurationChilds.item(i);
							if(item.getNodeName() == "sml:Settings")
							{
								settings = item;
								break;
							}
						}
						if(settings != null)
						{
							NodeList settingChilds = configuration.getChildNodes();
							for(int i=0 ; i<settingChilds.getLength() ; i++)
							{
								Element item = (Element)settingChilds.item(i);
								if(item.getNodeName() == "sml:setValue")
								{
									settings = item;
									NamedNodeMap setValueAttributes = item.getAttributes();
									for(int j=0 ; j < setValueAttributes.getLength() ; j++)
									{
										Attr attribute = (Attr)setValueAttributes.item(j);
										if(attribute.getName() != "ref")
										{
											throw new AssertionError("setValue can not use ["+attribute.getName()+"] !!");
										}
									}
								}
								else
								{
									throw new AssertionError("Settings can not inclued other than setValue !!");
								}
							}
						}
						else
						{
							throw new AssertionError("mode's configuration need inclued Settings !!");
						}
					}
					else
					{
						throw new AssertionError("mode need inclued configuration !!");
					}
				}
				else
				{
					throw new AssertionError("requires two or more Mode !!");
				}
			}
			else
			{
				throw new AssertionError("requires two or more ModeChoice !!");
			}
		}
		else
		{
			throw new AssertionError("requires two or more modes !!");
		}		
	}
	
	@Test(description = "Requirement 43" , groups  = "ConfigurableProcesses" , dependsOnMethods  = { "DependencyCore" })
	public void SetArrayValueRestriction()
	{
		NodeList modes = this.testSubject.getDocumentElement().getElementsByTagName("sml:modes");
		if(modes.getLength() > 0)
		{
			Element modeChoice = null;		
			for(int i=0 ; i<modes.getLength() ; i++)
			{
				Element item = (Element)modes.item(i);
				if(item.getNodeName() == "sml:ModeChoice")
				{
					modeChoice = item;
					break;
				}
			}
			
			if(modeChoice != null)
			{
				Element mode = null;
				NodeList modeChoiceChilds = modeChoice.getChildNodes();
				for(int i=0 ; i<modeChoiceChilds.getLength() ; i++)
				{
					Element item = (Element)modeChoiceChilds.item(i);
					if(item.getNodeName() == "sml:Mode")
					{
						mode = item;
						break;
					}
				}	
				if(mode != null)
				{
					Element configuration = null;
					NodeList modeChilds = mode.getChildNodes();
					for(int i=0 ; i<modeChilds.getLength() ; i++)
					{
						Element item = (Element)modeChilds.item(i);
						if(item.getNodeName() == "sml:configuration")
						{
							configuration = item;
							break;
						}
					}
					if(configuration != null)
					{
						Element settings = null;
						NodeList configurationChilds = configuration.getChildNodes();
						for(int i=0 ; i<configurationChilds.getLength() ; i++)
						{
							Element item = (Element)configurationChilds.item(i);
							if(item.getNodeName() == "sml:Settings")
							{
								settings = item;
								break;
							}
						}
						if(settings != null)
						{
							Element setArrayValue = null;
							NodeList settingChilds = configuration.getChildNodes();
							for(int i=0 ; i<settingChilds.getLength() ; i++)
							{
								Element item = (Element)settingChilds.item(i);
								if(item.getNodeName() == "sml:setArrayValue")
								{
									setArrayValue = item;
									break;
								}
							}
							if(setArrayValue != null)
							{
								int count = 0;
								NodeList setArrayValueChilds = setArrayValue.getChildNodes();
								for(int i=0 ; i<setArrayValueChilds.getLength() ; i++)
								{
									Element item = (Element)setArrayValueChilds.item(i);
									if(item.getNodeName() == "sml:ArrayValues")
									{
										count++;
									}
								}
								if(count <= 0)
								{
									throw new AssertionError("setArrayValue need inclued ArrayValues !!");
								}
							}
							else
							{
								throw new AssertionError("mode's Settings need inclued setArrayValue !!");
							}
						}
						else
						{
							throw new AssertionError("mode's configuration need inclued Settings !!");
						}
					}
					else
					{
						throw new AssertionError("mode need inclued configuration !!");
					}
				}
				else
				{
					throw new AssertionError("requires two or more Mode !!");
				}
			}
			else
			{
				throw new AssertionError("requires two or more ModeChoice !!");
			}
		}
		else
		{
			throw new AssertionError("requires two or more modes !!");
		}		
	}
	
	@Test(description = "Requirement 44" , groups  = "ConfigurableProcesses" , dependsOnMethods  = { "DependencyCore" })
	public void SetConstraintRestriction()
	{
		//同上，檢查mode裡面有sml:configuration，包含了settings，裡面有sml:setConstraint這個元素，並且元素裡有swe:AllowedValues、swe:AllowedTokens、swe:AllowedTimes其中一種為值
		
		NodeList modes = this.testSubject.getDocumentElement().getElementsByTagName("sml:modes");
		if(modes.getLength() > 0)
		{
			Element modeChoice = null;		
			for(int i=0 ; i<modes.getLength() ; i++)
			{
				Element item = (Element)modes.item(i);
				if(item.getNodeName() == "sml:ModeChoice")
				{
					modeChoice = item;
					break;
				}
			}
			
			if(modeChoice != null)
			{
				Element mode = null;
				NodeList modeChoiceChilds = modeChoice.getChildNodes();
				for(int i=0 ; i<modeChoiceChilds.getLength() ; i++)
				{
					Element item = (Element)modeChoiceChilds.item(i);
					if(item.getNodeName() == "sml:Mode")
					{
						mode = item;
						break;
					}
				}	
				if(mode != null)
				{
					Element configuration = null;
					NodeList modeChilds = mode.getChildNodes();
					for(int i=0 ; i<modeChilds.getLength() ; i++)
					{
						Element item = (Element)modeChilds.item(i);
						if(item.getNodeName() == "sml:configuration")
						{
							configuration = item;
							break;
						}
					}
					if(configuration != null)
					{
						Element settings = null;
						NodeList configurationChilds = configuration.getChildNodes();
						for(int i=0 ; i<configurationChilds.getLength() ; i++)
						{
							Element item = (Element)configurationChilds.item(i);
							if(item.getNodeName() == "sml:Settings")
							{
								settings = item;
								break;
							}
						}
						if(settings != null)
						{
							Element setConstraint = null;
							NodeList settingChilds = configuration.getChildNodes();
							for(int i=0 ; i<settingChilds.getLength() ; i++)
							{
								Element item = (Element)settingChilds.item(i);
								if(item.getNodeName() == "sml:setConstraint")
								{
									setConstraint = item;
									break;
								}
							}
							if(setConstraint != null)
							{
								int count = 0;
								NodeList setArrayValueChilds = setConstraint.getChildNodes();
								for(int i=0 ; i<setArrayValueChilds.getLength() ; i++)
								{
									Element item = (Element)setArrayValueChilds.item(i);
									if(item.getNodeName() == "swe:AllowedValues" || item.getNodeName() == "swe:AllowedTokens" || item.getNodeName() == "swe:AllowedTimes")
									{
										count++;
									}
								}
								if(count <= 0)
								{
									throw new AssertionError("setConstraint need set Value !!");
								}
							}
							else
							{
								throw new AssertionError("mode's Settings need inclued setArrayValue !!");
							}
						}
						else
						{
							throw new AssertionError("mode's configuration need inclued Settings !!");
						}
					}
					else
					{
						throw new AssertionError("mode need inclued configuration !!");
					}
				}
				else
				{
					throw new AssertionError("requires two or more Mode !!");
				}
			}
			else
			{
				throw new AssertionError("requires two or more ModeChoice !!");
			}
		}
		else
		{
			throw new AssertionError("requires two or more modes !!");
		}			
	}
}
