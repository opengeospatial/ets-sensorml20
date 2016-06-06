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
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
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
		//先找到extension這個element，然後去檢查他所屬的prefix，而這個prefix所屬的namespace需在最上方有定義，並且由別於基本的sml、gml、swe
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
		Schema schema = ValidationUtils.CreateSchema("swe.xsd" , "http://schemas.opengis.net/sweCommon/2.0/");
        Validator validator = schema.newValidator();
        Source source = new DOMSource(this.testSubject);       
        ETSAssert.assertSchemaValid(validator, source);
		//用swe common的XSD驗證
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
	
	@Test(description = "Requirement 15" , groups  = "CoreAbstractProcess" , dependsOnMethods  = { "DependencyCore" })
	public void SimpleInheritance()
	{
		//發一個request過去看看，把typeof裡的href連結的內容抓回來，去檢查裡面不能有configuration這個屬性
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
		//去檢查swe這個prefix的以下元素:Dataarray,DataRecord,DataStream,DataChioce,Vctor,Matrix，並將裡面內容抓出來組成一個完整的swe的xml並且用record_components.xsd來驗證
		
		
		Schema schema = ValidationUtils.CreateSchema("record_components.xsd" , "http://schemas.opengis.net/sweCommon/2.0/");
		
        Validator validator = schema.newValidator();
        Source source = new DOMSource(this.testSubject);       
        ETSAssert.assertSchemaValid(validator, source);
	}
}
