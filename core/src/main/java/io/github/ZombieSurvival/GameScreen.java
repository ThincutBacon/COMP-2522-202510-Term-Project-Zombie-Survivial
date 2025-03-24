package io.github.ZombieSurvival;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.Color;

import java.awt.*;

/**
 * The game screen of the game.
 *
 * @author Kanon Nishiyama
 * @version 2025
 */
public class GameScreen implements Screen {
    // Current running game
    private final RotNRun game;
    // Background
    private Texture backgroundTexture;
    private Texture platformTexture;
    // Player
    private Player playerStats;
    private Texture playerTexture;
    private Sprite playerSprite;
    private Rectangle playerHitbox;
    // Enemy
    private Texture standardZombieTexture;
    private Array<Sprite> enemySprites;
    private Rectangle enemyHitbox;
    // HUD
    private Stage HUD;
    private Table layout;
    private Label scoreLabel;
    private Label hpLabel;
    private Label nameLabel;
    // Backend
    private Vector2 touchPosition;
    private float enemySpawnTimer;
    private float itemSpawnTimer;
    private long startGameInMilliSeconds;
    private long endGameInMilliSeconds;
    // Numbers
    private float worldWidth;
    private float worldHeight;
    private float worldXCenter;
    private float worldYCenter;

    private final float spriteWidth = 1f;
    private final float spriteHeight = 1.5f;


    public GameScreen(final RotNRun game) {
        this.game = game;
        worldWidth = game.getViewport().getWorldWidth();
        worldHeight = game.getViewport().getWorldHeight();
        worldXCenter = worldWidth / 2;
        worldYCenter = worldHeight / 2;

        playerStats = Generate.createPlayer(Difficulty.NORMAL);
        // Load images
        backgroundTexture = new Texture("City_Ruins.png");
        playerTexture = new Texture("Player_Sprite_Large.png");
        standardZombieTexture = new Texture("Zombie_Sprite_Large.png");

        playerSprite = new Sprite(playerTexture);
        playerSprite.setSize(spriteWidth, spriteHeight);

        playerSprite.setCenter(worldXCenter, worldYCenter);

        HUD = new Stage(game.getViewport(), game.getSpriteBatch());
        layout = new Table();
        layout.top();
        layout.setFillParent(true);

        scoreLabel = new Label("100", new Label.LabelStyle(game.getFont(), Color.WHITE));
        hpLabel = new Label("3", new Label.LabelStyle(game.getFont(), Color.WHITE));
        nameLabel = new Label("THIS IS A GAME", new Label.LabelStyle(game.getFont(), Color.WHITE));

        layout.add(scoreLabel).expandX().padTop(10);
        layout.add(nameLabel).expandX().padTop(10);
        layout.add(hpLabel).expandX().padTop(10);

        HUD.addActor(layout);
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

    @Override
    public void render(final float delta) {
        // Clears screen
        game.applyViewport();
        game.getSpriteBatch().setProjectionMatrix(HUD.getCamera().combined);

        // Draws elements to screen
        SpriteBatch batch = game.getSpriteBatch();
        BitmapFont font = game.getFont();
        float worldWidth = game.getViewport().getWorldWidth();
        float worldHeight = game.getViewport().getWorldHeight();
        batch.begin();
            //draw text. Remember that x and y are in meters
            batch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
            playerSprite.draw(batch);
        batch.end();


        HUD.draw();
        playerSprite.setCenter(worldXCenter, 1);
        playerSprite.setCenter(worldXCenter, 2);
    }

    @Override
    public void resize(final int width, final int height) {
        game.updateViewport(width, height);
        worldWidth = game.getViewport().getWorldWidth();
        worldHeight = game.getViewport().getWorldHeight();
        worldXCenter = worldWidth / 2;
        worldYCenter = worldHeight / 2;
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
