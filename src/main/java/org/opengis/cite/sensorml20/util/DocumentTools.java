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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DocumentTools {	
	
	private static ArrayList<String> ProcessNames = new ArrayList<String>();
	
	public static ArrayList<Node> getAllNode(Node node)
	{
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.add(node);
		
		for(int i=0 ; i<node.getChildNodes().getLength() ; i++)
		{
			Node tempNode = node.getChildNodes().item(i);
			if(tempNode.getLocalName() != null)
			{
				ArrayList<Node> childs = getAllNode(tempNode);
				nodes.addAll(childs);
			}
		}
		return nodes;
	}
	
	public static String GetMasterParentNodeName(Node node)
	{
		if(ProcessNames.size() == 0)
		{
			ProcessNames.add("sml:SimpleProcess");
			ProcessNames.add("sml:AggregateProcess");
			ProcessNames.add("sml:PhysicalComponent");
			ProcessNames.add("sml:PhysicalSystem");
		}
		
		if(ProcessNames.contains(node.getNodeName()))
		{
			return node.getNodeName();
		}
		else
		{
			Node parentNode = node.getParentNode();
			if(parentNode != null)
			{
				return GetMasterParentNodeName(node.getParentNode());
			}
			else
			{
				return "";
			}
		}
	}
	
	public static ArrayList<Node> GetElementByLocalName(Node node , String localName)
	{
		ArrayList<Node> nodes = getAllNode(node);
		ArrayList<Node> rets = new ArrayList<Node>();
		
	    for (Node item : nodes) 
	    {
	    	if(item.getLocalName().equals(localName))
	    	{
	    		rets.add(item);
	    	}
	    }
	    return rets;
	}
		
	public static void MergeReference(Document doc) throws URISyntaxException, SAXException, IOException
	{
        if(doc != null)
        {
        	NodeList typeofNodes = doc.getElementsByTagName("sml:typeOf");
        	if(typeofNodes.getLength() > 0)
        	{
        		Element typeofNode = (Element)typeofNodes.item(0);
        		String ref = typeofNode.getAttribute("xlink:href");
        		URI uri = new URI(ref);
        		DocumentTools.MergeDocument(doc, URIUtils.parseURI(uri));
        	}
        }
	}
	
	public static void MergeDocument(Document base , Document merge)
	{
		Element mergeRootNode = merge.getDocumentElement();
		
		NodeList mergeRootChilds = mergeRootNode.getChildNodes();
		
		for(int mergeChildCount = 0 ; mergeChildCount < mergeRootChilds.getLength(); mergeChildCount++)
		{
			Node kid = mergeRootChilds.item(mergeChildCount).cloneNode(true);
			/*kid = base.importNode(kid, true);
			base.getDocumentElement().appendChild(kid);	*/

			//System.out.println("Merge Count : " + mergeChildCount + " Total : " + mergeRootChilds.getLength() + "Node : " + kid.getNodeName());
			//kid = base.importN	ode(kid, true);
			//System.out.println("Node : "  + kid.getNodeName());
			base.adoptNode(kid);
			base.getDocumentElement().appendChild(kid);	

		}	
	}
	
	public static Boolean ValidateNewNameSpace(String pre)
	{
		if(pre.equals("sml"))
		{
			return false;
		}
		if(pre.equals("gml"))
		{
			return false;
		}
		if(pre.equals("swe"))
		{
			return false;
		}
		return true;
	}
	
	public static void ElementToStream(Element element, OutputStream out) 
	{
		try 
		{
			DOMSource source = new DOMSource(element);
			StreamResult result = new StreamResult(out);
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();
			transformer.transform(source, result);
		} catch (Exception ex) 
		{
		}
	}
	 
	public static String DocumentToString(Document doc) 
	{
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ElementToStream(doc.getDocumentElement(), baos);
	    return new String(baos.toByteArray());
	}
}
