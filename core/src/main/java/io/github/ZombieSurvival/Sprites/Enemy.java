package io.github.ZombieSurvival.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * An interactable enemy entity.
 *
 * @author Kanon Nishiyama
 * @version 2025
 */
public class Enemy extends Sprite {
    private final int speed;

    /**
     * Constructs an Enemy object with the specified value, xCoordinate, and yCoordinate,
     * and speed.
     *
     * @param value an int
     * @param xCoordinate an int
     * @param yCoordinate an int
     * @param speed an int
     * @throws IllegalArgumentException if speed in smaller or equal to 0
     */
    public Enemy(final int value, final int xCoordinate, final int yCoordinate,
                 final int speed) {
        super(value, xCoordinate, yCoordinate);
        if (speed <= 0) {
            throw new IllegalArgumentException("Speed must be a non-zero positive integer.");
        }
        this.speed = speed;
    }

    /**
     * Returns the currentXCoordinate of this Enemy.
     *
     * @return currentXCoordinate as int
     */
    public int getCurrentXCoordinate() {
        return currentXCoordinate;
    }
    /**
     * Returns the currentYCoordinate of this Enemy.
     *
     * @return currentYCoordinate as int
     */
    public int getCurrentYCoordinate() {
        return currentYCoordinate;
    }
    /**
     * Moves the player in the specified direction.
     *
     * @param direction a Direction
     * @throws IllegalArgumentException if direction is not an expected direction
     */
    public void moveEnemy(final Direction direction) {
        switch (direction) {
            case N:
                currentYCoordinate += speed;
                break;
            case E:
                currentXCoordinate -= speed;
                break;
            case W:
                currentXCoordinate += speed;
                break;
            case S:
                currentYCoordinate -= speed;
                break;
            default:
                throw new IllegalArgumentException("Invalid directional input.");
        }
    }

    /**
     * Modifies the Player's HP by value.
     *
     * @param player a Player
     */
    @Override
    void modifyPlayerStat(final Player player) {
        player.modifyCurrentHP(value);
    }

    /**
     * Compares this Enemy to another Object for equality.
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

        Enemy enemy = (Enemy) object;

        return
            super.equals(enemy)
            && this.speed == enemy.speed
            && getCurrentXCoordinate() == enemy.getCurrentXCoordinate()
            && getCurrentYCoordinate() == enemy.getCurrentYCoordinate();
    }

    /**
     * Returns a hash code for this Enemy.
     *
     * @return a hash code
     */
    @Override
    public int hashCode() {
        final int usefulPrime = 23;
        int result;

        result = super.hashCode();
        result = usefulPrime * result + this.speed;
        result = usefulPrime * result + getCurrentXCoordinate();
        result = usefulPrime * result + getCurrentYCoordinate();
        return result;
    }

    /**
     * Returns a String representation of this Enemy.
     *
     * @return a String
     */
    @Override
    public String toString() {
        final StringBuilder builder;
        builder = new StringBuilder("Enemy{\n");
        builder.append("value='").append(this.value).append("', \n");
        builder.append("speed='").append(this.speed).append(", \n");
        builder.append("currentXCoordinate=").append(getCurrentXCoordinate()).append(", \n");
        builder.append("currentYCoordinate='").append(getCurrentYCoordinate());
        builder.append("\n}");
        return builder.toString();
    }
}
