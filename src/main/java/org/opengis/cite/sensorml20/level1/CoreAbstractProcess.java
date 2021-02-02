package org.opengis.cite.sensorml20.level1;

import java.util.ArrayList;
import org.opengis.cite.sensorml20.BaseFixture;
import org.opengis.cite.sensorml20.util.DocumentTools;
import org.opengis.cite.sensorml20.util.URIUtils;
import org.opengis.cite.sensorml20.util.UrlValidate;
import org.testng.Assert;
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
import org.testng.SkipException;

public class CoreAbstractProcess extends BaseFixture{

	@Test(description = "A.2.1 - Requirement 6" , groups  = "CoreAbstractProcess" , dependsOnGroups  = { "CoreConcepts" },priority = 1)
	public void DependencyCore()
	{
		//Dependency CoreConcepts
	}

	@Test(description = "A.2.2 - Requirement 7" , groups  = "CoreAbstractProcess" , dependsOnMethods  = { "DependencyCore" , "GmlDependency" , "UniqueId" , "ExtensionIndependence" ,
			"ExtensionRestrictions" , "SweCommonDependency" , "AggregateData" , "TypeOf" , "SimpleInheritance" , "Configuration" , "SWECommonDependency"},priority = 2)
	public void PackageFullyImplemented()
	{
		//Dependency All CoreAbstractProcess Tests


	}

	@Test(description = "A.2.3 - Requirement 8" , groups  = "CoreAbstractProcess" , dependsOnMethods  = { "DependencyCore" },priority = 3)
	public void GmlDependency()
	{
		/** this requirement is fulfill in the very beginning XSD validation.
		 *  TODO: We are still looking for better implementation way to show more precise messages if the XSD error was caused by this dependency.  **/
	}

	@Test(description = "A.2.4 - Requirement 9" , groups  = "CoreAbstractProcess" , dependsOnMethods  = { "DependencyCore" },priority = 4)
	public void UniqueId()
	{
		NodeList identifierList = this.testSubject.getDocumentElement().getElementsByTagName("gml:identifier");
		int listLength = identifierList.getLength();

		ArrayList<String> idList = new ArrayList<String>();
		for(int i=0 ; i<listLength ; i++)
		{
			Node item = identifierList.item(i);
			String id = item.getTextContent();
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

	@Test(description = "A.2.5 - Requirement 10" , groups  = "CoreAbstractProcess",priority = 5, dependsOnMethods  = { "DependencyCore" })
	public void ExtensionIndependence() throws ParserConfigurationException, SAXException, IOException
	{

		NodeList extensionNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:extension");

		if (extensionNodes != null && extensionNodes.getLength() > 0){
		    //for (Node item : extensionNodes)
			for(int i = 0; i < extensionNodes.getLength(); i++)
		    {
				Node item = extensionNodes.item(i);
				NodeList childs = item.getChildNodes();

				for (int j = 0; j < childs.getLength(); j++){
			    	Node firstChild = childs.item(j);
			    	if (firstChild.getLocalName() == null)
			    		continue;
			    	System.out.println(firstChild.getPrefix()+":"+firstChild.getLocalName());
			    	String pref = firstChild.getPrefix();
			    	if (pref == null)
			    		continue;
			    	Boolean result = DocumentTools.ValidateNewNameSpace(pref);
			    	if(!result)
			    	{
			    		throw new AssertionError("Models inside of the extension property shall exist within a namespace other than SensorML. (other than sml)");
			    	}
				}

		    }
		}
	}

	@Test(description = "A.2.6 - Requirement 11" , groups  = "CoreAbstractProcess" , dependsOnMethods  = { "DependencyCore" },priority = 6)
	public void ExtensionRestrictions()
	{
		/** this requirement is fulfill in the very beginning XSD validation.
		 *  TODO: We are still looking for better implementation way to show more precise messages if the XSD error was caused by this dependency.  **/
	}

	@Test(description = "A.2.7 - Requirement 12" , groups  = "CoreAbstractProcess" , dependsOnMethods  = { "DependencyCore" },priority = 7)
	public void SweCommonDependency()
	{
		/** this requirement is fulfill in the very beginning XSD validation.
		 *  TODO: We are still looking for better implementation way to show more precise messages if the XSD error was caused by this dependency.  **/
	}

	@Test(description = "A.2.8 - Requirement 13" , groups  = "CoreAbstractProcess" , dependsOnMethods  = { "DependencyCore" },priority = 8)
	public void AggregateData()
	{
		/** this requirement is fulfill in the very beginning XSD validation.
		 *  TODO: We are still looking for better implementation way to show more precise messages if the XSD error was caused by this dependency.  **/
	}

	@Test(description = "A.2.9 - Requirement 14" , groups  = "CoreAbstractProcess",priority = 9)
	public void TypeOf()
	{
		NodeList typeofList = this.testSubject.getDocumentElement().getElementsByTagName("sml:typeOf");
		int listLength = typeofList.getLength();
		if(listLength == 0)
		{
			for(int i=0 ; i<listLength ; i++)
			{
				Element item = (Element)typeofList.item(i);

				Assert.assertTrue(item.hasAttribute("xlink:title"), "Title is not defined!!" );
				Assert.assertTrue(item.hasAttribute("xlink:href"), "Url is not defined!!" );

				String url = item.getAttribute("xlink:href");

			    String urlRegex = "\\b(https?|ftp|file|ldap)://"
			            + "[-A-Za-z0-9+&@#/%?=~_|!:,.;]"
			            + "*[-A-Za-z0-9+&@#/%=~_|]";

			    Assert.assertTrue(url.matches(urlRegex), "TypeOf Url Error !!" );
			}
		}
	}

	@Test(description = "A.2.10 - Requirement 15" , groups  = "CoreAbstractProcess" , dependsOnMethods  = { "DependencyCore" , "TypeOf" },priority = 10)
	public void SimpleInheritance() throws SAXException, IOException, URISyntaxException
	{
		NodeList typeNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:typeOf");

		for(int typeCount = 0 ; typeCount < typeNodes.getLength() ; typeCount++)
		{
			Element typeNode = (Element)typeNodes.item(typeCount);

			String url = typeNode.getAttribute("xlink:href");
			URI absoluteUri = URIUtils.getAbsoluteUri(url, this.testSubjectUri);
			Assert.assertTrue(UrlValidate.ValidateHttpUrl(absoluteUri.toString()), "referenced process error" );

		}
	}

	@Test(description = "A.2.11 - Requirement 16" , groups  = "CoreAbstractProcess" , dependsOnMethods  = { "DependencyCore" , "SimpleInheritance"},priority = 11)
	public void Configuration() throws URISyntaxException, SAXException, IOException
	{
		NodeList typeNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:typeOf");
		if(typeNodes.getLength() >  0)
		{
			Element typeNode = (Element)typeNodes.item(0);
			String url = typeNode.getAttribute("xlink:href");
			URI uri = URIUtils.getAbsoluteUri(url, this.testSubjectUri);
			Document doc = URIUtils.parseURI(uri);
			NodeList configurationNodes = doc.getDocumentElement().getElementsByTagName("sml:configuration");

			int listLength = configurationNodes.getLength();
			if(listLength == 0)
			{
				throw new AssertionError("Configuration property does not exist !!");
			}
		}

	}

	@Test(description = "A.2.12 - Requirement 17" , groups  = "CoreAbstractProcess" , dependsOnMethods  = { "DependencyCore" },priority = 12)
	public void SWECommonDependency()
	{
		/**  this requirement is fulfill in the very beginning XSD validation.
		 *  TODO: We are still looking for better implementation way to show more precise messages if the XSD error was caused by this dependency.  **/
	}
}
