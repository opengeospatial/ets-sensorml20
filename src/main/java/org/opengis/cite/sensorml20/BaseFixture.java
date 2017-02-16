package org.opengis.cite.sensorml20;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.thaiopensource.util.Uri;

public class BaseFixture {
	
	protected static final String ROOT_PKG = "/org/opengis/cite/sensorml20/";
	
	protected Document originalSubject;
    protected Document testSubject;
    protected URI testSubjectUri;

    /**
     * 
     * @param testContext
     *            The test (group) context.
     */
    @BeforeClass(alwaysRun = true)
    public void obtainTestSubject(ITestContext testContext){
        Object obj = testContext.getSuite().getAttribute(
                SuiteAttribute.TEST_SUBJECT.getName());
        if ((null != obj) && Document.class.isAssignableFrom(obj.getClass())) {
            this.testSubject = Document.class.cast(obj);
            originalSubject = Document.class.cast(obj);
        }
        
        Object uriObj = testContext.getSuite().getAttribute(
                SuiteAttribute.TEST_SUBJECT_URI.getName());
        if ((null != uriObj)){        	
            this.testSubjectUri = URI.class.cast(uriObj);
            //System.out.println(this.testSubjectUri.toString());        	
        }
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
