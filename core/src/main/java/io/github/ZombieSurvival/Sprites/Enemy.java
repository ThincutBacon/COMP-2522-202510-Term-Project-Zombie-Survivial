package io.github.ZombieSurvival.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * An interactable enemy entity.
 *
 * @author Kanon Nishiyama
 * @version 2025
 */
public class Enemy extends Sprite {
    private final int attackValue;
    private final int speed;

    /**
     * Constructs an Enemy object with the specified attackValue and speed.
     *
     * @param texture a Texture
     * @param attackValue an int
     * @param speed an int
     * @throws IllegalArgumentException if attackValue or speed is a negative integer
     */
    public Enemy(final Texture texture, final int attackValue, final int speed) {
        super(texture);
        if (attackValue < 0) {
            throw new IllegalArgumentException("Attack Value cannot be negative.");
        }
        this.attackValue = attackValue;
        if (speed < 0) {
            throw new IllegalArgumentException("Speed cannot be negative.");
        }
        this.speed = speed;
    }

    /**
     * Decreases the Player's HP by value.
     *
     * @param player a Player
     */
    void modifyPlayerStat(final Player player) {
        player.modifyCurrentHP(-attackValue);
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
            (this.attackValue == enemy.attackValue
            && this.speed == enemy.speed);
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

        result = attackValue;
        result = usefulPrime * result + speed;
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
        builder.append("attackValue=").append(this.attackValue).append(", \n");
        builder.append("speed=").append(this.speed);
        builder.append("\n}");
        return builder.toString();
    }
}
