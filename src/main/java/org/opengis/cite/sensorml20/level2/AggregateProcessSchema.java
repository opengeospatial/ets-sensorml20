package org.opengis.cite.sensorml20.level2;

import java.net.URL;
import java.util.ArrayList;
import javax.xml.transform.dom.DOMSource;
import org.opengis.cite.sensorml20.BaseFixture;
import org.opengis.cite.sensorml20.ETSAssert;
import org.opengis.cite.sensorml20.util.DocumentTools;
import org.opengis.cite.sensorml20.util.UrlValidate;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <p>
 * AggregateProcessSchema class.
 * </p>
 *
 */
public class AggregateProcessSchema extends BaseFixture {

	/**
	 * <p>
	 * AggregateProcessSchemaValid.
	 * </p>
	 */
	@Test(description = "B.3.1 - Requirement 64")
	public void AggregateProcessSchemaValid() {
		DOMSource source = new DOMSource(this.testSubject);

		/*
		 * Schema schema = ValidationUtils.CreateSchema("aggregate_process.xsd" ,
		 * "http://schemas.opengis.net/sensorML/2.0/"); Validator validator =
		 * schema.newValidator(); ETSAssert.assertSchemaValid(validator, source);
		 */

		URL schRef = this.getClass().getResource("/org/opengis/cite/sensorml20/sch/aggregate_process.sch");
		ETSAssert.assertSchematronValid(schRef, source);
	}

	/**
	 * <p>
	 * ComponentReference.
	 * </p>
	 */
	@Test(description = "B.3.2 - Requirement 65")
	public void ComponentReference() {
		NodeList componentNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:component");
		for (int i = 0; i < componentNodes.getLength(); i++) {
			Node component = componentNodes.item(i);
			ArrayList<Node> componentChilds = DocumentTools.getAllNode(component);

			Boolean isRef = false;

			for (int j = 0; j < componentChilds.size(); j++) {
				if (componentChilds.get(j).getLocalName() != null) {
					Element comChild = (Element) componentChilds.get(j);

					if (comChild.getNodeName().equals("sml:setValue") && comChild.hasAttribute("ref")) {
						isRef = true;
						break;
					}
				}
			}

			if (isRef) {
				ArrayList<String> uids = new ArrayList<String>();

				NodeList identifiers = this.testSubject.getDocumentElement().getElementsByTagName("gml:identifier");
				for (int identifiersCount = 0; identifiersCount < identifiers.getLength(); identifiersCount++) {
					uids.add(identifiers.item(identifiersCount).getTextContent());
				}

				for (int j = 0; j < componentChilds.size(); j++) {
					if (componentChilds.get(j).getLocalName() != null
							&& componentChilds.get(j).getNodeName().equals("sml:typeOf")) {
						Element comChild = (Element) componentChilds.get(j);

						if (comChild.hasAttribute("xlink:title")) {
							String titleAttribute = comChild.getAttribute("xlink:title");

							if (uids.contains(titleAttribute)) {
								throw new AssertionError("value of xlink:title attribute shall be a uniqueID");
							}

							uids.add(titleAttribute);
						}
						else {
							throw new AssertionError(
									"component property shall require meaningful values for the xlink:title");
						}

						if (comChild.hasAttribute("xlink:href")) {
							if (!UrlValidate.ValidateHttpUrl(comChild.getAttribute("xlink:href"))) {
								throw new AssertionError("component shall define a resolvable URL for reference");
							}
						}
						else {
							throw new AssertionError(
									"component property shall require meaningful values for the xlink:href");
						}
					}
				}
			}
		}
	}

	/**
	 * <p>
	 * InputConnectionRestrictions.
	 * </p>
	 */
	@Test(description = "B.3.2 - Requirement 66")
	public void InputConnectionRestrictions() {
		NodeList inputNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:input");

		NodeList connectNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:connection");
		for (int connectCount = 0; connectCount < connectNodes.getLength(); connectCount++) {
			if (connectNodes.item(connectCount).hasChildNodes()) {
				NodeList linkNodes = connectNodes.item(connectCount).getChildNodes();

				for (int linkCount = 0; linkCount < linkNodes.getLength(); linkCount++) {
					Node link = linkNodes.item(linkCount);
					if (link.getLocalName() != null && link.getNodeName().equals("sml:Link")) {
						String source = "";
						String destination = "";

						NodeList linkChilds = link.getChildNodes();
						for (int linkChildCount = 0; linkChildCount < linkChilds.getLength(); linkChildCount++) {
							if (linkChilds.item(linkChildCount).getLocalName() != null) {
								Element linkChild = (Element) linkChilds.item(linkChildCount);
								if (linkChild.getNodeName().equals("sml:source")) {
									source = linkChild.getAttribute("ref");
								}
								if (linkChild.getNodeName().equals("sml:destination")) {
									destination = linkChild.getAttribute("ref");
								}
							}
						}

						if (source.indexOf("input") != -1 && destination.indexOf("input") != -1) {
							Boolean result = false;

							String[] sourceSplits = source.split("/");
							String inputName = sourceSplits[sourceSplits.length - 1];
							for (int inputCount = 0; inputCount < inputNodes.getLength(); inputCount++) {
								Element inputNode = (Element) inputNodes.item(inputCount);
								if (inputNode.getAttribute("name").equals(inputName)) {
									String processName = DocumentTools.GetMasterParentNodeName(inputNode);
									if (processName == "sml:AggregateProcess") {
										result = true;
									}
								}

							}

							Assert.assertTrue(result, "input-to-input connections shall from aggregate process");
						}
					}
				}
			}
		}
	}

	/**
	 * <p>
	 * OutputConnectionRestrictions.
	 * </p>
	 */
	@Test(description = "B.3.2 - Requirement 67")
	public void OutputConnectionRestrictions() {
		NodeList outputNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:output");

		NodeList connectNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:connection");
		for (int connectCount = 0; connectCount < connectNodes.getLength(); connectCount++) {
			if (connectNodes.item(connectCount).hasChildNodes()) {
				NodeList linkNodes = connectNodes.item(connectCount).getChildNodes();

				for (int linkCount = 0; linkCount < linkNodes.getLength(); linkCount++) {
					Node link = linkNodes.item(linkCount);
					if (link.getLocalName() != null && link.getNodeName().equals("sml:Link")) {
						String source = "";
						String destination = "";

						NodeList linkChilds = link.getChildNodes();
						for (int linkChildCount = 0; linkChildCount < linkChilds.getLength(); linkChildCount++) {
							if (linkChilds.item(linkChildCount).getLocalName() != null) {
								Element linkChild = (Element) linkChilds.item(linkChildCount);
								if (linkChild.getNodeName().equals("sml:source")) {
									source = linkChild.getAttribute("ref");
								}
								if (linkChild.getNodeName().equals("sml:destination")) {
									destination = linkChild.getAttribute("ref");
								}
							}
						}

						if (source.indexOf("output") != -1 && destination.indexOf("output") != -1) {
							Boolean result = false;

							String[] destSplits = destination.split("/");
							String outputName = destSplits[destSplits.length - 1];
							for (int outputCount = 0; outputCount < outputNodes.getLength(); outputCount++) {
								Element outputNode = (Element) outputNodes.item(outputCount);
								if (outputNode.getAttribute("name").equals(outputName)) {
									String processName = DocumentTools.GetMasterParentNodeName(outputNode);
									if (processName == "sml:AggregateProcess") {
										result = true;
									}
								}

							}

							Assert.assertTrue(result, "output cannot connect to another output of aggregate process");
						}
					}
				}
			}
		}
	}

	/**
	 * <p>
	 * MultipleConnections.
	 * </p>
	 */
	@Test(description = "B.3.2 - Requirement 68")
	public void MultipleConnections() {
		ArrayList<String> sources = new ArrayList<String>();

		NodeList connectNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:connection");
		for (int connectCount = 0; connectCount < connectNodes.getLength(); connectCount++) {
			if (connectNodes.item(connectCount).hasChildNodes()) {
				NodeList linkNodes = connectNodes.item(connectCount).getChildNodes();

				for (int linkCount = 0; linkCount < linkNodes.getLength(); linkCount++) {
					Node link = linkNodes.item(linkCount);
					if (link.getLocalName() != null && link.getNodeName().equals("sml:Link")) {
						NodeList linkChilds = link.getChildNodes();
						for (int linkChildCount = 0; linkChildCount < linkChilds.getLength(); linkChildCount++) {
							if (linkChilds.item(linkChildCount).getLocalName() != null) {
								Element linkChild = (Element) linkChilds.item(linkChildCount);
								if (linkChild.getNodeName().equals("sml:source") && linkChild.hasAttribute("ref")) {
									String connectRef = linkChild.getAttribute("ref");
									if (connectRef.indexOf("") != -1) {
										if (sources.contains(connectRef)) {
											throw new AssertionError("an input can only have one source connection");
										}
										else {
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

	/**
	 * <p>
	 * ParameterConnectionrestrictions.
	 * </p>
	 */
	@Test(description = "B.3.2 - Requirement 69")
	public void ParameterConnectionrestrictions() {
		NodeList connectNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:connection");
		for (int connectCount = 0; connectCount < connectNodes.getLength(); connectCount++) {
			if (connectNodes.item(connectCount).hasChildNodes()) {
				NodeList linkNodes = connectNodes.item(connectCount).getChildNodes();

				for (int linkCount = 0; linkCount < linkNodes.getLength(); linkCount++) {
					Node link = linkNodes.item(linkCount);
					if (link.getLocalName() != null && link.getNodeName().equals("sml:Link")) {
						NodeList linkChilds = link.getChildNodes();
						for (int linkChildCount = 0; linkChildCount < linkChilds.getLength(); linkChildCount++) {
							if (linkChilds.item(linkChildCount).getLocalName() != null) {
								Element linkChild = (Element) linkChilds.item(linkChildCount);
								if (linkChild.getNodeName().equals("sml:source")) {
									if (linkChild.getAttribute("ref").indexOf("parameter") != -1) {
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

	/**
	 * <p>
	 * PropertyConnectionrestrictions.
	 * </p>
	 */
	@Test(description = "B.3.2 - Requirement 70")
	public void PropertyConnectionrestrictions() {
		NodeList connectNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:connection");
		for (int connectCount = 0; connectCount < connectNodes.getLength(); connectCount++) {
			if (connectNodes.item(connectCount).hasChildNodes()) {
				NodeList linkNodes = connectNodes.item(connectCount).getChildNodes();

				for (int linkCount = 0; linkCount < linkNodes.getLength(); linkCount++) {
					Node link = linkNodes.item(linkCount);
					if (link.getLocalName() != null && link.getNodeName().equals("sml:Link")) {
						NodeList linkChilds = link.getChildNodes();
						for (int linkChildCount = 0; linkChildCount < linkChilds.getLength(); linkChildCount++) {
							if (linkChilds.item(linkChildCount).getLocalName() != null) {
								Element linkChild = (Element) linkChilds.item(linkChildCount);
								if (linkChild.getNodeName().equals("sml:destination")) {
									String connectRef = linkChild.getAttribute("ref");

									if (connectRef.indexOf("input") == -1 && connectRef.indexOf("output") == -1
											&& connectRef.indexOf("parameter") == -1) {
										throw new AssertionError("destinations shall be a input , output or parameter");
									}
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * <p>
	 * DesignatingLinkPaths.
	 * </p>
	 */
	@Test(description = "B.3.2 - Requirement 71")
	public void DesignatingLinkPaths() {
		NodeList linkNodes = this.testSubject.getDocumentElement().getElementsByTagName("sml:Link");
		for (int linkCount = 0; linkCount < linkNodes.getLength(); linkCount++) {
			Element link = (Element) linkNodes.item(linkCount);
			NodeList sourceNodes = link.getElementsByTagName("sml:source");
			for (int sourceCount = 0; sourceCount < sourceNodes.getLength(); sourceCount++) {
				Element source = (Element) sourceNodes.item(sourceCount);
				if (source.hasAttribute("ref")) {
					String sourceRef = source.getAttribute("ref");
					Boolean validateResult = ValidateLinkRef(
							(Element) this.testSubject.getDocumentElement().cloneNode(true), sourceRef);
					Assert.assertTrue(validateResult, "path rule error");

				}
				else {
					throw new AssertionError("destinations shall be a input , output or parameter");
				}
			}

			NodeList destinationNodes = link.getElementsByTagName("sml:destination");
			for (int destinationCount = 0; destinationCount < destinationNodes.getLength(); destinationCount++) {
				Element destination = (Element) destinationNodes.item(destinationCount);
				if (destination.hasAttribute("ref")) {
					String destinationRef = destination.getAttribute("ref");
					String[] paths = destinationRef.split("/");
					for (String path : paths) {
						Boolean lowerCamelCaseCheckResult = Character.isLowerCase(path.charAt(0));
						Assert.assertTrue(lowerCamelCaseCheckResult, "path rule error");
					}

					Boolean validateResult = ValidateLinkRef(
							(Element) this.testSubject.getDocumentElement().cloneNode(true), destinationRef);
					Assert.assertTrue(validateResult, "path rule error");
				}
				else {
					throw new AssertionError("destinations shall be a input , output or parameter");
				}
			}
		}
	}

	private Boolean ValidateLinkRef(Element node, String ref) {
		String[] refSplite = ref.split("/");

		if (refSplite.length == 0) {
			return false;
		}

		// Base Test
		NodeList baseElements = node.getChildNodes();
		Boolean isBase = false;
		for (int baseCount = 0; baseCount < baseElements.getLength(); baseCount++) {
			if (baseElements.item(baseCount).getLocalName() != null) {
				if (baseElements.item(baseCount).getLocalName().equals(refSplite[0])) {
					isBase = true;
				}
			}
		}
		if (!isBase) {
			return false;
		}

		ArrayList<Node> pathNodes = new ArrayList<Node>();

		for (int refCount = 0; refCount < refSplite.length; refCount++) {
			String name = refSplite[refCount];
			if (refCount == 0) {
				NodeList targetNodes = node.getElementsByTagName("sml:" + name);
				if (targetNodes.getLength() > 0) {
					for (int targetCount = 0; targetCount < targetNodes.getLength(); targetCount++) {
						pathNodes.add(targetNodes.item(targetCount));
					}
				}
				else {
					return false;
				}
			}
			else {
				ArrayList<Node> newPathNodes = new ArrayList<Node>();

				for (int pathNodeCount = 0; pathNodeCount < pathNodes.size(); pathNodeCount++) {
					Element pathNode = (Element) pathNodes.get(pathNodeCount);
					ArrayList<Node> tempNodes = DocumentTools.getAllNode(pathNode);
					for (int tempCount = 0; tempCount < tempNodes.size(); tempCount++) {
						if (tempNodes.get(tempCount) != null) {
							Element tempNode = (Element) tempNodes.get(tempCount);
							if (tempNode.getLocalName().equals(name) || tempNode.getAttribute("name").equals(name)) {
								newPathNodes.add(tempNode);
							}
						}
					}
				}
				if (newPathNodes.size() > 0) {
					pathNodes = newPathNodes;
				}
				else {
					return false;
				}
			}
		}

		return true;
	}

}
