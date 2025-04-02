package io.github.ZombieSurvival;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.ZombieSurvival.Screens.MainMenuScreen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Base game class.
 *
 * @author Kanon Nishiyama
 * @version 2025
 */
public class RotNRun extends Game {
    /**
     * Virtual width of the game.
     */
    public static final int VIRTUAL_WIDTH = 2000;
    /**
     * Virtual height of the game.
     */
    public static final int VIRTUAL_HEIGHT = 1500;
    /**
     * Hold the mouses current coordinates.
     */
    public static final Vector2 MOUSE_POSITION = new Vector2();
    /**
     * Path to save file.
     */
    public static final Path SAVE_FILE_PATH = Path.of(
        "core/src/main/java/io/github/ZombieSurvival/Save.txt");

    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private FitViewport viewport;

    /**
     * Returns the spriteBatch for this game.
     *
     * @return spriteBatch as SpriteBatch
     */
    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }
    /**
     * Returns the font used for this game.
     *
     * @return font as BitmapFont
     */
    public BitmapFont getFont() {
        return font;
    }
    /**
     * Applies viewport.
     */
    public void applyViewport() {
        final float red = 27f / 255f;
        final float blue = 14f / 255f;
        final float green = 25f / 255f;
        ScreenUtils.clear(red, blue, green, 1, true);

        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
    }
    /**
     * Updates viewport by width and height.
     *
     * @param width an int
     * @param height an int
     */
    public void updateViewport(final int width, final int height) {
        viewport.update(width, height, true);
    }
    /**
     * Automatically sets and converts the mouses coordinates to the world units of the viewport.
     */
    public void setMousePosition() {
        // Get where the touch happened on screen
        MOUSE_POSITION.set(Gdx.input.getX(), Gdx.input.getY());
        // Convert the units to the world units of the viewport
        viewport.unproject(MOUSE_POSITION);
    }
    /**
     * Checks to see if the mouse is within the set range of coordinates.
     *
     * @param minX a float
     * @param maxX a float
     * @param minY a float
     * @param maxY a float
     * @return true if the mouse is within the range, otherwise false
     */
    public boolean checkMouseOnButton(final float minX, final float maxX,
                                      final float minY, final float maxY) {
        setMousePosition();
        return (RotNRun.MOUSE_POSITION.x >= minX
            && RotNRun.MOUSE_POSITION.x <= maxX)
            && (RotNRun.MOUSE_POSITION.y >= minY
            && RotNRun.MOUSE_POSITION.y <= maxY);
    }

    /**
     * Initializes game variables.
     */
    @Override
    public void create() {
        spriteBatch = new SpriteBatch();

        Texture fontTexture = new Texture("Custom_Font.png");
        fontTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font = new BitmapFont(
            Gdx.files.internal("assets/Custom_Font.fnt"),
            new TextureRegion(fontTexture));
        viewport = new FitViewport(
            VIRTUAL_WIDTH,
            VIRTUAL_HEIGHT
        );

        final float fontScale = 1.5f;
        // font has 15pt, but we need to scale it to our viewport by ratio
        // of viewport height to screen height
        font.setUseIntegerPositions(false);
        font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight() - fontScale);

        if (!Files.exists(SAVE_FILE_PATH)) {
            try {
                Files.createFile(SAVE_FILE_PATH);
                System.out.println("New save file created.");
                List<String> scores = List.of(
                    "0",
                    "0",
                    "0"
                );
                Files.write(SAVE_FILE_PATH, scores);
            } catch (IOException error) {
                System.out.println("New save file failed to be created.");
            }
        }
        this.setScreen(new MainMenuScreen(this));
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
