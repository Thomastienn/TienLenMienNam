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
