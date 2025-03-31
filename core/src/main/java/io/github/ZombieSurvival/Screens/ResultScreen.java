package io.github.ZombieSurvival.Screens;

import com.badlogic.gdx.Screen;
import io.github.ZombieSurvival.RotNRun;

public class ResultScreen implements Screen {
    // Current running game
    private final RotNRun game;

    /**
     * Constructs a ResultScreen object with the specified instance of game.
     *
     * @param game a RotNRun
     * @throws IllegalArgumentException if game is null
     */
    public ResultScreen(final RotNRun game) {
        if (game == null) {
            throw new IllegalArgumentException("There is no game to the apply screen to.");
        }
        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
