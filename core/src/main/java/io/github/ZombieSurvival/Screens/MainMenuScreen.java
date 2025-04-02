package io.github.ZombieSurvival.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import io.github.ZombieSurvival.RotNRun;
import io.github.ZombieSurvival.Sprites.Difficulty;

import java.io.IOException;
import java.util.Scanner;

/**
 * The main menu of the game.
 *
 * @author Kanon Nishiyama
 * @version 2025
 */
public class MainMenuScreen implements Screen {
    // Current running game
    private final RotNRun game;
    // Difficulty Button Values
    private final float panelWidth = 900f;
    private final float difficultyWidth = 290f;
    private final float difficultyHeight = 120f;
    private final float difficultyPadding = difficultyWidth + 15f;

    private final float difficultyY = 380f;
    private final float difficultyMaxY = difficultyY + difficultyHeight;

    private final float difficultyEasyX = (RotNRun.VIRTUAL_WIDTH / 2f) - (panelWidth / 2f);
    private final float difficultyEasyMaxX = difficultyEasyX + difficultyWidth;
    private final float difficultyNormalX = difficultyEasyX + difficultyPadding;
    private final float difficultyNormalMaxX = difficultyNormalX + difficultyWidth;
    private final float difficultyHardX = difficultyNormalX + difficultyPadding;
    private final float difficultyHardMaxX = difficultyHardX + difficultyWidth;
    // Exit Button Values
    private final float exitLength = 150f;
    private final float exitXY = 100f;
    private final float exitMaxXY = exitXY + exitLength;
    // Textures
    private final Texture gameTitle;
    private final Texture easyButtonInactive;
    private final Texture easyButtonActive;
    private final Texture normalButtonInactive;
    private final Texture normalButtonActive;
    private final Texture normalButtonLocked;
    private final Texture hardButtonInactive;
    private final Texture hardButtonActive;
    private final Texture hardButtonLocked;
    private final Texture exitButtonInactive;
    private final Texture exitButtonActive;
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
     * Constructs a MainMenuScreen object with the specified instance of game.
     *
     * @param game a RotNRun
     * @throws IllegalArgumentException if game is null
     */
    public MainMenuScreen(final RotNRun game) {
        if (game == null) {
            throw new IllegalArgumentException("There is no game to the apply screen to.");
        }
        this.game = game;
        //Textures
        gameTitle = new Texture("Game_Title.png");
        easyButtonInactive = new Texture("Difficulty_Easy_Inactive.png");
        easyButtonActive = new Texture("Difficulty_Easy_Active.png");
        normalButtonInactive = new Texture("Difficulty_Normal_Inactive.png");
        normalButtonActive = new Texture("Difficulty_Normal_Active.png");
        normalButtonLocked = new Texture("Difficulty_Normal_Locked.png");
        hardButtonInactive = new Texture("Difficulty_Hard_Inactive.png");
        hardButtonActive = new Texture("Difficulty_Hard_Active.png");
        hardButtonLocked = new Texture("Difficulty_Hard_Locked.png");
        exitButtonInactive = new Texture("Exit_Button_Inactive.png");
        exitButtonActive = new Texture("Exit_Button_Active.png");
        try {
            Scanner scanner = new Scanner(RotNRun.SAVE_FILE_PATH);
            easyHighScore = scanner.nextInt();
            easyNewHighScore = scanner.nextBoolean();
            normalHighScore = scanner.nextInt();
            normalNewHighScore = scanner.nextBoolean();
            hardHighScore = scanner.nextInt();
            hardNewHighScore = scanner.nextBoolean();
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
     * Renders this MainMenuScreen.
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
            drawTitle(batch);
            drawDifficultyHighScores(batch);
            drawDifficultyButtons(batch);
            drawButtons(batch);
        batch.end();
        // Check for input
        input();
        inputCheats();
    }

    /*
     * Draws the title texture to screen.
     */
    private void drawTitle(final SpriteBatch batch) {
        // Title Values
        final float titleWidth = 1200f;
        final float titleHeight = 600f;
        final float titleX = (RotNRun.VIRTUAL_WIDTH / 2f) - (titleWidth / 2f);
        final float titleY = 650f;
        batch.draw(gameTitle, titleX, titleY, titleWidth, titleHeight);
    }

    /*
     * Draws the button textures to screen.
     */
    private void drawButtons(final SpriteBatch batch) {
        // Display button logic
        game.setMousePosition();
        // Exit Button
        if (game.checkMouseOnButton(exitXY, exitMaxXY, exitXY, exitMaxXY)) {
            batch.draw(exitButtonActive, exitXY, exitXY, exitLength, exitLength);
        } else {
            batch.draw(exitButtonInactive, exitXY, exitXY, exitLength, exitLength);
        }
    }

    /*
     * Draws the difficulty high scores to screen.
     */
    private void drawDifficultyHighScores(final SpriteBatch batch) {
        BitmapFont normalText = game.getNormalText();
        final float padding = 20f;

        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(normalText, "HIGH SCORES");
        float highScoreTextXCenter = (panelWidth / 2) - (glyphLayout.width / 2);
        float highScoreTextY = difficultyMaxY + (glyphLayout.height + padding) * 2;
        normalText.draw(batch, "HIGH SCORES",
            difficultyEasyX + highScoreTextXCenter, highScoreTextY);

        glyphLayout.setText(normalText, "000");
        float difficultyTextXCenter = (difficultyWidth / 2) - (glyphLayout.width / 2);
        float difficultyTextY = difficultyMaxY + glyphLayout.height + padding;

        // Easy
        String easyHS = String.format("%03d", easyHighScore);
        normalText.draw(batch, easyHS,
            difficultyEasyX + difficultyTextXCenter, difficultyTextY);

        // Normal
        String normalHS = String.format("%03d", normalHighScore);
        normalText.draw(batch, normalHS,
            difficultyNormalX + difficultyTextXCenter, difficultyTextY);

        // Hard
        String hardHS = String.format("%03d", hardHighScore);
        normalText.draw(batch, hardHS,
            difficultyHardX + difficultyTextXCenter, difficultyTextY);
        // Draw "new" text
        if (easyNewHighScore || normalNewHighScore || hardNewHighScore) {
            drawDifficultyNew(batch, difficultyTextXCenter, difficultyTextY);
        }
    }

    /*
     * Draws the "new" text above the high scores.
     */
    private void drawDifficultyNew(final SpriteBatch batch,
                                   final float difficultyTextXCenter,
                                   final float difficultyTextY) {
        BitmapFont yellowText = game.getYellowText();
        final float offset = 25f;
        // If high score was updated
        if (easyNewHighScore) {
            yellowText.draw(batch, "NEW",
                difficultyEasyX + difficultyTextXCenter - offset, difficultyTextY + offset);
        }
        if (normalNewHighScore) {
            yellowText.draw(batch, "NEW",
                difficultyNormalX + difficultyTextXCenter - offset, difficultyTextY + offset);
        }
        if (hardNewHighScore) {
            yellowText.draw(batch, "NEW",
                difficultyHardX + difficultyTextXCenter - offset, difficultyTextY + offset);
        }
    }

    /*
     * Draws the difficulty selection buttons to screen.
     */
    private void drawDifficultyButtons(final SpriteBatch batch) {
        game.setMousePosition();
        // EASY
        drawDifficultyButton(batch, difficultyEasyX, difficultyEasyMaxX,
            easyButtonActive, easyButtonInactive);
        // NORMAL
        if (normalUnlocked) {
            drawDifficultyButton(batch, difficultyNormalX, difficultyNormalMaxX,
                normalButtonActive, normalButtonInactive);
        } else {
            batch.draw(normalButtonLocked, difficultyNormalX, difficultyY,
                difficultyWidth, difficultyHeight);
        }
        // HARD
        if (hardUnlocked) {
            drawDifficultyButton(batch, difficultyHardX, difficultyHardMaxX,
                hardButtonActive, hardButtonInactive);
        } else {
            batch.draw(hardButtonLocked, difficultyHardX, difficultyY,
                difficultyWidth, difficultyHeight);
        }
    }

    /*
     * Draws the button texture to screen.
     */
    private void drawDifficultyButton(final SpriteBatch batch,
                                      final float xPosition, final float maxXPosition,
                                      final Texture textureActive, final Texture textureInactive) {
        if (game.checkMouseOnButton(xPosition, maxXPosition,
            difficultyY, difficultyMaxY)) {
            batch.draw(textureActive, xPosition, difficultyY,
                difficultyWidth, difficultyHeight);
        } else {
            batch.draw(textureInactive, xPosition, difficultyY,
                difficultyWidth, difficultyHeight);
        }
    }

    /*
     * Checks for user input.
     */
    private void input() {
        // On click
        if (Gdx.input.isTouched()) {
            game.setMousePosition();
            // Game select EASY
            if (game.checkMouseOnButton(difficultyEasyX, difficultyEasyMaxX,
                difficultyY, difficultyMaxY)) {
                game.setScreen(new GameScreen(game, Difficulty.EASY));
                dispose();
            }
            // Game select NORMAL
            if (normalUnlocked) {
                if (game.checkMouseOnButton(difficultyNormalX, difficultyNormalMaxX,
                    difficultyY, difficultyMaxY)) {
                    game.setScreen(new GameScreen(game, Difficulty.NORMAL));
                    dispose();
                }
            }
            // Game select HARD
            if (hardUnlocked) {
                if (game.checkMouseOnButton(difficultyHardX, difficultyHardMaxX,
                    difficultyY, difficultyMaxY)) {
                    game.setScreen(new GameScreen(game, Difficulty.HARD));
                    dispose();
                }
            }
            // Quit game
            if (game.checkMouseOnButton(exitXY, exitMaxXY, exitXY, exitMaxXY)) {
                dispose();
                Gdx.app.exit();
            }
        }
    }

    /*
     * Checks for user inputs that unlock cheat functions.
     */
    private void inputCheats() {
        // Unlock all difficulty options
        if (Gdx.input.isKeyPressed(Input.Keys.U)) {
            normalUnlocked = true;
            hardUnlocked = true;
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
