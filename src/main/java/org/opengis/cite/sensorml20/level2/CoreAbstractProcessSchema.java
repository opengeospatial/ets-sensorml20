package org.opengis.cite.sensorml20.level2;

import java.net.URL;
import java.util.ArrayList;

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
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CoreAbstractProcessSchema extends BaseFixture{
	
	@Test(description = "Requirement 45")
	public void CoreSchemaValid()
	{
		DOMSource source = new DOMSource(this.testSubject);
		      
		Schema schema = ValidationUtils.CreateSchema("core.xsd" , "http://schemas.opengis.net/sensorML/2.0/");
        Validator validator = schema.newValidator();
        ETSAssert.assertSchemaValid(validator, source);
				
		URL schRef = this.getClass().getResource(
				"/org/opengis/cite/sensorml20/sch/core.sch");
		ETSAssert.assertSchematronValid(schRef, source);
	}
	
	@Test(description = "Requirement 46")
	public void RefOrInlineValuePresent()
	{
		Node documentElement = this.testSubject.getDocumentElement();
		Assert.assertTrue(validateAllNodesReference(documentElement), "Reference Or inline value is Empty!!" );
	}	
	
	private Boolean validateAllNodesReference(Node node)
	{
		for(int i=0 ; i<node.getChildNodes().getLength() ; i++)
		{
			Node tempNode = node.getChildNodes().item(i);
			if(tempNode.getLocalName() != null)
			{
				String value = "";
				Element subNode = (Element)node.getChildNodes().item(i);
				if(subNode.hasAttribute("xlink:href"))
				{
					value = subNode.getAttribute("xlink:href");
				}
				else
				{
					value = node.getChildNodes().item(i).getNodeValue();
				}
				if(value == "")
				{
					return false;
				}
				
				Boolean tempResult = validateAllNodesReference(subNode);
				if(!tempResult)
				{
					return false;
				}						
			}
		}
		return true;
	}
	
	@Test(description = "Requirement 47")
	public void ExtensionNamespaceUnique()
	{
		NodeList extensionNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:extension");
		
		for(int extensionCount = 0 ; extensionCount < extensionNodes.getLength();extensionCount++)
		{
			NodeList extensionChilds = extensionNodes.item(extensionCount).getChildNodes();
			
			for(int eChildCount = 0 ; eChildCount < extensionChilds.getLength(); eChildCount++)
			{
				Node eChild = extensionChilds.item(eChildCount);
				
				if(eChild.getLocalName() != null && eChild.getLocalName().equals("value"))
				{
					NodeList extensionProcessList = eChild.getChildNodes();
					for(int epCount = 0 ; epCount < extensionProcessList.getLength() ; epCount++)
					{
						Node extensionProcess = extensionProcessList.item(epCount);
						if(extensionProcess.getLocalName() != null)
						{
							Boolean result = ValidateNewNameSpace(extensionProcess.getPrefix());
							Assert.assertTrue(result, "extension property shall be not element of sensorml20" );
						}
					}
				}
			}
		}
	}	
	
	@Test(description = "Requirement 48")
	public void ExtensionCoherentWithCore()
	{
		//This test cannot be run automatically as the meaning the extension shall be known and thus is not required to be implemented in the Executable Test Suite.
	}	
	
	@Test(description = "Requirement 49")
	public void ExtensionProcessExecution()
	{

	}	
	
	@Test(description = "Requirement 50")
	public void GmlDependency()
	{
		Boolean result = false;
		NamedNodeMap importList = this.testSubject.getDocumentElement().getAttributes();
		for(int i=0 ; i<importList.getLength() ; i++)
		{
			Node item = importList.item(i);
			if(item.getNodeValue().equals("http://www.opengis.net/gml/3.2"))
			{
				result = true;
			}
		}
		Assert.assertTrue(result, "This standard shall utilize and depend upon the OGC GML 3.2 standard schema" );
	}	
	
	@Test(description = "Requirement 51")
	public void SweCommonDependency()
	{
		Boolean result = false;
		NamedNodeMap importList = this.testSubject.getDocumentElement().getAttributes();
		for(int i=0 ; i<importList.getLength() ; i++)
		{
			Node item = importList.item(i);
			if(item.getNodeValue().equals("http://www.opengis.net/swe/2.0"))
			{
				result = true;
			}
		}
		Assert.assertTrue(result, "This standard shall utilize and depend upon the OGC SWE Common Data 2.0 standard" );
	}	
	
	@Test(description = "Requirement 52")
	public void GloballyUniqueId()
	{
		String resultMessage = "";
		
		NodeList list = this.testSubject.getDocumentElement().getElementsByTagName("gml:identifier");
		int listCount = list.getLength();
		
		if(listCount > 1)
		{
			resultMessage += " gml:identifier must be only one. ";
		}
		if(listCount == 0)
		{
			resultMessage += " must have one gml:identifier. ";
		}
		
		if(listCount == 1)
		{
			Element identifier = (Element)list.item(0);
			if(identifier.hasAttribute("codeSpace"))
			{
				String codeSpace = identifier.getAttributeNode("codeSpace").getValue();
				if(!codeSpace.equals("uid"))
				{
					resultMessage += " codeSpace value error. ";
				}
			}
			else
			{
				resultMessage += " must have codeSpace attribute. ";
			}
		}
		Assert.assertTrue(resultMessage.length() == 0, resultMessage );
	}	
	
	@Test(description = "Requirement 53")
	public void DocumentSecurityTags()
	{
		String result = "";
		
		NodeList list = this.testSubject.getDocumentElement().getElementsByTagName("sml:securityConstraints");
		if(list.getLength() > 0)
		{
			Node securityConstraints = list.item(0);
			ArrayList<Node> securityConstraintsNodes = DocumentTools.getAllNode(securityConstraints);
			
			securityConstraintsNodes.remove(0);
			
			for(int i=0 ; i<securityConstraintsNodes.size() ; i++)
			{
				Node sChild = securityConstraintsNodes.get(i);
				String childPrefix = sChild.getPrefix();
				if(ValidateNewNameSpace(childPrefix))
				{
					result = "securityConstraints property shall be defined in a new unique namespace";
				}
			}
		}
		Assert.assertTrue(result.length() == 0, result );
	}	
	
	private Boolean ValidateNewNameSpace(String pre)
	{
		if(pre.equals("sml"))
		{
			return false;
		}
		if(pre.equals("gml"))
		{
			return false;
		}
		if(pre.equals("swe"))
		{
			return false;
		}
		return true;
	}
	
	@Test(description = "Requirement 54")
	public void IndividualSecurityTags()
	{

	}	
	
	@Test(description = "Requirement 55")
	public void ContactRole()
	{
		NodeList contactNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:contact");
		for(int i=0 ; i<contactNodes.getLength();i++)
		{
			Element contactNode = (Element)contactNodes.item(i);
			if(contactNode.getParentNode().getLocalName().equals("ContactList") && contactNode.getParentNode().getParentNode().getLocalName().equals("contacts"))
			{
				if(!contactNode.hasAttribute("xlink:arcrole") && !contactNode.hasAttribute("xlink:role"))
				{
					throw new AssertionError("ContactList member property must define xlink:arcrole or xlink:role attribute");
				}
			}
		}
	}	
	
	@Test(description = "Requirement 56")
	public void TypeOfReference()
	{
		NodeList typeofNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:typeOf");
		ArrayList<String> uids = new ArrayList<String>();
	
		NodeList identifiers = this.testSubject.getDocumentElement().getElementsByTagName("gml:identifier");
		for(int identifiersCount=0;identifiersCount<identifiers.getLength();identifiersCount++)
		{
			uids.add(identifiers.item(identifiersCount).getTextContent());
		}
		
		for(int i=0 ; i<typeofNodes.getLength();i++)
		{
			Element typeofNode = (Element)typeofNodes.item(i);
			if(typeofNode.hasAttribute("xlink:title"))
			{
				String titleAttribute = typeofNode.getAttribute("xlink:title");
					
				if(uids.contains(titleAttribute))
				{
					throw new AssertionError("value of xlink:href attribute shall be a uniqueID");
				}
				uids.add(titleAttribute);
			}
			else
			{
				throw new AssertionError("typeOf property shall require meaningful values for the xlink:title");
			}
			
			if(typeofNode.hasAttribute("xlink:href"))
			{
				if(!UrlValidate.ValidateHttpUrl(typeofNode.getAttribute("xlink:href")))
				{
					throw new AssertionError("value of xlink:href attribute shall be a resolvable URL");
				}				
			}
			else
			{
				throw new AssertionError("typeOf property shall require meaningful values for the xlink:href");
			}
		}
	}	
	
	
	
	@Test(description = "Requirement 57")
	public void FoiArcroleAndTitle()
	{
		NodeList featureNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:feature");
		for(int i=0;i<featureNodes.getLength();i++)
		{
			Element feature = (Element)featureNodes.item(i);
			if(feature.getParentNode().getLocalName().equals("FeatureList") && feature.getParentNode().getParentNode().getLocalName().equals("featureOfInterest"))
			{
				if(!feature.hasAttribute("xlink:arcrole"))
				{
					throw new AssertionError("the member property of a FeatureList shall has a xlink:arcrole attribute");
				}
			}
		}
	}	
	
	@Test(description = "Requirement 58")
	public void ObservableDefinition()
	{
		NodeList observableNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:ObservableProperty");
		for(int i=0;i<observableNodes.getLength();i++)
		{
			Element observable = (Element)observableNodes.item(i);
			if(!observable.hasAttribute("definition"))
			{
				throw new AssertionError("the ObservableProperty element shall include the definition attribute");
			}
		}	
	}	
	
	@Test(description = "Requirement 59")
	public void DataRecord()
	{
		NodeList inputNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:input");
		for(int i=0 ; i<inputNodes.getLength(); i++)
		{
			if(!ValidateSWEDataComponent(inputNodes.item(i)))
			{
				throw new AssertionError("Input shall be surrounded by an appropriate aggregate data component");
			}
		}
		
		NodeList onputNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:output");
		for(int i=0 ; i<onputNodes.getLength(); i++)
		{
			if(!ValidateSWEDataComponent(onputNodes.item(i)))
			{
				throw new AssertionError("Output shall be surrounded by an appropriate aggregate data component");
			}
		}
		
		NodeList parameterNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:parameter");
		for(int i=0 ; i<parameterNodes.getLength(); i++)
		{
			if(!ValidateSWEDataComponent(parameterNodes.item(i)))
			{
				throw new AssertionError("Parameter shall be surrounded by an appropriate aggregate data component");
			}
		}
	}	
	
	private Boolean ValidateSWEDataComponent(Node node)
	{
		NodeList childNodes = node.getChildNodes();
		for(int i=0;i<childNodes.getLength();i++)
		{
			String localName = childNodes.item(i).getLocalName();
			if(localName != null)
			{
				if(localName != "DataRecord" && localName != "DataArray" && localName != "Vector" && localName != "Matrix")
				{
					return false;
				}
			}
		}
		return true;
	}
	
	@Test(description = "Requirement 60")
	public void VectorUse()
	{
		NodeList inputNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:input");
		for(int i=0 ; i<inputNodes.getLength(); i++)
		{
			if(!ValidatePositionChildsComponent(inputNodes.item(i)))
			{
				throw new AssertionError("swe:Vector shall be used when have position value");
			}
		}
		
		NodeList onputNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:output");
		for(int i=0 ; i<onputNodes.getLength(); i++)
		{
			if(!ValidatePositionChildsComponent(onputNodes.item(i)))
			{
				throw new AssertionError("swe:Vector shall be used when have position value");
			}
		}
		
		NodeList parameterNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:parameter");
		for(int i=0 ; i<parameterNodes.getLength(); i++)
		{
			if(!ValidatePositionChildsComponent(parameterNodes.item(i)))
			{
				throw new AssertionError("swe:Vector shall be used when have position value");
			}
		}
	}	
	
	private Boolean ValidatePositionChildsComponent(Node node)
	{
		ArrayList<Node> childs = DocumentTools.getAllNode(node);
		for(int i=0 ; i < childs.size() ; i++)
		{
			Node child = childs.get(i);
			if(child.getNodeName().equals("swe:coordinate"))
			{
				if(!child.getParentNode().getNodeName().equals("swe:Vector"))
				{
					return false;
				}
			}
		}
		return true;
	}
	
	
	@Test(description = "Requirement 61")
	public void DataStreamUrl()
	{
		Boolean result = false;
		
		NodeList dataStreamNodes = this.testSubject.getDocumentElement().getElementsByTagName("swe:DataStream");
		
		int nodesCount = dataStreamNodes.getLength();
		
		if(nodesCount == 0)
		{
			result = true;
		}
		
		for(int i=0 ; i<nodesCount; i++)
		{
			NodeList childs = dataStreamNodes.item(i).getChildNodes();
			for(int j=0 ; j<childs.getLength();j++)
			{
				if(childs.item(j).getLocalName() == null)
				{
					continue;
				}
				Element child = (Element)childs.item(j);
				if(child.getNodeName().equals("swe:values"))
				{
					if(child.hasAttribute("xlink:href"))
					{
						String attribute = child.getAttribute("xlink:href");
						if(UrlValidate.ValidateHttpUrl(attribute))
						{
							result = true;
						}
					}
				}
			}
		}
		
		Assert.assertTrue(result, "DataStream shall be referenced by a resolvable URL" );
	}	
	
	@Test(description = "Requirement 62")
	public void MultiplexedDataStream()
	{
		NodeList dataStreamNodes = this.testSubject.getDocumentElement().getElementsByTagName("swe:DataStream");
		
		for(int i=0 ; i<dataStreamNodes.getLength(); i++)
		{
			ArrayList<Node> dataStreamChilds = DocumentTools.getAllNode(dataStreamNodes.item(i));
			for(int j=0 ; j<dataStreamChilds.size();j++)
			{
				if(dataStreamChilds.get(j).getNodeName().equals("swe:DataChoice"))
				{
					NodeList dataChoiceChilds = dataStreamChilds.get(j).getChildNodes();
					for(int k=0 ; k<dataChoiceChilds.getLength();k++)
					{
						Node choiceChild = dataChoiceChilds.item(k);
						if(!choiceChild.getNodeName().equals("swe:item"))
						{						
							throw new AssertionError("When use DataChoice , each package must defined as an item.");
						}
					}
				}
			}	
		}		
	}	
}
