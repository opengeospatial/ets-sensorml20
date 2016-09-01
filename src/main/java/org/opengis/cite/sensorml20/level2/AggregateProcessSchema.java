package org.opengis.cite.sensorml20.level2;

import java.net.URL;
import java.util.ArrayList;

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
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AggregateProcessSchema extends BaseFixture{
	@Test(description = "Requirement 64")
	public void AggregateProcessSchemaValid()
	{
		DOMSource source = new DOMSource(this.testSubject);
		      
		Schema schema = ValidationUtils.CreateSchema("aggregate_process.xsd" , "http://schemas.opengis.net/sensorML/2.0/");
        Validator validator = schema.newValidator();
        ETSAssert.assertSchemaValid(validator, source);
				
		URL schRef = this.getClass().getResource(
				"/org/opengis/cite/sensorml20/sch/aggregate_process.sch");
		ETSAssert.assertSchematronValid(schRef, source);
	}
	
	@Test(description = "Requirement 65")
	public void ComponentReference()
	{
		NodeList componentNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:component");
		for(int i=0 ; i < componentNodes.getLength() ; i++)
		{
			Node component = componentNodes.item(i);
			ArrayList<Node> componentChilds = DocumentTools.getAllNode(component);
			
			Boolean isRef = false;
			
			for(int j=0 ; j < componentChilds.size() ; j++)
			{
				if(componentChilds.get(j).getLocalName() != null)
				{
					Element comChild = (Element)componentChilds.get(j);
					
					if(comChild.getNodeName().equals("sml:setValue") && comChild.hasAttribute("ref"))
					{
						isRef = true;
						break;
					}
				}
			}
			
			if(isRef)
			{
				ArrayList<String> uids = new ArrayList<String>();
		
				NodeList identifiers = this.testSubject.getDocumentElement().getElementsByTagName("gml:identifier");
				for(int identifiersCount=0;identifiersCount<identifiers.getLength();identifiersCount++)
				{
					uids.add(identifiers.item(identifiersCount).getTextContent());
				}
				
				for(int j=0 ; j < componentChilds.size() ; j++)
				{
					if(componentChilds.get(j).getLocalName() != null && componentChilds.get(j).getNodeName().equals("sml:typeOf"))
					{
						Element comChild = (Element)componentChilds.get(j);
						
						if(comChild.hasAttribute("xlink:title"))
						{
							String titleAttribute = comChild.getAttribute("xlink:title");
							
							if(uids.contains(titleAttribute))
							{
								throw new AssertionError("value of xlink:title attribute shall be a uniqueID");
							}
							
							uids.add(titleAttribute);
						}
						else
						{
							throw new AssertionError("component property shall require meaningful values for the xlink:title");
						}
						
						if(comChild.hasAttribute("xlink:href"))
						{
							if(!UrlValidate.ValidateHttpUrl(comChild.getAttribute("xlink:href")))
							{
								throw new AssertionError("component shall define a resolvable URL for reference");
							}				
						}
						else
						{
							throw new AssertionError("component property shall require meaningful values for the xlink:href");
						}
					}
				}
			}
		}
	}
	
	@Test(description = "Requirement 66")
	public void InputConnectionRestrictions()
	{
		NodeList inputNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:input");

		NodeList connectNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:connection");
		for(int connectCount=0 ; connectCount < connectNodes.getLength() ; connectCount++)
		{
			if(connectNodes.item(connectCount).hasChildNodes())
			{
				NodeList linkNodes = connectNodes.item(connectCount).getChildNodes();

				for(int linkCount = 0 ; linkCount < linkNodes.getLength() ; linkCount++)
				{
					Node link = linkNodes.item(linkCount);
					if(link.getLocalName() != null && link.getNodeName().equals("sml:Link"))
					{
						String source = "";
						String destination = "";
						
						NodeList linkChilds = link.getChildNodes();
						for(int linkChildCount = 0 ; linkChildCount < linkChilds.getLength() ; linkChildCount++)
						{
							if(linkChilds.item(linkChildCount).getLocalName() != null)
							{
								Element linkChild = (Element)linkChilds.item(linkChildCount);
								if(linkChild.getNodeName().equals("sml:source"))
								{
									source = linkChild.getAttribute("ref");
								}
								if(linkChild.getNodeName().equals("sml:destination"))
								{
									destination = linkChild.getAttribute("ref");
								}
							}
						}
						
						if(source.indexOf("input") != -1 && destination.indexOf("input") != -1 )
						{
							Boolean result = false;
							
							String[] sourceSplits = source.split("/");
							String inputName = sourceSplits[sourceSplits.length - 1];
							for(int inputCount = 0 ; inputCount < inputNodes.getLength() ; inputCount++)
							{
								Element inputNode = (Element)inputNodes.item(inputCount);
								if(inputNode.getAttribute("name").equals(inputName))
								{
									String processName = DocumentTools.GetMasterParentNodeName(inputNode);
									if(processName == "sml:AggregateProcess")
									{
										result = true;
									}
								}
								
							}
							
							Assert.assertTrue(result, "input-to-input connections shall from aggregate process" );
						}
					}
				}
			}
		}		
	}
	
	@Test(description = "Requirement 67")
	public void OutputConnectionRestrictions()
	{
		NodeList outputNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:output");

		NodeList connectNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:connection");
		for(int connectCount=0 ; connectCount < connectNodes.getLength() ; connectCount++)
		{
			if(connectNodes.item(connectCount).hasChildNodes())
			{
				NodeList linkNodes = connectNodes.item(connectCount).getChildNodes();

				for(int linkCount = 0 ; linkCount < linkNodes.getLength() ; linkCount++)
				{
					Node link = linkNodes.item(linkCount);
					if(link.getLocalName() != null && link.getNodeName().equals("sml:Link"))
					{
						String source = "";
						String destination = "";
						
						NodeList linkChilds = link.getChildNodes();
						for(int linkChildCount = 0 ; linkChildCount < linkChilds.getLength() ; linkChildCount++)
						{
							if(linkChilds.item(linkChildCount).getLocalName() != null)
							{
								Element linkChild = (Element)linkChilds.item(linkChildCount);
								if(linkChild.getNodeName().equals("sml:source"))
								{
									source = linkChild.getAttribute("ref");
								}
								if(linkChild.getNodeName().equals("sml:destination"))
								{
									destination = linkChild.getAttribute("ref");
								}
							}
						}
						
						if(source.indexOf("output") != -1 && destination.indexOf("output") != -1 )
						{
							Boolean result = false;
							
							String[] destSplits = destination.split("/");
							String outputName = destSplits[destSplits.length - 1];
							for(int outputCount = 0 ; outputCount < outputNodes.getLength() ; outputCount++)
							{
								Element outputNode = (Element)outputNodes.item(outputCount);
								if(outputNode.getAttribute("name").equals(outputName))
								{
									String processName = DocumentTools.GetMasterParentNodeName(outputNode);
									if(processName == "sml:AggregateProcess")
									{
										result = true;
									}
								}
								
							}
							
							Assert.assertTrue(result, "output cannot connect to another output of aggregate process" );
						}
					}
				}
			}
		}	
	}
	
	@Test(description = "Requirement 68")
	public void MultipleConnections()
	{
		ArrayList<String> sources = new ArrayList<String>();
		
		NodeList connectNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:connection");
		for(int connectCount=0 ; connectCount < connectNodes.getLength() ; connectCount++)
		{
			if(connectNodes.item(connectCount).hasChildNodes())
			{
				NodeList linkNodes = connectNodes.item(connectCount).getChildNodes();

				for(int linkCount = 0 ; linkCount < linkNodes.getLength() ; linkCount++)
				{
					Node link = linkNodes.item(linkCount);
					if(link.getLocalName() != null && link.getNodeName().equals("sml:Link"))
					{					
						NodeList linkChilds = link.getChildNodes();
						for(int linkChildCount = 0 ; linkChildCount < linkChilds.getLength() ; linkChildCount++)
						{
							if(linkChilds.item(linkChildCount).getLocalName() != null)
							{
								Element linkChild = (Element)linkChilds.item(linkChildCount);
								if(linkChild.getNodeName().equals("sml:source") && linkChild.hasAttribute("ref"))
								{
									String connectRef = linkChild.getAttribute("ref");
									if(connectRef.indexOf("") != -1)
									{
										if(sources.contains(connectRef))
										{
											throw new AssertionError("an input can only have one source connection");
										}
										else
										{
											sources.add(connectRef);
										}									
									}
								}
							}
						}
					}
				}
			}
		}
		
	}
	
	@Test(description = "Requirement 69")
	public void ParameterConnectionrestrictions()
	{
		NodeList connectNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:connection");
		for(int connectCount=0 ; connectCount < connectNodes.getLength() ; connectCount++)
		{
			if(connectNodes.item(connectCount).hasChildNodes())
			{
				NodeList linkNodes = connectNodes.item(connectCount).getChildNodes();

				for(int linkCount = 0 ; linkCount < linkNodes.getLength() ; linkCount++)
				{
					Node link = linkNodes.item(linkCount);
					if(link.getLocalName() != null && link.getNodeName().equals("sml:Link"))
					{					
						NodeList linkChilds = link.getChildNodes();
						for(int linkChildCount = 0 ; linkChildCount < linkChilds.getLength() ; linkChildCount++)
						{
							if(linkChilds.item(linkChildCount).getLocalName() != null)
							{
								Element linkChild = (Element)linkChilds.item(linkChildCount);
								if(linkChild.getNodeName().equals("sml:source"))
								{
									if(linkChild.getAttribute("ref").indexOf("parameter") != -1)
									{
										throw new AssertionError("A parameter can only be connected as a destination");
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	@Test(description = "Requirement 70")
	public void PropertyConnectionrestrictions()
	{
		NodeList connectNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:connection");
		for(int connectCount=0 ; connectCount < connectNodes.getLength() ; connectCount++)
		{
			if(connectNodes.item(connectCount).hasChildNodes())
			{
				NodeList linkNodes = connectNodes.item(connectCount).getChildNodes();

				for(int linkCount = 0 ; linkCount < linkNodes.getLength() ; linkCount++)
				{
					Node link = linkNodes.item(linkCount);
					if(link.getLocalName() != null && link.getNodeName().equals("sml:Link"))
					{					
						NodeList linkChilds = link.getChildNodes();
						for(int linkChildCount = 0 ; linkChildCount < linkChilds.getLength() ; linkChildCount++)
						{
							if(linkChilds.item(linkChildCount).getLocalName() != null)
							{
								Element linkChild = (Element)linkChilds.item(linkChildCount);
								if(linkChild.getNodeName().equals("sml:destination"))
								{
									String connectRef = linkChild.getAttribute("ref");
									
									if(connectRef.indexOf("input") == -1 && connectRef.indexOf("output") == -1 && connectRef.indexOf("parameter") == -1)
									{
										throw new AssertionError("destinations shall be a impit , output or parameter");
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	@Test(description = "Requirement 71")
	public void DesignatingLinkPaths()
	{/*
		NodeList sourceNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:source");
		for(int sourceCount = 0 ; sourceCount < sourceNodes.getLength() ; sourceCount++)
		{
			String validateResult = ValidateLikePath(sourceNodes.item(sourceCount));
			Assert.assertTrue( (validateResult.length() == 0) , validateResult );
		}
		
		NodeList destinationNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:destination");
		for(int destinationCount = 0 ; destinationCount < destinationNodes.getLength() ; destinationCount++)
		{
			String validateResult = ValidateLikePath(destinationNodes.item(destinationCount));
			Assert.assertTrue((validateResult.length() == 0), validateResult );
		}*/
	}
	
	
	private String ValidateLikePath(Node node)
	{
		ArrayList<String> pathList = new ArrayList<String>();
		
		Element validateNode = (Element)node;
		if(validateNode.hasAttribute("ref"))
		{
			String[] pathAry =  validateNode.getAttribute("ref").split("/");
			if(pathAry.length > 0)
			{
				String type = pathAry[0];
				
				NodeList linkTargetList = this.testSubject.getDocumentElement().getElementsByTagName("sml:" + type );
				
				for(int linkTargetCount = 0 ; linkTargetCount < linkTargetList.getLength() ;linkTargetCount++)
				{
					if( linkTargetList.item(linkTargetCount).getLocalName() != null )
					{
						NodeList linkTargetChildList = linkTargetList.item(linkTargetCount).getChildNodes();
						
						for(int targetChildCount = 0 ; targetChildCount < linkTargetChildList.getLength();targetChildCount++)
						{
							String result = CombinePath(linkTargetChildList.item(targetChildCount));
							if(result.length() > 0)
							{
								result = type + "/" + result;
								System.out.println(result);
								pathList.add(result);
							}
						}	
					}
				}
			}
			else
			{
				return "Reference Can not be Empty";
			}
		}
		else
		{
			return "Link Element shall have a reference";
		}
		
		return "";
	}
	
	private String CombinePath(Node node)
	{
		String result = "";
		
		if(node.getLocalName() != null)
		{
			Element element = (Element)node;
			String localName = node.getLocalName();
			if(localName.substring(0, 1).equals(localName.substring(0, 1).toLowerCase()) && element.getPrefix().equals("sml"))
			{
				if(element.hasAttribute("name"))
				{
					result += element.getAttribute("name");
				}
				else
				{
					result += localName;
				}
			}
			
			String childStr = "";
			NodeList nodeList = node.getChildNodes();
			for(int childCount = 0 ; childCount < nodeList.getLength() ; childCount++)
			{
				childStr += CombinePath(nodeList.item(childCount));
				if(childStr.length() > 0)
				{
					break;
				}
			}
			
			if(childStr.length() > 0)
			{
				result = result + "/" +  childStr;
			}
		}
		
		return result;
	}
}
