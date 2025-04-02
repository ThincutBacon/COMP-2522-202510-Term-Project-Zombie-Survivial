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
    // Textures
    private static final Texture BOX_TEXTURE =
        new Texture("RIP_Box.png");
    private static final Texture BUTTON_INACTIVE_TEXTURE =
        new Texture("Menu_Button_Inactive.png");
    private static final Texture BUTTON_ACTIVE_TEXTURE =
        new Texture("Menu_Button_Active.png");
    // Current running game
    private final RotNRun game;

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
        game.applyViewport();
        // For brevity
        SpriteBatch batch = game.getSpriteBatch();
        // Draw elements to screen
        batch.begin();
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
        batch.end();
        // Check for input
        input();
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
                game.setScreen(new MainMenuScreen(game));
                dispose();
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
