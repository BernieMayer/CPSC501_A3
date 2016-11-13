import static org.junit.Assert.*;

import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;
import org.jdom2.Attribute;
import org.jdom2.DataConversionException;
import org.junit.Test;

public class SerializerTest {

	@Test
	public void testSerialize() throws IllegalArgumentException, IllegalAccessException {
		String s = "s";
		Serializer ser = new Serializer();
		
		Document d = ser.serialize(s);
		
		Element r = d.getRootElement();
		
		List<Element> children = r.getChildren();
		
		assertEquals(1, children.size());
		
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
			out.output(d, System.out);
		} catch (Exception e)
		{
			System.out.println(e);
		}
		
		Element root = d.getRootElement();
		
		List<Element> obj = root.getChildren();
		
		assertEquals(obj.size(), 1);
		

		
		List<Element> fields = obj.get(0).getChildren();
		//assertEquals(3, fields.size());
		
		assertEquals("model", fields.get(0).getAttribute("name").getValue());
		assertEquals("identifier", fields.get(1).getAttribute("name").getValue());
		assertEquals("name", fields.get(2).getAttribute("name").getValue());
		//assertEquals("", fields.get(3).getAttribute("name").getValue());
		//assertFalse(object == null);
		//assertNotTrue(object, null);
	}
	
	

}
