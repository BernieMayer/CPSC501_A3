import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;
import org.jdom2.Attribute;
import org.jdom2.DataConversionException;
import org.junit.Test;

public class SerializerTest {
	
	
	@Test 
	public void testCreatingStatusObject()
	{
		Status stat = new Status();
		stat.aRandomFloat = (float) 0.245;
		stat.isRunning = false;
		stat.size = 400;
		Document d = null;
		try {
			d = new Serializer().serialize(stat);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		XMLFileWriter.wrtieXMLusingFileName(d, "testStatus.xml");
	}
	
	
	@Test
	public void testCreateObjectElement()
	{
		String s = "s";
		
		Serializer ser = new Serializer();
		Document d = null;
		try {
			d = ser.serialize(s);
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Method m = null;
		try {
			m = ser.getClass().getDeclaredMethod("createObjectElement",  new Class[] { Object.class});
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Element exp = d.getRootElement().getChild("object");
		Element elem = null;
		try {
			m.setAccessible(true);
			elem = (Element) m.invoke(ser, new Object[] {s});
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
		
		
		assertEquals(exp.getChildren().size(), elem.getChildren().size());
		//assertEquals(exp.getChildren(), elem.getChildren());
		
		XMLFileWriter.wrtieXMLusingFileName(d, "testCreateObjectElement.xml");
		
		
		
	}

	@Test
	public void testObjectsWithReferencesSerialization() throws IllegalArgumentException, IllegalAccessException
	{
		ClassB b = new ClassB();
		b.aString = "ab";
		b.stringB = "ac";
		Document doc = new Serializer().serialize(b);
		try {
    		XMLFileWriter.wrtieXMLusingFileName(doc, "testObject.xml");
    	} catch (Exception e)
    	{
    		e.printStackTrace();
    		System.out.println(e);
    	}
		
		List<Element> objs = doc.getRootElement().getChildren();
		
		List<Element> fields = objs.get(0).getChildren();
		
		assertEquals(b.getClass().getDeclaredFields().length, fields.size());
		
		XMLFileWriter.wrtieXMLusingFileName(doc, "testObjectsWithReferencesSerialization.xml");
		
	}
	
	@Test
	public void testSerialize() throws IllegalArgumentException, IllegalAccessException {
		String s = "s";
		Serializer ser = new Serializer();
		
		Document d = ser.serialize(s);
		
		try {
			XMLOutputter out = new XMLOutputter();
			out.output(d, System.out);
		} catch (Exception e)
		{
			System.out.println(e);
		}
		
		
		
		Element r = d.getRootElement();
		
		List<Element> children = r.getChildren();
		
		assertEquals(4, children.size());
		
		List<Attribute> attribs = children.get(0).getAttributes();
		
		assertEquals(2, attribs.size());
		
		assertEquals( s.getClass().getSimpleName() ,attribs.get(0).getValue());
		try {
			assertEquals( 0, attribs.get(1).getIntValue());
		} catch (DataConversionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//fail("Not yet implemented");
	}
	
	@Test
	public void testSerializeFields() throws IllegalArgumentException, IllegalAccessException 
	{
		Serializer s = new Serializer();
		Document d = s.serialize(new Car());
		
		assertEquals(3, new Car().getClass().getDeclaredFields().length);
		
		try {
			XMLOutputter out = new XMLOutputter();
			//out.output(d, System.out);
		} catch (Exception e)
		{
			System.out.println(e);
		}
		
		XMLFileWriter.wrtieXMLusingFileName(d, "testSerializeFields.xml");
		
		Element root = d.getRootElement();
		
		List<Element> obj = root.getChildren();

		//assertEquals(obj.size(), 1);
		

		
		List<Element> fields = obj.get(0).getChildren();
		System.out.println(fields);
		assertEquals(3, fields.size());
		
		assertEquals("model", fields.get(0).getAttribute("name").getValue());
		assertEquals("identifier", fields.get(1).getAttribute("name").getValue());
		assertEquals("name", fields.get(2).getAttribute("name").getValue());
		//assertEquals("", fields.get(3).getAttribute("name").getValue());
		//assertFalse(object == null);
		//assertNotTrue(object, null);
	}
	
	

}
