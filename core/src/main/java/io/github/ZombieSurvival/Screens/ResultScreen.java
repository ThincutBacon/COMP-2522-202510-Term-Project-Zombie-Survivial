package io.github.ZombieSurvival.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
    // Values
    private static final float ONE_PIXEL = 25f;
    private static final float BOX_WIDTH = 35 * ONE_PIXEL;
    private static final float BOX_HEIGHT = 50 * ONE_PIXEL;
    private static final float BOX_X = RotNRun.VIRTUAL_WIDTH / 2f - BOX_WIDTH / 2f;
    private static final float BOX_Y = RotNRun.VIRTUAL_HEIGHT / 2f - BOX_HEIGHT / 2f;

    private static final float BUTTON_WIDTH = 27 * ONE_PIXEL;
    private static final float BUTTON_HEIGHT = 10 * ONE_PIXEL;
    private static final float BUTTON_X = BOX_X + 4 * ONE_PIXEL;
    private static final float BUTTON_MAX_X = BUTTON_X + BUTTON_WIDTH;
    private static final float BUTTON_Y = BOX_Y + 4 * ONE_PIXEL;
    private static final float BUTTON_MAX_Y = BUTTON_Y + BUTTON_HEIGHT;
    // Unlock values
    private static final int DIFFICULTY_UNLOCK_VALUE = 100;
    // Textures
    private static final Texture BOX_TEXTURE =
        new Texture("Result_Box.png");
    private static final Texture BUTTON_INACTIVE_TEXTURE =
        new Texture("Menu_Button_Inactive.png");
    private static final Texture BUTTON_ACTIVE_TEXTURE =
        new Texture("Menu_Button_Active.png");
    // Current running game
    private final RotNRun game;
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

    @Override
    public void show() {

    }

    /**
     * Renders this ResultScreen.
     *
     * @param delta a float
     */
    @Override
    public void render(final float delta) {
        // Clears screen
        game.applyViewport();
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
        batch.draw(BOX_TEXTURE, BOX_X, BOX_Y, BOX_WIDTH, BOX_HEIGHT);
        // Draw button
        if (game.checkMouseOnButton(BUTTON_X, BUTTON_MAX_X,
            BUTTON_Y + ONE_PIXEL, BUTTON_MAX_Y)) {
            batch.draw(BUTTON_ACTIVE_TEXTURE, BUTTON_X, BUTTON_Y,
                BUTTON_WIDTH, BUTTON_HEIGHT);
        } else {
            batch.draw(BUTTON_INACTIVE_TEXTURE, BUTTON_X, BUTTON_Y,
                BUTTON_WIDTH, BUTTON_HEIGHT);
        }
    }

    /*
     * Draws title text to screen.
     */
    private void drawTitleText(final SpriteBatch batch) {
        BitmapFont bigText = game.getBigText();

        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(bigText, "FINAL\nSCORE");
        final float titleCenterX = (BOX_WIDTH / 2) - (glyphLayout.width / 2);
        final float titleY = BOX_Y + BOX_HEIGHT - glyphLayout.height;
        bigText.draw(batch, "FINAL\nSCORE",
            BOX_X + titleCenterX, titleY);

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
        final float difficultyCenterX = (BOX_X + BOX_WIDTH / 2) - (glyphLayout.width / 2);
        final float difficultyY = titleY - (glyphLayout.height * 7f);
        normalText.draw(batch, difficultyString, difficultyCenterX, difficultyY);

        String scoreString = String.format("SCORE: %03d", score);
        glyphLayout.setText(normalText, scoreString);
        final float scoreCenterX = (BOX_X + BOX_WIDTH / 2) - (glyphLayout.width / 2);
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
        final float newCenterX = (BOX_X + BOX_WIDTH / 2) - (glyphLayout.width / 2);
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
            if (game.checkMouseOnButton(BUTTON_X, BUTTON_MAX_X,
                BUTTON_Y + ONE_PIXEL, BUTTON_MAX_Y)) {
                writeToSave();
                game.setScreen(new MainMenuScreen(game));
                dispose();
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

        if (!normalUnlocked) {
            if (easyHighScore >= DIFFICULTY_UNLOCK_VALUE) {
                normalUnlocked = true;
            }
        }
        if (!hardUnlocked) {
            if (normalHighScore >= DIFFICULTY_UNLOCK_VALUE) {
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

    @Override
    public void dispose() {

    }
}
