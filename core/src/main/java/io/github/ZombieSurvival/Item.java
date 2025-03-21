package io.github.ZombieSurvival;

/**
 * An interactable item entity.
 *
 * @author Kanon Nishiyama
 * @version 2025
 */
public class Item extends Entity {
    private final ItemType itemType;

    /**
     * Constructs an Item object with the specified value, xCoordinate, and yCoordinate,
     * and itemType.
     *
     * @param value an int
     * @param xCoordinate an int
     * @param yCoordinate an int
     * @param itemType an int
     */
    public Item(final int value, final int xCoordinate, final int yCoordinate,
                final ItemType itemType) {
        super(value, xCoordinate, yCoordinate);
        this.itemType = itemType;
    }

    /**
     * Modifies the Player's stat, specified by the itemType, by value.
     *
     * @param player a Player
     */
    @Override
    void modifyPlayerStat(final Player player) {
        switch (itemType) {
            case HP:
                player.modifyCurrentHP(value);
                break;
            case
                STAMINA: player.modifyCurrentStamina(value);
                break;
            case
                SCORE: player.modifyCurrentScore(value);
                break;
            default: throw new IllegalArgumentException("Not a valid itemType");
        }
    }

    /**
     * Compares this Item to another Object for equality.
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

        Item item = (Item) object;

        return
            super.equals(item)
            && this.itemType == item.itemType;
    }

    /**
     * Returns a hash code for this Item.
     *
     * @return a hash code
     */
    @Override
    public int hashCode() {
        final int usefulPrime = 23;
        int result;

        result = super.hashCode();
        result = usefulPrime * result + this.itemType.hashCode();
        return result;
    }

    /**
     * Returns a String representation of this Item.
     *
     * @return a String
     */
    @Override
    public String toString() {
        final StringBuilder builder;
        builder = new StringBuilder("Item{\n");
        builder.append("value='").append(this.value).append("', \n");
        builder.append("xCoordinate=").append(getXCoordinate()).append(", \n");
        builder.append("yCoordinate='").append(getYCoordinate()).append(", \n");
        builder.append("itemType='").append(this.itemType);
        builder.append("\n}");
        return builder.toString();
    }
}
