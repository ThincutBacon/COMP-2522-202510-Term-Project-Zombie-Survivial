package io.github.ZombieSurvival.Sprites;

import com.badlogic.gdx.graphics.Texture;

/**
 * The character the player controls.
 *
 * @author Kanon Nishiyama
 * @version 2025
 */
public class Player extends Entity {
    private final int maxHP;
    private final int maxStamina;
    private final int maxCharge;
    private int currentHP;
    private int currentStamina;
    private int currentCharge;
    private boolean isCharged;
    private int currentScore;

    /**
     * Constructs a Player object with a specified maxHP, maxStamina, and maxCharge.
     *
     * @param texture a Texture
     * @param maxHP an int
     * @param maxStamina an int
     * @param maxCharge an int
     * @throws IllegalArgumentException if maxHP is smaller or equal to 0
     * @throws IllegalArgumentException if maxStamina is smaller or equal to 0
     * @throws IllegalArgumentException if maxCharge is smaller or equal to 0
     */
    public Player(final Texture texture,
                  final int maxHP, final int maxStamina, final int maxCharge) {
        // Sprite
        super(texture);
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
        currentScore = 0;
    }

    // HP related methods
    /**
     * Returns the maxHP of this Player.
     *
     * @return maxHP as int
     */
    public int getMaxHP() {
        return maxHP;
    }
    /**
     * Returns the currentHP of this Player.
     *
     * @return currentHP as int
     */
    public int getCurrentHP() {
        return currentHP;
    }
    /**
     * Modify the Player's currentHP by the specified amount.
     *
     * @param amount an int
     */
    public void modifyCurrentHP(final int amount) {
        currentHP += amount;
    }
    // Stamina related methods
    /**
     * Returns the maxStamina of this Player.
     *
     * @return maxStamina as int
     */
    public int getMaxStamina() {
        return maxStamina;
    }
    /**
     * Returns the currentStamina of this Player.
     *
     * @return currentStamina as int
     */
    public int getCurrentStamina() {
        return currentStamina;
    }
    /**
     * Modify the Player's currentStamina by the specified amount.
     *
     * @param amount an int
     */
    public void modifyCurrentStamina(final int amount) {
        currentStamina += amount;
    }
    // Ability related methods
    /**
     * Returns the currentCharge of this Player's ability.
     *
     * @return currentCharge as int
     */
    public int getCurrentCharge() {
        return currentCharge;
    }
    /**
     * Increases the currentCharge by 1 if the currentCharge is smaller than the maxCharge value.
     * If the currentCharge equals the maxCharge, changes isCharged value to true.
     */
    public void increaseCurrentCharge() {
        if (currentCharge < maxCharge) {
            currentCharge++;
        } else {
            isCharged = true;
        }
    }
    /**
     * Returns the isCharged of this Player's ability.
     *
     * @return isCharged as boolean
     */
    public boolean getIsCharged() {
        return isCharged;
    }
    /**
     * If the ability is charged, resets the currentCharge to 0 and resets the isCharged to false.
     */
    public void useAbility() {
        if (isCharged) {
            currentCharge = 0;
            isCharged = false;
        }
    }
    // Score related methods
    /**
     * Returns the currentScore of this Player.
     *
     * @return currentScore as int
     */
    public int getCurrentScore() {
        return currentScore;
    }
    /**
     * Modify the Player's currentScore by the specified amount.
     *
     * @param amount an int
     */
    public void modifyCurrentScore(final int amount) {
        currentScore += amount;
    }

    /**
     * Compares this Player to another Object for equality.
     *
     * @param object an Object
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Player player = (Player) object;

        return getMaxHP() == player.getMaxHP()
            && getMaxStamina() == player.getMaxStamina()
            && this.maxCharge == player.maxCharge
            && getCurrentHP() == player.getCurrentHP()
            && getCurrentStamina() == player.getCurrentStamina()
            && getCurrentCharge() == player.getCurrentCharge()
            && getIsCharged() == player.getIsCharged()
            && getCurrentScore() == player.getCurrentScore();
    }

    /**
     * Returns a hash code for this Player.
     *
     * @return a hash code
     */
    @Override
    public int hashCode() {
        final int usefulPrime = 23;
        int result;

        result = getMaxHP();
        result = usefulPrime * result + getMaxStamina();
        result = usefulPrime * result + maxCharge;
        result = usefulPrime * result + getCurrentHP();
        result = usefulPrime * result + getCurrentStamina();
        result = usefulPrime * result + getCurrentCharge();
        if (getIsCharged()) {
            result = usefulPrime * result + 1;
        } else {
            result = usefulPrime * result;
        }
        result = usefulPrime * result + getCurrentScore();
        return result;
    }

    /**
     * Returns a String representation of this Loan.
     *
     * @return a String
     */
    @Override
    public String toString() {
        final StringBuilder builder;
        builder = new StringBuilder("Player{");
        builder.append("maxHP=").append(getMaxHP()).append(", ");
        builder.append("maxStamina=").append(getMaxStamina()).append(", ");
        builder.append("maxCharge=").append(maxCharge).append(", ");
        builder.append("currentHP=").append(getCurrentStamina()).append(", ");
        builder.append("currentStamina=").append(getMaxStamina()).append(", ");
        builder.append("currentCharge=").append(getCurrentCharge()).append(", ");
        builder.append("isCharged=").append(getIsCharged()).append(", ");
        builder.append("currentScore=").append(getCurrentScore());
        builder.append("\n}");
        return builder.toString();
    }
}
