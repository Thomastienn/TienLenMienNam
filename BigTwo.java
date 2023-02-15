import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

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
    // ** 

    private ArrayList<Card> previousPlayedCard;
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

    public BigTwo(int players){
        // Set players in range 2 - 4 players
        players = Math.min(Math.max(players, 2), 4);
        
        this.previousPlayedCard = new ArrayList<>();
        this.listPlayers = new ArrayList<>();
        this.symbolRank = new ArrayList<>();
        this.shapeRank = new ArrayList<>();
        this.sc = new Scanner(System.in);
        this.cards = new ArrayList<>();
        this.currentState = "ANY";
        this.lastPlayerPlayed = 0;
        this.players = players;
        this.currentTurn = 0;
        this.round = 0;

        initRank();
        reset();
    }

    public void initRank(){
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
    }

    public void reset(){
        this.listPlayers = new ArrayList<>();
        generateAllCard();
        splitCards();
    }

    public boolean checkFinish(){
        return listPlayers.size() == 0;
    }
    public boolean checkWin(Player player){
        return player.getCardsAvailable().size() == 0;
    }

    public void generateAllCard(){
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

    public void splitCards(){

        // Main
        for(int i = 0; i < players; i++){
            ArrayList<Card> playerCards = new ArrayList<>();
            for(int j = 0; j < MAX_CARDS; j++){
                int randomIndex = (int)(Math.random() *cards.size());
                Card card = cards.get(randomIndex);
                cards.remove(randomIndex);
                playerCards.add(card);
            }
            playerCards.sort(((o1, o2) -> o1.compareTo(o2)));
            Player player = new Player(playerCards);
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
    public int checkInstantWin(){
        for(int i = 0; i < listPlayers.size(); i++){
            int[] symbolsPlayerCards = new int[symbolRank.size()];
            boolean hasThreeSpade = false;
            boolean allSameColor = true;

            // True is red and false is black
            boolean colorAllCards = listPlayers.get(i).getCardsAvailable().get(0).isRed();

            for(Card card: listPlayers.get(i).getCardsAvailable()){
                int index = symbolRank.indexOf(card.getSymbol());
                symbolsPlayerCards[index] += 1;

                if(card.getSymbol() == "3" && card.getShape() == "spade"){
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
            // TODO: Add case first round and other rounds


            // Check quad 2 and quad 3
            if(symbolsPlayerCards[MAX_CARDS-1] == 4 ||
                (symbolsPlayerCards[0] == 4)){
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

    public void printCards(Player player){
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

    public boolean allSameSymbol(ArrayList<Card> checkCards){
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

    public String checkStraight(ArrayList<Card> checkCards){
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

    public String checkSmackDown(ArrayList<Card> checkCards){
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

    public String stateOfCards(ArrayList<Card> playedCards){
        
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

    public ArrayList<Card> botsPlayed(ArrayList<Card> botCards){    
        ArrayList<Card> botPlayedCards = new ArrayList<>();

        // ! MAYBE USELESS
        // ! Should be removed

        int[] summaryBotCards = new int[MAX_CARDS];

        // Summary the cards
        for(int i = 0; i < botCards.size(); i++){
            Card card = botCards.get(i);
            int index = symbolRank.indexOf(card.getSymbol());
            summaryBotCards[index] += 1;
        }
        // ! END

        int cardsNeeded = -1;

        // ! OPTIMIZE in the future
        if(currentState.equals("SINGLE")){
            cardsNeeded = 1;
        } else if(currentState.equals("PAIR")){
            cardsNeeded = 2;
        } else if(currentState.equals("TRIP")){
            cardsNeeded = 3;
        } else if(currentState.equals("QUAD")){
            cardsNeeded = 4;
        }
        // ! END

        // TODO: continue doing the logic
        // currentState cannot be NONE
        switch(currentState){
            case "ANY":
                break;
            case "SINGLE":  
            case "PAIR":
            case "TRIP":
            case "QUAD":
                for(int i = 0; i < botCards.size()-cardsNeeded+1; i += 1){
                    ArrayList<Card> multipleCards = new ArrayList<>();
                    for(int j = i; j < i + cardsNeeded; j++){
                        Card card = botCards.get(j);
                        multipleCards.add(card);
                    }
                    
                    System.out.println(multipleCards);

                    if(allSameSymbol(multipleCards) && 
                    (compareToPrevCards(multipleCards) > 0)){
                        botPlayedCards = multipleCards;
                        break;
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
                    // for(int i = 0; i < botCards.size()-num+1; i += 1){
                    //     ArrayList<Card> multipleCards = new ArrayList<>();
                    //     for(int j = i; j < i + num; j++){
                    //         Card card = botCards.get(j);
                    //         multipleCards.add(card);
                    //     }
                        
                    //     System.out.println(multipleCards);
    
                    //     if((!checkStraight(multipleCards).equals("NONE")) && 
                    //     (compareToPrevCards(multipleCards) > 0)){
                    //         botPlayedCards = multipleCards;
                    //         break;
                    //     }
                    // }
                    // ! END

                    for(int i = 0; i < summaryBotCards.length; i++){
                        int symbolCount = summaryBotCards[i];

                        // Maybe the start of the straight
                        if(symbolCount >= 1){

                            // Count to find if the straight is long enough
                            int count = 0;

                            for(int j = i; j < i+num; j++){
                                if(summaryBotCards[j] >= 1){
                                    count += 1;
                                }
                            }

                            // If there is a straight valid
                            // Don't need to check if count >= 3
                            // because it's checked previously
                            if(count == num){
                                // Get the straight in Card form

                                String symbolStraight = symbolRank.get(i);
                                int symbolIndex = 0;

                                // Get the inital value
                                for(int j = 0; j < botCards.size(); j++){
                                    Card card = botCards.get(j);
                                    if(card.getSymbol().equals(symbolStraight)){
                                        botPlayedCards.add(card);
                                        symbolIndex = j;
                                        break;
                                    }
                                }

                                // *Get others
                                // Loop each symbol
                                // Ex: J Q K
                                // Loop: J -> Q -> K
                                // TODO
                                for(int j = i+1; j < i+num; j++){
                                    symbolStraight = symbolRank.get(j);

                                    // Find the card that has symbolStraight 
                                    for(int k = symbolIndex+1; k < botCards.size(); k++){
                                        Card curCard = botCards.get(k);
                                        if(curCard.getSymbol().equals(symbolStraight)){
                                            symbolIndex = k;
                                            botPlayedCards.add(curCard);
                                            break;
                                        }
                                    }
                                }
                                
                                break;
                            }
                        }
                    }

                    System.out.println(Arrays.toString(summaryBotCards));

                } else if(state.equals("SMD")){
                    
                }

                break;
        }

        if(botPlayedCards.size() != 0){
            previousPlayedCard = botPlayedCards;
        }
        return botPlayedCards;
    }

    // * NOTE: check valid before use this function
    public int compareToPrevCards(ArrayList<Card> checkCards){
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
        
        return checkCards.get(checkCards.size()-1).compareTo(previousPlayedCard.get(previousPlayedCard.size()-1));
    }


    public void tempMain() throws InterruptedException{
        // ! Optimize purposes
        // ! DEBUG Performance
        // long start1 = System.nanoTime();
        // long end1 = System.nanoTime(); 
        // System.out.println("Elapsed Time in nano seconds: "+ (end1-start1));

        // Init
        reset();

        // Instant-win winner
        int winner = checkInstantWin();
        if(winner != -1){
            System.out.println("Player " + winner + " wins!");
            printCards(listPlayers.get(winner));
            return;
        }
        
        // A round
        // TODO: Add more rounds instead of only 1

        while(!(checkFinish())){
            Player currentPlayer = listPlayers.get(currentTurn);
            ArrayList<Card> currentPlayerCards = currentPlayer.getCardsAvailable();

            // When everyone passes the turn
            if(lastPlayerPlayed == currentTurn){
                currentState = "ANY"; 
            }

            // If it's our turn
            // Main player moves
            if(currentTurn == 0){
                // Play the card OR Pass the turn
                System.out.println(currentState);
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

                        cardsPlayed.add(currentPlayerCards.get(indexCard));

                    } while(true);
                    System.out.println();

                    // Play the cards
                    String stateCard = stateOfCards(cardsPlayed);

                    // ! BUG: 0002 
                    // ! Haven't check if the value of the cards is larger
                    // ! than the previous card

                    // If the cards are a valid move 
                    if(!stateCard.equals("NONE")){

                        // If the cards is the same state as current state
                        // ANY is when player can do any move
                        if((currentState.equals("ANY") ||
                        stateCard.equals(currentState)) && (compareToPrevCards(cardsPlayed) > 0)){
                            currentState = stateCard;
                            currentPlayer.playCard(cardsPlayed);
                            previousPlayedCard = cardsPlayed;
                            lastPlayerPlayed = currentTurn;

                            // Update the state
                            if(currentState.equals("ANY")){
                                currentState = stateCard;
                            }
                        }
                    }
                }
            
            // Bot moves
            // If it's empty means bots skips all
            } else {
                // Find suitable cards and play
                // If there is no possible move -> It skips the turn
                printCards(currentPlayer);
                currentPlayer.playCard(botsPlayed(currentPlayerCards));
                // *Uncomment line below when finish 
                //Thread.sleep(3000);
            }

            // If current player plays all the cards
            // ! BUG: 001
            if(checkWin(currentPlayer)){
                listPlayers.remove(currentTurn);
                if(currentTurn == 0){
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

                    System.out.println("Player " + currentTurn + " finished in " + place + postFix + " place");
                }
                currentTurn = Math.max(currentTurn-1, 0);
            }

            // ! DEBUG ID: 001
            // Detail: If you finished, then the currentTurn can still
            // be 0 and it will counted the second player as main player

            System.out.println(currentTurn);
            currentTurn = (currentTurn+1)%(listPlayers.size());
        }

        sc.close();
    }

    public void run() throws InterruptedException{
        // Main
        // tempMain();

        // ! DEBUGGING PURPOSES
        // *DEBUG BOTS PLAY
        reset();
        generateAllCard();

        previousPlayedCard.add(cards.get(0));
        //previousPlayedCard.add(cards.get(4));
        //previousPlayedCard.add(cards.get(8));
        currentState = stateOfCards(previousPlayedCard);

        System.out.println(previousPlayedCard);

        Player bot = listPlayers.get(0);
        ArrayList<Card> lCards = bot.getCardsAvailable();
        // int last = lCards.size()-1;

        // lCards.remove(last);
        // lCards.remove(last-1);

        // lCards.add(cards.get(0));
        // lCards.add(cards.get(2));

        // lCards.sort(((o1, o2) -> o1.compareTo(o2)));

        printCards(bot);
        printCards(new Player(botsPlayed(lCards)));

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