package io.github.ZombieSurvival;

/**
 * A non-player entity.
 *
 * @author Kanon Nishiyama
 * @version 2025
 */
public abstract class Entity {
    /**
     * Value the entity will modify the Player's stats by.
     */
    protected final int value;
    /**
     * Entity's X coordinate.
     */
    protected int xCoordinate;
    /**
     * Entity's Y coordinate.
     */
    protected int yCoordinate;

    /**
     * Constructs an Entity object with the specified value, xCoordinate, and yCoordinate.
     *
     * @param value an int
     * @param xCoordinate an int
     * @param yCoordinate an int
     */
    public Entity(final int value, final int xCoordinate, final int yCoordinate) {
        this.value = value;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    /**
     * Modifies the given Player's stats by value.
     *
     * @param player a Player
     */
    void modifyPlayerStat(final Player player) {
        System.out.print("Modifying player stat by value!");
    }
    /**
     * Returns the xCoordinate of this Entity.
     *
     * @return xCoordinate as int
     */
    int getXCoordinate() {
        return xCoordinate;
    }
    /**
     * Returns the yCoordinate of this Entity.
     *
     * @return yCoordinate as int
     */
    int getYCoordinate() {
        return yCoordinate;
    }
}
