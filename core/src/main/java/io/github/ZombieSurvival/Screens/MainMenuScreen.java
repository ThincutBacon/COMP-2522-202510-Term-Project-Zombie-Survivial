package io.github.ZombieSurvival.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.ZombieSurvival.RotNRun;

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
    // Exit Button Values
    private final float exitLength = 150f;
    private final float exitXY = 100f;
    private final float exitMaxXY = exitXY + exitLength;
    // Textures
    private final Texture gameTitle;
    private final Texture startButtonInactive;
    private final Texture startButtonActive;
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
     * Draws the title texture to screen
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
     * Draws the button textures to screen
     */
    private void drawButtons(final SpriteBatch batch) {
        // Display button logic
        game.setMousePosition();
        // Start Button
        if (game.checkMouseOnButton(startX, startMaxX, startY, startMaxY)) {
            batch.draw(startButtonActive, startX, startY, startWidth, startHeight);
        } else {
            batch.draw(startButtonInactive, startX, startY, startWidth, startHeight);
        }
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
     * Checks for user input
     */
    private void input() {
        // On click
        if (Gdx.input.isTouched()) {
            game.setMousePosition();
            // Game start
            if (game.checkMouseOnButton(startX, startMaxX, startY, startMaxY)) {
                game.setScreen(new GameScreen(game));
                dispose();
            }
            if (game.checkMouseOnButton(startX, startMaxX, startY
                + buttonLayoutOffsetY, startMaxY + buttonLayoutOffsetY)) {
                game.setScreen(new GameScreen(game));
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
