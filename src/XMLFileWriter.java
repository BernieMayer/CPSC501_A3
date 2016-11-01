import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XMLFileWriter {
	
	public static void main(String[] args)
	{
		writeXML();
	}
	
	static void writeXML()
	{
		try {
			Element company = new Element("company");
			Document doc = new Document(company);
			//doc.setRooteElement(company)
			
			Element staff = new Element("staff");
			staff.setAttribute(new Attribute("id", "1"));
			staff.addContent(new Element("firstname").setText("Allen"));
			staff.addContent(new Element("lastname").setText("Kim"));
			staff.addContent(new Element("nickname").setText("John"));
			staff.addContent(new Element("salary").setText("200000"));
			
			doc.getRootElement().addContent(staff);
			
			Element staff2 = new Element("staff");
			staff2.setAttribute(new Attribute("id", "2"));
			staff2.addContent(new Element("firstname").setText("Steve"));
			staff2.addContent(new Element("lastname").setText("Smith"));
			staff2.addContent(new Element("nickname").setText("John"));
			staff2.addContent(new Element("salary").setText("20000"));
			
			doc.getRootElement().addContent(staff2);
			
			// new XMLOutputter().output(doc, System.out);
			XMLOutputter xmlOutput = new XMLOutputter();
			
			//display nice nice
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter("test.xml"));
			
			System.out.println("File Saved!");
			
		} catch (IOException io)
		{
			System.out.println(io.getMessage());
		}
	}

}
