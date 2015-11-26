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
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;

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
		File fXmlFile = new File("C:/Users/liu/Desktop/SensorML_Sample.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
	
		dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		//String nn = doc.getDocumentElement();
		//System.out.println("---------------------Output:["+nn+"]--------------------");

		NodeList aaa = doc.getDocumentElement().getElementsByTagName("sml:inputs");
		Element aaaa = (Element)aaa.item(0);
		
		
		//System.out.println("---------------------Output:["+aaaa.getAttribute(name)+"]--------------------");
		
		//aaaa.lookupNamespaceURI("sml");

		/*
		NodeList extensionList = this.testSubject.getDocumentElement().getElementsByTagName("gml:extension");
		int listLength = extensionList.getLength();
		if(listLength != 0)
		{
			for(int i=0 ; i<listLength ; i++)
			{
				Node item = extensionList.item(i);
				String namespace = item.getNamespaceURI();
					
			}
			
			
			
			throw new AssertionError("extension property does not exist !!");
		}*/
	}
	
	@Test(description = "Requirement 11" , groups  = "CoreAbstractProcess" , dependsOnMethods  = { "DependencyCore" })
	public void ExtensionRestrictions()
	{
			
	}
	
	@Test(description = "Requirement 12" , groups  = "CoreAbstractProcess" , dependsOnMethods  = { "DependencyCore" })
	public void SweCommonDependency()
	{
		Schema schema = ValidationUtils.CreateSchema("block_components.xsd" , "http://schemas.opengis.net/sweCommon/2.0/");
		
        Validator validator = schema.newValidator();
        Source source = new DOMSource(this.testSubject);       
        ETSAssert.assertSchemaValid(validator, source);
	}
	
	@Test(description = "Requirement 13" , groups  = "CoreAbstractProcess" , dependsOnMethods  = { "DependencyCore" })
	public void AggregateData()
	{
			
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
				String url = item.getAttribute("xlink:href");
				
			    String urlRegex = "\\b(https?|ftp|file|ldap)://"
			            + "[-A-Za-z0-9+&@#/%?=~_|!:,.;]"
			            + "*[-A-Za-z0-9+&@#/%=~_|]";
			    
			    Assert.assertTrue( "TypeOf Url Error !!" , url.matches(urlRegex) );
			}
		}
	}
	
	@Test(description = "Requirement 15" , groups  = "CoreAbstractProcess" , dependsOnMethods  = { "DependencyCore" })
	public void SimpleInheritance()
	{
			
	}
	
	@Test(description = "Requirement 16" , groups  = "CoreAbstractProcess" , dependsOnMethods  = { "DependencyCore" })
	public void Configuration()
	{
		NodeList configurationList = this.testSubject.getDocumentElement().getElementsByTagName("sml:configuration");
		int listLength = configurationList.getLength();
		if(listLength == 0)
		{
			throw new AssertionError("Configuration property does not exist !!");
		}
		else if(listLength > 1)
		{
			throw new AssertionError("Configuration property does not Unique !!");
		}
		
	}
	
	@Test(description = "Requirement 17" , groups  = "CoreAbstractProcess" , dependsOnMethods  = { "DependencyCore" })
	public void SWECommonDependency()
	{
		Schema schema = ValidationUtils.CreateSchema("record_components.xsd" , "http://schemas.opengis.net/sweCommon/2.0/");
		
        Validator validator = schema.newValidator();
        Source source = new DOMSource(this.testSubject);       
        ETSAssert.assertSchemaValid(validator, source);
	}
}
