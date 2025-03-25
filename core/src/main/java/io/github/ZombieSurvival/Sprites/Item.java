package io.github.ZombieSurvival.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * An interactable item entity.
 *
 * @author Kanon Nishiyama
 * @version 2025
 */
public class Item extends Sprite {
    private final ItemType itemType;
    private final int increaseValue;

    /**
     * Constructs an Item object with the specified increaseValue and itemType.
     *
     * @param increaseValue an int
     * @param itemType an int
     * @throws IllegalArgumentException if itemType is a null
     * @throws IllegalArgumentException if increaseValue is a negative integer
     */
    public Item(final int increaseValue, final ItemType itemType) {
        if (itemType == null) {
            throw new IllegalArgumentException("Item type cannot be null.");
        }
        this.itemType = itemType;
        if (increaseValue < 0) {
            throw new IllegalArgumentException("Item value cannot be negative.");
        }
        this.increaseValue = increaseValue;
    }

    /**
     * Increases the Player's stat, specified by the itemType, by increaseValue.
     *
     * @param player a Player
     * @throws IllegalArgumentException if itemType is not an expected ItemType option
     */
    void increasePlayerStat(final Player player) {
        switch (itemType) {
            case HP:
                player.modifyCurrentHP(increaseValue);
                break;
            case STAMINA:
                player.modifyCurrentStamina(increaseValue);
                break;
            case SCORE:
                player.modifyCurrentScore(increaseValue);
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
            (this.itemType == item.itemType
            && this.increaseValue == item.increaseValue);
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

        result = itemType.hashCode();
        result = usefulPrime * result + increaseValue;
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
        builder.append("itemType=").append(this.itemType).append(", \n");
        builder.append("increaseValue=").append(this.increaseValue);
        builder.append("\n}");
        return builder.toString();
    }
}
