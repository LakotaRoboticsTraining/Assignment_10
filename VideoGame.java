public class VideoGame extends Game implements Downloadable {

    public VideoGame(String name, int year, String type) {
        super(name, year, type);
    }

    @Override
    public void play() {
        // Challenge 1: must contain "Playing the video game" and the name
    }

    @Override
    public void download() {
        // Challenge 2: must contain "Download the video game" and the name
    }
}
