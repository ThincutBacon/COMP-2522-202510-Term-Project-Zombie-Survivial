package io.github.ZombieSurvival.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import io.github.ZombieSurvival.Sprites.Entity;
import io.github.ZombieSurvival.Sprites.EntityComparator;
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
    // Sprite
    private static final float SPRITE_WIDTH = 80f;
    private static final float SPRITE_HEIGHT = 1.5f * SPRITE_WIDTH;
    private static final float SPRITE_HITBOX_INSET = 10f;
    // Platform
    private static final float PLATFORM_WIDTH = 9f * 150f;
    private static final float PLATFORM_HEIGHT = 7f * 150f;
    private static final float PLATFORM_X = (RotNRun.VIRTUAL_WIDTH / 2f) - (PLATFORM_WIDTH / 2f);
    // Platform play area
    private static final float PLATFORM_AREA_PADDING = 20f;
    private static final float PLATFORM_AREA_X = PLATFORM_X + PLATFORM_AREA_PADDING;
    private static final float PLATFORM_AREA_MAX_X = PLATFORM_X + PLATFORM_WIDTH
                                                - SPRITE_WIDTH - PLATFORM_AREA_PADDING;
    private static final float PLATFORM_AREA_Y = 150f + PLATFORM_AREA_PADDING;
    private static final float PLATFORM_AREA_MAX_Y = PLATFORM_HEIGHT - SPRITE_HEIGHT
                                                + PLATFORM_AREA_PADDING;
    // Player movement speed
    private static final float PLAYER_SPEED = 300f;
    // GUI
    private static final float TOP_GUI_WINDOW_X_PADDING = 60;
    private static final float TOP_GUI_WINDOW_Y_PADDING = 40;
    private static final float TOP_GUI_Y_CENTER = RotNRun.VIRTUAL_HEIGHT - 50 - TOP_GUI_WINDOW_Y_PADDING;


    // Current running game
    private final RotNRun game;
    // Background
    private final Texture backgroundTexture;
    private final Texture platformTexture;
    // Sprites
    private final Array<Entity> allEntities;
    // Player
    private final Texture playerTexture;
    private final Texture playerDamagedTexture;
    private final Player playerSprite;
    private final Rectangle playerHitbox;
    // Enemy
    private final Texture standardZombieTexture;
    private final Array<Enemy> enemySprites;
    private final Rectangle enemyHitbox;
    // Item
    private final Array<Item> itemSprites;
    private final Rectangle itemHitbox;
    // HUD
    private final Texture healthFilledTexture;
    private final Texture healthEmptyTexture;
    private final Texture staminaContainerTexture;
    private final Texture staminaFillingTexture;
    // Backend
    private final long startGameInMilliSeconds;
    private float staminaDecreaseTimer;
    private float abilityChargeTimer;
    private boolean playerIsInvincible;
    private float invincibilityTimer;
    private float enemySpawnTimer;
    private float itemSpawnTimer;

    /**
     * Constructs a GameScreen object with the specified instance of game.
     *
     * @param game a RotNRun
     * @throws IllegalArgumentException if game is null
     */
    public GameScreen(final RotNRun game) {
        if (game == null) {
            throw new IllegalArgumentException("There is no game to the apply screen to.");
        }
        this.game = game;
        // Load textures
        // Background
        backgroundTexture = new Texture("City_Ruins.png");
        platformTexture = new Texture("Map_Platform.png");
        // Sprites
        allEntities = new Array<>();
        // Player
        playerTexture = new Texture("Player_Sprite_Large.png");
        playerDamagedTexture = new Texture("Player_Sprite_Large_Damaged.png");
        playerSprite = Generate.createPlayer(playerTexture, Difficulty.NORMAL);
        playerSprite.setSize(SPRITE_WIDTH, SPRITE_HEIGHT);
        playerSprite.setCenter((RotNRun.VIRTUAL_WIDTH / 2f), (RotNRun.VIRTUAL_HEIGHT / 2f));
        allEntities.add(playerSprite);
        playerHitbox = new Rectangle();
        playerHitbox.setWidth(SPRITE_WIDTH - (SPRITE_HITBOX_INSET * 2));
        playerHitbox.setHeight(SPRITE_HEIGHT - (SPRITE_HITBOX_INSET * 2));
        // Enemies
        standardZombieTexture = new Texture("Zombie_Sprite_Large.png");
        enemySprites = new Array<>();
        enemyHitbox = new Rectangle();
        enemyHitbox.setWidth(SPRITE_WIDTH - (SPRITE_HITBOX_INSET * 2));
        enemyHitbox.setHeight(SPRITE_HEIGHT - (SPRITE_HITBOX_INSET * 2));
        // Items
        itemSprites = new Array<>();
        itemHitbox = new Rectangle();
        // HUD
        healthFilledTexture = new Texture("Health_Filled.png");
        healthEmptyTexture = new Texture("Health_Empty.png");
        staminaContainerTexture  = new Texture("Stamina_Bar_Container.png");
        staminaFillingTexture  = new Texture("Stamina_Bar_Filling.png");
        // Backend
        staminaDecreaseTimer = 0;
        abilityChargeTimer = 0;
        enemySpawnTimer = 0;
        playerIsInvincible = false;
        invincibilityTimer = 0;
        itemSpawnTimer = 0;
        startGameInMilliSeconds = TimeUtils.millis();
    }

    @Override
    public void show() {

    }

    /**
     * Renders this GameScreen.
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
        // Increments and checks on timers
        checkTimers();
        // Draw elements to screen
        batch.begin();
            drawBackground(batch);
            drawSprites(batch);
            drawHUDStamina(batch);
            // drawHUDScore(batch, font);
            drawHUDHealth(batch);
        batch.end();
        // Check for inputs
        // Movement
        playerSprite.setX(MathUtils.clamp(playerSprite.getX(),
                                            PLATFORM_AREA_X, PLATFORM_AREA_MAX_X));
        playerSprite.setY(MathUtils.clamp(playerSprite.getY(),
                                            PLATFORM_AREA_Y, PLATFORM_AREA_MAX_Y));
        if (Gdx.input.isTouched()) {
            inputMovementTouch();
        }
        inputMovementKeys();
        // Run logic
        logicEnemyMovement();
        updateEntityHitboxCoordinates(playerHitbox, playerSprite);
        logicEnemyAttack();
        logicGameOver();
    }

    /*
     * Increments and checks on timers.
     */
    private void checkTimers() {
        float delta = Gdx.graphics.getDeltaTime();
        staminaDecreaseTimer += delta;
        abilityChargeTimer += delta;
        if (playerIsInvincible) {
            invincibilityTimer += delta;
        }
        enemySpawnTimer += delta;
        itemSpawnTimer += delta;

        final float staminaDecreaseTimerMax = 1f;
        if (staminaDecreaseTimer >= staminaDecreaseTimerMax) {
            playerSprite.modifyCurrentStamina(-1);
            staminaDecreaseTimer = 0;
        }
        final float abilityChargeTimerMax = 1f;
        if (abilityChargeTimer >= abilityChargeTimerMax) {
            playerSprite.increaseCurrentCharge();
            abilityChargeTimer = 0;
        }
        final float invincibilityTimerMax = 3f;
        if (invincibilityTimer >= invincibilityTimerMax) {
            playerIsInvincible = false;
            invincibilityTimer = 0;
        }
        final float enemySpawnTimerMax = 5.0f;
        if (enemySpawnTimer >= enemySpawnTimerMax) {
            createEnemy();
            enemySpawnTimer = 0;
        }
        final float itemSpawnTimerMax = 2.5f;
        if (itemSpawnTimer >= itemSpawnTimerMax) {
            createItem();
            itemSpawnTimer = 0;
        }
    }

    /*
     * Creates a new Enemy and stores it in enemySprites.
     */
    private void createEnemy() {
        Enemy enemySprite = Generate.createStandardZombie(standardZombieTexture);
        enemySprite.setSize(SPRITE_WIDTH, SPRITE_HEIGHT);
        // Randomize spawn location
        enemySprite.setX(MathUtils.random(PLATFORM_AREA_X, PLATFORM_AREA_MAX_X));
        enemySprite.setY(MathUtils.random(PLATFORM_AREA_Y, PLATFORM_AREA_MAX_Y));
        enemySprites.add(enemySprite); // Add it to the list
        allEntities.add(enemySprite);
    }

    /*
     * Creates a new Item and stores it in itemSprites.
     */
    private void createItem() {
        Item itemSprite = Generate.createNails(playerDamagedTexture);
        itemSprite.setSize(SPRITE_WIDTH, SPRITE_WIDTH);
        // Randomize spawn location
        itemSprite.setX(MathUtils.random(PLATFORM_AREA_X, PLATFORM_AREA_MAX_X));
        itemSprite.setY(MathUtils.random(PLATFORM_AREA_Y, PLATFORM_AREA_MAX_Y));
        itemSprites.add(itemSprite); // Add it to the list
        allEntities.add(itemSprite);
    }

    /*
     * Draws background textures to the screen.
     */
    private void drawBackground(final SpriteBatch batch) {
        batch.draw(backgroundTexture, 0, 0, RotNRun.VIRTUAL_WIDTH, RotNRun.VIRTUAL_HEIGHT);
        batch.draw(platformTexture, RotNRun.VIRTUAL_WIDTH / 2f - PLATFORM_WIDTH / 2f,
            0, PLATFORM_WIDTH, PLATFORM_HEIGHT);
    }

    /*
     * Draws sprites to the screen.
     */
    private void drawSprites(final SpriteBatch batch) {
        if (playerIsInvincible) {
            playerSprite.setTexture(playerDamagedTexture);
        } else {
            playerSprite.setTexture(playerTexture);
        }
        allEntities.sort(new EntityComparator());
        for (Entity entity: allEntities) {
            entity.draw(batch);
        }
    }

    /*
     * Draws player health to the screen.
     */
    private void drawHUDHealth(final SpriteBatch batch) {
        final float healthWidth = 11 * 10;
        final float healthHeight = 9 * 10;
        final float healthMargin = 20;
        final float healthY = TOP_GUI_Y_CENTER - (healthHeight / 2);
        float healthX = RotNRun.VIRTUAL_WIDTH - healthWidth - TOP_GUI_WINDOW_X_PADDING;
        for (int index = 0; index < playerSprite.getCurrentHP(); index++) {
            batch.draw(healthFilledTexture, healthX, healthY, healthWidth, healthHeight);
            healthX -= healthWidth + healthMargin;
        }
        for (int index = 0; index < (playerSprite.getMaxHP() - playerSprite.getCurrentHP()); index++) {
            batch.draw(healthEmptyTexture, healthX, healthY, healthWidth, healthHeight);
            healthX -= healthWidth + healthMargin;
        }
    }

    /*
     * Draws player stamina to the screen.
     */
    private void drawHUDStamina(final SpriteBatch batch) {
        final float staminaWidth = 9 * 90;
        final float staminaHeight = 9 * 9;
        final float staminaY = TOP_GUI_Y_CENTER - (staminaHeight / 2);
        final float staminaPercentage = (float) playerSprite.getCurrentStamina() / playerSprite.getMaxStamina();
        batch.draw(staminaFillingTexture, TOP_GUI_WINDOW_X_PADDING, staminaY,
            (staminaWidth *  staminaPercentage), staminaHeight);
        batch.draw(staminaContainerTexture, TOP_GUI_WINDOW_X_PADDING, staminaY,
            staminaWidth, staminaHeight);
    }

    /*
     * Draws player score to the screen.
     */
    private void drawHUDScore(final SpriteBatch batch, final BitmapFont font) {
        String scoreDisplay = String.format("%03d", playerSprite.getCurrentScore());
        font.draw(batch, scoreDisplay,
            TOP_GUI_WINDOW_X_PADDING, RotNRun.VIRTUAL_HEIGHT - TOP_GUI_WINDOW_Y_PADDING);
    }

    /*
     * Checks for movement inputs using touch.
     */
    private void inputMovementTouch() {
        float delta = Gdx.graphics.getDeltaTime();
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

    /*
     * Checks for movement inputs using the keyboard.
     */
    private void inputMovementKeys() {
        float delta = Gdx.graphics.getDeltaTime();
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

    /*
     * Update hit box position.
     */
    private void updateEntityHitboxCoordinates(final Rectangle entityHitbox, final Entity entity) {
        entityHitbox.setPosition(entity.getX() + SPRITE_HITBOX_INSET,
                                    entity.getY() + SPRITE_HITBOX_INSET);
    }

    /*
     * Runs enemy movement logic.
     */
    private void logicEnemyMovement() {
        float delta = Gdx.graphics.getDeltaTime();
        for (Enemy enemy : enemySprites) {
            float speed = enemy.getSpeed();
            if (enemy.getX() + SPRITE_HITBOX_INSET > playerSprite.getX()) {
                enemy.translateX(-speed * delta);
            }
            if (enemy.getX() - SPRITE_HITBOX_INSET < playerSprite.getX()) {
                enemy.translateX(speed * delta);
            }

            if (enemy.getY() + SPRITE_HITBOX_INSET > playerSprite.getY()) {
                enemy.translateY(-speed * delta);
            }
            if (enemy.getY() - SPRITE_HITBOX_INSET < playerSprite.getY()) {
                enemy.translateY(speed * delta);
            }
        }
    }

    /*
     * Run Enemy attack logic.
     */
    private void logicEnemyAttack() {
        for (Enemy enemy : enemySprites) {
            updateEntityHitboxCoordinates(enemyHitbox, enemy);
            if (enemyHitbox.overlaps(playerHitbox) && !playerIsInvincible) {
                enemy.attackPlayer(playerSprite);
                playerIsInvincible = true;
            }
        }
    }

    /*
     * Run game over logic.
     */
    private void logicGameOver() {
        if (playerSprite.getCurrentHP() <= 0) {
            game.setScreen(new MainMenuScreen(game));
            dispose();
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
