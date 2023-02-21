package com.bigtwo.main;

/* ---------------------------------------------------------------------------------
* 		Program Name: Card.java 
* 		Date Written: February 13th, 2023
* 			  Author: Thomas Vu
* 
*            Purpose: 1. To implement Card class. 
*                      
* ----------------------------------------------------------------------------------
* Modification History: 
* ----------------------------------------------------------------------------------
* Date			Person	CSR#	Description
* 2023-02-13	TV 		1.01	Initial Version 
* 2023-02-13	TV 		1.02	Implement attributes and getters setters 
* 2023-02-20	TV 		1.03	Apply industry standard 
* ----------------------------------------------------------------------------------
* Future Enhancements: 
* -------------------- 
* 1. Create attributes (Done 13-02-2023)
* 2. set methods (Done 13-02-2023)
* 3. To be complete 
* 								
*/ 

public class Card {
    private String symbol;
    private String shape;
    private int value;
    private boolean isRed;

    public Card(){}
    public Card(String symbol, String shape, int value, boolean isRed){
        this.symbol = symbol;
        this.shape = shape;
        this.value = value;
        this.isRed = isRed;
    }
    public int compareTo(Card compareCard) {
	
		int compareValue = ((Card) compareCard).getValue(); 

		return this.value - compareValue;
	}

    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public String getShape() {
        return shape;
    }
    public void setShape(String shape) {
        this.shape = shape;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public boolean isRed() {
        return isRed;
    }
    public void setRed(boolean isRed) {
        this.isRed = isRed;
    }
    @Override
    public String toString() {
        return this.symbol + String.valueOf(this.shape.charAt(0)).toUpperCase();
    }
    
}
