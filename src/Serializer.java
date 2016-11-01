import org.jdom2.*;

import java.util.ArrayList;

public class Serializer {
	
	public org.jdom2.Document serialize(Object obj)
	{
		Element baseElement = new Element("serialized");
		Document d = new Document(baseElement);
		
		String className = obj.getClass().getSimpleName();
		
		Element objectTag = new Element("Object");
		Attribute name = new Attribute("class", className);
		Attribute id = new Attribute("id", Integer.toString( obj.hashCode()));
		ArrayList<Attribute> attribs = new ArrayList<Attribute>();
		
		attribs.add(name);
		attribs.add(id);
		
		objectTag.setAttributes(attribs);
		d.getRootElement().addContent(objectTag);
		
		
		return d;
	}
	
	
	
	

}
