package io.github.ZombieSurvival;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Array;

import javax.sound.midi.Receiver;
import java.awt.*;

public class GameScreen implements Screen {
    private final RotNRun game;

    // Background
    private Texture backgroundTexture;
    private Texture platformTexture;
    // Player
    private Player playerStats;
    private Texture playerTexture;
    private Sprite playerSprite;
    private Rectangle playerHitbox;
    // Enemy
    private Texture standardZombieTexture;
    private Array<Sprite> enemySprites;
    private Rectangle enemyHitbox;
    // GUI
    private Vector2 touchPosition;
    private float enemySpawnTimer;
    private float itemSpawnTimer;
    private long startGameInMilliSeconds;
    private long endGameInMilliSeconds;


    public GameScreen(final RotNRun game) {
        this.game = game;
        playerStats = Generate.createPlayer(Difficulty.NORMAL);
        // Load images
        backgroundTexture = new Texture("City_Ruins.png");
        playerTexture = new Texture("Player_Sprite_Large.png");
        standardZombieTexture = new Texture("Zombie_Sprite_Large.png");

    }

    @Override
    public void show() {

    }

    @Override
    public void render(final float delta) {
        // Background color/cleared screen color
        final float red = 27f / 255f;
        final float blue = 14f / 255f;
        final float green = 25f / 255f;
        ScreenUtils.clear(red, blue, green, 1, true);

        game.getViewport().apply();
        game.getSpriteBatch().setProjectionMatrix(game.getViewport().getCamera().combined);

        game.getSpriteBatch().begin();
        //draw text. Remember that x and y are in meters
        game.getFont().draw(game.getSpriteBatch(), "Welcome to Rot 'N' Run!!! ", 1, 1.5f);
        game.getFont().draw(game.getSpriteBatch(), "Tap anywhere to begin!", 1, 1);
        game.getSpriteBatch().end();

        // Game start on touch
        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(final int width, final int height) {
        game.getViewport().update(width, height, true);
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
