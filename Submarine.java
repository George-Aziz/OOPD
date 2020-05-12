/*****************************************************************************
*  Author: George Aziz
*  Date Created: 29/04/2019
*  Date Last Modified: 26/05/2019
*  Purpose: Handles anything related with submarines ship type including
*           validating exclusive fields and toString methods
******************************************************************************/
// Based on code from lecture slides for Constructors, Accessors, Mutators and clone method
import java.text.DecimalFormat;


public class Submarine extends Ship //Ship is the parent class and Submarine is child that extends from the parent
{
	// Declare Constants 
	private static final String STEEL = "STEEL";
	private static final String ALLOY = "ALLOY";
	private static final String TITANIUM = "TITANIUM";
	
	//private class fields
	private String hull;
	private double maxDepth;
	
	/************************************************************
	Default Constructor: Submarine 
	IMPORT: none
	EXPORT: address of new Submarine object
	ASSERTION:
	*************************************************************/

	public Submarine()
	{
		super(); //Constructs Ship which is super using default constructor
		hull = STEEL;
		maxDepth = -399.25;

	}
	
	/************************************************************
	Alternate Constructor: Submarine 
	IMPORT: inSerialNum (String) , inComYear (Integer) , inHull (String) , inMaxDepth (Real)
	EXPORT: address of new Submarine object
	ASSERTION: Creates the object if the imports are valid and FAILS otherwise
	***********************************************************/

	public Submarine(String inSerialNum, int inComYear, Engine inEngine, String inHull, double inMaxDepth)
	{
	   super (inSerialNum, inComYear, inEngine); //Uses the alternate constructor of Ship
	   if ((inHull == null) || (validateHull(inHull) == false))
	   {
		   throw new IllegalArgumentException("Invalid Hull Material");
	   }
	   if (validateMaxDepth(inMaxDepth) == false)
	   {
		   throw new IllegalArgumentException("Invalid Maximum Depth");
	   }

	   hull = inHull.toUpperCase();
	   maxDepth = inMaxDepth;

	}
	   
	/************************************************************
	* Copy Constructor:
	* IMPORT: inSubmarine (Submarine)
	* EXPORT: address of new Submarine object
	* ASSERTION: Creates an object with an identical object state as the import.
	************************************************************/

	public Submarine(Submarine inSubmarine)
	{
	   super(inSubmarine); //Uses the copy constructor of Ship
	   hull = inSubmarine.getHull();
	   maxDepth = inSubmarine.getMaxDepth();
	}
	
	//Clone Method
	@Override
	public Submarine clone()
	{
	   return new Submarine(this); //Clones the Submarine object
	}

	//ACCESSORS
	public String getHull()
	{
	   return hull;
	}

	public double getMaxDepth()
	{
	   return maxDepth;
	}

	   
	//Makes all values fields into a string
	public String toString()
	{
		DecimalFormat df = new DecimalFormat("#.##"); 
		maxDepth = Double.valueOf(df.format(maxDepth)); //Rounds maxDepth to two dp to ensure output will only be to 2dp
		return ("The ship " + super.toString() + " It is a submarine with a " + hull + " hull and a max depth of " + maxDepth + " meters.\n");
	}

	//toFileString
	@Override
	public String toFileString() //In order for writeFile in FileManager to display in correct format
	{
		char shipType = 'S';
		return(shipType + "," + super.getSerialNum() + "," + super.getComYear() + "," + super.getEngine().getCylinders() + "," + super.getEngine().getFuel() + "," + hull + "," + maxDepth);
	}

	//toDistanceString
	@Override
	public String toDistanceString() //In order for destinationCheck to output the correct details
	{
		String shipType = "submarine";
		return("The fastest ship is a " + shipType + " with serialNum: " + super.getSerialNum() + " that took ");
	}

	   
	//MUTATORS
	/********************************************************************
	* SUBMODULE: setHull
	* IMPORT: inHull (String)
	* EXPORT: none
	* ASSERTION: sets the hull if valid and it fails otherwise
	*********************************************************************/
	public void setHull(String inHull)
	{
		if (validateHull(inHull))
		{
			hull = inHull;
		}
		else
		{
			throw new IllegalArgumentException("Invalid Hull Material");
		}
	}

	/********************************************************************
	* SUBMODULE: setMaxDepth
	* IMPORT: inMaxDepth (real)
	* EXPORT: none
	* ASSERTION: sets the maxDepth if valid and it fails otherwise
	*********************************************************************/
	public void setMaxDepth(double inMaxDepth)
	{
		if (validateMaxDepth(inMaxDepth))
		{
			maxDepth = inMaxDepth;
		}
		else
		{
			throw new IllegalArgumentException("Invalid Maximum Depth");
		}
	}

	/**************************************************************************************
	* SUBMODULE: equals
	* IMPORT: inObj (Submarine)
	* EXPORT: isEqual
	* ASSERTION: If all class fields are equal between submarines, they are interchangeable
	***************************************************************************************/
	public boolean equals(Object subObj)
	{
		boolean isEqual = false;
		if(subObj instanceof Submarine)
		{
			Submarine inSubmarine = (Submarine)subObj;
			isEqual = super.equals(inSubmarine) && hull.equals(inSubmarine.getHull()) && maxDepth == (inSubmarine.getMaxDepth());
		}
		return isEqual;
	}

	/*****************************************************************
	* SUBMODULE: calcTravel
	* IMPORT: distance (integer)
	* EXPORT: travelTime (Real)
	* ASSERTION: Calculates travel time for submarine object
	******************************************************************/
	public double calcTravel(int distance)
	{	
		double travelTime;
		int cylinders = getEngine().getCylinders();

		travelTime = (distance/cylinders)*(1/(10 + maxDepth * -1));

		return(travelTime);

	}

	//VALIDATION SUBMODULES:
	/*************************************************************************
	* SUBMODULE: validateHull
	* IMPORT: inHull (integer)
	* EXPORT: valid (boolean)
	* ASSERTION: Hull must be either made out of TITANIUM, ALLOY or STEEL
	************************************************************************/
	public static boolean validateHull(String inHull)
	{
		String mat = inHull.toUpperCase();
		return ((mat.equals(STEEL)) || (mat.equals(ALLOY)) || (mat.equals(TITANIUM)));
	}

	/*************************************************************************
	* SUBMODULE: validateMaxDepth
	* IMPORT: inMaxDepth (real)
	* EXPORT: valid (boolean)
	* ASSERTION: Max Depth must be between -500.0 to 0.0 (inclusive)
	************************************************************************/
	public static boolean validateMaxDepth(double inMaxDepth)
	{
		return((inMaxDepth >= -500.0) && (inMaxDepth <= 0.0));
	}
}
