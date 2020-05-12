/*****************************************************************************
*  Author: George Aziz
*  Date Created: 29/04/2019
*  Date Last Modified: 26/05/2019
*  Purpose: Handles anything related with Engine details for a ship including
*           validating exclusive fields
******************************************************************************/

public class Engine
{
	//Declare Constants 
	private static final String BATTERY = "BATTERY";
	private static final String DIESEL = "DIESEL";
	private static final String BIO = "BIO";

	//private class fields 
	private String fuel;
	private int cylinders;

	/************************************************************
	Default Constructor: Engine
	IMPORT: none
	EXPORT: address of new Engine object
	ASSERTION: 
	*************************************************************/

	public Engine()
	{
		cylinders = 15;
		fuel = BATTERY;
	}
	
	/************************************************************
	Alternate Constructor: Engine
	IMPORT: fuel (String), cylinders (Integer)
	EXPORT: address of new Engine object
	ASSERTION: Creates the object if the imports are valid and FAILS otherwise
	*************************************************************/
	   
	public Engine(int inCylinders, String inFuel)
	{
		if (validateCylinders(inCylinders) == false)
		{
		   throw new IllegalArgumentException("Invalid Cylinders amount");
		}
		if ((inFuel == null) || validateFuel(inFuel) == false)
		{
		   throw new IllegalArgumentException("Invalid Maximum Depth");
		}

		cylinders = inCylinders;
		fuel = inFuel.toUpperCase();

	}
	   
	/************************************************************
	* Copy Constructor: Engine
	* IMPORT: inEngine (Engine)
	* EXPORT: address of new Engine object
	* ASSERTION: Creates an object with an identical object state as the import.
	************************************************************/
	public Engine(Engine inEngine)
	{
	   cylinders = inEngine.getCylinders();
	   fuel = inEngine.getFuel();
	}
	
	//Clone Method
	public Engine clone()
	{
	   return new Engine(this); //Clones the Engine
	}

	//ACCESSORS
	public String getFuel()
	{
	   return fuel;
	}

	public int getCylinders()
	{
	   return cylinders;
	}

	//Makes all values fields into a string
	public String toString() //Used in Ship's toString
	{
	   return (", its engine has " + cylinders + " cylinders and runs on " + fuel + " fuel.");
	}

	public String toFileString() //In order for writeFile in FileManager to display in correct format
	{
	   return(cylinders + "," + fuel);
	}


	//MUTATORS
	/********************************************************************
	* SUBMODULE: setFuel
	* IMPORT: inFuel (String)
	* EXPORT: none
	* ASSERTION: sets the fuel if valid and it fails otherwise
	*********************************************************************/
	public void setFuel(String inFuel)
	{
		if (validateFuel(inFuel))
		{
			fuel = inFuel;
		}
		else
		{
			throw new IllegalArgumentException("Invalid Fuel Type");
		}
	}

	/********************************************************************
	* SUBMODULE: setCylinders
	* IMPORT: inCylinders (integer)
	* EXPORT: none
	* ASSERTION: sets the cylinders if valid and it fails otherwise
	*********************************************************************/
	public void setCylinders(int inCylinders)
	{
	   if (validateCylinders(inCylinders))
	   {
		   cylinders = inCylinders;
	   }
	   else
	   {
		   throw new IllegalArgumentException("Invalid Cylinders Amount");
	   }
	}

	/**************************************************************************************
	* SUBMODULE: equals
	* IMPORT: inObj (Object)
	* EXPORT: same
	* ASSERTION: If all class fields are equal between engines, they are interchangeable
	***************************************************************************************/
	public boolean equals(Object engObj)
	{
		boolean isEqual = false;
		if(engObj instanceof Engine)
		{
			Engine inEngine = (Engine)engObj;
			isEqual = cylinders == (inEngine.getCylinders()) && fuel.equals(inEngine.getFuel());
		}
		return isEqual;
	}

	//VALIDATION SUBMODULES:
	/*************************************************************************
	* SUBMODULE: validateFuel
	* IMPORT: inFuel (String)
	* EXPORT: valid (boolean)
	* ASSERTION: Fuel type must be either BATTERY,DIESEL or BIO
	************************************************************************/
	public static boolean validateFuel(String inFuel)
	{
		String type = inFuel.toUpperCase();
		return ((type.equals(BATTERY)) || (type.equals(DIESEL)) || (type.equals(BIO)));
	}
		
	/*************************************************************************
	* SUBMODULE: validateCylinders
	* IMPORT: inCylinders (integer)
	* EXPORT: valid (boolean)
	* ASSERTION: Ammount of cylinders must be between 2 and 20 (inclusive)
	************************************************************************/
	public static boolean validateCylinders(int inCylinders)
	{
		return((inCylinders >= 2) && (inCylinders <= 20));
	}
}
