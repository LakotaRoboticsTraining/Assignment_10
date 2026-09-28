import java.util.ArrayList;
import java.util.List;

public class Arcade {

    private String name;
    private String type;
    private int year;
    private List<Game> gameLibrary = new ArrayList<>();

    public Arcade(String name, String type, int year) {
        this.name = name;
        this.type = type;
        this.year = year;
    }

    public void addToLibrary(Game game) {
        if (!gameLibrary.contains(game)) {
            gameLibrary.add(game);
            System.out.println("Game added");
        }
    }

    public void listGameLibrary() {
        System.out.println("The arcade has " + gameLibrary.size() + " games.");
        for (Game game : gameLibrary) {
            System.out.println(game.toString());
        }
    }

    // Challenge 1: playGame(String name) â€” call overridden play(), or print doesn't have <name>
    public void playGame(String name) {
        // TODO
    }

    // Challenge 2: downloadGame(String name) â€” true for VideoGame/Downloadable, else false
    public boolean downloadGame(String name) {
        // TODO
        return false;
    }
}
