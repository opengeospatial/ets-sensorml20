package org.opengis.cite.sensorml20.level1;

import java.util.ArrayList;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import org.junit.Assert;
import org.opengis.cite.sensorml20.BaseFixture;
import org.opengis.cite.sensorml20.ETSAssert;
import org.opengis.cite.sensorml20.util.ValidationUtils;
import org.testng.annotations.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CoreConcepts extends BaseFixture{

	
	@Test(description = "Requirement 1" ,groups  = "CoreConceptss" ,priority = 1)
	public void CoreConceptUsed()
	{
		Schema schema = null;
		Node documentElement = this.testSubject.getDocumentElement();
		String documentName = documentElement.getNodeName();
		if(documentName.equals("sml:SimpleProcess"))
		{
			schema = ValidationUtils.CreateSchema("simple_process.xsd" , null);
		}else if(documentName.equals("sml:PhysicalComponent"))
		{
			schema = ValidationUtils.CreateSchema("simple_process.xsd" , null);
		}else if(documentName.equals("sml:AggregateProcess"))
		{
			schema = ValidationUtils.CreateSchema("aggregate_process.xsd" , null);
		}else if(documentName.equals("sml:PhysicalSystem"))
		{
			schema = ValidationUtils.CreateSchema("physical_system.xsd" , null);
		}
        Validator validator = schema.newValidator();
        Source source = new DOMSource(this.testSubject);       
        ETSAssert.assertSchemaValid(validator, source);
	}
	
	@Test(description = "Requirement 2" , groups  = "CoreConceptss" , priority = 2)
	public void CoreConceptProcesses()
	{
		ArrayList<String> result = new ArrayList<String>();
		
		NodeList inputs = this.testSubject.getElementsByTagName("sml:inputs");
		if(inputs.getLength() == 0)
		{
			result.add("Core Model Must Define Inputs");
		}
		
		NodeList outputs = this.testSubject.getElementsByTagName("sml:outputs");
		if(outputs.getLength() == 0)
		{
			result.add("Core Model Must Define Outputs");
		}
		
		NodeList parameters = this.testSubject.getElementsByTagName("sml:parameters");
		if(parameters.getLength() == 0)
		{
			result.add("Core Model Must Define Parameters");
		}
		Assert.assertTrue( GetArrayToString(result) , result.size() == 0 );
	}
	
	private String GetArrayToString(ArrayList<String> ary)
	{
		String ret = "";
		for (String str:ary) {
			ret += str + "\n";
	    }
		return ret;
	}
	
	@Test(description = "Requirement 3" , groups  = "CoreConceptss" , priority = 3)
	public void CoreConceptUniqueId()
	{
		NodeList identifierList = this.testSubject.getDocumentElement().getElementsByTagName("gml:identifier");
		int listLength = identifierList.getLength();
		if(listLength == 0)
		{
			throw new AssertionError("Identifier Not Found !!");
		}
		else if(listLength > 1)
		{
			ArrayList<String> idList = new ArrayList<String>();
			for(int i=0 ; i<listLength ; i++)
			{
				Node item = identifierList.item(i);
				String id = item.getNodeValue();
				if(idList.contains(id))
				{
					throw new AssertionError("ID is not unique !!");
				}
				else
				{
					idList.add(id);
				}
			}
		}
	}
	
	@Test(description = "Requirement 4" , groups  = "CoreConceptss" , priority = 4)
	public void CoreConceptMetadata()
	{
		ArrayList<String> result = new ArrayList<String>();
		String[] metadataName = new String[]{"sml:keywords","sml:identification","sml:classification","sml:characteristics","sml:capabilities"};
		for (String str:metadataName) {
			NodeList list = this.testSubject.getDocumentElement().getElementsByTagName("sml:identification");
			if(list.getLength() == 0)
			{
				result.add("Process Shall include metadata["+str.replace("sml:", "")+"]");
			}
	    }
		Assert.assertTrue( GetArrayToString(result) , result.size() == 0 );
	}
	
	@Test(description = "Requirement 5" , groups  = "CoreConceptss" , priority = 5)
	public void CoreConceptExecution()
	{
		Boolean result = true;
		
		NodeList configurationList = this.testSubject.getDocumentElement().getElementsByTagName("sml:configuration");
		if(configurationList.getLength() > 0)
		{
			NodeList setList = configurationList.item(0).getFirstChild().getChildNodes();
			for(int setIndex = 0 ; setIndex<setList.getLength() ; setIndex++)
			{
				Boolean tempResult = false;
				
				Element setNode = (Element)setList.item(setIndex);
				String nodeRef = setNode.getAttribute("ref");
				String[] refAry = nodeRef.split("/");
				if(refAry.length == 3)
				{
					NodeList parameters = this.testSubject.getDocumentElement().getElementsByTagName("sml:"+refAry[0]);
					if(parameters.getLength() > 0)
					{
						NodeList parameterList = parameters.item(0).getFirstChild().getChildNodes();
						for(int parameteIndex = 0 ; parameteIndex < parameterList.getLength() ; parameteIndex++)
						{
							Element paramererNode = (Element)parameterList.item(parameteIndex);
							if(paramererNode.getAttribute("name") == refAry[1])
							{
								NodeList paramererDataRecords = paramererNode.getFirstChild().getChildNodes();
								for(int dataRecordIndex = 0;dataRecordIndex < paramererDataRecords.getLength(); dataRecordIndex++)
								{
									Element dataRecordNode = (Element)paramererDataRecords.item(dataRecordIndex);
									if(dataRecordNode.getAttribute("name") == refAry[2])
									{
										tempResult = true;
									}
								}
							}
						}
					}	
				}	
				if(!tempResult)
				{
					result = false;
				}
			}
		}
		
		//還想不到錯誤名稱怎麼訂，先這樣吧!!
		Assert.assertTrue( "Execution Verify Error !!" , result );
	}
}

