package org.opengis.cite.sensorml20.level1;

import java.util.ArrayList;
import org.opengis.cite.sensorml20.BaseFixture;
import org.opengis.cite.sensorml20.util.DocumentTools;
import org.opengis.cite.sensorml20.util.URIUtils;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class CoreAbstractProcess extends BaseFixture{

	@Test(description = "Requirement 6" , groups  = "CoreAbstractProcess" , dependsOnGroups  = { "CoreConceptss" })
	public void DependencyCore()
	{
		//Dependency CoreConcepts
	}
	
	@Test(description = "Requirement 7" , groups  = "CoreAbstractProcess" , dependsOnMethods  = { "DependencyCore" , "GmlDependency" , "UniqueId" , "ExtensionIndependence" , 
			"ExtensionRestrictions" , "SweCommonDependency" , "AggregateData" , "TypeOf" , "SimpleInheritance" , "Configuration" , "SWECommonDependency"})
	public void PackageFullyImplemented()
	{	
		//Dependency All CoreAbstractProcess Tests
	}
	
	@Test(description = "Requirement 8" , groups  = "CoreAbstractProcess" , dependsOnMethods  = { "DependencyCore" })
	public void GmlDependency()
	{
		
	}
	
	@Test(description = "Requirement 9" , groups  = "CoreAbstractProcess" , dependsOnMethods  = { "DependencyCore" })
	public void UniqueId()
	{
		NodeList identifierList = this.testSubject.getDocumentElement().getElementsByTagName("gml:identifier");
		int listLength = identifierList.getLength();

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
	
	@Test(description = "Requirement 10" , groups  = "CoreAbstractProcess" /*, dependsOnMethods  = { "DependencyCore" }*/)
	public void ExtensionIndependence() throws ParserConfigurationException, SAXException, IOException
	{
		ArrayList<Node> extensionNodes = DocumentTools.GetElementByLocalName(this.testSubject.getDocumentElement(), "extension");
		
	    for (Node item : extensionNodes) 
	    {
	    	Boolean result = DocumentTools.ValidateNewNameSpace(item.getPrefix());
	    	if(!result)
	    	{
	    		throw new AssertionError("extsnsion property shall within a separate namespace.");
	    	}   	
	    }	
	}
	
	@Test(description = "Requirement 11" , groups  = "CoreAbstractProcess" , dependsOnMethods  = { "DependencyCore" })
	public void ExtensionRestrictions()
	{
		
	}
	
	@Test(description = "Requirement 12" , groups  = "CoreAbstractProcess" , dependsOnMethods  = { "DependencyCore" })
	public void SweCommonDependency()
	{
//		Schema schema = ValidationUtils.CreateSchema("block_components.xsd" , "http://schemas.opengis.net/sweCommon/2.0/");
//		
//        Validator validator = schema.newValidator();
//        Source source = new DOMSource(this.testSubject);       
//        ETSAssert.assertSchemaValid(validator, source);
	}
	
	@Test(description = "Requirement 13" , groups  = "CoreAbstractProcess" , dependsOnMethods  = { "DependencyCore" })
	public void AggregateData()
	{
		//reference sweCommon for testing
		//ERROR Message: cvc-elt.1.a: Cannot find the declaration of element 'sml:SimpleProcess'. 
		//Location: expected [false] but found [true]
//		Schema schema = ValidationUtils.CreateSchema("swe.xsd" , "http://schemas.opengis.net/sweCommon/2.0/");
//        Validator validator = schema.newValidator();
//        Source source = new DOMSource(this.testSubject);       
//        ETSAssert.assertSchemaValid(validator, source);		
	}
	
	@Test(description = "Requirement 14" , groups  = "CoreAbstractProcess" )
	public void TypeOf()
	{
		NodeList typeofList = this.testSubject.getDocumentElement().getElementsByTagName("sml:typeOf");
		int listLength = typeofList.getLength();
		if(listLength == 0)
		{
			for(int i=0 ; i<listLength ; i++)
			{
				Element item = (Element)typeofList.item(i);
				
				Assert.assertTrue(item.hasAttribute("xlink:title"), "Title does not define!!" );
				Assert.assertTrue(item.hasAttribute("xlink:href"), "Url does not define!!" );
				
				String url = item.getAttribute("xlink:href");
				
			    String urlRegex = "\\b(https?|ftp|file|ldap)://"
			            + "[-A-Za-z0-9+&@#/%?=~_|!:,.;]"
			            + "*[-A-Za-z0-9+&@#/%=~_|]";
			    
			    Assert.assertTrue(url.matches(urlRegex), "TypeOf Url Error !!" );
			}
		}
	}
	
	@Test(description = "Requirement 15" , groups  = "CoreAbstractProcess" , dependsOnMethods  = { "DependencyCore" , "TypeOf" })
	public void SimpleInheritance() throws SAXException, IOException
	{
		NodeList typeNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:typeOf");
		
		for(int typeCount = 0 ; typeCount < typeNodes.getLength() ; typeCount++)
		{
			Element typeNode = (Element)typeNodes.item(typeCount);
			
			String url = typeNode.getAttribute("xlink:href");

			try {
				URI uri = new URI(url);
				Document doc = URIUtils.parseURI(uri); 
				
				NodeList configurationNodes = doc.getDocumentElement().getElementsByTagName("sml:configuration");
				
				Assert.assertTrue((configurationNodes.getLength() > 0), "Shall not include the configuration property of referenced process" );
				
			} catch (URISyntaxException e) {
				throw new AssertionError(e.toString());
			}
		}
	}
	
	@Test(description = "Requirement 16" , groups  = "CoreAbstractProcess" , dependsOnMethods  = { "DependencyCore" })
	public void Configuration()
	{
		throw new SkipException("TODO: when a process is defined typeOf property, one should check its configuration element is existed.");
		/*NodeList configurationList = this.testSubject.getDocumentElement().getElementsByTagName("sml:configuration");
		int listLength = configurationList.getLength();
		if(listLength == 0)
		{
			throw new AssertionError("Configuration property does not exist !!");
		}
		else if(listLength > 1)
		{
			throw new AssertionError("Configuration property does not Unique !!");
		}*/
		
	}
	
	@Test(description = "Requirement 17" , groups  = "CoreAbstractProcess" , dependsOnMethods  = { "DependencyCore" })
	public void SWECommonDependency()
	{
		/*//去檢查swe這個prefix的以下元素:Dataarray,DataRecord,DataStream,DataChioce,Vctor,Matrix，並將裡面內容抓出來組成一個完整的swe的xml並且用record_components.xsd來驗證
				
		Schema schema = ValidationUtils.CreateSchema("record_components.xsd" , "http://schemas.opengis.net/sweCommon/2.0/");
		
        Validator validator = schema.newValidator();
        Source source = new DOMSource(this.testSubject);       
        ETSAssert.assertSchemaValid(validator, source);*/
	}
}
