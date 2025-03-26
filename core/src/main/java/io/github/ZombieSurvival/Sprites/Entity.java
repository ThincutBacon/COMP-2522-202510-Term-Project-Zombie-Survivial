package io.github.ZombieSurvival.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * A sprite that exists on map in game.
 *
 * @author Kanon Nishiyama
 * @version 2025
 */
public abstract class Entity extends Sprite implements Comparable<Entity> {
    /**
     * Constructs an Entity object with the specified texture.
     *
     * @param texture a Texture
     */
    public Entity(final Texture texture) {
        super(texture);
    }

    /**
     * Compares this entity to another entity based on their Y coordinate.
     *
     * @param other the student to compare to
     * @return a negative integer, zero, or a positive integer as this student
     *         is less than, equal to, or greater than the specified student
     */
    @Override
    public int compareTo(final Entity other) {
        final int amplifier = 1000;
        return (int) ((other.getY() - this.getY()) * amplifier);
    }
}
