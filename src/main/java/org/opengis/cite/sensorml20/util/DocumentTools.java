package org.opengis.cite.sensorml20.util;

import java.util.ArrayList;

import org.w3c.dom.Node;

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
}
