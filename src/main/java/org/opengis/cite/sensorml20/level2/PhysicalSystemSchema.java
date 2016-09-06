package org.opengis.cite.sensorml20.level2;

import java.net.URL;

import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;

import org.opengis.cite.sensorml20.BaseFixture;
import org.opengis.cite.sensorml20.ETSAssert;
import org.opengis.cite.sensorml20.util.ValidationUtils;
import org.testng.annotations.Test;

public class PhysicalSystemSchema extends BaseFixture{
	@Test(description = "Requirement 79")
	public void AggregateProcessSchemaValid()
	{
		DOMSource source = new DOMSource(this.testSubject);
		      
/*		Schema schema = ValidationUtils.CreateSchema("physical_system.xsd" , "http://schemas.opengis.net/sensorML/2.0/");
        Validator validator = schema.newValidator();
        ETSAssert.assertSchemaValid(validator, source);*/
				
		URL schRef = this.getClass().getResource(
				"/org/opengis/cite/sensorml20/sch/physical_system.sch");
		ETSAssert.assertSchematronValid(schRef, source);
	}
}
