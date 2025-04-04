package io.github.ZombieSurvival.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.ZombieSurvival.RotNRun;

/**
 * The game over screen of the game.
 *
 * @author Kanon Nishiyama
 * @version 2025
 */
public class GameOverScreen implements Screen {
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
    private final Texture boxTexture = new Texture("RIP_Box.png");
    private final Texture buttonInactiveTexture = new Texture("Menu_Button_Inactive.png");
    private final Texture buttonActiveTexture = new Texture("Menu_Button_Active.png");

    /**
     * Constructs a GameOverScreen object with the specified instance of game.
     *
     * @param game a RotNRun
     * @throws IllegalArgumentException if game is null
     */
    public GameOverScreen(final RotNRun game) {
        if (game == null) {
            throw new IllegalArgumentException("There is no game to the apply screen to.");
        }
        this.game = game;
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
        game.clearViewport();
        // For brevity
        SpriteBatch batch = game.getSpriteBatch();
        // Draw elements to screen
        batch.begin();
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
        batch.end();
        // Check for input
        input();
    }

    /*
     * Checks for user input.
     */
    private void input() {
        if (Gdx.input.isTouched()) {
            game.setMousePosition();
            // Game select EASY
            if (game.checkMouseOnButton(buttonX, buttonMaxX,
                buttonY + onePixel, buttonMaxY)) {
                dispose();
                game.setScreen(new MainMenuScreen(game));
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
    }
}
