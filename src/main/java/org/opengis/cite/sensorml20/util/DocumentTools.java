package org.opengis.cite.sensorml20.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.opengis.cite.sensorml20.SuiteAttribute;
import org.opengis.cite.sensorml20.level1.PhysicalSystem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.beust.jcommander.internal.Console;

/**
 * <p>
 * DocumentTools class.
 * </p>
 *
 */
public class DocumentTools {

	private static ArrayList<String> ProcessNames = new ArrayList<String>();

	/**
	 * <p>
	 * getAllNode.
	 * </p>
	 * @param node a {@link org.w3c.dom.Node} object
	 * @return a {@link java.util.ArrayList} object
	 */
	public static ArrayList<Node> getAllNode(Node node) {
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.add(node);

		for (int i = 0; i < node.getChildNodes().getLength(); i++) {
			Node tempNode = node.getChildNodes().item(i);
			if (tempNode.getLocalName() != null) {
				ArrayList<Node> childs = getAllNode(tempNode);
				nodes.addAll(childs);
			}
		}
		return nodes;
	}

	/**
	 * <p>
	 * GetMasterParentNodeName.
	 * </p>
	 * @param node a {@link org.w3c.dom.Node} object
	 * @return a {@link java.lang.String} object
	 */
	public static String GetMasterParentNodeName(Node node) {
		if (ProcessNames.size() == 0) {
			ProcessNames.add("sml:SimpleProcess");
			ProcessNames.add("sml:AggregateProcess");
			ProcessNames.add("sml:PhysicalComponent");
			ProcessNames.add("sml:PhysicalSystem");
		}

		if (ProcessNames.contains(node.getNodeName())) {
			return node.getNodeName();
		}
		else {
			Node parentNode = node.getParentNode();
			if (parentNode != null) {
				return GetMasterParentNodeName(node.getParentNode());
			}
			else {
				return "";
			}
		}
	}

	/**
	 * <p>
	 * GetElementByLocalName.
	 * </p>
	 * @param node a {@link org.w3c.dom.Node} object
	 * @param localName a {@link java.lang.String} object
	 * @return a {@link java.util.ArrayList} object
	 */
	public static ArrayList<Node> GetElementByLocalName(Node node, String localName) {
		ArrayList<Node> nodes = getAllNode(node);
		ArrayList<Node> rets = new ArrayList<Node>();

		for (Node item : nodes) {
			if (item.getLocalName().equals(localName)) {
				rets.add(item);
			}
		}
		return rets;
	}

	/**
	 * <p>
	 * MergeReference.
	 * </p>
	 * @param doc a {@link org.w3c.dom.Document} object
	 * @param docUri a {@link java.net.URI} object
	 * @throws java.net.URISyntaxException if any.
	 * @throws org.xml.sax.SAXException if any.
	 * @throws java.io.IOException if any.
	 */
	public static void MergeReference(Document doc, URI docUri) throws URISyntaxException, SAXException, IOException {
		System.out.println("Start Merging");
		if (doc != null) {
			// System.out.println("Document is not null");
			NodeList typeofNodes = doc.getElementsByTagName("sml:typeOf");
			if (typeofNodes.getLength() > 0) {
				Element typeofNode = (Element) typeofNodes.item(0);
				// TODO: the href attribute should allow relative URL
				String ref = typeofNode.getAttribute("xlink:href");
				URI uri = URIUtils.getAbsoluteUri(ref, docUri);
				Document merge = URIUtils.parseURI(uri);
				DocumentTools.MergeDocument(doc, merge);
				// System.out.println("Merged result: ");
				// System.out.println(DocumentTools.DocumentToString(doc));
				NamedNodeMap docImports = doc.getDocumentElement().getAttributes();
				NamedNodeMap mergeImports = merge.getDocumentElement().getAttributes();

				for (int mergeCount = 0; mergeCount < mergeImports.getLength(); mergeCount++) {
					Node mergeItem = mergeImports.item(mergeCount);
					Boolean isAdd = true;
					for (int docCount = 0; docCount < docImports.getLength(); docCount++) {
						Node docItem = docImports.item(docCount);
						if (docItem.getNodeName().equals(mergeItem.getNodeName())) {
							isAdd = false;
						}
					}
					if (isAdd) {
						doc.getDocumentElement().setAttribute(mergeItem.getNodeName(), mergeItem.getNodeValue());
					}
				}
			}
		}
	}

	/**
	 * <p>
	 * MergeDocument.
	 * </p>
	 * @param base a {@link org.w3c.dom.Document} object
	 * @param merge a {@link org.w3c.dom.Document} object
	 */
	public static void MergeDocument(Document base, Document merge) {
		Element mergeRootNode = merge.getDocumentElement();

		NodeList mergeRootChilds = mergeRootNode.getChildNodes();

		for (int mergeChildCount = 0; mergeChildCount < mergeRootChilds.getLength(); mergeChildCount++) {
			Node kid = mergeRootChilds.item(mergeChildCount).cloneNode(true);
			/*
			 * kid = base.importNode(kid, true);
			 * base.getDocumentElement().appendChild(kid);
			 */

			// System.out.println("Merge Count : " + mergeChildCount + " Total : " +
			// mergeRootChilds.getLength() + "Node : " + kid.getNodeName());
			// kid = base.importN ode(kid, true);
			// System.out.println("Node : " + kid.getNodeName());
			base.adoptNode(kid);
			base.getDocumentElement().appendChild(kid);

		}
	}

	/**
	 * <p>
	 * ValidateNewNameSpace.
	 * </p>
	 * @param pre a {@link java.lang.String} object
	 * @return a {@link java.lang.Boolean} object
	 */
	public static Boolean ValidateNewNameSpace(String pre) {
		if (pre.equals("sml")) {
			return false;
		}
		if (pre.equals("gml")) {
			return false;
		}
		if (pre.equals("swe")) {
			return false;
		}
		return true;
	}

	/**
	 * <p>
	 * ElementToStream.
	 * </p>
	 * @param element a {@link org.w3c.dom.Element} object
	 * @param out a {@link java.io.OutputStream} object
	 */
	public static void ElementToStream(Element element, OutputStream out) {
		try {
			DOMSource source = new DOMSource(element);
			StreamResult result = new StreamResult(out);
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();
			transformer.transform(source, result);
		}
		catch (Exception ex) {
		}
	}

	/**
	 * <p>
	 * DocumentToString.
	 * </p>
	 * @param doc a {@link org.w3c.dom.Document} object
	 * @return a {@link java.lang.String} object
	 */
	public static String DocumentToString(Document doc) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ElementToStream(doc.getDocumentElement(), baos);
		return new String(baos.toByteArray());
	}

}
