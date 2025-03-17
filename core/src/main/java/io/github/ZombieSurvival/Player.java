package io.github.ZombieSurvival;

/**
 * The character the player controls.
 *
 * @author Kanon Nishiyama
 * @version 2025
 */
public class Player {
    private final int maxHP;
    private final int maxStamina;
    private final int maxCharge;
    private int currentHP;
    private int currentStamina;
    private int currentCharge;
    private boolean isCharged;
    private int xCoordinate;
    private int yCoordinate;
    private int currentScore;

    /**
     * Constructs a Player object with a specified maxHP, maxStamina, and maxCharge.
     *
     * @param maxHP an int
     * @param maxStamina an int
     * @param maxCharge an int
     * @throws IllegalArgumentException if maxHP is smaller or equal to 0
     * @throws IllegalArgumentException if maxStamina is smaller or equal to 0
     * @throws IllegalArgumentException if maxCharge is smaller or equal to 0
     */
    public Player(final int maxHP, final int maxStamina, final int maxCharge) {
        // Set HP max value
        if (maxHP <= 0) {
            throw new IllegalArgumentException("maxHP must be a larger then 0.");
        }
        this.maxHP = maxHP;
        // Set Stamina max value
        if (maxStamina <= 0) {
            throw new IllegalArgumentException("maxStamina must be a larger then 0.");
        }
        // Set Ability Charge max value
        this.maxStamina = maxStamina;
        if (maxCharge <= 0) {
            throw new IllegalArgumentException("maxCharge must be a larger then 0.");
        }
        this.maxCharge = maxCharge;
        // Set current values
        currentHP = maxHP;
        currentStamina = maxStamina;
        // Set default values
        currentCharge = 0;
        isCharged = false;
        xCoordinate = 0;
        yCoordinate = 0;
        currentScore = 0;
    }


}
