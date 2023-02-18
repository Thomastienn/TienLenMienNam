import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import org.w3c.dom.events.MouseEvent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class BigTwo {
    // Attributes

    // **
    // States : ANY, SINGLE, PAIR, TRIP, QUAD, SGT_X, SMD_X
    // NONE: Not valid
    // SGT: Straight - Ex: J Q K A
    // SMD: Smack Down - Ex: 3 3 4 4 5 5
    // Leading number behind SMD is the number of pairs. The larger the number, the stronger the cards. 
    // SGT also has leading number which is the number of cards
    // TRIP: Triple - Ex: 8 8 8
    // QUAD: Four of a kind - Ex: 7 7 7 7
    // currentState cannot be NONE

    // symbolRank: 3,4,5,6,7,8,9,10,J,Q,K,A,2
    // shapeRank: spade, club, diamond, heart
    // cards: All the possible combinations of cards
    // players: Number of players
    // MAX_CARDS: Number of cards that a player gets
    // round: The number of rounds played
    // lastPlayerPlayed: stores the ID of the last played player
    // allSymboolStates = {"NONE", "SINGLE", "PAIR", "TRIP", "QUAD"}
    // min: the smallest card in the table

    // ** 

    private ArrayList<Card> previousPlayedCard;
    private ArrayList<String> allSymbolStates;
    private ArrayList<Player> listPlayers;
    private ArrayList<String> symbolRank;
    private ArrayList<String> shapeRank;
    private final int MAX_CARDS = 13;
    private ArrayList<Card> cards;
    private int lastPlayerPlayed;
    private String currentState;
    private int currentTurn;
    private int players;
    private Scanner sc;
    private int round;
    private Card min;

    public BigTwo(int players){
        // Set players in range 2 - 4 players
        players = Math.min(Math.max(players, 2), 4);
        
        this.allSymbolStates = new ArrayList<>();
        this.symbolRank = new ArrayList<>();
        this.shapeRank = new ArrayList<>();
        this.sc = new Scanner(System.in);
        this.players = players;
        this.currentTurn = 0;
        this.round = 0;

        init();
        reset();
    }

    private void init(){
        // ! DO NOT CHANGE THE ORDER
        for(int i = 3; i <= 10; i++){
            symbolRank.add(Integer.toString(i));
        }
        symbolRank.add("J");
        symbolRank.add("Q");
        symbolRank.add("K");
        symbolRank.add("A");
        symbolRank.add("2");

        // Spade, Club, Diamond, Heart
        // ! DO NOT CHANGE THE ORDER
        shapeRank.add("spade");
        shapeRank.add("club");
        shapeRank.add("diamond");
        shapeRank.add("heart");

        // NONE, SINGLE, PAIR, TRIP, QUAD
        // ! DO NOT CHANGE THE ORDER
        allSymbolStates.add("NONE");
        allSymbolStates.add("SINGLE");
        allSymbolStates.add("PAIR");
        allSymbolStates.add("TRIP");
        allSymbolStates.add("QUAD");
    }

    private void reset(){
        this.min = new Card("", "", 55, false);
        this.previousPlayedCard = new ArrayList<>();
        this.listPlayers = new ArrayList<>();
        this.cards = new ArrayList<>();
        this.lastPlayerPlayed = 0;
        this.currentState = "ANY";

        generateAllCard();
        splitCards();
    }

    private boolean checkFinish(){
        return listPlayers.size() == 1;
    }
    private boolean checkWin(Player player){
        return player.getCardsAvailable().size() == 0;
    }

    private void generateAllCard(){
        for(int i = 0; i < symbolRank.size(); i++){
            for(int j = 0; j < shapeRank.size(); j++){

                String shape = shapeRank.get(j);
                String symbol = symbolRank.get(i);
                int value = shapeRank.size()*i+j;
                boolean isRed = (shape=="diamond" || shape=="heart");

                Card card = new Card(symbol, shape, value, isRed);
                cards.add(card);
            }
        }
    }

    private void splitCards(){

        // Main
        for(int i = 0; i < players; i++){
            ArrayList<Card> playerCards = new ArrayList<>();
            for(int j = 0; j < MAX_CARDS; j++){
                int randomIndex = (int)(Math.random() *cards.size());
                Card card = cards.get(randomIndex);

                // If it's the first round
                if(round == 0){
                    // If the card is the 3 spade (smallest card)
                    // so we can get the first player
                    // If there is no 3 spade get the smallest card
                    if(card.getValue() < min.getValue()){
                        min = card;
                        currentTurn = i;
                    }
                }

                playerCards.add(card);
                cards.remove(randomIndex);
            }
            playerCards.sort(((o1, o2) -> o1.compareTo(o2)));
            Player player = new Player(playerCards, i);
            listPlayers.add(player);
        }
        // DEBUG ADD SPECIFIC CARDS TO PLAYER
        
        // ArrayList<Card> playerCardsDebug = new ArrayList<>();
        // playerCardsDebug.add(new Card("4", "spade", 0,false));
        // playerCardsDebug.add(new Card("4", "club", 0,false));
        
        // playerCardsDebug.add(new Card("5", "spade", 0,false));
        // playerCardsDebug.add(new Card("5", "club", 0,false));
        
        // playerCardsDebug.add(new Card("6", "spade", 0,false));
        // playerCardsDebug.add(new Card("6", "club", 0,false));
        
        // playerCardsDebug.add(new Card("7", "spade", 0,false));
        // playerCardsDebug.add(new Card("7", "club", 0,false));
        
        // playerCardsDebug.add(new Card("8", "spade", 0,false));
        // playerCardsDebug.add(new Card("8", "club", 0,false));
        
        // playerCardsDebug.add(new Card("3", "spade", 0,false));
        // playerCardsDebug.add(new Card("K", "club", 0,false));
        
        // playerCardsDebug.add(new Card("J", "spade", 0,false));
        
        // Player player = new Player(playerCardsDebug);
        // listPlayers.add(player);

        // ArrayList<Card> playerCards = new ArrayList<>();
        // for(int j = 0; j < MAX_CARDS; j++){
        //     int randomIndex = (int)(Math.random() * cards.size());
        //     Card card = cards.get(randomIndex);
        //     cards.remove(randomIndex);
        //     playerCards.add(card);
        // }
    
        // Player player2 = new Player(playerCards);
        // listPlayers.add(player2);

        // END OF DEBUG
    }

    // Return the ID of the winner
    private int checkInstantWin(){
        for(int i = 0; i < listPlayers.size(); i++){
            int[] symbolsPlayerCards = new int[symbolRank.size()];
            boolean hasThreeSpade = false;
            boolean allSameColor = true;

            // True is red and false is black
            boolean colorAllCards = listPlayers.get(i).getCardsAvailable().get(0).isRed();

            for(Card card: listPlayers.get(i).getCardsAvailable()){
                int index = symbolRank.indexOf(card.getSymbol());
                symbolsPlayerCards[index] += 1;

                if(card.getValue() == 0){
                    hasThreeSpade = true;
                } 
                if(card.isRed() != colorAllCards){
                    allSameColor = false;
                }
            }

            // DEBUG SUMMARY PLAYER CARDS
            // System.out.println("Player " + i);
            // for(int j = 0; j < symbolsPlayerCards.length; j++){
            //     System.out.println(symbolRank.get(j)+ ": " + symbolsPlayerCards[j]);
            // }
            // System.out.println();

            // Check instant win
            // Add case first round and other rounds
            if(round == 0 && hasThreeSpade){
                ArrayList<Card> threePairSMD = findStraightPairs(cards, 3, 2);
                if(threePairSMD.contains(listPlayers.get(i).getCardsAvailable().get(0))){
                    return i;
                }
            }

            // Check quad 2 and quad 3
            // Quad 3 only valid if it's the first round
            if(symbolsPlayerCards[MAX_CARDS-1] == 4 ||
                (symbolsPlayerCards[0] == 4 && round == 0)){
                return i;
            }

            // Check full straight
            boolean win = true;
            for(int j = 0; j < MAX_CARDS - 1; j++){
                if(symbolsPlayerCards[j] == 0){
                    win = false;
                    break;
                }
            }
            if(win){
                return i;
            }
            
            // Check 6 pairs
            int numPairs = 0;
            for(int num: symbolsPlayerCards){
                if(num == 2 || num == 4){
                    numPairs += (int)(num/2);
                }
            }
            if(numPairs == 6){
                return i;
            }

            // Check if all the same colors
            if(allSameColor){
                return i;
            }

            // Check 5-pair smackdown 
            if(numPairs == 5){
                for(int j = 0; j < symbolsPlayerCards.length-5; j++){
                    int countSequencePairs = 0;
                    for(int k = j; k < j+5; k++){
                        if(symbolsPlayerCards[k] == 2){
                            countSequencePairs += 1;
                        } else {
                            countSequencePairs = 0;
                        }
                        if(countSequencePairs == 5){
                            return i;
                        }
                    }
                }
            }
        }
        return -1;
    }

    private void printCards(Player player){
        ArrayList<Card> playerCards = player.getCardsAvailable();

        System.out.println("Cards: ");
        for(int i = 0; i < playerCards.size(); i++){
            System.out.printf("%-4s",String.valueOf(i));
        }

        System.out.println();

        for(Card card: playerCards){
            System.out.printf("%-4s",card);
        }
        System.out.println();
    }

    private boolean allSameSymbol(ArrayList<Card> checkCards){
        if(checkCards.size() == 0 || checkCards.size() == 1){
            return true;
        }
        String symbol = checkCards.get(0).getSymbol();
        for(Card card: checkCards){
            if(!(card.getSymbol().equalsIgnoreCase(symbol))){
                return false;
            }
        }
        return true;
    }

    private String checkStraight(ArrayList<Card> checkCards){
        if(checkCards.size() >= 3){  
            String initCardSymbol = checkCards.get(0).getSymbol();
            int indexInit = symbolRank.indexOf(initCardSymbol);
            boolean isStraight = true;
            boolean hasTwo = false;
    
            for(Card card: checkCards){
                if(!(card.getSymbol().equals(symbolRank.get(indexInit)))){
                    isStraight = false;
                    break;
                }
                if(card.getSymbol().equals("2")){
                    hasTwo = true;
                }
                indexInit += 1;
            }
            if(isStraight && !hasTwo){
                return "SGT_" + checkCards.size();
            }
        }
        return "NONE";
    }

    private String checkSmackDown(ArrayList<Card> checkCards){
        if(checkCards.size() >= 6 && checkCards.size()%2==0){
            int numPairs = 0;
            boolean isContinuous = true;
            boolean isAllPairs = true;
            String initCardSymbol = checkCards.get(0).getSymbol();
            int indexInit = symbolRank.indexOf(initCardSymbol);

            // Check by pair
            for(int i = 0; i < checkCards.size()-1; i+=2){
                Card card1 = checkCards.get(i);
                Card card2 = checkCards.get(i+1);

                // If they are pairs
                if(card1.getSymbol().equals(card2.getSymbol())){
                    numPairs += 1;
                    
                    // If it's not continuous from the previous pair
                    if(!(card1.getSymbol().equals(symbolRank.get(indexInit)))){
                        isContinuous = false;
                        break;
                    }
                } else {
                    isAllPairs = false;
                    break;
                }
                indexInit += 1;
            }
            if(isContinuous && isAllPairs && numPairs >= 3){
                return ("SMD_"+numPairs);
            }
        }
        return "NONE";
    }

    private String stateOfCards(ArrayList<Card> playedCards){
        
        // Check all the cards have the same symbol
        if(allSameSymbol(playedCards)){
            // Check single
            if(playedCards.size() == 1){
                return "SINGLE";
            }
    
            // Check pair
            if(playedCards.size() == 2){
                return "PAIR";
            }
    
            // Check triple  
            if(playedCards.size() == 3 ){
                return "TRIP";
            }  

            // Check quad
            if(playedCards.size() == 4){
                return "QUAD";
            }
        }

        // Check straight
        String resultCheckStraight = checkStraight(playedCards);
        if(!resultCheckStraight.equals("NONE")){  
            return resultCheckStraight;
        }

        // Check smack down
        String resultCheckSMG = checkSmackDown(playedCards);
        if(!resultCheckSMG.equals("NONE")){
            return resultCheckSMG;
        }


        return "NONE";
    }

    private ArrayList<Card> findSameSymbol(ArrayList<Card> deck, int lengthCard){
        for(int i = 0; i < (deck.size() - lengthCard + 1); i += 1){
            ArrayList<Card> multipleCards = new ArrayList<>();
            for(int j = i; j < i + lengthCard; j++){
                Card card = deck.get(j);
                multipleCards.add(card);
            }
            
            // ! DEBUG PURPOSES
            System.out.println(multipleCards);

            if(allSameSymbol(multipleCards) &&
            (compareToPrevCards(multipleCards) > 0)){
                return multipleCards;
            }
        }
        return new ArrayList<>();
    }

    private ArrayList<Card> findStraightPairs(ArrayList<Card> deck, int lengthCard, int nPairs){
        ArrayList<Card> result = new ArrayList<>();

        int[] summaryBotCards = new int[MAX_CARDS];

        // Summary the cards
        for(int i = 0; i < deck.size(); i++){
            Card card = deck.get(i);
            int index = symbolRank.indexOf(card.getSymbol());
            summaryBotCards[index] += 1;
        }

        // Loop through the count of the presence of each symbol
        // Ex: 3 4 5 6 7 8 9 10 J Q K A 2 -> length = MAX_CARDS
        //     0 2 1 1 0 0 0 2  3 3 0 1 2 -> Must total up to 13

        for(int i = 0; i < MAX_CARDS-lengthCard; i++){
            int symbolCount = summaryBotCards[i];

            // Maybe the start of the straight
            if(symbolCount >= nPairs){

                // Count to find if the straight is long enough
                int count = 0;

                for(int j = i; j < i+lengthCard; j++){
                    if(summaryBotCards[j] >= nPairs){
                        count += 1;
                    }
                }

                // A straight or smack down must has a
                // sequence range from 3 different symbols
                if(count < 3){
                    continue;
                }

                // A straight must be exactly the same length
                // as the opponent cards played before
                // but the smack down is different 
                // -> the more pairs -> the more powerful

                // If there is a straight valid
                if(nPairs==1?(count == lengthCard):(count >= lengthCard)){
                    // Get the straight in Card form

                    String symbolStraight = symbolRank.get(i);

                    if(previousPlayedCard.size() != 0){
                        String prevStartSymbol = previousPlayedCard.get(0).getSymbol();

                        // Have to check if the initial of card is greater than prev deck
                        // It's smaller
                        if(count == lengthCard){
                            if(i < symbolRank.indexOf(prevStartSymbol)){
                                continue;
                            }
                        } 
                    }

                    int symbolIndex = -1;

                    // Get all the initial value same symbol
                    for(int o1 = 0; o1 < nPairs; o1++){
                        // Get the inital value
                        for(int j = symbolIndex+1; j < deck.size(); j++){
                            Card card = deck.get(j);
                            if(card.getSymbol().equals(symbolStraight)){
                                result.add(card);
                                symbolIndex = j;
                                break;
                            }
                        }
                    }
                    

                    // *Get others
                    // Loop each symbol
                    // Ex: J Q K
                    // Loop: J -> Q -> K

                    for(int j = i+1; j < i+lengthCard; j++){
                        symbolStraight = symbolRank.get(j); 

                        // Find the card that has symbolStraight
                        for(int o1 = 0; o1 < nPairs; o1++){ 
                            for(int k = symbolIndex+1; k < deck.size(); k++){
                                Card curCard = deck.get(k);
                                if(curCard.getSymbol().equals(symbolStraight)){
                                    symbolIndex = k;
                                    result.add(curCard);
                                    break;
                                }
                            }
                        }
                    }
                    
                    if(count == lengthCard && previousPlayedCard.size() != 0){
                        // Check if the last symbol is greater than the last of the prev
                        Card prevLastCard = previousPlayedCard.get(previousPlayedCard.size()-1);
                        if(result.get(result.size()-1).compareTo(prevLastCard) < 0){
                            // If there is more to check
                            // Ex: bot has K spade and K heart
                            // Prev is K diamond
                            // result is K spade
                            // We can continue to check the next K which is K heart
                            result.remove(result.size()-1);
                            
                            // symbolIndex is the index of the last card
                            // Start checking from it
                            boolean foundGreater = false;

                            for(int j = symbolIndex+1; j < deck.size(); j++){
                                Card curCard = deck.get(j);
                                
                                // If it goes too far
                                // In the above example then it reaches A
                                if(!curCard.getSymbol().equals(prevLastCard.getSymbol())){
                                    break;
                                }
                                
                                // If it found the greater one 
                                // In the above example, it found K heart
                                if(curCard.compareTo(prevLastCard) > 0){
                                    foundGreater = true;
                                    result.add(curCard);
                                    break;
                                }
                            }

                            // This straight not working
                            if(!foundGreater){
                                // Remove the result and start again
                                result = new ArrayList<>();
                                continue;
                            }
                        }
                    }
                    
                    break;
                }
            }
        }
        System.out.println(Arrays.toString(summaryBotCards));

        if(result.size() != 0){
            return result;
        }
        return new ArrayList<>();
    }

    private ArrayList<Card> botsPlayed(ArrayList<Card> botCards, int botID){    
        ArrayList<Card> botPlayedCards = new ArrayList<>();
        
        // ! OPTIMIZE in the future
        int cardsNeeded = allSymbolStates.indexOf(currentState);
        // ! END

        // currentState cannot be NONE
        switch(currentState){
            case "ANY":
                // ! BUG:
                // ! If it has 3 spade and this is the first round
                // ! then it has to play cards that 
                // ! has 3 spade in it 
                // ! If there is no 3 spade in deck
                // ! -> The cards played must include the smallest card
                // *FIXED

                if(round == 0 && 
                    previousPlayedCard.size() == 0 &&
                    botCards.get(0).getValue() == min.getValue()){

                    // First is find if there is straight with the min
                    int lenStraight = -1;
                    for(int i = botCards.size()-1; i >= 3; i--){
                        botPlayedCards = findStraightPairs(botCards, i, 1);
                        boolean valid = false;

                        if(botPlayedCards.size() != 0){
                            lenStraight = i;
                            valid = true;
                        }

                        if(!botPlayedCards.contains(min)){
                            botPlayedCards = new ArrayList<>();
                            lenStraight = -1;
                        } else {
                            // If there is a straight include min
                            if(valid){
                                break;
                            }
                        }
                    }
                    if(lenStraight != -1){
                        currentState = "SGT_" + lenStraight;
                        break;
                    }

                    // !BUG
                    // *FIXED

                    // Next is to check from TRIP to SINGLE
                    for(int i = 3; i >= 1; i--){
                        botPlayedCards = findSameSymbol(botCards, i);
                        boolean valid = false;
                        if(botPlayedCards.size() != 0){
                            // Set current state
                            currentState = allSymbolStates.get(i);
                            valid = true;
                        }
                        if(!botPlayedCards.contains(min)){
                            botPlayedCards = new ArrayList<>();
                        } else {
                            // If they are all symbol and include min
                            if(valid){
                                break;
                            }
                        }
                    }

                    break;
                }

                // If there is a smack down and 
                // You almost run out of cards
                // -> Play it
                int smackDown = -1;
                for(int i = 4; i >= 3; i--){
                    botPlayedCards = findStraightPairs(botCards, i, 2);
                    
                    // If it has smack down and a single last card
                    if(botPlayedCards.size() != 0 && botCards.size() <= i*2+1){
                        smackDown = i;   
                        break;                     
                    }
                }
                if(smackDown != -1){
                    currentState = "SMD_" + smackDown;
                    break;
                }

                // If there is a straight -> play it
                int straightLen = -1;

                for(int i = botCards.size()-1; i >= 3; i--){
                    // ! BUG: if there is no prev
                    // ! This could cause out of bounds
                    // *FIXED

                    botPlayedCards = findStraightPairs(botCards, i, 1);
                    if(botPlayedCards.size() != 0){
                        straightLen = i;
                        break;
                    }
                }
                if(straightLen != -1){
                    currentState = "SGT_" + straightLen; 
                    break;
                }

                // If there is cards from type TRIP to type SINGLE -> play it
                for(int i = 3; i >= 1; i--){
                    // Play quad instead of 2 if the cards is less than 6
                    if(i == 2 && (botCards.size() <= 6)){
                        botPlayedCards = findSameSymbol(botCards, 4);
                        if(botPlayedCards.size() != 0){
                            // Set current state
                            currentState = "QUAD";
                            break;
                        }
                    }

                    // If there is less than 3 cards and state is ANY
                    // Then play the largest card
                    // So higher chance that the bot wins
                    if(i == 1 && (botCards.size() <= 3)){
                        botPlayedCards.add(botCards.get(botCards.size()-1));
                        currentState = "SINGLE";
                        break;
                    }

                    botPlayedCards = findSameSymbol(botCards, i);
                    if(botPlayedCards.size() != 0){
                        // Set current state
                        currentState = allSymbolStates.get(i);
                        break;
                    }
                }
                break;
            case "SINGLE":  
            case "PAIR":
            case "TRIP":
            case "QUAD":
                // If there is less than 3 cards and state is ANY
                // Then play the largest card
                // So higher chance that the bot wins
                if(currentState.equals("SINGLE") && (botCards.size() <= 3)){
                    Card playCard = botCards.get(botCards.size()-1);
                    if(playCard.compareTo(previousPlayedCard.get(0)) > 0){
                        botPlayedCards.add(playCard);
                    }
                    break;
                }

                botPlayedCards = findSameSymbol(botCards, cardsNeeded);
                
                // A quad can be smacked back by a 4-pair
                if(currentState.equals("QUAD")){
                    if(botPlayedCards.size() == 0){
                        botPlayedCards = findStraightPairs(botCards, 4, 2);
                    }
                }

                // Can smack down a 2 with smack down or quad
                boolean singleTwo = (currentState.equals("SINGLE") &&
                previousPlayedCard.get(0).getSymbol().equals("2"));

                boolean pairTwo = (currentState.equals("PAIR") &&
                previousPlayedCard.get(0).getSymbol().equals("2") &&
                previousPlayedCard.get(1).getSymbol().equals("2"));

                if(singleTwo || pairTwo){

                    if(botPlayedCards.size() == 0){
                        // Check if there is a quad first
                        botPlayedCards = findSameSymbol(botCards, 4);

                        // Next check if there is a 4-pair smack down
                        if(botPlayedCards.size() == 0){
                            botPlayedCards = findStraightPairs(botCards, 4, 2);
                        }

                        // Check only smack down single card
                        // Next check 3-pair smack down
                        if((botPlayedCards.size() == 0) && singleTwo){
                            botPlayedCards = findStraightPairs(botCards, 3, 2);
                        }
                    }
                }

                break;

            // Default is SGT and SMD ONLY
            default:
                String[] stateWithNum = currentState.split("_");
                String state = stateWithNum[0];
                int num = Integer.parseInt(stateWithNum[1]);

                if(state.equals("SGT")){
                    // ! This solution cannot check 
                    // ! non-adjacent straight 
                    // *FIXED

                    // ! HAVEN'T compare value to the prev deck
                    // ! LOGIC ERROR #0005
                    // *FIXED

                    // ! HAVEN'T compare last value
                    // *FIXED

                    botPlayedCards = findStraightPairs(botCards, num, 1);

                } else if(state.equals("SMD")){
                    botPlayedCards = findStraightPairs(botCards, num, 2);

                    // Quad can smack back smackdown 3 pairs
                    if(botPlayedCards.size() == 0 && num == 3){
                        botPlayedCards = findSameSymbol(botCards, 4);
                        if(botPlayedCards.size() != 0){
                            // Set current state
                            currentState = "QUAD";
                            break;
                        // Can still smack back 3 pairs by 4-pair smack down
                        } else {
                            botPlayedCards = findStraightPairs(botCards, 4, 2);
                        }
                    }
                }

                break;
        }

        if(botPlayedCards.size() != 0){
            previousPlayedCard = botPlayedCards;
            lastPlayerPlayed = botID;
        }
        return botPlayedCards;
    }

    // * NOTE: check valid before use this function
    private int compareToPrevCards(ArrayList<Card> checkCards){
        // ! BUG: 0003
        // ! Cannot check by sum of all values
        // ! -> Check last cards of the deck
        // * FIXED

        // Deck of cards cannot be equal
        // -> Cannot return 0
        
        // Positive: Greater than prev
        // Negative: Less than prev

        if(currentState.equals("ANY")){
            return 1;
        }
        if(previousPlayedCard.size() == 0){
            return 1;
        }
        
        return checkCards.get(checkCards.size()-1).compareTo(previousPlayedCard.get(previousPlayedCard.size()-1));
    }

    private String cardToDir(Card card){
        return System.getProperty("user.dir").replace("\\", "/") + "/img/" + card.toString() + ".png";
    }

    private boolean checkValid(ArrayList<Card> cardsPlayed, String playerCardsState){
        // ! BUG: Have to check if it's the first play, player cards must have the min
        boolean checkSmacking = false;
        
        // Check if player can smack down
        if(previousPlayedCard.size() == 1){
            // Check if last card was a 2
            if(previousPlayedCard.get(0).getSymbol().equals("2")){
                // Check if player plays a smack down
                if(playerCardsState.contains("SMD")){
                    checkSmacking = true;
                }
            }
        } else {
            // Smack back a smack down sequence
            if(currentState.contains("SMD")){
                String[] stateWithNum = currentState.split("_");
                int num = Integer.parseInt(stateWithNum[1]);

                // The one with more pairs wins
                if(playerCardsState.contains("SMD")){
                    String[] stateWithNumPlayer = playerCardsState.split("_");
                    int numPlayer = Integer.parseInt(stateWithNumPlayer[1]);

    
                    if(numPlayer > num){
                        checkSmacking = true;
                    }

                // A quad can smack back a 3-pair smack down
                } else if(playerCardsState.equals("QUAD")){
                    if(num==3){
                        checkSmacking = true;
                    }
                }
            // Smack down a pair of 2
            } else if(currentState.equals("PAIR")){
                if(previousPlayedCard.get(0).getSymbol().equals("2") &&
                    previousPlayedCard.get(1).getSymbol().equals("2")){

                    // Check if a player wants to smack down pair of 2
                    // Only valid if the smack down sequence is greater than 3 pairs

                    String[] stateWithNumPlayer = playerCardsState.split("_");
                    int numPlayer = Integer.parseInt(stateWithNumPlayer[1]);

                    if((playerCardsState.contains("SMD") && numPlayer >= 4) ||
                        playerCardsState.equals("QUAD")){
                        checkSmacking = true;
                    }
                }
            // Smack down a quad by smack down sequence >= 4
            } else if(currentState.equals("QUAD")){
                // Check if a player wants to smack down a quad
                if(playerCardsState.contains("SMD")){
                    String[] stateWithNumPlayer = playerCardsState.split("_");
                    int numPlayer = Integer.parseInt(stateWithNumPlayer[1]);

                    // Only valid if the smack down sequence is greater than 3 pairs
                    if(numPlayer >= 4){
                        checkSmacking = true;
                    }
                }
            }
        }

        boolean playMin = true;
        if(previousPlayedCard.size()==0 && round == 0){
            if(!cardsPlayed.contains(min)){
                playMin = false;
            }
        }
    
        return (!playerCardsState.equals("NONE") && 
        ((currentState.equals("ANY")) || playerCardsState.equals(currentState) || checkSmacking) && 
        ((compareToPrevCards(cardsPlayed) > 0) || checkSmacking) && 
        playMin);
    }

    private void initGUI(){
        // Directory
        final String currentWorkingDir = System.getProperty("user.dir").replace("\\", "/");
        final String imgDir = currentWorkingDir + "/img";
        ImageIcon backIcon = new ImageIcon(imgDir + "/gray_back.png");

        // Get screen size
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = device.getDisplayMode().getWidth();
        int height = device.getDisplayMode().getHeight()-100;

        JFrame frame = new JFrame("Big Two");

        JPanel mainPanel = new JPanel(new BorderLayout());
        //mainPanel.setBackground(Color.RED);

        // First line
        JPanel firstLine = new JPanel();
        JLabel player1 = new JLabel(backIcon);

        firstLine.add(player1);
        firstLine.setBackground(Color.RED);

        // Second line
        JPanel secondLine = new JPanel(new BorderLayout());
        JLabel player2 = new JLabel(backIcon);
        JLabel player3 = new JLabel(backIcon);
        JPanel prevCards = new JPanel(new GridLayout(0, Math.max(previousPlayedCard.size(), 1)));

        // Load previous card on the deck
        for(Card card: previousPlayedCard){
            ImageIcon cardImg = new ImageIcon(cardToDir(card));
            JLabel cardPrev = new JLabel(cardImg);
            prevCards.add(cardPrev);
        }
        prevCards.setBackground(Color.GREEN);

        secondLine.add(player2, BorderLayout.WEST);
        secondLine.add(prevCards, BorderLayout.CENTER);
        secondLine.add(player3, BorderLayout.EAST);
        secondLine.setBackground(Color.YELLOW);

        // Third line
        JPanel thirdLine = new JPanel(new BorderLayout());
        JPanel btns = new JPanel();
        JPanel playerCards = new JPanel();

        JButton playBtn = new JButton("Play");
        JButton skipBtn = new JButton("Skip");
        
        btns.add(playBtn);
        btns.add(skipBtn);
        
        // Load main player cards
        for(Player player: listPlayers){
            if(player.getId() == 0){
                for(int i = 0; i< player.getCardsAvailable().size(); i++){
                    Card card = player.getCardsAvailable().get(i);

                    ImageIcon cardImg = new ImageIcon(cardToDir(card));
                    JButton cardBtn = new JButton();

                    cardBtn.setIcon(cardImg);
                    cardBtn.setBorderPainted(false); 
                    cardBtn.setContentAreaFilled(false); 
                    cardBtn.setActionCommand(Integer.toString(i));
                    cardBtn.addActionListener(e -> {
                        System.out.println(cardBtn.getActionCommand());   
                    });

                    playerCards.add(cardBtn);
                }
            }
        }
        playerCards.setBackground(Color.BLUE);

        thirdLine.add(playerCards, BorderLayout.SOUTH);
        thirdLine.add(btns, BorderLayout.NORTH);

        // Add the the main
        mainPanel.add(firstLine, BorderLayout.NORTH);
        mainPanel.add(secondLine, BorderLayout.CENTER);
        mainPanel.add(thirdLine, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setVisible(true);
    }

    private void playConsole() throws InterruptedException{
        // ! Optimize purposes
        // ! DEBUG Performance
        // long start1 = System.nanoTime();
        // long end1 = System.nanoTime(); 
        // System.out.println("Elapsed Time in nano seconds: "+ (end1-start1));

        
        // A round
        // Added more rounds instead of only 1
        
        String userContinue;

        // Many rounds 
        do {
            // Instant-win winner
            int winner = checkInstantWin();
            if(winner != -1){
                System.out.println("Player " + winner + " wins!");
                printCards(listPlayers.get(winner));
                return;
            }

            // Main flow of a round
            while(!(checkFinish())){
                Player currentPlayer = listPlayers.get(currentTurn);
                ArrayList<Card> currentPlayerCards = currentPlayer.getCardsAvailable();

                // ! BUG: It updates everyTIME
                // *FIXED 

                //When everyone passes the turn
                if(currentPlayer.getId() == lastPlayerPlayed){
                    currentState = "ANY"; 
                }

                // If it's our turn
                // Main player moves
                if(currentPlayer.getId() == 0){
                    // Play the card OR Pass the turn
                    System.out.println("PREV: " + previousPlayedCard);
                    System.out.println(currentState);
                    System.out.println("MIN: " + min);

                    printCards(listPlayers.get(0));

                    System.out.print("Play or Skip (P/S): ");
                    String playerChoose = sc.nextLine();

                    // Player plays
                    if(playerChoose.equalsIgnoreCase("P") ||
                        currentState.equals("ANY")){
                        ArrayList<Card> cardsPlayed = new ArrayList<>();

                        String indexCardStr;

                        // Select cards to play
                        do {
                            System.out.print("Choose card: ");
                            indexCardStr = sc.nextLine();
                            
                            if(indexCardStr.length() == 0){
                                break;
                            }
                            
                            int indexCard = Integer.parseInt(indexCardStr);

                            try {
                                Card played = currentPlayerCards.get(indexCard);
                                int idx = cardsPlayed.indexOf(played); 

                                // Delete cards if it already existed in the played cards
                                // If it's not existed then add to the play cards
                                if(idx < 0){
                                    cardsPlayed.add(played);
                                } else {
                                    cardsPlayed.remove(idx);
                                }
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("OUT OF BOUNDS");
                                continue;
                            }

                        } while(true);
                        System.out.println();

                        // Play the cards
                        String stateCard = stateOfCards(cardsPlayed);

                        // ! BUG: 0002 
                        // ! Haven't check if the value of the cards is larger
                        // ! than the previous card
                        // *FIXED

                        // If the cards are a valid move 
                        // If the cards is the same state as current state
                        // ANY is when player can do any move
                        if(checkValid(cardsPlayed, stateCard)){
                            currentState = stateCard;
                            currentPlayer.playCard(cardsPlayed);
                            previousPlayedCard = cardsPlayed;
                            lastPlayerPlayed = 0;

                            // Update the state
                            if(currentState.equals("ANY")){
                                currentState = stateCard;
                            }
                        } else {
                            // If player play different state
                            // OR player plays lower rank than
                            // previous cards that bot played
                            // If it's not a valid state
                            // Play again new combination of cards
                            continue;
                        }
                    }
                
                // Bot moves
                // If it's empty means bots skips all
                } else {
                    // Find suitable cards and play
                    // If there is no possible move -> It skips the turn
                    System.out.println(previousPlayedCard);
                    printCards(currentPlayer);
                    currentPlayer.playCard(botsPlayed(currentPlayerCards, currentPlayer.getId()));
                    printCards(currentPlayer);
                    // *Uncomment line below when finish 
                    //Thread.sleep(3000);
                }

                // If current player plays all the cards
                if(checkWin(currentPlayer)){
                    if(currentPlayer.getId() == 0){
                        System.out.println("You win!");
                    } else {
                        int place = ((players-listPlayers.size())+1);
                        String postFix = "";
                        
                        switch(place){
                            case 1:
                            postFix = "st";
                            break;
                            case 2:
                            postFix = "nd";
                                break;
                            case 3:
                                postFix = "rd";
                                break;
                            default:
                                postFix = "th";
                                break;
                            }
                            
                            System.out.println("Player " + currentPlayer.getId() + " finished in " + place + postFix + " place");
                    }
                    listPlayers.remove(currentTurn);
                    currentTurn = Math.max(currentTurn-1, 0);
                }

                // ! DEBUG ID: 001
                // Detail: If you finished, then the currentTurn can still
                // be 0 and it will counted the second player as main player
                // *FIXED

                System.out.println();
                System.out.println("Turn " + currentTurn);
                currentTurn = (currentTurn+1)%(listPlayers.size());
            }
            // Last player -> lose
            Player lastPlayer = listPlayers.get(0);
            System.out.println("Player " + lastPlayer.getId() + " loses!");
            
            System.out.print("Do you want to continue (Y/N): ");
            userContinue = sc.nextLine();
            if(userContinue.equalsIgnoreCase("N") ||
            userContinue.equalsIgnoreCase("no") || 
            userContinue.equalsIgnoreCase("nope") || 
            userContinue.equalsIgnoreCase("nah")){
                break;
            }

            round += 1;
            reset();
        } while(true);

        sc.close();
    }

    public void run() throws InterruptedException{  
        // Main
        playConsole();

        // Initialize GUI
        // cards = new ArrayList<>();
        // generateAllCard();
        // previousPlayedCard.add(cards.get(20));
        // previousPlayedCard.add(cards.get(21));
        // previousPlayedCard.add(cards.get(22));
        // previousPlayedCard.add(cards.get(23));
        // previousPlayedCard.add(cards.get(24));
        //initGUI();

        // Get the system
        // System.getProperty("os.name")

        // ! DEBUGGING PURPOSES
        // *DEBUG PRINT ALL THE COMBINATION OF CARDS
        // cards = new ArrayList<>();
        // generateAllCard();
        // System.out.println(cards + " " + cards.size());
        // *DEBUG BOTS PLAY
        // System.out.println(round);
        // cards = new ArrayList<>();
        // generateAllCard();

        // previousPlayedCard.add(cards.get(0));
        // previousPlayedCard.add(cards.get(1));

        //currentState = stateOfCards(previousPlayedCard);
        // currentState = "ANY";

        // System.out.println(previousPlayedCard);

        // Player bot = listPlayers.get(0);
        // ArrayList<Card> lCards = bot.getCardsAvailable();

        // int last = lCards.size()-1;

        // lCards.remove(0);
        // lCards.remove(0);
        // lCards.remove(0);
        // lCards.remove(0);
        // lCards.remove(0);
        // lCards.remove(0);
        // lCards.remove(0);
        // lCards.remove(0);

        // lCards.add(cards.get(2));
        // lCards.add(cards.get(3));

        // lCards.add(cards.get(6));
        // lCards.add(cards.get(7));

        // lCards.add(cards.get(8));
        // lCards.add(cards.get(9));

        // lCards.add(cards.get(12));
        // lCards.add(cards.get(13));

        // lCards.sort(((o1, o2) -> o1.compareTo(o2)));

        // printCards(bot);
        // printCards(new Player(botsPlayed(lCards, 0), -1));

        // *DEBUG PRINT RANDOM CARDS
        // reset();
        // printCards(listPlayers.get(0));

        // *DEBUG PRINT ALL PLAYERS CARDS
        // for(int i = 0; i < listPlayers.size(); i++){
        //     Player player = listPlayers.get(i);
        //     System.out.println("Player " + i);
        //     printCards(player);
        //     System.out.println();
        // }

        // *DEBUG COMPARE DECK OF CARDS TO PREV DECK
        // ArrayList<Card> playerCardsDebug = new ArrayList<>();
        // playerCardsDebug.add(new Card("K", "spade", 10,false));

        // previousPlayedCard = new ArrayList<>();
        // previousPlayedCard.add(new Card("J", "spade", 5,false));

        // System.out.println(compareToPrevCards(playerCardsDebug));

        // *DEBUG ADD SPECIFIC CARDS TO PLAYER
        // ArrayList<Card> playerCardsDebug = new ArrayList<>();
        // playerCardsDebug.add(new Card("3", "spade", 0,false));
        // playerCardsDebug.add(new Card("3", "club", 0,false));
        // playerCardsDebug.add(new Card("4", "spade", 0,false));
        // playerCardsDebug.add(new Card("4", "club", 0,false));
        // playerCardsDebug.add(new Card("5", "spade", 0,false));
        // playerCardsDebug.add(new Card("5", "spade", 0,false));
        // playerCardsDebug.add(new Card("6", "spade", 0,false));
        // playerCardsDebug.add(new Card("6", "spade", 0,false));
        // *DEBUG STATE OF CARDS
        // System.out.println(stateOfCards(playerCardsDebug));
        // System.out.println(checkStraight(playerCardsDebug));
        // System.out.println(checkSmackDown(playerCardsDebug));

        // *DEBUG PLAYER CARDS
        // for(int i = 0; i < listPlayers.size(); i++){
        //     System.out.println(i);
        //     for(Card card: listPlayers.get(i).getCardsAvailable()){
        //         System.out.println(card.getSymbol() + " " + card.getShape());
        //     }
        //     System.out.println();
        // }

        // *DEBUG SUMMARY PLAYER CARDS
        //checkInstantWin();

        // *DEBUG INSTANT WIN
        // splitCards();
        // Player player = listPlayers.get(0);
        // for(Card card: player.getCardsAvailable()){
        //     System.out.println(card.getShape() + " " + card.getSymbol());
        // }
        // checkInstantWin();

        // *DEBUG INSTANT WIN
        //
        // int times= 0;
        // while(true){
        //     // Main
        //     times += 1;
        //     reset();
        //     int winner = checkInstantWin();
        //     if(winner != -1){
        //         // DEBUG PRINT PLAYERS CARDS
        //         for(int i = 0; i < listPlayers.size(); i++){
        //             Player player = listPlayers.get(i);
        //             System.out.println("Player " + i);
        
        //             ArrayList<Card> list = player.getCardsAvailable();
                    
        //             for(int j = 0; j < list.size(); j++){
        //                 Card card = list.get(j);
        //                 System.out.print(card.getSymbol() + card.getShape() + " ");
        //             }
        //             System.out.println("\n");
        //         }
        //         System.out.println("Player " + winner+ " WINS");
        //         System.out.println("After " + times + " times");
        //         return;
        //     }
        // }
        // int times = 0;
        // while(true){
        //     int winner = checkInstantWin();
        //     if(winner != -1){
        //         System.out.println("Player " + winner + " wins!");
        //         printCards(listPlayers.get(winner));
        //         System.out.println(times);
        //         return;
        //     }
        //     times++;
        //     reset();
        // }

        // *DEBUG PRINT PLAYERS CARDS
        //
        // for(int i = 0; i < listPlayers.size(); i++){
        //     Player player = listPlayers.get(i);
        //     System.out.println("Player " + i);

        //     ArrayList<Card> list = player.getCardsAvailable();
            
        //     for(int j = 0; j < list.size(); j++){
        //         Card card = list.get(j);
        //         System.out.print(card.getSymbol() + card.getShape() + " ");
        //     }
        //     System.out.println("\n");
        // ! END OF DEBUGGING
    }
}