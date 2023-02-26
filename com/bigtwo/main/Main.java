package com.bigtwo.main;

/* ---------------------------------------------------------------------------------
* 		Program Name: Main.java 
* 		Date Written: February 13th, 2023
* 			  Author: Thomas Vu
* 
*            Purpose: 1. To control the main flow of the game. 
*                      
* ----------------------------------------------------------------------------------
* Modification History: 
* ----------------------------------------------------------------------------------
* Date			Person	CSR#	Description
* 2023-02-13	TV 		1.01	Initial Version 
* 2023-02-13	TV 		1.02	Run BigTwo class
* 2023-02-20	TV 		1.03	Add choosing number of players
* 2023-02-20	TV 		1.04	Apply industry standard
* ----------------------------------------------------------------------------------
* Future Enhancements: 
* -------------------- 
* 1. To allow input about number of players via parameterization (Done 20-02-2023)
* 								
*/

import javax.swing.JOptionPane;

public class Main {
    /**
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        // declare variables for ProgramName, and version.
        String programName = Main.class.getName();
        String programVersion = "1.74";

        // output at start programName and Version.
        System.out.println(" Start: " + programName);
        System.out.println("Verson: " + programVersion + "\n");

        String players = JOptionPane.showInputDialog("How many players do you want? (2-4)", "4");

        try {
            BigTwo game = new BigTwo(Integer.parseInt(players), programVersion);
            game.run();
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println();
        System.out.println("End of Program: " + programName);
    }
}
