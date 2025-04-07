package io.github.ZombieSurvival.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.ZombieSurvival.RotNRun;
import io.github.ZombieSurvival.Sprites.Difficulty;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

/**
 * The result screen of the game.
 *
 * @author Kanon Nishiyama
 * @version 2025
 */
public class ResultScreen implements Screen {
    // Current running game
    private final RotNRun game;
    // Values
    private final float onePixel = 25f;
    private final float boxWidth = 35 * onePixel;
    private final float boxHeight = 50 * onePixel;
    private final float boxX = RotNRun.VIRTUAL_WIDTH / 2f - boxWidth / 2f;
    private final float boxY = RotNRun.VIRTUAL_HEIGHT / 2f - boxHeight / 2f;

    private final float buttonWidth = 27 * onePixel;
    private final float buttonHeight = 10 * onePixel;
    private final float buttonX = boxX + 4 * onePixel;
    private final float buttonMaxX = buttonX + buttonWidth;
    private final float buttonY = boxY + 4 * onePixel;
    private final float buttonMaxY = buttonY + buttonHeight;
    // Textures
    private final Texture boxTexture = new Texture("Result_Box.png");
    private final Texture buttonInactiveTexture = new Texture("Menu_Button_Inactive.png");
    private final Texture buttonActiveTexture = new Texture("Menu_Button_Active.png");
    // Sounds
    private final Music bgm = Gdx.audio.newMusic(Gdx.files.internal("Surrender to Shadows.wav"));
    // Result display values
    private final Difficulty difficulty;
    private final int score;
    // Saved values
    private int easyHighScore = 0;
    private boolean easyNewHighScore = false;
    private int normalHighScore = 0;
    private boolean normalNewHighScore = false;
    private int hardHighScore = 0;
    private boolean hardNewHighScore = false;
    private boolean normalUnlocked = false;
    private boolean hardUnlocked = false;

    /**
     * Constructs a ResultScreen object with the specified instance of game.
     *
     * @param game a RotNRun
     * @param difficulty a Difficulty
     * @param score an int
     * @throws IllegalArgumentException if game is null
     */
    public ResultScreen(final RotNRun game, final Difficulty difficulty, final int score) {
        if (game == null) {
            throw new IllegalArgumentException("There is no game to the apply screen to.");
        }
        this.game = game;
        this.difficulty = difficulty;
        this.score = score;
        try {
            Scanner scanner = new Scanner(RotNRun.SAVE_FILE_PATH);
            easyHighScore = scanner.nextInt();
            scanner.nextBoolean();
            normalHighScore = scanner.nextInt();
            scanner.nextBoolean();
            hardHighScore = scanner.nextInt();
            scanner.nextBoolean();
            normalUnlocked = scanner.nextBoolean();
            hardUnlocked = scanner.nextBoolean();
            System.out.println("Easy Difficulty High Score: " + easyHighScore);
            System.out.println("Normal Difficulty High Score: " + normalHighScore);
            System.out.println("Hard Difficulty High Score: " + hardHighScore);

        } catch (IOException error) {
            System.out.println("Failed to read scores from save file.");
        }
    }

    /**
     * Runs whenever the screen in shown.
     */
    @Override
    public void show() {
        bgm.play();
    }

    /**
     * Renders this ResultScreen.
     *
     * @param delta a float
     */
    @Override
    public void render(final float delta) {
        // Clears screen
        game.clearViewport();
        // For brevity
        SpriteBatch batch = game.getSpriteBatch();
        // Draw elements to screen
        batch.begin();
            drawTextures(batch);
            drawTitleText(batch);
        batch.end();
        // Check for input
        input();
    }

    /*
     * Draws textures to screen.
     */
    private void drawTextures(final SpriteBatch batch) {
        // Draw box
        batch.draw(boxTexture, boxX, boxY, boxWidth, boxHeight);
        // Draw button
        if (game.checkMouseOnButton(buttonX, buttonMaxX,
            buttonY + onePixel, buttonMaxY)) {
            batch.draw(buttonActiveTexture, buttonX, buttonY,
                buttonWidth, buttonHeight);
        } else {
            batch.draw(buttonInactiveTexture, buttonX, buttonY,
                buttonWidth, buttonHeight);
        }
    }

    /*
     * Draws title text to screen.
     */
    private void drawTitleText(final SpriteBatch batch) {
        BitmapFont bigText = game.getBigText();

        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(bigText, "FINAL\nSCORE");
        final float titleCenterX = (boxWidth / 2) - (glyphLayout.width / 2);
        final float titleY = boxY + boxHeight - glyphLayout.height;
        bigText.draw(batch, "FINAL\nSCORE",
            boxX + titleCenterX, titleY);

        drawDifficultyText(batch, titleY);
    }

    /*
     * Draws difficulty text to screen.
     */
    private void drawDifficultyText(final SpriteBatch batch, final float titleY) {
        BitmapFont normalText = game.getNormalText();
        GlyphLayout glyphLayout = new GlyphLayout();
        String difficultyString = String.format("DIFFICULTY: %6s", difficulty);
        glyphLayout.setText(normalText, difficultyString);
        final float difficultyCenterX = (boxX + boxWidth / 2) - (glyphLayout.width / 2);
        final float difficultyY = titleY - (glyphLayout.height * 7f);
        normalText.draw(batch, difficultyString, difficultyCenterX, difficultyY);

        String scoreString = String.format("SCORE: %03d", score);
        glyphLayout.setText(normalText, scoreString);
        final float scoreCenterX = (boxX + boxWidth / 2) - (glyphLayout.width / 2);
        final float scoreY = difficultyY - (glyphLayout.height * 2f);
        normalText.draw(batch, scoreString, scoreCenterX, scoreY);

        if (score > getMatchingHighScore()) {
            drawNewHighScore(batch, scoreY);
        }
    }

    /*
     * Draws new high-score text to screen.
     */
    private void drawNewHighScore(final SpriteBatch batch, final float scoreY) {
        BitmapFont yellowText = game.getYellowText();
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(yellowText, "NEW HIGH-SCORE!!!");
        final float newCenterX = (boxX + boxWidth / 2) - (glyphLayout.width / 2);
        final float newY = scoreY - (glyphLayout.height * 3.5f);
        switch (difficulty) {
            case EASY:
                easyNewHighScore = true;
                break;
            case NORMAL:
                normalNewHighScore = true;
                break;
            case HARD:
                hardNewHighScore = true;
                break;
            default:
                break;
        }
        yellowText.draw(batch, "NEW HIGH-SCORE!!!", newCenterX, newY);
    }

    /*
     * Gets the corresponding current high score value.
     */
    private int getMatchingHighScore() {
        switch (difficulty) {
            case EASY:
                return easyHighScore;
            case NORMAL:
                return normalHighScore;
            case HARD:
                return hardHighScore;
            default:
                return -1;
        }
    }

    /*
     * Gets the corresponding current high score value.
     */
    private void input() {
        if (Gdx.input.isTouched()) {
            game.setMousePosition();
            // Game select EASY
            if (game.checkMouseOnButton(buttonX, buttonMaxX,
                buttonY + onePixel, buttonMaxY)) {
                writeToSave();
                dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        }
    }

    /*
     * Writes current values into the save file.
     */
    private void writeToSave() {
        setNewValues();
        try {
            List<String> scores = List.of(
                Integer.toString(easyHighScore),
                Boolean.toString(easyNewHighScore),
                Integer.toString(normalHighScore),
                Boolean.toString(normalNewHighScore),
                Integer.toString(hardHighScore),
                Boolean.toString(hardNewHighScore),
                Boolean.toString(normalUnlocked),
                Boolean.toString(hardUnlocked)
            );
            Files.write(RotNRun.SAVE_FILE_PATH, scores);
        } catch (IOException error) {
            System.out.println("Failed to write to save file.");
        }
    }

    /*
     * Sets any new values.
     */
    private void setNewValues() {
        if (score > getMatchingHighScore()) {
            switch (difficulty) {
                case EASY:
                    easyHighScore = score;
                    break;
                case NORMAL:
                    normalHighScore = score;
                    break;
                case HARD:
                    hardHighScore = score;
                    break;
                default:
                    break;
            }
        }

        // Unlock values
        final int difficultyUnlockValue = 100;
        if (!normalUnlocked) {
            if (easyHighScore >= difficultyUnlockValue) {
                normalUnlocked = true;
            }
        }
        if (!hardUnlocked) {
            if (normalHighScore >= difficultyUnlockValue) {
                hardUnlocked = true;
            }
        }
    }

    /**
     * Updates viewport by width and height when window is resized.
     *
     * @param width an int
     * @param height an int
     */
    @Override
    public void resize(final int width, final int height) {
        game.updateViewport(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    /**
     * Removes resources.
     */
    @Override
    public void dispose() {
        boxTexture.dispose();
        buttonInactiveTexture.dispose();
        buttonActiveTexture.dispose();
        bgm.dispose();
    }
}
