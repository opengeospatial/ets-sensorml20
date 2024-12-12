package org.opengis.cite.sensorml20.level2;

import java.net.URL;

import javax.xml.transform.dom.DOMSource;

import org.opengis.cite.sensorml20.BaseFixture;
import org.opengis.cite.sensorml20.ETSAssert;
import org.testng.annotations.Test;

/**
 * <p>
 * PhysicalSystemSchema class.
 * </p>
 *
 */
public class PhysicalSystemSchema extends BaseFixture {

	/**
	 * <p>
	 * AggregateProcessSchemaValid.
	 * </p>
	 */
	@Test(description = "B.5.1 - Requirement 79")
	public void AggregateProcessSchemaValid() {
		DOMSource source = new DOMSource(this.testSubject);

		/*
		 * Schema schema = ValidationUtils.CreateSchema("physical_system.xsd" ,
		 * "http://schemas.opengis.net/sensorML/2.0/"); Validator validator =
		 * schema.newValidator(); ETSAssert.assertSchemaValid(validator, source);
		 */

		URL schRef = this.getClass().getResource("/org/opengis/cite/sensorml20/sch/physical_system.sch");
		ETSAssert.assertSchematronValid(schRef, source);
	}

}
