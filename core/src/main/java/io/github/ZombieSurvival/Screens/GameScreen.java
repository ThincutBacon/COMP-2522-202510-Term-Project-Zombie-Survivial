package io.github.ZombieSurvival.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import io.github.ZombieSurvival.Sprites.Player;
import io.github.ZombieSurvival.Sprites.Enemy;
import io.github.ZombieSurvival.Sprites.Item;
import io.github.ZombieSurvival.Sprites.Generate;
import io.github.ZombieSurvival.Sprites.Difficulty;
import io.github.ZombieSurvival.RotNRun;

/**
 * The game screen of the game.
 *
 * @author Kanon Nishiyama
 * @version 2025
 */
public class GameScreen implements Screen {
    // Current running game
    private final RotNRun game;
    // Sprite size
    private final float spriteWidth = 80f;
    private final float spriteHeight = 1.5f * spriteWidth;

    // Background
    private Texture backgroundTexture;
    private Texture platformTexture;
    // Player
    private Texture playerTexture;
    private Player playerSprite;
    private Rectangle playerHitbox;
    // Enemy
    private Texture standardZombieTexture;
    private Array<Enemy> enemySprites;
    private Rectangle enemyHitbox;
    // Item
    private Array<Item> itemSprites;
    private Rectangle itemHitbox;
    // Backend
    private float enemySpawnTimer;
    private float itemSpawnTimer;
    private long startGameInMilliSeconds;

    public GameScreen(final RotNRun game) {
        this.game = game;
        // Load textures
        // Background
        backgroundTexture = new Texture("City_Ruins.png");
        platformTexture = new Texture("Map_Platform.png");
        // Player
        playerTexture = new Texture("Player_Sprite_Large.png");
        playerHitbox = new Rectangle();
        playerSprite = Generate.createPlayer(playerTexture, Difficulty.NORMAL);
        playerSprite.setSize(spriteWidth, spriteHeight);
        playerSprite.setCenter((RotNRun.VIRTUAL_WIDTH / 2f), (RotNRun.VIRTUAL_HEIGHT / 2f));
        // Enemies
        standardZombieTexture = new Texture("Zombie_Sprite_Large.png");
        enemySprites = new Array<>();
        enemyHitbox = new Rectangle();
        // Items
        itemSprites = new Array<>();
        itemHitbox = new Rectangle();
        // Backend
        enemySpawnTimer = 0;
        itemSpawnTimer = 0;
        startGameInMilliSeconds = TimeUtils.millis();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(final float delta) {
        // Clears screen
        game.applyViewport();

        // For brevity
        SpriteBatch batch = game.getSpriteBatch();
        BitmapFont font = game.getFont();
        // Draw elements to screen
        draw(batch, font);
    }

    private void draw(final SpriteBatch batch, final BitmapFont font) {
        // Platform size
        final float platformWidth = 9f * 150f;
        final float platformHeight = 7f * 150f;
        // Draws elements to screen
        batch.begin();
            batch.draw(backgroundTexture, 0, 0, RotNRun.VIRTUAL_WIDTH, RotNRun.VIRTUAL_HEIGHT);
            batch.draw(platformTexture, RotNRun.VIRTUAL_WIDTH / 2f - platformWidth / 2f,
                0, platformWidth, platformHeight);
            playerSprite.draw(batch);
            for (Item item : itemSprites) {
                item.draw(batch);
            }
            for (Enemy enemy : enemySprites) {
                enemy.draw(batch);
            }
        batch.end();
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
