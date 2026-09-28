public class Pinball extends Game {

    public Pinball(String name, int year, String type) {
        super(name, year, type);
    }

    @Override
    public void play() {
        System.out.println("Playing the pinball game " + getName());
    }
}
