import org.jdom2.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Map;

public class Serializer {
	
	//private Document d;
	private Map referenceTable;
	private ArrayList<Object> objectsToSerialize;
	public Serializer()
	{
		//d = new Document(new Element("serialized"));
		referenceTable = new IdentityHashMap();
		objectsToSerialize = new ArrayList<Object>();
	}
	
	public org.jdom2.Document serialize(Object obj) throws IllegalArgumentException, IllegalAccessException
	{
		String id = Integer.toString(referenceTable.size());
		referenceTable.put(obj, id);
		Element baseElement = new Element("serialized");
		Document d = new Document(baseElement);
		
		
		
		Element objectTag = new Element("Object");
		if (obj == null)
		{
			objectTag.addContent(new Element("null"));
			return d;
		}
		
		
		
		Attribute name = new Attribute("class", obj.getClass().getSimpleName());
		Attribute idAttrib = new Attribute("id", id);
		ArrayList<Attribute> attribs = new ArrayList<Attribute>();
		
		attribs.add(name);
		attribs.add(idAttrib);
		
		objectTag.setAttributes(attribs);
		d.getRootElement().addContent(objectTag);
		
		
		if (! obj.getClass().isArray())
		{
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field aField:fields)
			{
				aField.setAccessible(true);
				Object fieldObj = aField.get(obj);
				
				if (fieldObj == null)
				{
					Element fieldElem = createPrimitiveElement(obj, aField);
					
					objectTag.addContent(fieldElem);
					
				}
				
				if (aField.getType().isPrimitive())
				{
					
				    Element fieldElem = createPrimitiveElement(obj, aField);
				    
				    
				    objectTag.addContent(fieldElem);
				    
					
					
					
				} else 
				{
					Element fieldElem = new Element("field");
					Attribute nameAttrib = new Attribute("name", aField.getName());
					fieldElem.setAttribute(nameAttrib);
					
					Element ref = new Element("reference");
					
					if (referenceTable.containsKey(fieldObj))
					{
						ref.setText( referenceTable.get(fieldObj).toString());
					} else {
						ref.setText(Integer.toString(referenceTable.size()));
						referenceTable.put(fieldObj, Integer.toString(referenceTable.size()));
						Element objElem = createObjectElement(fieldObj);
						d.getRootElement().addContent(objElem);
					}
					
					
					//objectTag.addContent(fieldElem);

					
					
					//handle object references here
					//System.out.println("handling object of type " + fieldObj.getClass().getSimpleName() );
				}
			}
			
		} else 
		{
			//handle arrays here
			System.out.println("handling array");
			
		}
		return d;
	}


	/**
	 * @param obj
	 * @param aField
	 * @return
	 * @throws IllegalAccessException
	 */
	private Element createPrimitiveElement(Object obj, Field aField) throws IllegalAccessException {
		Element fieldElem = new Element("field");
		Attribute nameAttrib = new Attribute("name", aField.getName());
		fieldElem.setAttribute(nameAttrib);
		
		
		Attribute declaringClass = new Attribute("declaringclass", obj.getClass().getSimpleName());
		fieldElem.setAttribute(declaringClass);
		
		Element valElem = new Element("value");
		valElem.setText(aField.get(obj).toString());

		fieldElem.addContent(valElem);
		return fieldElem;
	}


	private Element createObjectElement(Object object) throws IllegalArgumentException, IllegalAccessException {
		
		if (object == null)
		{
			return new Element("null");
		}
		
		Element elem = new Element("object");
		String id = (String) referenceTable.get(object);
		Attribute name = new Attribute("class", object.getClass().getSimpleName());
		Attribute idAttrib = new Attribute("id", id);
		ArrayList<Attribute> attribs = new ArrayList<Attribute>();
		
		attribs.add(name);
		attribs.add(idAttrib);
		
		elem.setAttributes(attribs);
		if ( ! object.getClass().isArray())
		{
			//get the fields here
			Field[] fields = object.getClass().getFields();
			
			
			for (Field aField:fields)
			{
				aField.setAccessible(true);
				Object fieldObj = aField.get(object);
				if (fieldObj == null)
				{
					Element fieldElem = new Element("field");
					Attribute declaringClass = new Attribute("declaringclass", object.getClass().getSimpleName());
					fieldElem.setAttribute(declaringClass);
					Attribute nameAtt = new Attribute("name", aField.getName());
					fieldElem.setAttribute(nameAtt);
					
					Element valElem = new Element("value").setText("null");
					
					fieldElem.addContent(valElem);
					
					elem.addContent(fieldElem);
					
				}
				
				if (fieldObj.getClass().isPrimitive())
				{
					elem.addContent(createPrimitiveElement(object, aField));
				} else {
					this.objectsToSerialize.add(fieldObj);
				}
			}
			
			
		} else {
			//handle non array case here
			return new Element("Array");
		}

		
		

		return elem;

	}
	
	//public 
	
	
	
	
	

}
