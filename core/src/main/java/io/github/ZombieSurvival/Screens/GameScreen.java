package io.github.ZombieSurvival.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    // Sprite size
    private static final float SPRITE_WIDTH = 80f;
    private static final float SPRITE_HEIGHT = 1.5f * SPRITE_WIDTH;
    // Player movement speed
    private static final float PLAYER_SPEED = 20f;

    // Current running game
    private final RotNRun game;
    // Background
    private final Texture backgroundTexture;
    private final Texture platformTexture;
    // Player
    private final Texture playerTexture;
    private final Player playerSprite;
    private final Rectangle playerHitbox;
    // Enemy
    private final Texture standardZombieTexture;
    private final Array<Enemy> enemySprites;
    private final Rectangle enemyHitbox;
    // Item
    private final Array<Item> itemSprites;
    private final Rectangle itemHitbox;
    // Backend
    private final long startGameInMilliSeconds;
    private float abilityChargeTimer;
    private float enemySpawnTimer;
    private float itemSpawnTimer;

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
        playerSprite.setSize(SPRITE_WIDTH, SPRITE_HEIGHT);
        playerSprite.setCenter((RotNRun.VIRTUAL_WIDTH / 2f), (RotNRun.VIRTUAL_HEIGHT / 2f));
        // Enemies
        standardZombieTexture = new Texture("Zombie_Sprite_Large.png");
        enemySprites = new Array<>();
        enemyHitbox = new Rectangle();
        // Items
        itemSprites = new Array<>();
        itemHitbox = new Rectangle();
        // Backend
        abilityChargeTimer = 0;
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
        // Check for inputs
        if (Gdx.input.isTouched()) {
            inputMovementTouch(delta);
        }
        inputMovementKeys(delta);
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

    private void inputMovementTouch(final float delta) {
        game.setMousePosition();
        if (playerSprite.getX() > RotNRun.MOUSE_POSITION.x) {
            playerSprite.translateX(-PLAYER_SPEED * delta);
        }
        if (playerSprite.getX() < RotNRun.MOUSE_POSITION.x) {
            playerSprite.translateX(PLAYER_SPEED * delta);
        }

        if (playerSprite.getY() > RotNRun.MOUSE_POSITION.y) {
            playerSprite.translateY(-PLAYER_SPEED * delta);
        }
        if (playerSprite.getY() < RotNRun.MOUSE_POSITION.y) {
            playerSprite.translateY(PLAYER_SPEED * delta);
        }
    }

    private void inputMovementKeys(final float delta) {
        game.setMousePosition();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)
            || Gdx.input.isKeyPressed(Input.Keys.D)
            || Gdx.input.isKeyPressed(Input.Keys.NUMPAD_6)) {
            playerSprite.translateX(PLAYER_SPEED * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)
            || Gdx.input.isKeyPressed(Input.Keys.A)
            || Gdx.input.isKeyPressed(Input.Keys.NUMPAD_4)) {
            playerSprite.translateX(-PLAYER_SPEED * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)
            || Gdx.input.isKeyPressed(Input.Keys.S)
            || Gdx.input.isKeyPressed(Input.Keys.NUMPAD_2)) {
            playerSprite.translateY(-PLAYER_SPEED * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)
            || Gdx.input.isKeyPressed(Input.Keys.W)
            || Gdx.input.isKeyPressed(Input.Keys.NUMPAD_8)) {
            playerSprite.translateY(PLAYER_SPEED * delta);
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
