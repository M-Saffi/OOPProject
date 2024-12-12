import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Deck {
    private final List<String> cards = new ArrayList<>();

    public Deck(String[] ranks, String[] suits) {
        for (String rank : ranks) {
            for (String suit : suits) {
                cards.add(rank + " of " + suit);
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public int size() {
        return cards.size();
    }

    public List<String> deal(int count) {
        List<String> hand = new ArrayList<>(cards.subList(0, count));
        cards.subList(0, count).clear();
        return hand;
    }
}