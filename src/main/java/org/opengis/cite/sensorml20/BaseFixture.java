package org.opengis.cite.sensorml20;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.SchemaFactory;

import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class BaseFixture {
	
	protected static final String ROOT_PKG = "/org/opengis/cite/sensorml20/";

    protected Document testSubject;

    /**
     * 
     * @param testContext
     *            The test (group) context.
     * @throws IOException IOException
     * @throws SAXException SAXException
     */
    @BeforeClass(alwaysRun = true)
    public void obtainTestSubject(ITestContext testContext) throws SAXException, IOException {
        Object obj = testContext.getSuite().getAttribute(
                SuiteAttribute.TEST_SUBJECT.getName());
        if ((null != obj) && Document.class.isAssignableFrom(obj.getClass())) {
            this.testSubject = Document.class.cast(obj);
        }
        
        /*if(this.testSubject == null)
        {
    		try {		
    			String path = System.getProperty("user.dir") +  "/target/test-classes/sensorml/SensorML_Sample.xml";
    			File fXmlFile = new File(path);
    			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    			this.testSubject = dBuilder.parse(fXmlFile);    
    			System.out.println("[File]"+fXmlFile);
    			
    		} catch (ParserConfigurationException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        }*/

    }

    /**
     * Sets the test subject (intended only to facilitate unit testing).
     * 
     * @param testSubject
     *            A Document node representing the test subject.
     */
    public void setTestSubject(Document testSubject) {
        this.testSubject = testSubject;
    }
}
