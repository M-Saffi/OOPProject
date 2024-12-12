public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the game of Bluff!");
        Game game = new Game();
        game.initialize();
        game.play();
    }
}