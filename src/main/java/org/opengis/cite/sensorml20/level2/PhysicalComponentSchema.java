package org.opengis.cite.sensorml20.level2;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;

import org.opengis.cite.sensorml20.BaseFixture;
import org.opengis.cite.sensorml20.ETSAssert;
import org.opengis.cite.sensorml20.util.DocumentTools;
import org.opengis.cite.sensorml20.util.UrlValidate;
import org.opengis.cite.sensorml20.util.ValidationUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class PhysicalComponentSchema extends BaseFixture{
	@Test(description = "Requirement 72")
	public void AggregateProcessSchemaValid()
	{
		DOMSource source = new DOMSource(this.testSubject);
		      
		Schema schema = ValidationUtils.CreateSchema("physical_component.xsd" , "http://schemas.opengis.net/sensorML/2.0/");
        Validator validator = schema.newValidator();
        ETSAssert.assertSchemaValid(validator, source);
				
		URL schRef = this.getClass().getResource(
				"/org/opengis/cite/sensorml20/sch/physical_component.sch");
		ETSAssert.assertSchematronValid(schRef, source);
	}
	
	@Test(description = "Requirement 73")
	public void AttachedToTarget()
	{
		NodeList attachedTotNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:attachedTo");
		for(int attachCount = 0 ; attachCount < attachedTotNodes.getLength() ; attachCount++)
		{
			Element attachedTo = (Element)attachedTotNodes.item(attachCount);
			
			if(attachedTo.hasAttribute("xlink:href"))
			{
				try{
					URL url = new URL(attachedTo.getAttribute("xlink:href"));
					DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
					DocumentBuilder db = dbf.newDocumentBuilder();
					Document doc = db.parse(url.openStream());
					String ProcessName = doc.getDocumentElement().getNodeName();
					if(!ProcessName.equals("sml:PhysicalComponent") && !ProcessName.equals("sml:PhysicalSystem"))
					{
						throw new AssertionError("reference is not PhysicalSystem or PhysicalComponent");
					}	
				}
				catch(Exception ex)
				{
					throw new AssertionError("It's not a resolvable reference Url");
				}
			}
			else
			{
				throw new AssertionError("attachedTo property shall have xlink:href attribute");
			}
		}
	}
	
	@Test(description = "Requirement 74")
	public void AttachedToReference()
	{
		NodeList attachedToNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:attachedTo");
		for(int attachedToCount = 0 ; attachedToCount < attachedToNodes.getLength() ; attachedToCount++)
		{
			Element attachedTo = (Element)attachedToNodes.item(attachedToCount);
			
			ArrayList<String> uids = new ArrayList<String>();
		
			NodeList identifiers = this.testSubject.getDocumentElement().getElementsByTagName("gml:identifier");
			for(int identifiersCount=0;identifiersCount<identifiers.getLength();identifiersCount++)
			{
				uids.add(identifiers.item(identifiersCount).getTextContent());
			}
				
			if(attachedTo.hasAttribute("xlink:title"))
			{
				String titleAttribute = attachedTo.getAttribute("xlink:title");
					
				if(uids.contains(titleAttribute))
				{
					throw new AssertionError("value of xlink:title attribute shall be a uniqueID");
				}
			}
			else
			{
				throw new AssertionError("attachedTo property shall require meaningful values for the xlink:title");
			}
				
			if(attachedTo.hasAttribute("xlink:href"))
			{
				if(!UrlValidate.ValidateHttpUrl(attachedTo.getAttribute("xlink:href")))
				{
					throw new AssertionError("attachedTo shall define a resolvable URL for reference");
				}				
			}
				else
			{
				throw new AssertionError("attachedTo property shall require meaningful values for the xlink:href");
			}
		}
	}
	
	@Test(description = "Requirement 75")
	public void PositionByPosition()
	{
		NodeList positionNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:position");
		for(int positionCount = 0 ; positionCount < positionNodes.getLength() ; positionCount++)
		{
			Element position = (Element)positionNodes.item(positionCount);
			NodeList positionChilds = position.getChildNodes();
			
			Boolean useDataRecord = false;
			
			for(int pchildCount = 0 ; pchildCount < positionChilds.getLength() ; pchildCount++)
			{
				Node pChild = positionChilds.item(pchildCount);
				if(pChild.getLocalName() != null)
				{
					if(pChild.getNodeName().equals("swe:DataRecord"))
					{
						useDataRecord = true;
						ArrayList<Node> fieldList = new ArrayList<Node>();
						NodeList recordChilds = pChild.getChildNodes();
						for(int recordChildCount = 0 ; recordChildCount < recordChilds.getLength() ; recordChildCount++)
						{
							Node recodeChild = recordChilds.item(recordChildCount);
							if(recodeChild.getNodeName().equals("swe:field"))
							{
								fieldList.add(recodeChild);
							}
						}
						
						if(fieldList.size() == 2)
						{
							Element firstField = (Element)fieldList.get(0);
							Element secondField = (Element)fieldList.get(1);
							
							if(!firstField.getAttribute("name").equals("location"))
							{
								throw new AssertionError("First field name is not location");
							}
							if(!secondField.getAttribute("name").equals("orientation"))
							{
								throw new AssertionError("Second field name is not orientation");
							}
						}
						else
						{
							throw new AssertionError("Position property shall consist of two swe:Vector fields");
						}
					}
				}
			}
			Assert.assertTrue(useDataRecord, "The position property shall take a swe:DataRecord as its value" );
		}
	}
	
	@Test(description = "Requirement 76")
	public void DynamicState()
	{
		NodeList positionNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:position");
		for(int positionCount = 0 ; positionCount < positionNodes.getLength() ; positionCount++)
		{
			NodeList positionChilds = positionNodes.item(positionCount).getChildNodes();
			for(int childCount = 0 ; childCount < positionChilds.getLength() ; childCount++)
			{
				Node child = positionChilds.item(childCount);
				if(!child.getNodeName().equals("swe:DataArray") && !child.getNodeName().equals("sml:AbstractProcess"))
				{
					throw new AssertionError("dynamic state shall be described using a swe:DataArray or a sml:AbstractProcess.");
				}
			}
		}
	}
	
	@Test(description = "Requirement 77")
	public void PositionByTrajectory()
	{
		NodeList positionNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:position");
		for(int positionCount = 0 ; positionCount < positionNodes.getLength() ; positionCount++)
		{
			NodeList positionChilds = positionNodes.item(positionCount).getChildNodes();
			for(int childCount = 0 ; childCount < positionChilds.getLength() ; childCount++)
			{
				Node positionChild = positionChilds.item(childCount);
				if(positionChild.getNodeName().equals("swe:DataArray"))
				{
					NodeList dataArrayChilds = positionChild.getChildNodes();
					for(int dataArrayCount = 0 ; dataArrayCount < dataArrayChilds.getLength();dataArrayCount++)
					{
						
						if(dataArrayChilds.item(dataArrayCount).getLocalName() != null)
						{
							Element dataArrayChild = (Element)dataArrayChilds.item(dataArrayCount);
							if(dataArrayChild.getAttribute("name").equals("trajectory"))
							{
								Boolean useSweTime = false;
								Boolean useVector = false;
								
								ArrayList<Node> trajectoryChilds = DocumentTools.getAllNode(dataArrayChild);
								for(int trajectoryChildCount = 0 ; trajectoryChildCount < trajectoryChilds.size() ; trajectoryChildCount++)
								{
									Node trajectoryChild = trajectoryChilds.get(trajectoryChildCount);
									if(trajectoryChild.getLocalName() != null)
									{
										if(trajectoryChild.getNodeName().equals("swe:Time"))
										{
											useSweTime = true;
										}
										if(trajectoryChild.getNodeName().equals("swe:Vector"))
										{
											useVector = true;
										}	
									}
								}
								
								Assert.assertTrue((useSweTime && useVector), "DataArray Shall contains a time field and one or more swe:Vector elements as its fields" );
							}
						}
					}
				}
			}
		}
	}
	
	@Test(description = "Requirement 78")
	public void PositionByProcess()
	{
		Boolean useSmlAbstractProcess = false;
		
		NodeList positionNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:position");
		for(int positinCount = 0 ; positinCount < positionNodes.getLength() ; positinCount++)
		{
			Node pChild = positionNodes.item(positinCount);
			if(pChild.getNodeName().equals("sml:SimpleProcess") || pChild.getNodeName().equals("sml:PhysicalComponent") || pChild.getNodeName().equals("sml:PhysicalSystem") || pChild.getNodeName().equals("sml:AggregateProcess"))
			{
				useSmlAbstractProcess = true;
				break;
			}
		}
		
		if(useSmlAbstractProcess)
		{
			Boolean useSweData = false;
			
			NodeList outputNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:outputs");
			for(int outCount = 0 ; outCount < outputNodes.getLength() ; outCount++)
			{
				ArrayList<Node> outputChilds = DocumentTools.getAllNode(outputNodes.item(outCount));
				for(int oChildCount = 0 ; oChildCount < outputChilds.size() ; oChildCount++)
				{
					Node oChild = outputChilds.get(oChildCount);
					if(oChild.getNodeName().equals("we:DataArray") || oChild.getNodeName().equals("swe:DataRecord"))
					{
						useSweData = true;
						break;
					}
				}
			}
			Assert.assertTrue(useSweData, "output of the process shall contains a swe:DataArray , or a swe:DataRecord " );
		}
	}
}
