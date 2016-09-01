package org.opengis.cite.sensorml20.level2;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;

import org.opengis.cite.sensorml20.BaseFixture;
import org.opengis.cite.sensorml20.ETSAssert;
import org.opengis.cite.sensorml20.util.DocumentTools;
import org.opengis.cite.sensorml20.util.ValidationUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConfigurableProcessSchema extends BaseFixture{
	@Test(description = "Requirement 80")
	public void AggregateProcessSchemaValid()
	{
		DOMSource source = new DOMSource(this.testSubject);
		      
		Schema schema = ValidationUtils.CreateSchema("configuration.xsd" , "http://schemas.opengis.net/sensorML/2.0/");
        Validator validator = schema.newValidator();
        ETSAssert.assertSchemaValid(validator, source);
				
		URL schRef = this.getClass().getResource(
				"/org/opengis/cite/sensorml20/sch/configuration.sch");
		ETSAssert.assertSchematronValid(schRef, source);
	}
	
	@Test(description = "Requirement 81")
	public void ModeRestriction()
	{
		NodeList parameterNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:parameters");
		
		ArrayList<Node> parameterAllChilds = new ArrayList<Node>();
		
		for(int parametersCount = 0 ; parametersCount < parameterNodes.getLength() ; parametersCount++)
		{
			ArrayList<Node> tempChilds = DocumentTools.getAllNode(parameterNodes.item(parametersCount));
			tempChilds.remove(0);
			parameterAllChilds.addAll(tempChilds);
		}
		
		NodeList setValueNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:setValue");
		
		for(int setValueCount = 0 ; setValueCount < setValueNodes.getLength() ; setValueCount++)
		{
			Element setValue = (Element)setValueNodes.item(setValueCount);
			String setValueReference = setValue.getAttribute("ref");
			if(setValueReference != null)
			{
				String[] refSplits = setValueReference.split("/");
				String parameterName = refSplits[refSplits.length-1];
				
				Boolean isLocalParameter = false;
				
				for(int allParameterChildCount = 0 ; allParameterChildCount < parameterAllChilds.size() ; allParameterChildCount++)
				{
					Element pChild = (Element)parameterAllChilds.get(allParameterChildCount);

					if(pChild.getAttribute("name").equals(parameterName))
					{
						isLocalParameter = true;
					}
				}
				
				Assert.assertTrue(isLocalParameter, "setValue references only parameter properties within the current process or parent process" );
			}
			else
			{
				throw new AssertionError("SetValue property shall consist of ref attribute");
			}
		}
	}
	
	@Test(description = "Requirement 82")
	public void ParameterValues()
	{
		NodeList parameterNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:parameters");
		
		ArrayList<Node> parameterAllChilds = new ArrayList<Node>();
		
		for(int parametersCount = 0 ; parametersCount < parameterNodes.getLength() ; parametersCount++)
		{
			ArrayList<Node> tempChilds = DocumentTools.getAllNode(parameterNodes.item(parametersCount));
			tempChilds.remove(0);
			parameterAllChilds.addAll(tempChilds);
		}
		
		NodeList setValueNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:setValue");
		
		for(int setValueCount = 0 ; setValueCount < setValueNodes.getLength() ; setValueCount++)
		{
			Element setValue = (Element)setValueNodes.item(setValueCount);
			String setValueReference = setValue.getAttribute("ref");
			if(setValueReference != null)
			{
				String[] refSplits = setValueReference.split("/");
				String parameterName = refSplits[refSplits.length-1];
				
				for(int allParameterChildCount = 0 ; allParameterChildCount < parameterAllChilds.size() ; allParameterChildCount++)
				{
					Element pChild = (Element)parameterAllChilds.get(allParameterChildCount);

					if(pChild.getAttribute("name").equals(parameterName))
					{
						ArrayList<Node> fieldChilds = DocumentTools.getAllNode(parameterAllChilds.get(allParameterChildCount));
						fieldChilds.remove(0);
						
						for(int fieldChildCount = 0 ; fieldChildCount < fieldChilds.size() ; fieldChildCount++)
						{
							Node filedChild = fieldChilds.get(fieldChildCount);
							if(filedChild.getNodeName().equals("swe:interval"))
							{
								String[] intervalSplits = filedChild.getTextContent().split(" ");
								if(intervalSplits.length > 1)
								{
									Double minNum = Double.parseDouble(intervalSplits[0]);
									Double maxNum = Double.parseDouble(intervalSplits[1]);
									
									Double setValueNum = Double.parseDouble(setValue.getTextContent());
									
									if(setValueNum < minNum || setValueNum > maxNum)
									{
										throw new AssertionError("The parameter values set by a Mode cannot be outside of the allow values");
									}
								}
								else
								{
									throw new AssertionError("Interval format error");
								}
							}
						}
					}
				}	
			}
			else
			{
				throw new AssertionError("SetValue property shall consist of ref attribute");
			}
		}
	}
}
