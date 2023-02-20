package com.bigtwo.main;

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
