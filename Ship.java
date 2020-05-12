/*****************************************************************************
*  Author: George Aziz
*  Date Created: 20/05/2019
*  Date Last Modified: 26/05/2019
*  Purpose: Parent class of submarine and fighter jet. Contains all class fields 
			common in both child classes
******************************************************************************/

// Based on code from module 8 slides for Constructors, Accessors, Mutators and clone method
public abstract class Ship
{	
	//private class fields
	private String serialNum;
	private int comYear;
	private Engine shipEngine;
	
	/************************************************************
	Default Constructor: Ship 
	IMPORT: none
	EXPORT: address of new Ship object
	ASSERTION: 
	*************************************************************/
	
	public Ship()
	{
		serialNum = "123.456";
		comYear = 2019;
		shipEngine = new Engine(); //default Engine
	}
	
	/************************************************************
	Alternate Constructor: Ship
	IMPORT: inSerialNum (String) , inComYear (Integer) , inEngine (Engine)
	EXPORT: address of new Ship object
	ASSERTION: Creates the object if the imports are valid and FAILS otherwise
	***********************************************************/

	public Ship(String inSerialNum, int inComYear, Engine inEngine)
	{		
		if ((inSerialNum == null) || (validateSerialNum(inSerialNum) == false))
		{
			throw new IllegalArgumentException("Invalid Serial Number");
		}
		if (validateComYear(inComYear) == false)
		{
			throw new IllegalArgumentException("Invalid Commission Year");
		}
		if (inEngine == null)
		{
			throw new IllegalArgumentException("Invalid Engine");
		}

		serialNum = inSerialNum;
		comYear = inComYear;
		shipEngine = new Engine(inEngine); //Making a copy of engine

	}
	   
   /************************************************************
   * Copy Constructor:
   * IMPORT: inShip (Ship)
   * EXPORT: address of new Ship object
   * ASSERTION: Creates an object with an identical object state as the import.
   ************************************************************/

	public Ship(Ship inShip)
	{
		serialNum = inShip.getSerialNum();
		comYear = inShip.getComYear();
		shipEngine = inShip.getEngine();
	}

	//Clone Method
	public abstract Ship clone();
	

	//ACCESSORS
	public String getSerialNum()
	{
		return serialNum;
	}

	public int getComYear()
	{
		return comYear;
	}

	public Engine getEngine()
	{
		return new Engine(shipEngine);
	}
	
	/**************************************************************************************
	* SUBMODULE: equals
	* IMPORT: inObj (Ship)
	* EXPORT: isEqual
	* ASSERTION: If all class fields are equal between ships, they are interchangeable
	***************************************************************************************/
	public boolean equals(Object shipObj)
	{
		boolean isEqual = false;
		if(shipObj instanceof Ship)
		{
			Ship inShip = (Ship)shipObj;
			isEqual = serialNum.equals(inShip.getSerialNum()) && comYear == (inShip.getComYear()) && shipEngine.equals(inShip.getEngine());
		}
		return isEqual;
	}
	
	//Makes all values fields into a string
	public String toString()
	{
		return (serialNum + " was comissioned in " + comYear + shipEngine.toString());
	}
	
	//ABSTRACT toFileString
	public abstract String toFileString();
	
	//ABSTRACT toDistanceString
	public abstract String toDistanceString(); //In order for destinationCheck to output the correct details
	
	//MUTATORS
	/************************************************************
	* SUBMODULE: setSerialNum
	* IMPORT: inSerialNum (String)
	* EXPORT: none
	* ASSERTION: sets the serialNum if valid and it fails otherwise
	************************************************************/
	public void setSerialNum(String inSerialNum)
	{
		if (validateSerialNum(inSerialNum))
		{
			serialNum = inSerialNum;
		}
		else
		{
			throw new IllegalArgumentException("Invalid Serial Number");
		}
	}
		
	/********************************************************************
	* SUBMODULE: setComYear
	* IMPORT: inComYear (integer)
	* EXPORT: none
	* ASSERTION: sets the comYear if valid and it fails otherwise
	*********************************************************************/
	public void setComYear(int inComYear)
	{
		if (validateComYear(inComYear))
		{
			comYear = inComYear;
		}
		else
		{
			throw new IllegalArgumentException("Invalid Commision Year");
		}
	}
	
	/********************************************************************
	* SUBMODULE: setEngine
	* IMPORT: inEngine (Engine)
	* EXPORT: none
	* ASSERTION: sets the Engine object if valid and it fails otherwise
	*********************************************************************/
	public void setEngine(Engine inEngine)
	{
		if(inEngine != null)
		{
			shipEngine = new Engine(inEngine); //Making a copy
		}
		else 
		{
			throw new IllegalArgumentException("Invalid engine details");
		}
	}
	
	/*****************************************************************
	* ABSTRACT SUBMODULE: calcTravel 
	* IMPORT: distance (integer)
	* EXPORT: travelTime (Real)
	* ASSERTION: Calculates travel time for ship object
	******************************************************************/
	public abstract double calcTravel(int distance);
	
	//VALIDATION SUBMODULES:
	/*************************************************************************
	* SUBMODULE: validateSerialNum
	* IMPORT: inSerialNum (String)
	* EXPORT: valid (boolean)
	* ASSERTION: Serial Number is between 0 and 42 (inclusive)
	************************************************************************/
	public static boolean validateSerialNum(String inSerialNum)
	{
		boolean validation = false; 
		String[] section = inSerialNum.split("\\.");
		int intXXX, intYYY;
		String XXX, YYY;
		
		try
		{
			if (section.length == 2)
			{
				XXX = section[0]; // First Part before '.'
				YYY = section[1];  //Second part after '.'

				intXXX = Integer.parseInt(XXX); //Converts the first part into an integer
				intYYY = Integer.parseInt(YYY); //Converts the second part into an integer


				if ((XXX.length() == 3) && (YYY.length() == 3) && (intXXX >= 100) && (intXXX <= 300) && (intYYY >= 001) && (intYYY <= 999))
				{
					validation = true; //Validation returns true only if all statements are true in if
				}
			}
		}
		catch (NumberFormatException e)
		{
			validation = false; //If spaces or characters are inputted, normally it would throw an exception because its unable to convert the string into an integer
		}
		return validation;
	}


	/*************************************************************************
	* SUBMODULE: validateComYear
	* IMPORT: inComYear (integer)
	* EXPORT: valid (boolean)
	* ASSERTION: Commision Year is between 1950 and 2022 (inclusive)
	************************************************************************/
	public static boolean validateComYear(int inComYear)
	{
		return((inComYear >= 1950) && (inComYear <= 2022));
	}
}
