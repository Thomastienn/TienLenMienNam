package com.bigtwo.main;

/* ---------------------------------------------------------------------------------
* 		Program Name: Player.java 
* 		Date Written: February 13th, 2023
* 			  Author: Laura Tran
* 
*            Purpose: 1. To implement Player class. 
*                      
* ----------------------------------------------------------------------------------
* Modification History: 
* ----------------------------------------------------------------------------------
* Date			Person	CSR#	Description
* 2023-02-13	LT  	1.01	Initial Version 
* 2023-02-13	LT 		1.02	Implement attributes and getters setters 
* 2023-02-20	LT 		1.03	Apply industry standard
* ----------------------------------------------------------------------------------
* Future Enhancements: 
* -------------------- 
* 1. Implement attributes (Done 13-02-2023)
* 2. To be complete
* 								
*/ 

import java.util.ArrayList;

public class Player {
    private int id;
    private ArrayList<Card> cardsAvailable;

    public Player(ArrayList<Card> cardsAvailable, int id){
        this.cardsAvailable = cardsAvailable;
        this.id = id;
    }

    public ArrayList<Card> getCardsAvailable() {
        return cardsAvailable;
    }

    public void setCardsAvailable(ArrayList<Card> cardsAvailable) {
        this.cardsAvailable = cardsAvailable;
    }
    
    public void playCard(ArrayList<Card> cards){
        if(cards.size() == 0){
            return;
        }
        for(Card card: cards){
            cardsAvailable.remove(card);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
