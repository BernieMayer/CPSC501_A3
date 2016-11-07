import org.jdom2.*;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Serializer {
	
	public org.jdom2.Document serialize(Object obj) throws IllegalArgumentException, IllegalAccessException
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
		
		if (obj.getClass().isArray())
		{
			for (Field aField:obj.getClass().getFields())
			{
				if (aField.getClass().isPrimitive())
				{
					String value = aField.get(obj).toString();
					
				    Element fieldElem = new Element("field");
				    Attribute nameAttrib = new Attribute("name", aField.getName());
				    fieldElem.setAttribute(nameAttrib);
				    
				    
				    Attribute declaringClass = new Attribute("declaringclass", obj.getClass().getSimpleName());
				    fieldElem.setAttribute(declaringClass);
				    
				    Element valElem = new Element("value");
				    valElem.setText(aField.get(obj).toString());
				    
				    fieldElem.addContent(valElem);
				    
				    
				    objectTag.addContent(fieldElem);
				    
					
					
					
				} else 
				{
					//handle object references here
				}
			}
			
		} else 
		{
			//handle arrays here
			
		}
		
		return d;
	}
	
	

	
	
	
	
	
	

}
