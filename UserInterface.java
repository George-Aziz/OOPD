/*****************************************************************************
*  Author: George Aziz
*  Date Created: 04/05/2019
*  Date Last Modified: 26/05/2019
*  Purpose: Handles all user input / output for this program
******************************************************************************/

import java.util.*;
import java.io.*;
import java.lang.Math.*;

public class UserInterface
{	
	//Class Fields 
	private ShipStorage sStore;
    private FileManager fileMgr;
	
	/************************************************************
	Default Constructor: 
	IMPORT: none
	EXPORT: none
	ASSERTION: 
    *************************************************************/
	public UserInterface()
	{
		sStore = new ShipStorage();
        fileMgr = new FileManager();
	}
	

	/************************************************************
	SUBMODULE: mainMenu
	IMPORT: none
	EXPORT: none
	ASSERTION: Main menu that's the first output the user sees and is able to go into other menus 
    *************************************************************/
	public void mainMenu()
	{
        int option = 0; //Option by default is invalid so the menu keeps looping
		do
		{
			//First output prompt to the user
			System.out.println("\n\nWelcome, please select one of the options listed below: \n");           
			System.out.println("1: Load ships from file");                                    
			System.out.println("2: Add a submarine");
			System.out.println("3: Add a fighter jet");
			System.out.println("4: Check which ship is the fastest");                                     
			System.out.println("5: Find Duplicates");                                                    
			System.out.println("6: View the ships");
			System.out.println("7: Save ships to file");
			System.out.println("8: Exit \n");
			
			option = inputInteger("Please input the number next to the option:","ERROR: That wasn't a choice. Please select one of the 8 options \n \n",1,8); 
			
			switch(option)
			{
				case 1:
					addShipMenuCSV(sStore, fileMgr); //If user wants to add Ships from a file
				break;
				case 2:
					addSubs(sStore); //If user wants to add a submarine manually
				break;
				case 3:
					addJets(sStore); //If the user wants to add a fighter jet manually
				break;	
				case 4:
					if (sStore.getShipCount() < 1) //If ship count is 0 then that means addShip has never been called
					{
						System.out.println("There are no ships available. Please either add or load ships");
					}
					else
					{
						destinationCheckMenu(sStore); //If the user wants to check which ship is the fastest
					}
				break;
				case 5:
					displayDupe(sStore); //If the user wants to check if there are any duplicates 
					break;
					
				case 6:
					viewShipsMenu(); //If the user wants to view all ships that are currently stored 
					break;
					
				case 7:
					saveShips(sStore, fileMgr); //If the user wants to save all the ships currently stored in the shipArray
					break;
					
				case 8:
					System.out.println("You have selected to exit. Good Bye"); //If the user wants to exit the program
					break;
			}

		} 
		while (option != 8); //Validation to ensure that this menu keeps being outputted unless the options in the selected range has been chosen 
	}

	/************************************
	SUBMODULE: getFileName
	IMPORT: none 
	EXPORT: fileName (String)
	ASSERTION: Retrieves the file name
	************************************/
	public String getFileName()
	{
		Scanner sc = new Scanner(System.in);
		String fileName;
		System.out.println("\nPlease enter the file name with its extension (.csv): ");

		fileName = sc.nextLine(); //User inputs the file name

		return fileName;
	}
	
	/*******************************************************************
	SUBMODULE: addShipMenuCSV (loads the file)
	IMPORT: sStore (ShipStorage), filemgr (FileManager)
	EXPORT: none
	ASSERTION: Menu for when the user picks to load ships from a CSV file
	*********************************************************************/
	public void addShipMenuCSV(ShipStorage sStore, FileManager fileMgr) 
	{
		int tempShipCount = sStore.getShipCount(); //ShipCount before any file has been loaded
		String fileName;
		int count = 0; //Count is 0 since no line has been processed yet
		
		//Initial input to see if the file exists
		fileName = getFileName();
		count = fileMgr.readFile(fileName, sStore);

		//If the file inputted by the user is invalid, the while loop will start with the error message first and then another input
		while (count == 0)		
		{
			System.out.println("Invalid file!\n");
			fileName = getFileName();

			count = fileMgr.readFile(fileName, sStore); //The readfile method will return a count value tha represents the amount of valid lines processed
		}
		
		if (count != 0) //If the count is nott 0, meaning more than 0 then there is at least one ship loaded into the system
		{
			int counter = sStore.getShipCount() - tempShipCount; //If there any ships in the current system the new shipCount minus tempShipCount will result in the number of ships loaded from file
			System.out.println(counter  + " ships loaded!"); //To notify loading the ships were succesful to the user
		}	
		
	}
	
	/********************************************************************************
	SUBMODULE: saveShips
	IMPORT: sStore (ShipStorage), fileMgr (FileManager)
	EXPORT: none
	ASSERTION: When the user wants to save ships into a file
	*********************************************************************************/
	public void saveShips(ShipStorage sStore, FileManager fileMgr)
	{
		System.out.println("\nNOTE: If you enter a file that already exists, it will overwrite the file!");
		String fileName = getFileName();

		fileMgr.writeFile(fileName, sStore);
	}
	
	/*****************************************************************************************
	SUBMODULE: addSubs
	IMPORT: sStore (ShipStorage)
	EXPORT: none
	ASSERTION: Inputs for all submarine ship type
	******************************************************************************************/
	public void addSubs(ShipStorage sStore)
	{	
		String serialNum, fuel, hull;
		int inputComYear, inputCylinders;
		double inputMaxDepth;
		
		if (sStore.getShipCount() >= 30) //If ship count is larger than or equal to 30 that means there is no more space in the array hence an error message must be displayed to notify the user
		{
			System.out.println("There is no space left for a file to be added!");
		}
		else
		{	
			//User input for the serial number
			serialNum = inputSerialNum("\nPlease input the serial number (XXX.YYY):", "ERROR: Format must be XXX.YYY \nXXX must be in the range of 100 - 300 (Inclusive)\nYYY must be in the range of 001 to 999\nIf you want to input a single integer, it must be 001" );

			
			//User input for commission year
			inputComYear = inputInteger("\nPlease input the commission year (1950 - 2022)", "ERROR: year must be from range of 1950 - 2022 (Inclusive). No decimal points!", 1950, 2022); //No need for calling validation submodule since input submodule already validates input


			//User input for ship cylinders
			inputCylinders = inputInteger("\nPlease input the amount of cylinders (2 - 20):", "ERROR: Cylinders can only be from 2 - 20 (Inclusive)", 2, 20);

			//User input for fuel type
			fuel = inputFuel("\nPlease input the fuel type of the ship (Battery/Diesel/Bio):", "ERROR: Fuel types can only be either Battery, Diesel, or Bio");

			Engine subEngine = new Engine(inputCylinders, fuel); //Construct subEngine using inputCylinders and fuel

			//User input for hull material
			hull = inputHull("\nPlease input the hull material (Steel/Alloy/Titanium):", "ERROR: Materials must be either Steel, Alloy or Titanium");
			

			//User input for the maximum depth
			inputMaxDepth = inputDouble("\nPlease input the maximum depth of the submarine (-500.0 - 0.0):", "ERROR: Maximum depth must be in range of -500.0 to 0.0 (inclusive)!", -500.0, 0.0); //No need for calling validation submodule since input submodule already validates input

			//Construct subObject Using inputSerialNum, inputComYear, inputHull, inputMaxDepth
			Submarine subObject = new Submarine(serialNum, inputComYear, subEngine, hull, inputMaxDepth);

			sStore.addShip(subObject);	
		}
	}
	
	/*****************************************************************************************
	SUBMODULE: addJets
	IMPORT: sStore (ShipStorage)
	EXPORT: none
	ASSERTION: Inputs for all fighter jet ship type
	******************************************************************************************/
	public void addJets (ShipStorage sStore)
	{
		String serialNum, fuel, ordnance;
		int inputCylinders, inputComYear;
		double inputWingSpan;

		if (sStore.getShipCount() >= 30) //If ship count is larger than or equal to 30 that means there is no more space in the array hence an error message must be displayed to notify the user
		{
			System.out.println("There is no space left for a file to be added!");
		}
		else
		{
			//User input for the serial number
			serialNum = inputSerialNum("\nPlease input the serial number (XXX.YYY):", "ERROR: Format must be XXX.YYY \nXXX must be in the range of 100 - 300 (Inclusive)\nYYY must be in the range of 001 to 999\nIf you want to input a single integer, it must be 001" );


			//User input for commission year
			inputComYear = inputInteger("\nPlease input the commission year (1950 - 2022)", "ERROR: year must be from range of 1950 - 2022 (Inclusive). No decimal points!", 1950, 2022); //No need for calling validation submodule since input submodule already validates input


			//User input for ship cylinders
			inputCylinders = inputInteger("\nPlease input the amount of cylinders (2 - 20):", "ERROR: Cylinders can only be from 2 - 20 (Inclusive)", 2, 20);
			

			//User input for fuel type
			fuel = inputFuel("\nPlease input the fuel type of the ship (Battery/Diesel/Bio):", "ERROR: Fuel types can only be either Battery, Diesel, or Bio");

			Engine jetEngine = new Engine(inputCylinders, fuel); //Construct subEngine using inputCylinders and fuel


			//User input for the wing span
			inputWingSpan = inputDouble("\nPlease input the wing span of the fighter jet (Range from 2.20 - 25.6):", "ERROR: Wing span must be in range of 2.20 to 25.6 (inclusive)!", 2.20, 25.6); //No need for calling validation submodule since input submodule already validates input

			ordnance = inputOrdnance("\nPlease input the ordnance of the fighter jet:"); //User input for the ordnance. No error message needed as anything can be ordnance as long as it isn't empty

			//Construct jetObject Using inputOrdnance and inputWingSpan
			FighterJet jetObject = new FighterJet(serialNum, inputComYear, jetEngine, inputWingSpan, ordnance);

			sStore.addShip(jetObject);
		}
	}
	
	
	/*****************************************************************************************
	SUBMODULE: destinationCheckMenu
	IMPORT: sStore (ShipStorage)
	EXPORT: distance (Integer)
	ASSERTION: When the user wants to see which ship is the fastest, this menu will be called
	******************************************************************************************/
	public int destinationCheckMenu(ShipStorage sStore) 
	{
		int distance;
		
		distance = inputInteger("\nPlease input the distance to check which ship is the fastest", "ERROR: Input must be a positive number!", 0, Integer.MAX_VALUE); //Ensures the input can only be positive
		
		String fastestShip = sStore.destinationCheck(distance);
		System.out.println(fastestShip); 

		return distance;
	}
	
	/********************************************************************************
	SUBMODULE: displayDupe
	IMPORT: sStore (ShipStorage)
	EXPORT: none
	ASSERTION: When the user wants to check if any of the stored ships are duplicates
	*********************************************************************************/
	public void displayDupe(ShipStorage sStore) 
	{
		String [] dupeArray = sStore.findDuplicates();
		System.out.println("The following are duplicate ships: ");
		
		if (dupeArray[0] == null) //If there are no ships to check in the array
		{
			System.out.println("There are no duplicates");
		}
		
		for (int index = 0; index < dupeArray.length; index++)
		{
			if (dupeArray[index] != null) //If the array is filled with elements (Means there are duplicates)
			{
				System.out.println(dupeArray[index]);
			}
		}
	}
	
	/************************************************************
	SUBMODULE: viewShipsMenu
	IMPORT: sStore (ShipStorage)
	EXPORT: none
	ASSERTION: view all the ships
    *************************************************************/
	public void viewShipsMenu()
	{
		String[] viewShipArray = sStore.viewShips();
		//Based on code from UserInterface.java from worked examples 
	
		if (viewShipArray[0] == null) //If there are no ships to view
		{
			System.out.println("\nThere are no ships loaded to view!");
		}
		else
		{
			System.out.println("Ships currently in the system: \n");
			for (int viewShipIndex = 0; viewShipIndex < sStore.getShipCount(); viewShipIndex++) 
			{
				if (viewShipArray[viewShipIndex] != null) //If the array is filled with elements (Means there are ships)
				{
					System.out.println(viewShipArray[viewShipIndex]);
				}
			}
		}
		
	}
	
	/*************************
	SUBMODULE: showMessage
	IMPORT: message (String)
	EXPORT: none
	***************************/
	public static void showMessage(String message)
	{
		System.out.println(message);
	}

	/********************************************************************************
	SUBMODULE: inputInteger
	IMPORT: prompt (String), error (String), min (Integer), max (Integer)
	EXPORT: integerInput (Integer)
	ASSERTION: User input submodule to input an integer that is within the specified range
	*********************************************************************************/
	public int inputInteger(String prompt, String error, int min, int max)
	{
		Scanner sc = new Scanner(System.in);
		String outputString;
		int integerInput = min - 1; //By default the input is always invalid to ensure loop keeps looping even if nothing is inputted
		outputString = prompt; //The output is set to the prompt that is imported when submodule is called       

		do
		{
			try
			{
				System.out.println(outputString); //Outputs the prompt for the user
				outputString = error + "\n" + prompt; //Makes the output prompt include the error if input is invalid
				integerInput = sc.nextInt(); //User inputs an integer
			}
			catch (InputMismatchException e)
			{
				sc.nextLine(); //advances scanner to the next line
			}
		}
		while ((integerInput < min || integerInput > max)); //Validation boundaries for integers that get imported
		return integerInput;
	}

	/********************************************************************************
	SUBMODULE: inputDouble
	IMPORT: prompt (String), error (String), min (Real), max (Real)
	EXPORT: integerInput (Integer)
	ASSERTION: User input submodule to input a real that is within the specified range
	*********************************************************************************/
	public double inputDouble(String prompt, String error, double min, double max)
	{
		Scanner sc = new Scanner(System.in);
		String outputString;
		double doubleInput = min - 1.0; //By default the input is always invalid to ensure loop keeps looping even if nothing is inputted
		outputString = prompt; //The output is set to the prompt that is imported when submodule is called       
		
		do
		{
			try
			{
				System.out.println(outputString); //Outputs the prompt for the user
				outputString = error + "\n" + prompt; //Makes the output prompt include the error if input is invalid
				doubleInput = sc.nextDouble(); //User inputs a double
			}
			catch (InputMismatchException e)
			{
				sc.nextLine(); //advances scanner to the next line
			}
		}
		while ((doubleInput < min) || (doubleInput > max)); //Validation boundaries for double that get imported
		return doubleInput;
	}
	
	
	/********************************************************************************
	SUBMODULE: inputSerialNum
	IMPORT: prompt (String), error (String)
	EXPORT: inputSerialNum (String)
	ASSERTION: User input submodule to input the serialNum of ship and validate input
	*********************************************************************************/
	public String inputSerialNum(String prompt, String error)
	{
		Scanner sc = new Scanner(System.in);
		String outputString, inputSerialNum;
		outputString = prompt; //The output is set to the prompt that is imported when submodule is called 
		inputSerialNum = null;
		
		do
		{
			System.out.println(outputString); //Outputs the prompt for the user
			outputString = error + "\n" + prompt; //Makes the output prompt include the error if input is invalid
			inputSerialNum = sc.nextLine(); //User inputs a string
		
		}
		while ((inputSerialNum == null) || (inputSerialNum.isEmpty()) || Ship.validateSerialNum(inputSerialNum) == false);
		return inputSerialNum;
	}
	
	/********************************************************************************
	SUBMODULE: inputFuel
	IMPORT: prompt (String), error (String)
	EXPORT: inputFuel (String)
	ASSERTION: User input submodule to input the fuel type of ship and validate input
	*********************************************************************************/
	public String inputFuel(String prompt, String error)
	{
		Scanner sc = new Scanner(System.in);
		String outputString, inputFuel;
		outputString = prompt; //The output is set to the prompt that is imported when submodule is called 
		inputFuel = null;
		
		do
		{
			System.out.println(outputString); //Outputs the prompt for the user
			outputString = error + "\n" + prompt; //Makes the output prompt include the error if input is invalid
			inputFuel = sc.nextLine(); //User inputs a string
		}
		while ((inputFuel == null) || (inputFuel.isEmpty()) || Engine.validateFuel(inputFuel) == false);
		return inputFuel;
	}
	
	/********************************************************************************
	SUBMODULE: inputHull
	IMPORT: prompt (String), error (String)
	EXPORT: inputHull (String)
	ASSERTION: User input submodule to input the hull material and validate input
	*********************************************************************************/
	public String inputHull(String prompt, String error)
	{
		Scanner sc = new Scanner(System.in);
		String outputString, inputHull;
		outputString = prompt; //The output is set to the prompt that is imported when submodule is called 
		inputHull = null;
		
		do
		{
			System.out.println(outputString); //Outputs the prompt for the user
			outputString = error + "\n" + prompt; //Makes the output prompt include the error if input is invalid
			inputHull = sc.nextLine(); //User inputs a string
			
		}
		while ((inputHull == null) || (inputHull.isEmpty()) || Submarine.validateHull(inputHull) == false);
		return inputHull;
	}
	
	/********************************************************************************
	SUBMODULE: inputOrdnance
	IMPORT: prompt (String), error (String)
	EXPORT: inputOrdnance (String)
	ASSERTION: User input submodule to input the ordnance of the ship
	*********************************************************************************/
	public String inputOrdnance(String prompt)
	{
		Scanner sc = new Scanner(System.in);
		String inputOrdnance = null;

		do
		{
			System.out.println(prompt); //Outputs the prompt for the user
			inputOrdnance = sc.nextLine(); //User inputs a string
		}
		while ((inputOrdnance == null) || (inputOrdnance.isEmpty()));
		return inputOrdnance;
	}
}
