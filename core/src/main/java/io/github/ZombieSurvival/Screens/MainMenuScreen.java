package io.github.ZombieSurvival.Screens;

import com.badlogic.gdx.Gdx;
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
    // Start Button Values
    private final float startWidth = 900f;
    private final float startHeight = 90f;
    private final float startX = (RotNRun.VIRTUAL_WIDTH / 2f) - (startWidth / 2f);
    private final float startY = 520f;
    private final float startMaxX = startX + startWidth;
    private final float startMaxY = startY + startHeight;
    private final float buttonLayoutOffsetY = -200f;
    // Difficulty Button Values
    private final float difficultyWidth = 290f;
    private final float difficultyHeight = 100f;
    private final float difficultyPadding = difficultyWidth + 15f;
    private final float difficultyY = 430f;
    private final float difficultyMaxY = difficultyY + difficultyHeight;
    private final float difficultyEasyX = (RotNRun.VIRTUAL_WIDTH / 2f) - (startWidth / 2f);
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
    private final Texture startButtonInactive;
    private final Texture startButtonActive;
    private final Texture easyButtonInactive;
    private final Texture easyButtonActive;
    private final Texture normalButtonInactive;
    private final Texture normalButtonActive;
    private final Texture hardButtonInactive;
    private final Texture hardButtonActive;
    private final Texture exitButtonInactive;
    private final Texture exitButtonActive;

    private int easyHighScore = 0;
    private int normalHighScore = 0;
    private int hardHighScore = 0;

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
        startButtonInactive = new Texture("Start_Button_Inactive.png");
        startButtonActive = new Texture("Start_Button_Active.png");
        easyButtonInactive = new Texture("Difficulty_Easy_Inactive.png");
        easyButtonActive = new Texture("Difficulty_Easy_Active.png");
        normalButtonInactive = new Texture("Difficulty_Normal_Inactive.png");
        normalButtonActive = new Texture("Difficulty_Normal_Active.png");
        hardButtonInactive = new Texture("Difficulty_Hard_Inactive.png");
        hardButtonActive = new Texture("Difficulty_Hard_Active.png");
        exitButtonInactive = new Texture("Exit_Button_Inactive.png");
        exitButtonActive = new Texture("Exit_Button_Active.png");


        try {
            Scanner scanner = new Scanner(RotNRun.SAVE_FILE_PATH);
            easyHighScore = scanner.nextInt();
            normalHighScore = scanner.nextInt();
            hardHighScore = scanner.nextInt();
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
    }

    /*
     * Draws the title texture to screen.
     */
    private void drawTitle(final SpriteBatch batch) {
        // Title Values
        final float titleWidth = 1200f;
        final float titleHeight = 600f;
        final float titleX = (RotNRun.VIRTUAL_WIDTH / 2f) - (titleWidth / 2f);
        final float titleY = startY + 200f;
        batch.draw(gameTitle, titleX, titleY, titleWidth, titleHeight);
    }

    /*
     * Draws the button textures to screen.
     */
    private void drawButtons(final SpriteBatch batch) {
        // Display button logic
        game.setMousePosition();
//        // Start Button
//        if (game.checkMouseOnButton(startX, startMaxX, startY, startMaxY)) {
//            batch.draw(startButtonActive, startX, startY, startWidth, startHeight);
//        } else {
//            batch.draw(startButtonInactive, startX, startY, startWidth, startHeight);
//        }
        // Another Button
        if (game.checkMouseOnButton(startX, startMaxX, startY
            + buttonLayoutOffsetY, startMaxY + buttonLayoutOffsetY)) {
            batch.draw(startButtonActive, startX, startY + buttonLayoutOffsetY,
                startWidth, startHeight);
        } else {
            batch.draw(startButtonInactive, startX, startY + buttonLayoutOffsetY,
                startWidth, startHeight);
        }
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
        BitmapFont font = game.getFont();
        final float padding = 20f;

        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, "HIGH SCORES");
        float highScoreTextXCenter = (startWidth / 2) - (glyphLayout.width / 2);
        float highScoreTextY = difficultyMaxY + (glyphLayout.height + padding) * 2;
        font.draw(batch, "HIGH SCORES",
            difficultyEasyX + highScoreTextXCenter, highScoreTextY);

        glyphLayout.setText(font, "000");
        float difficultyTextXCenter = (difficultyWidth / 2) - (glyphLayout.width / 2);
        float difficultyTextY = difficultyMaxY + glyphLayout.height + padding;

        // Easy
        String easyHS = String.format("%03d", easyHighScore);
        font.draw(batch, easyHS,
            difficultyEasyX + difficultyTextXCenter, difficultyTextY);

        // Normal
        String normalHS = String.format("%03d", normalHighScore);
        font.draw(batch, easyHS,
            difficultyNormalX + difficultyTextXCenter, difficultyTextY);

        // Hard
        String hardHS = String.format("%03d", hardHighScore);
        font.draw(batch, hardHS,
            difficultyHardX + difficultyTextXCenter, difficultyTextY);
    }

    /*
     * Draws the difficulty button textures to screen.
     */
    private void drawDifficultyButtons(final SpriteBatch batch) {
        game.setMousePosition();
        // EASY
        if (game.checkMouseOnButton(difficultyEasyX, difficultyEasyMaxX,
            difficultyY, difficultyMaxY)) {
            batch.draw(easyButtonActive, difficultyEasyX, difficultyY,
                difficultyWidth, difficultyHeight);
        } else {
            batch.draw(easyButtonInactive, difficultyEasyX, difficultyY,
                difficultyWidth, difficultyHeight);
        }
        // NORMAL
        if (game.checkMouseOnButton(difficultyNormalX, difficultyNormalMaxX,
            difficultyY, difficultyMaxY)) {
            batch.draw(normalButtonActive, difficultyNormalX, difficultyY,
                difficultyWidth, difficultyHeight);
        } else {
            batch.draw(normalButtonInactive, difficultyNormalX, difficultyY,
                difficultyWidth, difficultyHeight);
        }
        // HARD
        if (game.checkMouseOnButton(difficultyHardX, difficultyHardMaxX,
            difficultyY, difficultyMaxY)) {
            batch.draw(hardButtonActive, difficultyHardX, difficultyY,
                difficultyWidth, difficultyHeight);
        } else {
            batch.draw(hardButtonInactive, difficultyHardX, difficultyY,
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
            if (game.checkMouseOnButton(difficultyNormalX, difficultyNormalMaxX,
                difficultyY, difficultyMaxY)) {
                game.setScreen(new GameScreen(game, Difficulty.NORMAL));
                dispose();
            }
            // Game select HARD
            if (game.checkMouseOnButton(difficultyHardX, difficultyHardMaxX,
                difficultyY, difficultyMaxY)) {
                game.setScreen(new GameScreen(game, Difficulty.HARD));
                dispose();
            }
            // How to play button (NOT YET IMPLEMENTED)
            if (game.checkMouseOnButton(startX, startMaxX, startY
                + buttonLayoutOffsetY, startMaxY + buttonLayoutOffsetY)) {
                dispose();
            }
            // Quit game
            if (game.checkMouseOnButton(exitXY, exitMaxXY, exitXY, exitMaxXY)) {
                dispose();
                Gdx.app.exit();
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
