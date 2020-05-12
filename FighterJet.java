/*****************************************************************************
*  Author: George Aziz
*  Date Created: 29/04/2019
*  Date Last Modified: 26/05/2019
*  Purpose: Handles anything related with Fighter Jets ship type including
*           validating exclusive fields and toString methods
******************************************************************************/
// Constructors, Accessors, Mutators and clone method based on code from lecture slides
import java.text.DecimalFormat;

public class FighterJet extends Ship //Ship is the parent class and FighterJet is child that extends from the parent
{
	//private class fields
	private double  wingSpan; 
	private String ordnance;
	
	/************************************************************
	Default Constructor: FighterJet
	IMPORT: none
	EXPORT: address of new FighterJet object
	ASSERTION: 
	*************************************************************/
	
	public FighterJet()
	{
		super(); //Constructs Ship which is super using default constructor
		wingSpan = 23.3;
		ordnance = "Missile";
		
	}
	
	/************************************************************
	Alternate Constructor: FighterJet
	IMPORT: inSerialNum (String) , inComYear (Integer) , inEngine (Engine), inOrdnance (String) , inWingSpan (Real)
	EXPORT: address of new FighterJet object
	ASSERTION: Creates the object if the imports are valid and FAILS otherwise
	***********************************************************/

	public FighterJet(String inSerialNum, int inComYear, Engine inEngine, double inWingSpan, String inOrdnance)
	{
	   super (inSerialNum, inComYear, inEngine); //Uses the alternate constructor of Ship

	   if (validateWingSpan(inWingSpan) == false)
	   {
		   throw new IllegalArgumentException("Invalid Wing Span");
	   }
	   if (inOrdnance == null)
	   {
		   throw new IllegalArgumentException("Invalid Ordnance");
	   }

	   wingSpan = inWingSpan;
	   ordnance = inOrdnance;
	}

	/************************************************************
	* Copy Constructor: FighterJet
	* IMPORT: inFighterJet (FighterJet)
	* EXPORT: address of new FighterJet object
	* ASSERTION: Creates an object with an identical object state as the import.
	************************************************************/

	public FighterJet(FighterJet inFighterJet)
	{
	   super(inFighterJet); //Uses the copy constructor of Ship
	   wingSpan = inFighterJet.getWingSpan();
	   ordnance = inFighterJet.getOrdnance();
	}
	
	//Clone Method
	@Override
	public FighterJet clone()
	{   
	   return new FighterJet(this); //Clones the Fighter Jet
	}

	//ACCESSORS
	public double getWingSpan()
	{
	   return wingSpan;
	}

	public String getOrdnance()
	{
	   return ordnance;
	}

	//Makes all values fields into a string
	public String toString()
	{
		DecimalFormat df = new DecimalFormat("#.##");
		wingSpan = Double.valueOf(df.format(wingSpan)); //Rounds wingSpan to two dp to ensure output will only be to 2dp
		return ("The ship " + super.toString() + " It is a fighter jet with a wing span of " + wingSpan + " meters and equipped with " + ordnance + "\n");
	} 


	//toFileString
	@Override
	public String toFileString() //In order for writeFile in FileManager to display in correct format
	{
		char shipType = 'F';
		return(shipType + "," + super.getSerialNum() + "," + super.getComYear() + "," + super.getEngine().getCylinders() + "," + super.getEngine().getFuel() + "," + wingSpan + "," + ordnance);
	}

	//toDistanceString
	@Override
	public String toDistanceString() //In order for destinationCheck to output the correct details
	{
		String shipType = "fighter jet";
		return("The fastest ship is a " + shipType + " with serialNum: " + super.getSerialNum() + " that took ");
	}


	//MUTATORS
	/********************************************************************
	* SUBMODULE: setOrdnance
	* IMPORT: inOrdnance (String)
	* EXPORT: none
	* ASSERTION: sets the ordnance if valid and it fails otherwise
	*********************************************************************/
	public void setOrdnance(String inOrdnance)
	{
		if (ordnance != null)
		{
			ordnance = inOrdnance;
		}
		else
		{
			throw new IllegalArgumentException("Invalid Ordanace");
		}
	}

	/********************************************************************
	* SUBMODULE: setWingSpan
	* IMPORT: inWingSpan (real)
	* EXPORT: none
	* ASSERTION: sets the wingSpan if valid and it fails otherwise
	*********************************************************************/
	public void setWingSpan(double inWingSpan)
	{
		if (validateWingSpan(inWingSpan))
		{
			wingSpan = inWingSpan;
		}
		else
		{
			throw new IllegalArgumentException("Invalid Wing Span distance");
		}
	}

	/**************************************************************************************
	* SUBMODULE: equals
	* IMPORT: inObj (FighterJet)
	* EXPORT: same
	* ASSERTION: If all class fields are equal between fighter jets, they are interchangeable
	***************************************************************************************/
	public boolean equals(Object jetObj)
	{
		boolean isEqual = false;
		if(jetObj instanceof FighterJet)
		{
			FighterJet inFighterJet = (FighterJet)jetObj;
			isEqual = super.equals(inFighterJet) && wingSpan == (inFighterJet.getWingSpan()) && ordnance.equals(inFighterJet.getOrdnance());
		}
		return isEqual;
	} 

	/******************************************************************
	* SUBMODULE: calcTravel
	* IMPORT: distance (integer)
	* EXPORT: travelTime (Real)
	* ASSERTION: Calculates travel time for the fighter jet objects
	*******************************************************************/
	@Override //Compiler check
	public double calcTravel(int distance)
	{	
		double travelTime;

		int cylinders = getEngine().getCylinders();

		travelTime = (distance / (wingSpan * cylinders * 150));

		return(travelTime);

	}

	//VALIDATION SUBMODULES:
	/*************************************************************************
	* SUBMODULE: validateWingSpan
	* IMPORT: inWingSpan (real)
	* EXPORT: valid (boolean)
	* ASSERTION: Wing Span length must be between 2.20 to 25.6 (inclusive)
	************************************************************************/
	public static boolean validateWingSpan(double inWingSpan)
	{
		return((inWingSpan >= 2.20) && (inWingSpan <= 25.6));
	}
}
		
