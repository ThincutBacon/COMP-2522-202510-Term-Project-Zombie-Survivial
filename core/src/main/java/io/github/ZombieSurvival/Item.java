package io.github.ZombieSurvival;

public class Item extends Entity {
    private final ItemType itemType;

    public Item(final int value, final int xCoordinate, final int yCoordinate, final ItemType itemType) {
        super(value, xCoordinate, yCoordinate);
        this.itemType = itemType;
    }

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
}
