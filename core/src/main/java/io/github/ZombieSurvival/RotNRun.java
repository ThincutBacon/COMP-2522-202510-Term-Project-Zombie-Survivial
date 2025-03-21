package io.github.ZombieSurvival;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Base game class.
 *
 * @author Kanon Nishiyama
 * @version 2025
 */
public class RotNRun extends Game {
    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private FitViewport viewport;

    /**
     * Initializes game variables.
     */
    @Override
    public void create() {
        final float defaultViewportWidth = 30;
        final float defaultViewportHeight = 22.5f;

        spriteBatch = new SpriteBatch();
        font = new BitmapFont(); // Using LibGDX default font
        viewport = new FitViewport(
            defaultViewportWidth,
            defaultViewportHeight
        );

        // font has 15pt, but we need to scale it to our viewport by ratio
        // of viewport height to screen height
        font.setUseIntegerPositions(false);
        font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());

        // this.setScreen(new MainMenuScreen(this));
    }

    /**
     * Render's the game.
     */
    public void render() {
        super.render(); // important!
    }

    /**
     * Removes resources.
     */
    public void dispose() {
        spriteBatch.dispose();
        font.dispose();
    }

}
