package io.github.ZombieSurvival.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
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
    // Current running game
    private final RotNRun game;
    // Sprite
    private final float spriteWidth = 80f;
    private final float spriteHeight = 1.5f * spriteWidth;
    private final float spriteHitBoxInset = 10f;
    // Item Sprite
    private final float itemSpriteLength = 65f;
    private final float itemSpriteHitBoxInset = 0f;
    // Platform
    private final float platformWidth = 9f * 150f;
    private final float platformHeight = 7f * 150f;
    private final float platformX = (RotNRun.VIRTUAL_WIDTH / 2f) - (platformWidth / 2f);
    // Platform play area
    private final float platformAreaPadding = 20f;
    private final float platformAreaX = platformX + platformAreaPadding;
    private final float platformAreaMaxX = platformX + platformWidth
                                                - spriteWidth - platformAreaPadding;
    private final float platformAreaY = 150f + platformAreaPadding;
    private final float platformAreaMaxY = platformHeight - spriteHeight
                                                + platformAreaPadding;
    // Player movement speed
    private final float playerSpeed = 300f;
    // Player ability radius
    private final float abilityRadius = 150f;
    // GUI
    private final float topGuiWindowXPadding = 60;
    private final float topGuiWindowYPadding = 40;
    private final float topGuiYCenter = RotNRun.VIRTUAL_HEIGHT - 50
                                                - topGuiWindowYPadding;
    // Textures
    // Background
    private final Texture backgroundTexture = new Texture("City_Ruins.png");
    private final Texture platformTexture = new Texture("Map_Platform.png");
    // Player
    private final Texture playerTexture = new Texture("Player_Sprite_Large.png");
    private final Texture playerDamagedTexture = new Texture("Player_Sprite_Large_Damaged.png");
    private final Texture abilityTexture = new Texture("Ability_Circle.png");
    // Enemy
    private final Texture standardZombieTexture = new Texture("Zombie_Sprite_Large.png");
    // Items
    private final Texture itemHealthTexture = new Texture("Item_Health.png");
    private final Texture itemStaminaTexture = new Texture("Item_Stamina.png");
    private final Texture itemScoreTexture = new Texture("Item_Score.png");
    // HUD
    private final Texture healthFilledTexture = new Texture("Health_Filled.png");
    private final Texture healthEmptyTexture = new Texture("Health_Empty.png");
    private final Texture staminaContainerTexture = new Texture("Stamina_Bar_Container.png");
    private final Texture staminaFillingTexture = new Texture("Stamina_Bar_Filling.png");
    // Sprites
    private final Array<Entity> allEntities;
    // Player
    private final Difficulty gameDifficulty;
    private final Player playerSprite;
    private final Rectangle playerHitBox;
    private final Circle playerAbility;
    // Enemy
    private final Array<Enemy> enemySprites;
    private final Rectangle enemyHitBox;
    // Item
    private final Array<Item> itemSprites;
    private final Rectangle itemHitBox;
    // Backend
    private float staminaDecreaseTimer;
    private float abilityChargeTimer;
    private boolean abilityActivated;
    private float abilityCooldownTimer;
    private boolean playerIsInvincible;
    private float invincibilityTimer;
    private float enemySpawnTimer;
    private float itemSpawnTimer;

    /**
     * Constructs a GameScreen object with the specified instance of game.
     *
     * @param game a RotNRun
     * @param difficulty a Difficulty
     * @throws IllegalArgumentException if game is null
     */
    public GameScreen(final RotNRun game, final Difficulty difficulty) {
        if (game == null) {
            throw new IllegalArgumentException("There is no game to the apply screen to.");
        }
        this.game = game;
        // Sprites
        allEntities = new Array<>();
        // Player
        gameDifficulty = difficulty;
        playerSprite = Generate.createPlayer(playerTexture, gameDifficulty);
        playerSprite.setSize(spriteWidth, spriteHeight);
        playerSprite.setCenter((RotNRun.VIRTUAL_WIDTH / 2f), (RotNRun.VIRTUAL_HEIGHT / 2f));
        allEntities.add(playerSprite);
        playerHitBox = new Rectangle();
        playerHitBox.setWidth(spriteWidth - (spriteHitBoxInset * 2));
        playerHitBox.setHeight(spriteHeight - (spriteHitBoxInset * 2));
        playerAbility = new Circle();
        playerAbility.setRadius(abilityRadius);
        // Enemies
        enemySprites = new Array<>();
        enemyHitBox = new Rectangle();
        enemyHitBox.setWidth(spriteWidth - (spriteHitBoxInset * 2));
        enemyHitBox.setHeight(spriteHeight - (spriteHitBoxInset * 2));
        // Items
        itemSprites = new Array<>();
        itemHitBox = new Rectangle();
        itemHitBox.setWidth(itemSpriteLength - (itemSpriteHitBoxInset * 2));
        itemHitBox.setHeight(itemSpriteLength - (itemSpriteHitBoxInset * 2));
        // Backend
        staminaDecreaseTimer = 0;
        abilityChargeTimer = 0;
        abilityActivated = false;
        abilityCooldownTimer = 0;
        playerIsInvincible = false;
        invincibilityTimer = 0;
        enemySpawnTimer = 0;
        itemSpawnTimer = 0;
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
        game.clearViewport();

        // Increments and checks on timers
        incrementTimers();
        checkPlayerTimers();
        checkSpawnTimers();
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
        if (!abilityActivated) {
            abilityChargeTimer += delta;
        }
        if (abilityActivated) {
            abilityCooldownTimer += delta;
        }
        if (playerIsInvincible) {
            invincibilityTimer += delta;
        }
        enemySpawnTimer += delta;
        itemSpawnTimer += delta;
    }

    /*
     * Checks on player related timers.
     */
    private void checkPlayerTimers() {
        final float staminaDecreaseTimerMax = 1f;
        if (staminaDecreaseTimer >= staminaDecreaseTimerMax) {
            playerSprite.modifyCurrentStamina(-1);
            staminaDecreaseTimer = 0;
        }
        final float abilityChargeTimerMax = 0.5f;
        if (abilityChargeTimer >= abilityChargeTimerMax) {
            playerSprite.increaseCurrentCharge();
            abilityChargeTimer = 0;
        }
        final float abilityCooldownTimerMax = 1.5f;
        if (abilityCooldownTimer >= abilityCooldownTimerMax) {
            abilityActivated = false;
            abilityCooldownTimer = 0;
        }
        final float invincibilityTimerMax = 3f;
        if (invincibilityTimer >= invincibilityTimerMax) {
            playerIsInvincible = false;
            invincibilityTimer = 0;
        }
    }

    /*
     * Checks on entity spawn timers.
     */
    private void checkSpawnTimers() {
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
        enemySprite.setSize(spriteWidth, spriteHeight);
        // Randomize spawn location
        enemySprite.setX(MathUtils.random(platformAreaX, platformAreaMaxX));
        enemySprite.setY(MathUtils.random(platformAreaY, platformAreaMaxY));
        enemySprites.add(enemySprite); // Add it to the list
        allEntities.add(enemySprite);
    }

    /*
     * Creates a new Item and stores it in itemSprites.
     */
    private void createItem() {
        Item itemSprite = chooseRandomItem();
        itemSprite.setSize(itemSpriteLength, itemSpriteLength);
        // Randomize spawn location
        itemSprite.setX(MathUtils.random(platformAreaX, platformAreaMaxX));
        itemSprite.setY(MathUtils.random(platformAreaY, platformAreaMaxY));
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
            return Generate.createBrokenZombie(standardZombieTexture);
        } else if (chance > fastZombie) { // 12% chance for stamina item
            return Generate.createDogZombie(standardZombieTexture);
        } else { // 64% chance for score item
            return Generate.createStandardZombie(standardZombieTexture);
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
            return Generate.createBandage(itemHealthTexture);
        } else { // 70% chance for minor
            return Generate.createMedKit(itemHealthTexture);
        }
    }

    /*
     * Selects and returns a random stamina item.
     */
    private Item chooseRandomStaminaItem(final int chance) {
        final int majorStamina = 8;
        final int mediumStamina = 4;
        if (chance > majorStamina) { // 20% chance for major
            return Generate.createWaterBottle(itemStaminaTexture);
        } else if (chance > mediumStamina) { // 40% chance for medium
            return Generate.createApple(itemStaminaTexture);
        } else { // 40% chance for minor
            return Generate.createSandwich(itemStaminaTexture);
        }
    }

    /*
     * Selects and returns a random score item.
     */
    private Item chooseRandomScoreItem(final int chance) {
        final int majorScore = 8;
        final int mediumScore = 5;
        if (chance > majorScore) { // 20% chance for major
            return Generate.createNails(itemScoreTexture);
        } else if (chance > mediumScore) { // 30% chance for medium
            return Generate.createWoodenPlank(itemScoreTexture);
        } else { // 50% chance for minor
            return Generate.createMetalSheet(itemScoreTexture);
        }
    }



    /*
     * Draws all elements onto the screen.
     */
    private void drawAll() {
        // For brevity
        SpriteBatch batch = game.getSpriteBatch();
        BitmapFont normalText = game.getNormalText();
        // Draw elements to screen
        batch.begin();
            drawBackground(batch);
            drawSprites(batch);
            drawHUDStamina(batch);
            drawHUDScore(batch, normalText);
            drawHUDHealth(batch);
            drawHUDAbility(batch);
        batch.end();
    }

    /*
     * Draws background textures to the screen.
     */
    private void drawBackground(final SpriteBatch batch) {
        batch.draw(backgroundTexture, 0, 0, RotNRun.VIRTUAL_WIDTH, RotNRun.VIRTUAL_HEIGHT);
        batch.draw(platformTexture, RotNRun.VIRTUAL_WIDTH / 2f - platformWidth / 2f,
            0, platformWidth, platformHeight);
    }

    /*
     * Draws sprites to the screen.
     */
    private void drawSprites(final SpriteBatch batch) {
        if (abilityActivated) {
            batch.draw(abilityTexture,
                playerSprite.getX() + (spriteWidth / 2) - abilityRadius,
                playerSprite.getY() + (spriteHeight / 2) - abilityRadius,
                abilityRadius * 2, abilityRadius * 2);
        }
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
        final float healthY = topGuiYCenter - (healthHeight / 2);
        float healthX = RotNRun.VIRTUAL_WIDTH - healthWidth - topGuiWindowXPadding;
        for (int index = 0;
             index < playerSprite.getCurrentHP(); index++) {
            batch.draw(healthFilledTexture, healthX, healthY, healthWidth, healthHeight);
            healthX -= healthWidth + healthMargin;
        }
        for (int index = 0;
             index < (playerSprite.getMaxHP() - playerSprite.getCurrentHP()); index++) {
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
        final float staminaY = topGuiYCenter - (staminaHeight / 2);
        final float staminaPercentage =
            (float) playerSprite.getCurrentStamina() / playerSprite.getMaxStamina();
        batch.draw(staminaFillingTexture, topGuiWindowXPadding, staminaY,
            (staminaWidth *  staminaPercentage), staminaHeight);
        batch.draw(staminaContainerTexture, topGuiWindowXPadding, staminaY,
            staminaWidth, staminaHeight);
    }

    /*
     * Draws player score to the screen.
     */
    private void drawHUDScore(final SpriteBatch batch, final BitmapFont font) {
        String scoreDisplay = String.format("%03d", playerSprite.getCurrentScore());
        font.draw(batch, scoreDisplay,
            topGuiWindowXPadding,
            RotNRun.VIRTUAL_HEIGHT - topGuiWindowYPadding
                - staminaContainerTexture.getHeight() * 2);
    }

    /*
     * Draws player ability charge to the screen.
     */
    private void drawHUDAbility(final SpriteBatch batch) {
        final float staminaWidth = 9 * 120;
        final float staminaHeight = 9 * 9;
        final float staminaY = topGuiWindowYPadding;
        final float staminaX =  RotNRun.VIRTUAL_WIDTH / 2f - staminaWidth / 2f;
        final float staminaPercentage =
            (float) playerSprite.getCurrentCharge() / playerSprite.getMaxCharge();
        batch.draw(staminaFillingTexture, staminaX, staminaY,
            (staminaWidth *  staminaPercentage), staminaHeight);
        batch.draw(staminaContainerTexture, staminaX, staminaY,
            staminaWidth, staminaHeight);
    }



    /*
     * Checks for all types of input.
     */
    private void inputAll() {
        playerSprite.setX(MathUtils.clamp(playerSprite.getX(),
            platformAreaX, platformAreaMaxX));
        playerSprite.setY(MathUtils.clamp(playerSprite.getY(),
            platformAreaY, platformAreaMaxY));
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
        if (playerSprite.getX() > game.getMouseX()) {
            playerSprite.translateX(-playerSpeed * delta);
            if (playerSprite.isFlipX()) {
                playerSprite.flip(true, false);
            }
        }
        if (playerSprite.getX() < game.getMouseX()) {
            playerSprite.translateX(playerSpeed * delta);
            if (!playerSprite.isFlipX()) {
                playerSprite.flip(true, false);
            }
        }

        if (playerSprite.getY() > game.getMouseY()) {
            playerSprite.translateY(-playerSpeed * delta);
        }
        if (playerSprite.getY() < game.getMouseY()) {
            playerSprite.translateY(playerSpeed * delta);
        }
    }

    /*
     * Checks for movement inputs using the keyboard.
     */
    private void inputMovementKeys() {
        float delta = Gdx.graphics.getDeltaTime();
        game.setMousePosition();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)
            || Gdx.input.isKeyPressed(Input.Keys.D)) {
            playerSprite.translateX(playerSpeed * delta);
            if (!playerSprite.isFlipX()) {
                playerSprite.flip(true, false);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)
            || Gdx.input.isKeyPressed(Input.Keys.A)) {
            playerSprite.translateX(-playerSpeed * delta);
            if (playerSprite.isFlipX()) {
                playerSprite.flip(true, false);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)
            || Gdx.input.isKeyPressed(Input.Keys.S)) {
            playerSprite.translateY(-playerSpeed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)
            || Gdx.input.isKeyPressed(Input.Keys.W)) {
            playerSprite.translateY(playerSpeed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && playerSprite.getIsCharged()) {
            playerSprite.useAbility();
            abilityActivated = true;
        }
    }

    /*
     * Update hit box position.
     */
    private void updateEntityHitBoxCoordinates(final Rectangle entityHitBox, final Entity entity,
                                               final float insetValue) {
        entityHitBox.setPosition(entity.getX() + insetValue, entity.getY() + insetValue);
    }



    /*
     * Runs all logic.
     */
    private void logicAll() {
        logicEnemyMovement();
        logicEnemyHitBox();
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
            if (enemy.getX() + spriteHitBoxInset > playerSprite.getX()) {
                enemy.translateX(-speed * delta);
                if (enemy.isFlipX()) {
                    enemy.flip(true, false);
                }
            }
            if (enemy.getX() - spriteHitBoxInset < playerSprite.getX()) {
                enemy.translateX(speed * delta);
                if (!enemy.isFlipX()) {
                    enemy.flip(true, false);
                }
            }

            if (enemy.getY() + spriteHitBoxInset > playerSprite.getY()) {
                enemy.translateY(-speed * delta);
            }
            if (enemy.getY() - spriteHitBoxInset < playerSprite.getY()) {
                enemy.translateY(speed * delta);
            }
        }
    }

    /*
     * Runs logic that are affected by the enemy hit box.
     */
    private void logicEnemyHitBox() {
        for (Enemy enemy : enemySprites) {
            updateEntityHitBoxCoordinates(enemyHitBox, enemy, spriteHitBoxInset);
            if (abilityActivated) {
                playerAbility.setPosition(playerSprite.getX() + (spriteWidth / 2),
                    playerSprite.getY() + (spriteHeight / 2));
                if (Intersector.overlaps(playerAbility, enemyHitBox)) {
                    enemySprites.removeValue(enemy, true);
                    allEntities.removeValue(enemy, true);
                }
            }
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
            updateEntityHitBoxCoordinates(playerHitBox, playerSprite, spriteHitBoxInset);
            updateEntityHitBoxCoordinates(itemHitBox, item, itemSpriteHitBoxInset);
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
            dispose();
            game.setScreen(new ResultScreen(game, gameDifficulty, playerSprite.getCurrentScore()));
        }
    }

    /*
     * Run game over logic.
     */
    private void logicGameOver() {
        if (playerSprite.getCurrentHP() <= 0) {
            dispose();
            game.setScreen(new GameOverScreen(game));
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

    /**
     * Removes resources.
     */
    @Override
    public void dispose() {
        backgroundTexture.dispose();
        platformTexture.dispose();
        playerTexture.dispose();
        playerDamagedTexture.dispose();
        abilityTexture.dispose();
        standardZombieTexture.dispose();
        itemHealthTexture.dispose();
        itemStaminaTexture.dispose();
        itemScoreTexture.dispose();
        healthFilledTexture.dispose();
        healthEmptyTexture.dispose();
        staminaContainerTexture.dispose();
        staminaFillingTexture.dispose();
    }


}
