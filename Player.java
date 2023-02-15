import java.util.ArrayList;

public class Player {
    private ArrayList<Card> cardsAvailable;

    public Player(ArrayList<Card> cardsAvailable){
        this.cardsAvailable = cardsAvailable;
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
}
