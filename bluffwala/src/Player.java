import java.util.ArrayList;
import java.util.List;

class Player {
    private final String name;
    private final List<String> hand;

    public Player(String name, List<String> hand) {
        this.name = name;
        this.hand = new ArrayList<>(hand);
    }

    public String getName() {
        return name;
    }

    public List<String> getHand() {
        return hand;
    }

    public boolean playCards(List<String> cards) {
        if (hand.containsAll(cards)) {
            hand.removeAll(cards);
            return true;
        }
        return false;
    }

    public void pickUpPile(List<String> pile) {
        hand.addAll(pile);
    }
}