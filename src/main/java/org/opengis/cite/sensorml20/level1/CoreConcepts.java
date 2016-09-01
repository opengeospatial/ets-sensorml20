package org.opengis.cite.sensorml20.level1;

import java.util.ArrayList;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
//import org.junit.Assert;
import org.opengis.cite.sensorml20.BaseFixture;
import org.opengis.cite.sensorml20.ETSAssert;
import org.opengis.cite.sensorml20.util.ValidationUtils;
import org.testng.Assert;
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
		
		if(this.testSubject.getElementsByTagName("sml:inputs").getLength() == 0)
		{
			result.add("Core Model Must Define Inputs");
		}
		
		if(this.testSubject.getElementsByTagName("sml:outputs").getLength() == 0)
		{
			result.add("Core Model Must Define Outputs");
		}
		
		if(this.testSubject.getElementsByTagName("sml:parameters").getLength() == 0)
		{
			result.add("Core Model Must Define Parameters");
		}
		
		String documentName = this.testSubject.getDocumentElement().getNodeName();
		if(documentName == "sml:PhysicalSystem" || documentName == "sml:AggregateProcess")
		{
			String process = documentName.replace("sml:", "");
			if(this.testSubject.getElementsByTagName("sml:connections").getLength() == 0)
			{
				result.add(process + " Must Define Methodology");
			}
		}
	
		Assert.assertTrue(result.size() == 0, GetArrayToString(result) );
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
			NodeList list = this.testSubject.getDocumentElement().getElementsByTagName(str);
			if(list.getLength() == 0)
			{
				result.add("Process Shall include metadata["+str.replace("sml:", "")+"]");
			}
	    }
		//check to meet one of the qualifications
		metadataName = new String[]{ "sml:validTime", "sml:securityConstraints", "sml:legalConstraints"};
		boolean isAny = false;
		for (String str:metadataName) {
			NodeList list = this.testSubject.getDocumentElement().getElementsByTagName(str);
			
			if(list.getLength() != 0)
			{
				isAny = true;
				break;
			}
	    }
		
		Assert.assertTrue(isAny, "Process Shall include qualification metadata (one of the following elements: validTime, securityConstraints, legalConstraints) " );
		Assert.assertTrue(result.size() == 0, GetArrayToString(result) );
	}
	
	@Test(description = "Requirement 5" , groups  = "CoreConceptss" , priority = 5)
	public void CoreConceptExecution()
	{
		ArrayList<String> result = new ArrayList<String>();
		
		if(this.testSubject.getDocumentElement().getElementsByTagName("sml:input").getLength() == 0)
		{
			result.add("Core Model Must Define Inputs");
		}
		if(this.testSubject.getDocumentElement().getElementsByTagName("sml:output").getLength() == 0)
		{
			result.add("Core Model Must Define Output");
		}
		if(this.testSubject.getDocumentElement().getElementsByTagName("sml:parameter").getLength() == 0)
		{
			result.add("Core Model Must Define Parameter");
		}
		if(this.testSubject.getDocumentElement().getElementsByTagName("sml:method").getLength() == 0)
		{
			result.add("Core Model Must Define Method");
		}		
		Assert.assertTrue(result.size() == 0, GetArrayToString(result) );
		
		//必須檢查input . output . parameter . method這四個Tag --2016/05/17
		
		
		//檢查Description(3-30又覺得不太像是這樣做)
		/*if(this.testSubject.getElementsByTagName("gml:description").getLength() == 0)
		{
			throw new AssertionError("Description undefined !!");
		}*/
	}
}

