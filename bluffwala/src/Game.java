import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Game {
    private static final String[] RANKS = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
    private static final String[] SUITS = {"Hearts", "Diamonds", "Clubs", "Spades"};
    private final Deck deck = new Deck(RANKS, SUITS);
    private final List<Player> players = new ArrayList<>();
    private final List<String> pile = new ArrayList<>();
    private int currentRankIndex = 0;

    public void initialize() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of players (2-6): ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine();

        while (numPlayers < 2 || numPlayers > 6) {
            System.out.print("Invalid number of players. Enter again (2-6): ");
            numPlayers = scanner.nextInt();
            scanner.nextLine();
        }

        deck.shuffle();
        int cardsPerPlayer = deck.size() / numPlayers;
        for (int i = 0; i < numPlayers; i++) {
            List<String> hand = deck.deal(cardsPerPlayer);
            players.add(new Player("Player " + (i + 1), hand));
        }

        System.out.println("Game initialized. Cards have been dealt.");
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            for (Player player : players) {
                if (player.getHand().isEmpty()) {
                    System.out.println(player.getName() + " has won the game!");
                    return;
                }

                System.out.println("\n" + player.getName() + "'s turn.");
                System.out.println("Current rank to play: " + RANKS[currentRankIndex]);
                System.out.println("Your hand: " + player.getHand());

                System.out.print("Enter cards to play (comma-separated, or 'pass' to skip): ");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("pass")) {
                    System.out.println(player.getName() + " passed.");
                } else {
                    List<String> playedCards = List.of(input.split(","));

                    if (player.playCards(playedCards)) {
                        pile.addAll(playedCards);
                        System.out.println(player.getName() + " played " + playedCards.size() + " card(s).");
                    } else {
                        System.out.println("Invalid cards played. Try again.");
                        continue;
                    }

                    System.out.print("Does anyone call bluff? (yes/no): ");
                    String bluffCall = scanner.nextLine();

                    if (bluffCall.equalsIgnoreCase("yes")) {
                        boolean isBluff = checkBluff(playedCards, RANKS[currentRankIndex]);
                        if (isBluff) {
                            System.out.println(player.getName() + " was bluffing! They pick up the pile.");
                            player.pickUpPile(pile);
                        } else {
                            System.out.println("Bluff call was wrong! Challenger picks up the pile.");
                            Player challenger = getNextPlayer(player);
                            challenger.pickUpPile(pile);
                        }
                        pile.clear();
                    }
                }

                currentRankIndex = (currentRankIndex + 1) % RANKS.length;
            }
        }
    }

    private boolean checkBluff(List<String> playedCards, String currentRank) {
        for (String card : playedCards) {
            if (!card.startsWith(currentRank)) {
                return true;
            }
        }
        return false;
    }

    private Player getNextPlayer(Player currentPlayer) {
        int currentIndex = players.indexOf(currentPlayer);
        return players.get((currentIndex + 1) % players.size());
    }
}