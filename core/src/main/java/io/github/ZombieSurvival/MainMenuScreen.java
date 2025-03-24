package io.github.ZombieSurvival;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * The main menu of the game.
 *
 * @author Kanon Nishiyama
 * @version 2025
 */
public class MainMenuScreen implements Screen {
    private final RotNRun game;

    private final Texture startButtonInactive;
    private final Texture startButtonActive;

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

        startButtonInactive = new Texture("Start_Button_Inactive.png");
        startButtonActive = new Texture("Start_Button_Active.png");
    }

    @Override
    public void show() {

    }

    private float getPositionOffsetX(final float frameWidth, final BitmapFont bitmapFont, final String value) {
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(bitmapFont, value);
        return (frameWidth / 2) - (glyphLayout.width / 2);
    }

    private float getPositionOffsetY(final float frameHeight, final BitmapFont bitmapFont, final String value) {
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(bitmapFont, value);
        return (frameHeight / 2) - (glyphLayout.height / 2);
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

        // Draws elements to screen
        SpriteBatch batch = game.getSpriteBatch();
        BitmapFont font = game.getFont();
        Viewport viewport = game.getViewport();
        float xCenter = getPositionOffsetX(game.getViewport().getWorldWidth(), font, "Welcome to Rot 'N' Run!!!");
        float yCenter = getPositionOffsetY(game.getViewport().getWorldHeight(), font, "Welcome to Rot 'N' Run!!!");
        float x2Center = getPositionOffsetX(game.getViewport().getWorldWidth(), font, "Tap anywhere to begin!");
        batch.begin();
            //draw text. Remember that x and y are in meters
            font.draw(batch, "Welcome to Rot 'N' Run!!!", xCenter, yCenter + 300f);
            font.draw(batch, "Tap anywhere to begin!", x2Center, yCenter + 220);

            game.setMousePosition();

            if ((RotNRun.MOUSE_POSITION.x >= viewport.getWorldWidth() / 2 - 450 && RotNRun.MOUSE_POSITION.x <= viewport.getWorldWidth() / 2 + 450)
            && (RotNRun.MOUSE_POSITION.y >= yCenter - 80 && RotNRun.MOUSE_POSITION.y <= yCenter - 80 + 90)) {
                batch.draw(startButtonActive, viewport.getWorldWidth() / 2 - 450, yCenter - 80, 900, 90);
            } else {
                batch.draw(startButtonInactive, viewport.getWorldWidth() / 2 - 450, yCenter - 80, 900, 90);
            }



        batch.end();

        // Game start on touch
        if (Gdx.input.isTouched()) {
            game.setMousePosition();
            if ((RotNRun.MOUSE_POSITION.x >= RotNRun.VIRTUAL_WIDTH / 2 - 450 && RotNRun.MOUSE_POSITION.x <= RotNRun.VIRTUAL_WIDTH / 2 + 450)
                && (RotNRun.MOUSE_POSITION.y >= yCenter - 80 && RotNRun.MOUSE_POSITION.y <= yCenter - 80 + 90)) {
                game.setScreen(new GameScreen(game));
                dispose();
            }
        }
    }

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
