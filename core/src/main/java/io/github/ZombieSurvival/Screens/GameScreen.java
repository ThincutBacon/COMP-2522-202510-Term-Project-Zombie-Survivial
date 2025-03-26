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
    // Constants
    // Sprite
    private static final float SPRITE_WIDTH = 80f;
    private static final float SPRITE_HEIGHT = 1.5f * SPRITE_WIDTH;
    private static final float SPRITE_HIT_BOX_INSET = 10f;
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
    private static final float TOP_GUI_Y_CENTER = RotNRun.VIRTUAL_HEIGHT - 50
                                                - TOP_GUI_WINDOW_Y_PADDING;
    // Textures
    // Background
    private static final Texture BACKGROUND_TEXTURE =
        new Texture("City_Ruins.png");
    private static final Texture PLATFORM_TEXTURE =
        new Texture("Map_Platform.png");
    // Player
    private static final Texture PLAYER_TEXTURE =
        new Texture("Player_Sprite_Large.png");
    private static final Texture PLAYER_DAMAGED_TEXTURE =
        new Texture("Player_Sprite_Large_Damaged.png");
    // Enemy
    private static final Texture STANDARD_ZOMBIE_TEXTURE =
        new Texture("Zombie_Sprite_Large.png");
    // HUD
    private static final Texture HEALTH_FILLED_TEXTURE =
        new Texture("Health_Filled.png");
    private static final Texture HEALTH_EMPTY_TEXTURE =
        new Texture("Health_Empty.png");
    private static final Texture STAMINA_CONTAINER_TEXTURE =
        new Texture("Stamina_Bar_Container.png");
    private static final Texture STAMINA_FILLING_TEXTURE =
        new Texture("Stamina_Bar_Filling.png");
    // Instance Variables
    // Current running game
    private final RotNRun game;
    // Sprites
    private final Array<Entity> allEntities;
    // Player
    private final Player playerSprite;
    private final Rectangle playerHitBox;
    // Enemy
    private final Array<Enemy> enemySprites;
    private final Rectangle enemyHitBox;
    // Item
    private final Array<Item> itemSprites;
    private final Rectangle itemHitBox;
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
        // Sprites
        allEntities = new Array<>();
        // Player
        playerSprite = Generate.createPlayer(PLAYER_TEXTURE, Difficulty.NORMAL);
        playerSprite.setSize(SPRITE_WIDTH, SPRITE_HEIGHT);
        playerSprite.setCenter((RotNRun.VIRTUAL_WIDTH / 2f), (RotNRun.VIRTUAL_HEIGHT / 2f));
        allEntities.add(playerSprite);
        playerHitBox = new Rectangle();
        playerHitBox.setWidth(SPRITE_WIDTH - (SPRITE_HIT_BOX_INSET * 2));
        playerHitBox.setHeight(SPRITE_HEIGHT - (SPRITE_HIT_BOX_INSET * 2));
        // Enemies
        enemySprites = new Array<>();
        enemyHitBox = new Rectangle();
        enemyHitBox.setWidth(SPRITE_WIDTH - (SPRITE_HIT_BOX_INSET * 2));
        enemyHitBox.setHeight(SPRITE_HEIGHT - (SPRITE_HIT_BOX_INSET * 2));
        // Items
        itemSprites = new Array<>();
        itemHitBox = new Rectangle();
        enemyHitBox.setWidth(SPRITE_WIDTH - (SPRITE_HIT_BOX_INSET * 2));
        enemyHitBox.setHeight(SPRITE_WIDTH - (SPRITE_HIT_BOX_INSET * 2));
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

        // Increments and checks on timers
        incrementTimers();
        checkTimers();
        // Draw elements to screen
        drawAll();
        // Check for inputs
        inputAll();
        // Run logic
        logicAll();
    }

    /*
     * Increments timers by delta.
     */
    private void incrementTimers() {
        float delta = Gdx.graphics.getDeltaTime();
        staminaDecreaseTimer += delta;
        abilityChargeTimer += delta;
        if (playerIsInvincible) {
            invincibilityTimer += delta;
        }
        enemySpawnTimer += delta;
        itemSpawnTimer += delta;
    }

    /*
     * Checks on timers.
     */
    private void checkTimers() {
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
        final float itemSpawnTimerMax = 3.5f;
        if (itemSpawnTimer >= itemSpawnTimerMax) {
            createItem();
            itemSpawnTimer = 0;
        }
    }

    /*
     * Creates a new Enemy and stores it in enemySprites.
     */
    private void createEnemy() {
        Enemy enemySprite = chooseRandomEnemy();
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
        Item itemSprite = chooseRandomItem();
        itemSprite.setSize(SPRITE_WIDTH, SPRITE_WIDTH);
        // Randomize spawn location
        itemSprite.setX(MathUtils.random(PLATFORM_AREA_X, PLATFORM_AREA_MAX_X));
        itemSprite.setY(MathUtils.random(PLATFORM_AREA_Y, PLATFORM_AREA_MAX_Y));
        itemSprites.add(itemSprite); // Add it to the list
        allEntities.add(itemSprite);
    }

    /*
     * Selects and returns a random enemy.
     */
    private Enemy chooseRandomEnemy() {
        final int chance = MathUtils.random(1, 50);
        final int slowZombie = 38;
        final int fastZombie = 32;
        if (chance > slowZombie) { // 24% chance for healing item
            return Generate.createBrokenZombie(STANDARD_ZOMBIE_TEXTURE);
        } else if (chance > fastZombie) { // 12% chance for stamina item
            return Generate.createDogZombie(STANDARD_ZOMBIE_TEXTURE);
        } else { // 64% chance for score item
            return Generate.createStandardZombie(STANDARD_ZOMBIE_TEXTURE);
        }
    }


    /*
     * Selects and returns a random item.
     */
    private Item chooseRandomItem() {
        final int chanceType = MathUtils.random(1, 100);
        final int chanceSize = MathUtils.random(1, 10);
        final int healthItem = 90;
        final int staminaItem = 60;
        if (chanceType > healthItem) { // 10% chance for healing item
            return chooseRandomHealthItem(chanceSize);
        } else if (chanceType > staminaItem) { // 30% chance for stamina item
            return chooseRandomStaminaItem(chanceSize);
        } else { // 60% chance for score item
            return chooseRandomScoreItem(chanceSize);
        }
    }

    /*
     * Selects and returns a random health item.
     */
    private Item chooseRandomHealthItem(final int chance) {
        final int majorHealth = 7;
        if (chance > majorHealth) { // 30% chance for major
            return Generate.createBandage(STAMINA_FILLING_TEXTURE);
        } else { // 70% chance for minor
            return Generate.createMedKit(STAMINA_FILLING_TEXTURE);
        }
    }

    /*
     * Selects and returns a random stamina item.
     */
    private Item chooseRandomStaminaItem(final int chance) {
        final int majorStamina = 8;
        final int mediumStamina = 4;
        if (chance > majorStamina) { // 20% chance for major
            return Generate.createWaterBottle(STAMINA_FILLING_TEXTURE);
        } else if (chance > mediumStamina) { // 40% chance for medium
            return Generate.createApple(STAMINA_FILLING_TEXTURE);
        } else { // 40% chance for minor
            return Generate.createSandwich(STAMINA_FILLING_TEXTURE);
        }
    }

    /*
     * Selects and returns a random score item.
     */
    private Item chooseRandomScoreItem(final int chance) {
        final int majorScore = 8;
        final int mediumScore = 5;
        if (chance > majorScore) { // 20% chance for major
            return Generate.createNails(STAMINA_FILLING_TEXTURE);
        } else if (chance > mediumScore) { // 30% chance for medium
            return Generate.createWoodenPlank(STAMINA_FILLING_TEXTURE);
        } else { // 50% chance for minor
            return Generate.createMetalSheet(STAMINA_FILLING_TEXTURE);
        }
    }

    /*
     * Draws all elements onto the screen.
     */
    private void drawAll() {
        // For brevity
        SpriteBatch batch = game.getSpriteBatch();
        BitmapFont font = game.getFont();
        // Draw elements to screen
        batch.begin();
        drawBackground(batch);
        drawSprites(batch);
        drawHUDStamina(batch);
        drawHUDScore(batch, font);
        drawHUDHealth(batch);
        batch.end();
    }

    /*
     * Draws background textures to the screen.
     */
    private void drawBackground(final SpriteBatch batch) {
        batch.draw(BACKGROUND_TEXTURE, 0, 0, RotNRun.VIRTUAL_WIDTH, RotNRun.VIRTUAL_HEIGHT);
        batch.draw(PLATFORM_TEXTURE, RotNRun.VIRTUAL_WIDTH / 2f - PLATFORM_WIDTH / 2f,
            0, PLATFORM_WIDTH, PLATFORM_HEIGHT);
    }

    /*
     * Draws sprites to the screen.
     */
    private void drawSprites(final SpriteBatch batch) {
        if (playerIsInvincible) {
            playerSprite.setTexture(PLAYER_DAMAGED_TEXTURE);
        } else {
            playerSprite.setTexture(PLAYER_TEXTURE);
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
        for (int index = 0;
             index < playerSprite.getCurrentHP(); index++) {
            batch.draw(HEALTH_FILLED_TEXTURE, healthX, healthY, healthWidth, healthHeight);
            healthX -= healthWidth + healthMargin;
        }
        for (int index = 0;
             index < (playerSprite.getMaxHP() - playerSprite.getCurrentHP()); index++) {
            batch.draw(HEALTH_EMPTY_TEXTURE, healthX, healthY, healthWidth, healthHeight);
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
        final float staminaPercentage =
            (float) playerSprite.getCurrentStamina() / playerSprite.getMaxStamina();
        batch.draw(STAMINA_FILLING_TEXTURE, TOP_GUI_WINDOW_X_PADDING, staminaY,
            (staminaWidth *  staminaPercentage), staminaHeight);
        batch.draw(STAMINA_CONTAINER_TEXTURE, TOP_GUI_WINDOW_X_PADDING, staminaY,
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
     * Checks for all types of input.
     */
    private void inputAll() {
        playerSprite.setX(MathUtils.clamp(playerSprite.getX(),
            PLATFORM_AREA_X, PLATFORM_AREA_MAX_X));
        playerSprite.setY(MathUtils.clamp(playerSprite.getY(),
            PLATFORM_AREA_Y, PLATFORM_AREA_MAX_Y));
        if (Gdx.input.isTouched()) {
            inputMovementTouch();
        }
        inputMovementKeys();
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
    private void updateEntityHitBoxCoordinates(final Rectangle entityHitBox, final Entity entity) {
        entityHitBox.setPosition(entity.getX() + SPRITE_HIT_BOX_INSET,
                                    entity.getY() + SPRITE_HIT_BOX_INSET);
    }

    /*
     * Runs all logic.
     */
    private void logicAll() {
        logicEnemyMovement();
        updateEntityHitBoxCoordinates(playerHitBox, playerSprite);
        logicEnemyAttack();
        logicItemPickup();
        logicGameOver();
        logicEndRun();
    }

    /*
     * Runs enemy movement logic.
     */
    private void logicEnemyMovement() {
        float delta = Gdx.graphics.getDeltaTime();
        for (Enemy enemy : enemySprites) {
            float speed = enemy.getSpeed();
            if (enemy.getX() + SPRITE_HIT_BOX_INSET > playerSprite.getX()) {
                enemy.translateX(-speed * delta);
            }
            if (enemy.getX() - SPRITE_HIT_BOX_INSET < playerSprite.getX()) {
                enemy.translateX(speed * delta);
            }

            if (enemy.getY() + SPRITE_HIT_BOX_INSET > playerSprite.getY()) {
                enemy.translateY(-speed * delta);
            }
            if (enemy.getY() - SPRITE_HIT_BOX_INSET < playerSprite.getY()) {
                enemy.translateY(speed * delta);
            }
        }
    }

    /*
     * Run Enemy attack logic.
     */
    private void logicEnemyAttack() {
        for (Enemy enemy : enemySprites) {
            updateEntityHitBoxCoordinates(enemyHitBox, enemy);
            if (enemyHitBox.overlaps(playerHitBox) && !playerIsInvincible) {
                enemy.attackPlayer(playerSprite);
                playerIsInvincible = true;
            }
        }
    }

    /*
     * Run Item pickup logic.
     */
    private void logicItemPickup() {
        for (Item item : itemSprites) {
            updateEntityHitBoxCoordinates(itemHitBox, item);
            if (itemHitBox.overlaps(playerHitBox)) {
                item.increasePlayerStat(playerSprite);
                itemSprites.removeValue(item, true);
                allEntities.removeValue(item, true);
            }
        }
    }

    /*
     * Run end run logic.
     */
    private void logicEndRun() {
        if (playerSprite.getCurrentStamina() <= 0) {
            long timeElapsed = TimeUtils.timeSinceMillis(startGameInMilliSeconds);
            game.setScreen(new MainMenuScreen(game));
            dispose();
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
