package io.github.ZombieSurvival;

public abstract class Entity {
    protected int value;
    protected int xCoordinate;
    protected int yCoordinate;

    public Entity(final int value, final int xCoordinate, final int yCoordinate) {
        this.value = value;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }
}
