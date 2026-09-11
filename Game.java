public class Game {

    private String name;
    private int year;
    private String type;

    public Game(String name, int year, String type) {
        this.name = name;
        this.year = year;
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public int getYear() {
        return this.year;
    }

    public String getType() {
        return this.type;
    }

    public void play() {
        System.out.println("Playing a generic game.");
    }

    @Override
    public String toString() {
        return "Game [name=" + name + ", year=" + year + ", type=" + type + "]";
    }
}
