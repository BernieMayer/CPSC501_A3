import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.*;

public class Deserializer {
	Map referenceTable;
	public Deserializer()
	{
		referenceTable = new IdentityHashMap();
	}
	
	public Object deserialize(Document document)
	{
		Element mainObjElem = document.getRootElement().getChildren().get(0);
		Class mainClass = null;
		try {
			mainClass = Class.forName(mainObjElem.getAttributeValue("class"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object mainObj = null;
		if (mainClass.isArray())
		{
			//handle the array case here 
			int length = Integer.parseInt(mainObjElem.getAttribute("length").toString());
			mainObj = Array.newInstance(mainClass.getComponentType(), length );
		} else {
			Constructor construct = null;
			try {
				construct = mainClass.getDeclaredConstructor(null);
			} catch (NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			construct.setAccessible(true);
			
			try {
				mainObj = construct.newInstance(null);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		List<Element> objects = document.getRootElement().getChildren();
		
		for (int i = 0; i < objects.size(); i ++)
		{
			
		}
		
		
		//assign field values to the mainObj
		return mainObj;
	}
	
	
	private Object setupObjectFields(Object obj, List<Element> fields)
	{
		for (int i = 0; i < fields.size(); i++)
		{
			Element fieldElem = fields.get(i);
			String className = fieldElem.getAttributeValue("declaringclass");
			Class fieldClass = null;
			try {
				fieldClass = Class.forName(className);
			} catch (ClassNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			String nameOfField = fieldElem.getAttributeValue("name");
			Field aField = null;
			try {
				aField = fieldClass.getDeclaredField(nameOfField);
			} catch (NoSuchFieldException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			aField.setAccessible(true);
			
			Element valueElem = fieldElem.getChildren().get(0);
			try {
				aField.set(obj, aField.getType());
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return obj;
	}
	
	private Object deserializeValueElement(Element valueElement, Class type)
	{
		String typeOfValueElement = valueElement.getName();
		if (typeOfValueElement.equals("null"))
		{
			return null;
		} else if (typeOfValueElement.equals("reference"))
		{
			return referenceTable.get(valueElement.getText());
		} else {
			//handle primitives here...
			if (type.equals(short.class))
				return Short.valueOf(valueElement.getText());
			else if (type.equals(byte.class))
				return Byte.valueOf(valueElement.getText());
			else if (type.equals(int.class))
				return Integer.valueOf(valueElement.getText());
			else if (type.equals(long.class))
				return Long.valueOf(valueElement.getText());
			else if (type.equals(float.class))
				return Float.valueOf(valueElement.getText());
			else if (type.equals(double.class))
				return Double.valueOf(valueElement.getText());
			else if (type.equals(char.class))
				return new Character(valueElement.getText().charAt(0));
			else if (type.equals(boolean.class))
				if (valueElement.equals("true"))
					return Boolean.TRUE;
				else 
					return Boolean.FALSE;
			else 
				return valueElement.getText();
		}
	}
	
	
	
	

}
