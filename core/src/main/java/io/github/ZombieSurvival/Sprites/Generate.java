package io.github.ZombieSurvival.Sprites;

import com.badlogic.gdx.graphics.Texture;

/**
 * Generates entities with preset status values.
 *
 * @author Kanon Nishiyama
 * @version 2025
 */
public final class Generate {
    private Generate() { }

    // Player
    /**
     * Creates a Player with stats based on the selected difficulty.
     *
     * @param texture a Texture
     * @param difficulty as Difficulty
     * @return the created Player object
     * @throws IllegalArgumentException if difficulty is null or an unexpected option
     */
    public static Player createPlayer(final Texture texture, final Difficulty difficulty) {
        // Easy
        final int easyHP = 5;
        final int easyStamina = 100;
        final int easyCharge = 50;
        // Normal
        final int normalHP = 3;
        final int normalStamina = 100;
        final int normalCharge = 100;
        // Hard
        final int hardHP = 3;
        final int hardStamina = 50;
        final int hardCharge = 150;
        // Create Player
        switch (difficulty) {
            case EASY:
                return new Player(texture, easyHP, easyStamina, easyCharge);
            case NORMAL:
                return new Player(texture, normalHP, normalStamina, normalCharge);
            case HARD:
                return new Player(texture, hardHP, hardStamina, hardCharge);
            default:
                throw new IllegalArgumentException("Invalid difficulty value.");
        }
    }
    // Enemies
    /**
     * Creates a Standard Zombie Enemy.
     *
     * @param texture a Texture
     * @return the created Enemy object
     */
    public static Enemy createStandardZombie(final Texture texture) {
        final int attackValue = 1;
        final float speed = 100f;

        return new Enemy(texture, attackValue, speed);
    }
    // Items
    // HP
    /**
     * Creates a Bandage Item. A minor healing item.
     *
     * @param texture a Texture
     * @return the created Item object
     */
    public static Item createBandage(final Texture texture) {
        final int increaseValue = 1;

        return new Item(texture, increaseValue, ItemType.HP);
    }
    /**
     * Creates a Med Kit Item. A major healing item.
     *
     * @param texture a Texture
     * @return the created Item object
     */
    public static Item createMedKit(final Texture texture) {
        final int increaseValue = 3;

        return new Item(texture, increaseValue, ItemType.HP);
    }
    // Stamina
    /**
     * Creates a Water Bottle Item. A minor stamina item.
     *
     * @param texture a Texture
     * @return the created Item object
     */
    public static Item createWaterBottle(final Texture texture) {
        final int increaseValue = 5;

        return new Item(texture, increaseValue, ItemType.STAMINA);
    }
    /**
     * Creates a Apple Item. A medium stamina item.
     *
     * @param texture a Texture
     * @return the created Item object
     */
    public static Item createApple(final Texture texture) {
        final int increaseValue = 15;

        return new Item(texture, increaseValue, ItemType.STAMINA);
    }
    /**
     * Creates a Sandwich Item. A major stamina item.
     *
     * @param texture a Texture
     * @return the created Item object
     */
    public static Item createSandwich(final Texture texture) {
        final int increaseValue = 25;

        return new Item(texture, increaseValue, ItemType.STAMINA);
    }
    // Score
    /**
     * Creates a Nails Item. A minor score item.
     *
     * @param texture a Texture
     * @return the created Item object
     */
    public static Item createNails(final Texture texture) {
        final int increaseValue = 1;

        return new Item(texture, increaseValue, ItemType.SCORE);
    }
    /**
     * Creates a Wooden Plank Item. A medium score item.
     *
     * @param texture a Texture
     * @return the created Item object
     */
    public static Item createWoodenPlank(final Texture texture) {
        final int increaseValue = 3;

        return new Item(texture, increaseValue, ItemType.SCORE);
    }
    /**
     * Creates a Metal Sheet Item. A major score item.
     *
     * @param texture a Texture
     * @return the created Item object
     */
    public static Item createMetalSheet(final Texture texture) {
        final int increaseValue = 5;

        return new Item(texture, increaseValue, ItemType.SCORE);
    }
}
