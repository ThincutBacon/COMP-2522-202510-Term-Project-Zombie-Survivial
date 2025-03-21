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
    abstract void modifyPlayerStat(Player player);
    /**
     * Returns the xCoordinate of this Entity.
     *
     * @return xCoordinate as int
     */
    public int getXCoordinate() {
        return xCoordinate;
    }
    /**
     * Returns the yCoordinate of this Entity.
     *
     * @return yCoordinate as int
     */
    public int getYCoordinate() {
        return yCoordinate;
    }

    /**
     * Compares this Entity to another Object for equality.
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

        Entity entity = (Entity) object;

        return this.value == entity.value
            && getXCoordinate() == entity.getXCoordinate()
            && getYCoordinate() == entity.getYCoordinate();
    }

    /**
     * Returns a hash code for this Entity.
     *
     * @return a hash code
     */
    @Override
    public int hashCode() {
        final int usefulPrime = 23;
        int result;

        result = this.value;
        result = usefulPrime * result + getXCoordinate();
        result = usefulPrime * result + getYCoordinate();
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
        builder = new StringBuilder("Entity{\n");
        builder.append("value='").append(this.value).append("', \n");
        builder.append("xCoordinate=").append(getXCoordinate()).append(", \n");
        builder.append("yCoordinate='").append(getYCoordinate());
        builder.append("\n}");
        return builder.toString();
    }
}
