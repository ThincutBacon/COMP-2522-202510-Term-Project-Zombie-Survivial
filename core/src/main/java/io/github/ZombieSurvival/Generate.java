package io.github.ZombieSurvival;

/**
 * Generates entities with preset status values.
 *
 * @author Kanon Nishiyama
 * @version 2025
 */
public final class Generate {
    private Generate instance = null;

    private Generate() { }

    public Generate getInstance() {
        if (instance == null) {
            instance = new Generate();
        }
        return instance;
    }

    /**
     * Creates an instance of Player based on the selected difficulty.
     *
     * @param difficulty as Difficulty
     * @return the created Player object
     * @throws IllegalArgumentException if difficulty is null or an unexpected option
     */
    public static Player createPlayer(final Difficulty difficulty) {
        // Easy
        final int easyHP = 5;
        final int easyStamina = 100;
        final int easyCharge = 50;
        // Normal
        final int normalHP = 3;
        final int normalStamina = 100;
        final int normalCharge = 100;
        // Hard
        final int hardHP = 3;
        final int hardStamina = 50;
        final int hardCharge = 150;
        // Create Player
        switch (difficulty) {
            case EASY:
                return new Player(easyHP, easyStamina, easyCharge);
            case NORMAL:
                return new Player(normalHP, normalStamina, normalCharge);
            case HARD:
                return new Player(hardHP, hardStamina, hardCharge);
            default:
                throw new IllegalArgumentException("Invalid difficulty value.");
        }
    }
}
