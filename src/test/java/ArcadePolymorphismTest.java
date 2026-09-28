import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

class ArcadePolymorphismTest {

    private Arcade arcadeWithSampleGames() {
        Arcade arcade = new Arcade("Arcade of Legions", "Variety", 1982);
        captureOutput(() -> {
            arcade.addToLibrary(new VideoGame("Pokemon", 1996, "RPG"));
            arcade.addToLibrary(new Pinball("Spaceball", 1986, "Pinball"));
        });
        return arcade;
    }

    private String captureOutput(Runnable action) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(outputStream));
            action.run();
            return outputStream.toString();
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void videoGameImplementsDownloadable() {
        assertTrue(Downloadable.class.isAssignableFrom(VideoGame.class),
            "VideoGame should implement the Downloadable interface.");
    }

    @Test
    void playGameUsesOverriddenVideoGameBehavior() {
        Arcade arcade = arcadeWithSampleGames();

        String output = captureOutput(() -> arcade.playGame("Pokemon"));

        assertTrue(output.contains("Playing the video game Pokemon"),
            "playGame() should call VideoGame's overridden play() method.");
    }

    @Test
    void playGameUsesOverriddenPinballBehavior() {
        Arcade arcade = arcadeWithSampleGames();

        String output = captureOutput(() -> arcade.playGame("Spaceball"));

        assertTrue(output.contains("Playing the pinball game Spaceball"),
            "playGame() should call Pinball's overridden play() method.");
    }

    @Test
    void playGameReportsMissingGames() {
        Arcade arcade = arcadeWithSampleGames();

        String output = captureOutput(() -> arcade.playGame("Pac-Man"));

        assertTrue(output.contains("doesn't have Pac-Man"),
            "playGame() should report when a game is not in the library.");
    }

    @Test
    void downloadGameReturnsTrueForVideoGames() {
        Arcade arcade = arcadeWithSampleGames();

        String output = captureOutput(() -> {
            boolean downloaded = arcade.downloadGame("Pokemon");
            assertTrue(downloaded,
                "downloadGame() should return true for a VideoGame.");
        });

        assertTrue(output.contains("Download the video game Pokemon"),
            "downloadGame() should call download() on VideoGame objects.");
    }

    @Test
    void downloadGameReturnsFalseForNonDownloadableGames() {
        Arcade arcade = arcadeWithSampleGames();

        boolean downloaded = arcade.downloadGame("Spaceball");

        assertFalse(downloaded,
            "downloadGame() should return false for games that are not VideoGame objects.");
    }

    @Test
    void mainDemonstratesPolymorphicPlayAndDownload() {
        String output = captureOutput(() -> Main.main(new String[]{}));

        assertTrue(output.contains("doesn't have Pac-Man"),
            "Main should try to play a game that is not in the library.");
        assertTrue(output.contains("Playing the video game Pokemon"),
            "Main should play the video game through the arcade.");
        assertTrue(output.contains("Playing the pinball game Spaceball"),
            "Main should play the pinball game through the arcade.");
        assertTrue(output.contains("Pokemon download was successful"),
            "Main should report a successful video game download.");
        assertTrue(output.contains("Spaceball download was unsuccessful"),
            "Main should report an unsuccessful pinball download.");
    }
}
