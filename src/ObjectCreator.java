
import java.util.Scanner;
import java.util.Vector;

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
    	
    	String input;
    	boolean runCreator = true;
    	
    	while (runCreator) {
    	input = in.nextLine();
    	//System.out.println(input);
    	
    	if (input.equals("q"))
    	{
    		runCreator = false;
    	} else if (input.equals("s"))
    	{
    		createSimpleObject();
    	} else if (input.equals("o"))
    	{
    		createComplexObject();
    	} else if (input.equals("p"))
    	{
    		createObjectContainingPrimitiveArray();
    	} else if (input.equals("r"))
    	{
    		createObjectContainingReferencesArray();
    	} else if (input.equals("c"))
    	{
    		createVectorCollection();
    	}
    	//input = "";
    	
    	}
    	
    }
    
    public void createVectorCollection()
    {
    	Vector<String> aVec = new Vector();
    	
    	System.out.println("Creating a class called Vector");
    	System.out.println("Enter the number of strings to put into the vector");
    	
    	int numVecs = Integer.parseInt(in.nextLine());
    	for (int i = 0; i < numVecs; i++)
    	{
    		System.out.println("Enter a string");
    		aVec.add(in.nextLine());
    	}
    	System.out.print("Done creating a vector");
    	
    	Serializer ser = new Serializer();
    	Document d = null;
    	try {
    		d = ser.serialize(aVec);
    	} catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    	XMLFileWriter.wrtieXMLusingFileName(d, "vector.xml");
    	
    	
    }
    
    public void createObjectContainingReferencesArray()
    {
    	Classroom exampleRoom = new Classroom();
    	
    	System.out.println("Creating a class called ClassRoom");
    	System.out.println("Enter the length for the field attendanceList which is of type String[]");
    	int size = Integer.parseInt(in.nextLine());
    	String[] names = new String[size];
    	
    	System.out.println("You will now enter the Strings to fill this array");
    	for (int i = 0; i < size; i++)
    	{
    		System.out.println("attendanceList[" + i + "] is ");
    		names[i] = in.nextLine();
    	}
    	exampleRoom.attendanceList = names;
    	System.out.println("Done creating an an instance of Classroom");
    	
    	Serializer ser = new Serializer();
    	Document d = null;
    	try {
    		d = ser.serialize(exampleRoom);
    		XMLFileWriter.wrtieXMLusingFileName(d, "ArrayWithReferencesObject.xml");
    		System.out.println("Done creating the file");
    	} catch (Exception e)
    	{
    		System.out.println(e);
    		e.printStackTrace();
    	}
    	
    	
    	
    	
    }
    
    public void createObjectContainingPrimitiveArray()
    {
    	Data data = new Data();
    	System.out.println("Creating a class called Data");
    	System.out.println("Enter the length for the field aArray which is of type int[] ");
    	int size = Integer.parseInt(in.nextLine());
    	int[] array = new int[size];
    	System.out.println("You will now enter the integers to fill this array");
    	for (int i = 0; i < size; i++)
    	{
    		System.out.println("aArray[" + i + "] is ");
    		array[i] = Integer.parseInt(in.nextLine());
    	}
    	data.aArray = array;
    	System.out.println("Done create an instance of data");
    	
    	Serializer ser = new Serializer();
    	Document d = null;
		try {
			d = ser.serialize(data);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	XMLFileWriter.wrtieXMLusingFileName(d, "ArrayObject.xml");
    	
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
    		e.printStackTrace();
    		System.out.println(e);
    	}	
    }
    
    public void createComplexObject()
    {
    	System.out.println("Now create a GameState object");
    	GameState aGame = createGameStateObject();
    	Serializer ser = new Serializer();
    	
    	try {
    		Document doc = ser.serialize(aGame);
    		XMLFileWriter.wrtieXMLusingFileName(doc, "ComplexObject.xml");
    	} catch (Exception e)
    	{
    		System.out.println(e);
    	}
    }
    
    private GameState createGameStateObject()
    {
    	GameState g = new GameState();
    	for (int i = 0; i < 4; i++)
    	{
    		String input;
    		if (i == 0)
    		{
    			System.out.println("Enter a character name (this is a field named characterName of type string) ");
    			input = in.nextLine();
    			g.characterName = input;
    		} else if ( i == 1 )
    		{
    			System.out.println("Would you like to create a field object caled aCar of type Car (y) ?");
    			input = in.nextLine();
    			if (input.equals("y"))
    			{
    				g.aCar = createCarObject();
    			} else 
    			{
    				g.aCar = null;
    				System.out.println("The gamestate object now has a null reference for aCar");
    			}
    		} else if (i == 2)
    		{
    			System.out.println("Enter the number of objects in the game scene(primitive of type int)");
    			input = in.nextLine();
    			g.numberOfSceneObjects = Integer.parseInt(input);
    		} else {
    			System.out.println("Would you like to use this instance of GameState for the field aGameState (g)");
    			System.out.println("Do you want aGameState to be a new instance (new)");
    			System.out.println("Do you wnat aGameState to be null (null)");
    			
    			
    			input = in.nextLine();
    			
    			if (input.equals("g"))
    			{
    				g.aGameState = g;
    			} else if (input.equals("new")) {
    				g.aGameState = createGameStateObject();
    			} else {
    				g.aGameState = null;
    			}
    		}
    	}
    	
    	
    	return g;
    }
    
    private Car createCarObject()
    {
    	Car aCar = new Car();
    	for (int i = 0; i < 3; i ++)
    	{
    		String input;
    		if (i == 0)
    		{
    			System.out.println("Enter in the field model which is a String");
    			input = in.nextLine();
    			aCar.model = input;
    		} else if (i == 1)
    		{
    			System.out.println("Enter in the field identifier which is a int");
    			input = in.nextLine();
    			aCar.identifier = Integer.parseInt(input);
    			
    		} else if (i == 2)
    		{
    			System.out.println("Enter in the field name  which is a String");
    			input = in.nextLine();
    			aCar.name = input;
    		}
    	}
    	
    	return aCar;
    }
    
    
    
    

}
