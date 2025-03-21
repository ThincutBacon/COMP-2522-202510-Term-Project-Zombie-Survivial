package io.github.ZombieSurvival;

public class Enemy extends Entity {
    private int speed;
    private int currentXCoordinate;
    private int currentYCoordinate;

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
                yCoordinate += speed;
                break;
            case E:
                xCoordinate -= speed;
                break;
            case W:
                xCoordinate += speed;
                break;
            case S:
                yCoordinate -= speed;
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
}
