/*****************************************************************************
*  Author: George Aziz
*  Date Created: 04/05/2019
*  Date Last Modified: 26/05/2019
*  Purpose: Managing any file reading/writing including processing every line 
*           of a loaded file
******************************************************************************/
// readFile and processLine based on code from WeaponReader.java from worked examples
// Retrieved from: https://lms.curtin.edu.au/bbcswebdav/pid-6792715-dt-content-rid-34984528_1/courses/2019_1_COMP1001_V1_L1_A1_INT_676587/WeaponReader.java

//writeFile bassed on module 5 (File I/O) slides

import java.util.*;
import java.io.*;

public class FileManager
{
 
	/************************************************************
	SUBMODULE: readFile
	IMPORT: fileName (String), sStore (ShipStorage)
	EXPORT: none
	ASSERTION: To be able to read the file inputted by the user
	*************************************************************/
	public int readFile(String fileName, ShipStorage sStore)
	{	
			FileInputStream fileStrm = null;
			InputStreamReader inRdr;
			BufferedReader bufRdr = null;
			String line;

			int count = 0;

		try
		{
			fileStrm = new FileInputStream(fileName);
			inRdr = new InputStreamReader(fileStrm);
			bufRdr = new BufferedReader(inRdr);

			//Use while loop to make sure the file isn't empty
			line = bufRdr.readLine();
			while (line != null)
			{
				try
				{
					processLine(line, sStore); //The line will be processed

					count++; //The count only increases if a line has been processed properly
				}
				catch (IllegalArgumentException e)
				{
					UserInterface.showMessage(e.getMessage() + " " + line); //Any error will be shown to the user
				}
				line = bufRdr.readLine(); //read next line and repeat the process
			}

			fileStrm.close(); //Once all lines are read, the file must be closed
		}
		catch (IOException e) 
		{
			try
			{
				if (fileStrm != null)
				{
					fileStrm.close();
				}
			}
			catch (IOException ex)
			{
				//File won't close, nothing that can be done
			}
		}

		return count; //Count is the amount of lines processed properly
	}
	
	/************************************************************************************
	SUBMODULE: processLine
	IMPORT: line (String), sStore (ShipStorage)
	EXPORT: none
	ASSERTION: To be able to process a line from a file that should represent ships
    ************************************************************************************/
	
	public void processLine(String line, ShipStorage sStore)
	{
		String [] lineArray = line.split(","); //After every section/ class field, a ',' will be used to seperate them
		
		if (lineArray.length != 7) //If there aren't 7 class fields then the line is invalid
		{
			throw new IllegalArgumentException("ERROR - Invalid line!");
		}
		
		if (lineArray[0].length() != 1) //If the first classfield which is the shipType either 'S' or 'F' is not 1 length
		{
			throw new IllegalArgumentException("ERROR - The first field must be only 1 character long");
		}
		
        String serialNum, fuel;
        int comYear, cylinders;	
		char type = lineArray[0].charAt(0); //The first element in the array is the type which is a character data type
		
		//Validation Methods are called to check if each field is valid 
        if (Ship.validateSerialNum(lineArray[1])) 
        {
            serialNum = lineArray[1];
        }
        else 
        {
			throw new IllegalArgumentException("Invalid serial number");
        }

        if (Ship.validateComYear(Integer.parseInt(lineArray[2])))
        {
            comYear = Integer.parseInt(lineArray[2]);
        }
        else 
        {
			throw new IllegalArgumentException("Invalid year");
        }

        if (Engine.validateCylinders(Integer.parseInt(lineArray[3])))
        {
            cylinders = Integer.parseInt(lineArray[3]);
        }
        else
        {
			throw new IllegalArgumentException("Invalid cylinders");
        }

        if (Engine.validateFuel(lineArray[4]))
        {
            fuel = lineArray[4];
        }
        else 
        {
            throw new IllegalArgumentException("Invalid fuel type");
	    }

		Engine engineCF = new Engine(cylinders, fuel); //Construct engineCF using cylinders and fuel
		
		try
		{
			switch(Character.toUpperCase(lineArray[0].charAt(0))) //convert the character to uppercase in order to make error checking easier
			{
				case 'S':
					//Variables exclusive to the submarines
					double maxDepth;
                    String hull;
					
                    if (Submarine.validateHull(lineArray[5]))
                    {
                        hull = lineArray[5];
                    }
                    else 
                    {
				        throw new IllegalArgumentException("Invalid hull material");
                    }

                    if (Submarine.validateMaxDepth(Double.parseDouble(lineArray[6])))
                    {
                        maxDepth = Double.parseDouble(lineArray[6]);
                    }
                    else 
                    {
                        throw new IllegalArgumentException("Invalid maximum depth");
                    }
					
					//Construct subObject Using serialNum, comYear, hull, maxDepth
					Submarine subObject = new Submarine(serialNum, comYear, engineCF, hull, maxDepth);
					
					sStore.addShip(subObject); //The line will become a subObject and added to shipArray
					break;
				case 'F':
					//Variables exclusive to the fighter jets
					double wingSpan;
				    if (FighterJet.validateWingSpan(Double.parseDouble(lineArray[5])))
                    {
                        wingSpan = Double.parseDouble(lineArray[5]);
                    }
                    else 
                    {
				        throw new IllegalArgumentException("Invalid wing span");
                    }
					String ordnance = lineArray[6];
					
					//Construct jetObject using serialNum, comYear, ordnance, wingSpan
					FighterJet jetObject = new FighterJet(serialNum, comYear, engineCF, wingSpan, ordnance);
					
					sStore.addShip(jetObject); //The line will become a jetObject and added to shipArray
					
					break;
				default:
				    throw new IllegalArgumentException("Invalid ship type detected"); 
			}
		}
		catch(IllegalArgumentException e)
		{
			UserInterface.showMessage(e.getMessage() + " " + line);
		}
	}
	/************************************************************
	SUBMODULE: writeFile
	IMPORT: fileName (String), sStore (ShipStorage)
	EXPORT: none
	ASSERTION: To be able to write ships to a file
    *************************************************************/
	public void writeFile(String fileName, ShipStorage sStore)
	{

		Ship [] shipArray = sStore.getShipArray();
		String message;
		
		FileOutputStream fileStrm = null;
   		PrintWriter pw;

		try 
		{
			fileStrm = new FileOutputStream(fileName); 
			pw = new PrintWriter(fileStrm);
			
			for (int fileIndex = 0; fileIndex < sStore.getShipCount(); fileIndex++) 
			{
				pw.println(shipArray[fileIndex].toFileString()); //Goes through each index of the array and adds the string created from toFileString method
			}
			pw.close(); //Writer must be closed
			message = "Ships succesfully saved!";
			UserInterface.showMessage(message);
		}
		catch (IOException e)
		{
			if (fileStrm != null) 
			{
         		try 
				{ 
					fileStrm.close(); 
				} 
				catch (IOException ex2) { } // We can’t do anything more! 
      		}
			
			message = "Error in writing to file:" + e.getMessage(); //If an error occurs whilst saving to File, a message will appear to the user
			UserInterface.showMessage(message);
		}
		
	}
}
