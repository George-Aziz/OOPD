/*****************************************************************************
*  Author: George Aziz
*  Date created: 04/05/2019
*  Date Last Modified: 25/05/2019
*  Purpose: Managing the ship array, destinationCheck to calulcate which ship 
*           is the fastest, find duplicate ships in the ship Array and also
*           handling ship outputs
******************************************************************************/

import java.text.DecimalFormat;

public class ShipStorage
{
	//Class Constants 
    private static final int MAX = 30; //Value for shipArray, max amount of ships
	private static final int DUPEMAX = 900; //900 value since there can be a maximum of 900 duplicates in shipArray (30^2)

	//Class Fields
	private Ship[] shipArray; //Array of ship objects
	private int shipCount;
	
	/************************************************************
	Default Constructor: ShipStorage
	IMPORT: none
	EXPORT: none
	ASSERTION: Create ship array. Also initialise the shipCount variable
	*************************************************************/
	
	public ShipStorage()
	{
		shipArray = new Ship[MAX];
		shipCount = 0;
	}
	
	//ACCESSORS
	public int getShipCount()
	{
		return shipCount;
	}
	
	public Ship[] getShipArray()
	{
		Ship[] shipArrayCopy = new Ship[MAX]; //CopyArray of shipArray
		for (int copyIndex = 0; copyIndex < shipCount; copyIndex++)
		{
			shipArrayCopy[copyIndex] = shipArray[copyIndex].clone(); //In order for the object array to be used, a copy must be made
		}
		return shipArrayCopy;
	}
	
	
	/**************************************************************************************
	* SUBMODULE: equals
	* IMPORT: inObj (shipObj)
	* EXPORT: isEqual
	* ASSERTION: If all shipObj and elements of shipArray are equal, they are interchangeable
	***************************************************************************************/
	public boolean equals(Ship shipObj)
	{
		boolean isEqual = false;
		if(shipObj instanceof Ship)
		{
			Ship inShip = (Ship)shipObj;
			for (int equalsIndex = 0; equalsIndex < shipCount; equalsIndex++)
			{
				if (shipObj.equals(shipArray[equalsIndex]))
				{
					isEqual = true;	
				}
			}
		}
		return isEqual;	
	}
	
	/************************************************************
	SUBMODULE: addShip
	IMPORT: Ship shipObject (Object)
	EXPORT: none
	ASSERTION: if object is not null and shipCount is not 30 then add object to shipArray
	*************************************************************/
	
	public void addShip(Ship shipObject) //Importing shipObject from FileManager
	{
		
		if ((shipObject != null) && (shipCount < MAX)) //If the ship object is valid
		{
			shipArray[shipCount] = shipObject.clone();
			shipCount++;	
		}
		else
		{
			String message = "The object is either null or there isn't any space";
			UserInterface.showMessage(message);
		}
	}


	/****************************************************************************
	SUBMODULE: destinationCheck
	IMPORT: distance (Integer)
	EXPORT: fastestShip (String)
	ASSERTION: To Find the fastest ship out of all ships stored in the system
	*****************************************************************************/
	
	public String destinationCheck(int distance)
	{
		int fastestShipIndex; 
		double fastestShipVal; //The time it takes for a ship to travel a specified distance
		String fastestShip, shipType, shipString;
		
		fastestShipVal = Double.MAX_VALUE; //Start	from 0 as all ships should be larger than 0
		fastestShipIndex = -1 ; //negative value to indicate not found yet	
		fastestShip = ""; //As default the fastest Ship is nothing
		//Find Fastest Ship
		for (int shipIndex = 0; shipIndex < shipCount; shipIndex++)
		{
			if (fastestShipVal > shipArray[shipIndex].calcTravel(distance))
			{
				fastestShipVal = shipArray[shipIndex].calcTravel(distance); //The fastest ship's time value will be saved as fastestShipVal
				fastestShipIndex = shipIndex; //Saves the index of the fastest ship
			}
		}
		DecimalFormat df = new DecimalFormat("#.##");
		fastestShipVal = Double.valueOf(df.format(fastestShipVal));
		
		
		//Creates string to be outputted
		fastestShip = shipArray[fastestShipIndex].toDistanceString() + fastestShipVal + " hours to travel " + distance + " meters";
        return fastestShip;
	}
	
	/************************************************************
	SUBMODULE: findDuplicates
	IMPORT: none
	EXPORT: duperArray []
	ASSERTION: To Find if there any ships that are duplicates in ship array
	*************************************************************/
	
	public String[] findDuplicates()
	{
		String [] dupeArray = new String[DUPEMAX];
		int dupeCount = 0; //index of dupeArray 
		
		//Ship Duplicate Check
		for (int shipIndexA = 0; shipIndexA < shipCount; shipIndexA++) //Starts from the first element
		{ 
			for (int shipIndexB = shipIndexA + 1 ; shipIndexB < shipCount; shipIndexB++) //Starts from the second element
			{ 
				if (shipArray[shipIndexA].equals(shipArray[shipIndexB])) //Checks if element and element+1 are equal
				{ 
					dupeArray[dupeCount] = shipArray[shipIndexB].toString(); //If equal, then the object's toString gets passed onto the dupeArray
					dupeCount++; //dupeCount increases in order if there are more duplicates, tto be stored
				} 
			} 
		}
		return dupeArray;
	}

	/************************************************************
	SUBMODULE: viewShips
	IMPORT: none
	EXPORT: viewShipArray (ARRAY OF String)
	ASSERTION: view all the ships
    *************************************************************/

	public String[] viewShips()
    {	
		String[] viewShipArray = new String[MAX]; //MAX is 30 and there can only be 30 ships
		
		for (int viewShipIndex = 0; viewShipIndex < shipCount; viewShipIndex++) 
		{
			viewShipArray[viewShipIndex] = shipArray[viewShipIndex].toString(); //viewShipArray stores the string of shipArray through toString of the type of ship
		}
		
		return viewShipArray;
	}
}
