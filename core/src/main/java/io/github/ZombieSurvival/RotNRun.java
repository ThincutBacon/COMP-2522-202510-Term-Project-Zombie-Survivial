package io.github.ZombieSurvival;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
     * Path to save file.
     */
    public static final Path SAVE_FILE_PATH = Path.of(
        "core/src/main/java/io/github/ZombieSurvival/Save.txt");
    /**
     * Viewport of the game.
     */
    private static final FitViewport VIEWPORT = new FitViewport(
        VIRTUAL_WIDTH,
        VIRTUAL_HEIGHT
    );
    /**
     * Hold the mouses current coordinates.
     */
    private static final Vector2 MOUSE_POSITION = new Vector2();

    private SpriteBatch spriteBatch;
    private BitmapFont normalText;
    private BitmapFont bigText;
    private BitmapFont yellowText;

    /*
     * Creates a new save file if one does not already exist.
     */
    private void createSaveFile() {
        if (!Files.exists(SAVE_FILE_PATH)) {
            try {
                Files.createFile(SAVE_FILE_PATH);
                System.out.println("New save file created.");
                List<String> scores = List.of(
                    "0", // Easy High Score
                    "false", // Easy High Score Updated
                    "0", // Normal High Score
                    "false", // Normal High Score Updated
                    "0", // Hard High Score
                    "false", // Hard High Score Updated
                    "false", // Normal Difficulty Unlocked
                    "false" // Hard Difficulty Unlocked
                );
                Files.write(SAVE_FILE_PATH, scores);
            } catch (IOException error) {
                System.out.println("New save file failed to be created.");
            }
        }
    }
    /*
     * Sets up the font by the given configurations.
     */
    private void setFontConfig(final BitmapFont font, final float scale, final Color color) {
        font.setUseIntegerPositions(false);
        font.getData().setScale(
            VIEWPORT.getWorldHeight() / Gdx.graphics.getHeight() - scale);
        font.setColor(color);
    }

    /**
     * Initializes game variables. Technical stand in for the constructor.
     */
    @Override
    public void create() {
        // Create save file if it does not already exist
        createSaveFile();
        // Create new sprite batch
        spriteBatch = new SpriteBatch();
        // Create fonts
        // Custom font texture
        Texture fontTexture = new Texture("Custom_Font.png");
        fontTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        // Normal Text
        final float normalTextScale = 1.5f;
        normalText = new BitmapFont(
            Gdx.files.internal("assets/Custom_Font.fnt"),
            new TextureRegion(fontTexture));
        setFontConfig(normalText, normalTextScale, Color.WHITE);
        // Big Text
        final float bigTextScale = 0.5f;
        bigText = new BitmapFont(
            Gdx.files.internal("assets/Custom_Font.fnt"),
            new TextureRegion(fontTexture));
        setFontConfig(bigText, bigTextScale, Color.WHITE);
        // Yellow Text
        final float yellowTextScale = 2.5f;
        yellowText = new BitmapFont(
            Gdx.files.internal("assets/Custom_Font.fnt"),
            new TextureRegion(fontTexture));
        setFontConfig(yellowText, yellowTextScale, Color.YELLOW);
        // Start game on MainMenuScreen
        this.setScreen(new MainMenuScreen(this));
    }


    /**
     * Returns the spriteBatch for this game.
     *
     * @return spriteBatch as SpriteBatch
     */
    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }
    /**
     * Returns the normal text font used for this game.
     *
     * @return normalText as BitmapFont
     */
    public BitmapFont getNormalText() {
        return normalText;
    }
    /**
     * Returns the bit text font used for this game.
     *
     * @return bigText as BitmapFont
     */
    public BitmapFont getBigText() {
        return bigText;
    }
    /**
     * Returns the yellow special text font used for this game.
     *
     * @return yellowText as BitmapFont
     */
    public BitmapFont getYellowText() {
        return yellowText;
    }
    /**
     * Clears the viewport.
     */
    public void clearViewport() {
        final float red = 27f / 255f;
        final float blue = 14f / 255f;
        final float green = 25f / 255f;
        ScreenUtils.clear(red, blue, green, 1, true);

        VIEWPORT.apply();
        spriteBatch.setProjectionMatrix(VIEWPORT.getCamera().combined);
    }
    /**
     * Updates viewport by width and height.
     *
     * @param width an int
     * @param height an int
     */
    public void updateViewport(final int width, final int height) {
        VIEWPORT.update(width, height, true);
    }
    /**
     * Automatically sets and converts the mouses coordinates to the world units of the viewport.
     */
    public void setMousePosition() {
        // Get where the touch happened on screen
        MOUSE_POSITION.set(Gdx.input.getX(), Gdx.input.getY());
        // Convert the units to the world units of the viewport
        VIEWPORT.unproject(MOUSE_POSITION);
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
        return (MOUSE_POSITION.x >= minX
            && MOUSE_POSITION.x <= maxX)
            && (MOUSE_POSITION.y >= minY
            && MOUSE_POSITION.y <= maxY);
    }
    /**
     * Returns the X coordinate of the mouse.
     *
     * @return MOUSE_POSITION.x as float
     */
    public float getMouseX() {
        return MOUSE_POSITION.x;
    }
    /**
     * Returns the Y coordinate of the mouse.
     *
     * @return MOUSE_POSITION.x as float
     */
    public float getMouseY() {
        return MOUSE_POSITION.y;
    }

    /**
     * Render's the game.
     */
    public void render() {
        super.render();
    }

    /**
     * Removes resources.
     */
    public void dispose() {
        spriteBatch.dispose();
        normalText.dispose();
        bigText.dispose();
        yellowText.dispose();
    }

}
