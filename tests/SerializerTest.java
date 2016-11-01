import static org.junit.Assert.*;

import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Attribute;
import org.jdom2.DataConversionException;
import org.junit.Test;

public class SerializerTest {

	@Test
	public void testSerialize() {
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
			assertEquals( s.hashCode(), attribs.get(1).getIntValue());
		} catch (DataConversionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//fail("Not yet implemented");
	}
	
	

}
