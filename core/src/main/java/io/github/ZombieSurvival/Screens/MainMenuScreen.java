package io.github.ZombieSurvival.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.ZombieSurvival.RotNRun;
import io.github.ZombieSurvival.Sprites.Difficulty;

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
    private final float startY = 500f;
    private final float startMaxX = startX + startWidth;
    private final float startMaxY = startY + startHeight;
    private final float buttonLayoutOffsetY = -150f;
    // Difficulty Button Values
    private final float difficultyWidth = 290f;
    private final float difficultyHeight = 100f;
    private final float difficultyPadding = difficultyWidth + 15f;
    private final float difficultyX = (RotNRun.VIRTUAL_WIDTH / 2f) - (startWidth / 2f);
    private final float difficultyY = 500f;
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
        // Difficulty Buttons
        // EASY
        if (game.checkMouseOnButton(difficultyX, difficultyX + difficultyWidth,
            difficultyY, difficultyY + difficultyHeight)) {
            batch.draw(easyButtonActive, difficultyX, difficultyY,
                difficultyWidth, difficultyHeight);
        } else {
            batch.draw(easyButtonInactive, startX, startY,
                difficultyWidth, difficultyHeight);
        }
        // NORMAL
        if (game.checkMouseOnButton(difficultyX + difficultyPadding,
            difficultyX + difficultyWidth + difficultyPadding,
            difficultyY, difficultyY + difficultyHeight)) {
            batch.draw(normalButtonActive,
                difficultyX + difficultyPadding, difficultyY,
                difficultyWidth, difficultyHeight);
        } else {
            batch.draw(normalButtonInactive,
                startX + difficultyPadding, difficultyY,
                difficultyWidth, difficultyHeight);
        }
        // HARD
        if (game.checkMouseOnButton(difficultyX + difficultyPadding * 2,
            difficultyX + difficultyWidth + difficultyPadding * 2,
            difficultyY, difficultyY + difficultyHeight)) {
            batch.draw(hardButtonActive,
                difficultyX + difficultyPadding * 2, difficultyY,
                difficultyWidth, difficultyHeight);
        } else {
            batch.draw(hardButtonInactive,
                startX + difficultyPadding * 2, startY,
                difficultyWidth, difficultyHeight);
        }
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
     * Checks for user input.
     */
    private void input() {
        // On click
        if (Gdx.input.isTouched()) {
            game.setMousePosition();
            // Game select EASY
            if (game.checkMouseOnButton(difficultyX, difficultyX + difficultyWidth,
                difficultyY, difficultyY + difficultyHeight)) {
                game.setScreen(new GameScreen(game, Difficulty.EASY));
                dispose();
            }
            // Game select NORMAL
            if (game.checkMouseOnButton(difficultyX + difficultyPadding,
                difficultyX + difficultyWidth + difficultyPadding,
                difficultyY, difficultyY + difficultyHeight)) {
                game.setScreen(new GameScreen(game, Difficulty.NORMAL));
                dispose();
            }
            // Game select HARD
            if (game.checkMouseOnButton(difficultyX + difficultyPadding * 2,
                difficultyX + difficultyWidth + difficultyPadding * 2,
                difficultyY, difficultyY + difficultyHeight)) {
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
