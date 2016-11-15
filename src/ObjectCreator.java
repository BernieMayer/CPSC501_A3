
import java.util.Scanner;

import org.jdom2.Document;
public class ObjectCreator {
	
	Scanner in;
	
	public ObjectCreator()
	{
		in = new Scanner(System.in);
	}
	
	public void printMenu()
    {
    	System.out.println("To create a simple Object (s)");
    	System.out.println("To create a object that has references to other objects (o)");
    	System.out.println("To create an object that has an array of primitives (p)");
    	System.out.println("To create an object that has a array of references(r)");
    	System.out.println("To create an object that uses Java Collection classes (c)");
    	System.out.println("To quit press (q)");
    }
    
    
    public static void main(String[] args)
    {
    	ObjectCreator objCreator = new ObjectCreator();
    	objCreator.start();
    }
    
    public void start()
    {
    	
    	
    	printMenu();
    	
    	String input = in.nextLine();
    	boolean runCreator = true;
    	
    	while (runCreator) {
    	System.out.println(input);
    	
    	if (input.equals("q"))
    	{
    		runCreator = false;
    	} else if (input.equals("s"))
    	{
    		createSimpleObject();
    	}
    	else {
    	input = in.nextLine();
    	}
    	}
    	
    }
    
    public void createSimpleObject()
    {
    	Status status = new Status();
    	for (int i = 0; i < 3; i++)
    	{
    		if (i ==0)
    		{
    			System.out.println("Enter the value for the field named size which is an int type");
    			String input = in.nextLine();
    			int aInt = Integer.parseInt(input);
    			status.size = aInt;
    			
    			
    		} else if (i == 1)
    		{
    			System.out.println("Enter the value for the field named isRunning which is a boolean type");
    			String input = in.nextLine();
    			if (input.equalsIgnoreCase("true"))
    			{
    				status.isRunning = true;
    			} else if (input.equalsIgnoreCase("false"))
    			{
    				status.isRunning = false;
    			} else {
    				System.out.println("Invalid value");
    			}
    			
    		} else {
    			System.out.println("Enter the value for the field named aRandomFloat which is a float");
    			String input = in.nextLine();
    			float aFloat = Float.parseFloat(input);
    			status.aRandomFloat = aFloat;
    			
    			
    		}
    	}
    	System.out.println("Status class object is being sent off");
    	Serializer ser = new Serializer();
    	try {
    		Document doc = ser.serialize(status);
    		XMLFileWriter.wrtieXMLusingFileName(doc, "SimpleObject.xml");
    	} catch (Exception e)
    	{
    		System.out.println(e);
    	}
    	
    	
    	
    }
    

}
