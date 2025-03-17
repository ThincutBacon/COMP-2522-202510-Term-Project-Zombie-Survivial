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


    public int getMaxHP() {
        return maxHP;
    }
    public int getCurrentHP() {
        return currentHP;
    }
    public void modifyCurrentHP(final int amount) {
        currentHP += amount;
    }

    public int getMaxStamina() {
        return maxStamina;
    }
    public int getCurrentStamina() {
        return currentStamina;
    }
    public void modifyCurrentStamina(final int amount) {
        currentStamina += amount;
    }

    public int getCurrentCharge() {
        return currentCharge;
    }
    public void increaseCurrentCharge() {
        if (currentCharge < maxCharge) {
            currentCharge++;
        } else {
            isCharged = true;
        }
    }
    public boolean getIsCharged() {
        return isCharged;
    }
    public void useAbility() {
        if (isCharged) {
            currentCharge = 0;
            isCharged = false;
        }
    }

    public int getXCoordinate() {
        return xCoordinate;
    }
    public int getYCoordinate() {
        return yCoordinate;
    }
//    public void movePlayer(Directions direction) {
//         switch (direction)
//             case
//    }

    public int getCurrentScore() {
        return currentScore;
    }
    public void modifyCurrentScore(int amount) {
        currentScore += amount;
    }
}
