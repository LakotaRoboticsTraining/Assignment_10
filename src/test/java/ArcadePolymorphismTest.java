import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Challenge 2: VideoGame implements Downloadable")
    void videoGameImplementsDownloadable() {
        assertTrue(Downloadable.class.isAssignableFrom(VideoGame.class),
            "challenge2 failed - VideoGame should implement Downloadable.");
    }

    @Test
    @DisplayName("Challenge 1: playGame uses VideoGame.play")
    void playGameUsesOverriddenVideoGameBehavior() {
        Arcade arcade = arcadeWithSampleGames();
        String output = captureOutput(() -> arcade.playGame("Pokemon"));

        assertTrue(output.contains("Playing the video game Pokemon"),
            "challenge1 failed - playGame(\"Pokemon\") should print: Playing the video game Pokemon");
    }

    @Test
    @DisplayName("Challenge 1: playGame uses Pinball.play")
    void playGameUsesOverriddenPinballBehavior() {
        Arcade arcade = arcadeWithSampleGames();
        String output = captureOutput(() -> arcade.playGame("Spaceball"));

        assertTrue(output.contains("Playing the pinball game Spaceball"),
            "challenge1 failed - playGame(\"Spaceball\") should print: Playing the pinball game Spaceball");
    }

    @Test
    @DisplayName("Challenge 1: missing game message")
    void playGameReportsMissingGames() {
        Arcade arcade = arcadeWithSampleGames();
        String output = captureOutput(() -> arcade.playGame("Pac-Man"));

        assertTrue(output.contains("doesn't have Pac-Man"),
            "challenge1 failed - playGame(\"Pac-Man\") should say the arcade doesn't have Pac-Man.");
    }

    @Test
    @DisplayName("Challenge 2: downloadGame for VideoGame")
    void downloadGameReturnsTrueForVideoGames() {
        Arcade arcade = arcadeWithSampleGames();
        String output = captureOutput(() -> {
            boolean downloaded = arcade.downloadGame("Pokemon");
            assertTrue(downloaded,
                "challenge2 failed - downloadGame(\"Pokemon\") should return true.");
        });

        assertTrue(output.contains("Download the video game Pokemon"),
            "challenge2 failed - downloadGame should print: Download the video game Pokemon");
    }

    @Test
    @DisplayName("Challenge 2: downloadGame for Pinball")
    void downloadGameReturnsFalseForNonDownloadableGames() {
        Arcade arcade = arcadeWithSampleGames();
        boolean downloaded = arcade.downloadGame("Spaceball");

        assertFalse(downloaded,
            "challenge2 failed - downloadGame(\"Spaceball\") should return false (not Downloadable).");
    }

    @Test
    @DisplayName("Challenge 3: Main play + download flow")
    void mainDemonstratesPolymorphicPlayAndDownload() {
        String output = captureOutput(() -> Main.main(new String[] {}));

        assertTrue(output.contains("doesn't have Pac-Man"),
            "challenge3 failed - Main should try playGame(\"Pac-Man\") and report it is missing.");
        assertTrue(output.contains("Playing the video game Pokemon"),
            "challenge3 failed - Main should play Pokemon through the arcade.");
        assertTrue(output.contains("Playing the pinball game Spaceball"),
            "challenge3 failed - Main should play Spaceball through the arcade.");
        assertTrue(output.contains("Pokemon download was successful"),
            "challenge3 failed - Main should print that Pokemon download was successful.");
        assertTrue(output.contains("Spaceball download was unsuccessful"),
            "challenge3 failed - Main should print that Spaceball download was unsuccessful.");
    }
}
