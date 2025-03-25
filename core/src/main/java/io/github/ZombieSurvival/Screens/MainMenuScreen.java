package io.github.ZombieSurvival.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.ZombieSurvival.RotNRun;

/**
 * The main menu of the game.
 *
 * @author Kanon Nishiyama
 * @version 2025
 */
public class MainMenuScreen implements Screen {
    private final RotNRun game;
    // Start Button Values
    private final float startWidth = 900f;
    private final float startHeight = 90f;
    private final float startX = (RotNRun.VIRTUAL_WIDTH / 2f) - (startWidth / 2f);
    private final float startY = (RotNRun.VIRTUAL_HEIGHT / 2f) - 90f;
    private final float startMaxX = startX + startWidth;
    private final float startMaxY = startY + startHeight;
    // Textures
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
        //Textures
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

        // For brevity
        SpriteBatch batch = game.getSpriteBatch();
        BitmapFont font = game.getFont();
        // Draw elements to screen
        draw(batch, font);
        // Check for input
        input();


    }

    private void draw(SpriteBatch batch, BitmapFont font) {
        // Numbers
        float xCenter = getPositionOffsetX(RotNRun.VIRTUAL_WIDTH, font,
            "Welcome to Rot 'N' Run!!!");
        float yCenter = getPositionOffsetY(RotNRun.VIRTUAL_HEIGHT, font,
            "Welcome to Rot 'N' Run!!!");
        float x2Center = getPositionOffsetX(RotNRun.VIRTUAL_WIDTH, font,
            "Click START to begin!");
        final float welcomeTextY = yCenter + 300f;
        final float interactTextY = yCenter + 220f;
        // Draws elements to screen
        batch.begin();
        font.draw(batch, "Welcome to Rot 'N' Run!!!", xCenter, welcomeTextY);
        font.draw(batch, "Click START to begin!", x2Center, interactTextY);

        // Display button logic
        game.setMousePosition();
        // Start Button
        if ((RotNRun.MOUSE_POSITION.x >= startX && RotNRun.MOUSE_POSITION.x <= startMaxX)
            && (RotNRun.MOUSE_POSITION.y >= startY && RotNRun.MOUSE_POSITION.y <= startMaxY)) {
            batch.draw(startButtonActive, startX, startY, startWidth, startHeight);
        } else {
            batch.draw(startButtonInactive, startX, startY, startWidth, startHeight);
        }
        batch.end();
    }

    private void input() {
        // Game start on click start
        if (Gdx.input.isTouched()) {
            game.setMousePosition();
            if ((RotNRun.MOUSE_POSITION.x >= startX && RotNRun.MOUSE_POSITION.x <= startMaxX)
                && (RotNRun.MOUSE_POSITION.y >= startY && RotNRun.MOUSE_POSITION.y <= startMaxY)) {
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
