/*****************************************************************************
*  Author: George Aziz
*  Date Created: 6/05/2019
*  Date Last Modified: 26/05/2019
*  Purpose: Calls the UI which is basically the connection of all the class files
******************************************************************************/

import java.util.*;

public class ShipManager
{
	public static void main (String [] args)
	{
		
		UserInterface uI = new UserInterface();
	
	    uI.mainMenu(); //Call the main menu which connects all the methods in userInterface that would call methods from other classes
		
	}
}
