package io.github.ZombieSurvival.Sprites;

import java.util.Comparator;

/**
 * Compares Entity's by their Y coordinate.
 *
 * @author Kanon Nishiyama
 * @version 2025
 */
public class EntityComparator implements Comparator<Entity> {
    /**
     * Compares the two Students by their Student IDs.
     * @param first a Student
     * @param second a Student
     * @return an int where 0 means they are the same
     *                      - means first < second
     *                      + means first > second
     */
    @Override
    public int compare(final Entity first, final Entity second) {
        return first.compareTo(second);
    }
}
