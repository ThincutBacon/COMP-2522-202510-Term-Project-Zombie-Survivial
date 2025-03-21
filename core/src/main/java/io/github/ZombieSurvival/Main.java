package io.github.ZombieSurvival;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private Texture backgroundTexture;
    private Texture platformTexture;

    private Texture playerTexture;
    private Sprite playerSprite;
    private Rectangle playerHitbox;
    private ShapeRenderer visualPlayerHitbox;

    private Texture enemyTexture;
    private Sprite enemySprite;
    private Rectangle enemyHitbox;
    private ShapeRenderer visualEnemyHitbox;

    private Array<Sprite> enemySprites;

    private Sound sfx;
    private Music music;
    private SpriteBatch spriteBatch;
    private FitViewport viewport;

    private Vector2 touchPos;
    private float enemySpawnTimer;

    private final float viewportWidth = 30;
    private final float viewportHeight = 22.5f;

    @Override
    public void create() {
        backgroundTexture = new Texture("City_Ruins.png");
        platformTexture = new Texture("Map_Platform.png");

        playerTexture = new Texture("Player_Sprite_Large.png");
        playerSprite = new Sprite(playerTexture);
        playerSprite.setSize(1, 1.5f);
        playerSprite.setPosition(viewportWidth/2-1, viewportHeight/2-1);
        playerHitbox = new Rectangle();
        visualPlayerHitbox = new ShapeRenderer();

        enemyTexture = new Texture("Zombie_Sprite_Large.png");
        enemySprites = new Array<>();
        enemyHitbox = new Rectangle();
        visualEnemyHitbox = new ShapeRenderer();

        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(viewportWidth, viewportHeight);
        touchPos = new Vector2();
    }

    @Override
    public void resize(final int width, final int height) {
        viewport.update(width, height, true); // true centers the camera
    }

    @Override
    public void render() {
        // organize code into three methods
        input();
        logic();
        draw();
    }

    private void input() {
        float delta = Gdx.graphics.getDeltaTime();
        float speed = 5.0f;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            // todo: Do something when the user presses the right arrow
            playerSprite.translateX(speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            // todo: Do something when the user presses the right arrow
            playerSprite.translateX(-speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            // todo: Do something when the user presses the right arrow
            playerSprite.translateY(-speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            // todo: Do something when the user presses the right arrow
            playerSprite.translateY(speed * delta);
        }

        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY()); // Get where the touch happened on screen
            viewport.unproject(touchPos); // Convert the units to the world units of the viewport
            if (playerSprite.getX() > touchPos.x) {
                playerSprite.translateX(-speed * delta);
            }
            if (playerSprite.getX() < touchPos.x) {
                playerSprite.translateX(speed * delta);
            }

            if (playerSprite.getY() > touchPos.y) {
                playerSprite.translateY(-speed * delta);
            }
            if (playerSprite.getY() < touchPos.y) {
                playerSprite.translateY(speed * delta);
            }
        }
    }

    private void logic() {
        playerSprite.setX(MathUtils.clamp(playerSprite.getX(), 4.5f, viewportWidth - 5.3f - playerSprite.getWidth()));
        playerSprite.setY(MathUtils.clamp(playerSprite.getY(), 2.5f, viewportHeight - 6 - playerSprite.getHeight()));
        playerHitbox.set(playerSprite.getX() + 0.4f, playerSprite.getY() + 0.4f, playerSprite.getWidth() - 0.8f, playerSprite.getHeight() - 0.8f);

        float speed = 2f;
        float delta = Gdx.graphics.getDeltaTime(); // retrieve the current delta

        enemySpawnTimer += delta; // Adds the current delta to the timer
        if (enemySpawnTimer > 3f) { // Check if it has been more than a second
            enemySpawnTimer = 0; // Reset the timer
            createEnemies(); // Create the droplet
        }

        // loop through each drop
        int index = 0;
        for (Sprite enemySprite : enemySprites) {
            enemyHitbox.set(enemySprite.getX(), enemySprite.getY(), enemySprite.getWidth(), enemySprite.getHeight());

            if (enemySprite.getX() + 0.5f > playerSprite.getX()) {
                enemySprite.translateX(-speed * delta);
            }
            if (enemySprite.getX() - 0.5f < playerSprite.getX()) {
                enemySprite.translateX(speed * delta);
            }

            if (enemySprite.getY() + 0.5f > playerSprite.getY()) {
                enemySprite.translateY(-speed * delta);
            }
            if (enemySprite.getY() - 0.5f < playerSprite.getY()) {
                enemySprite.translateY(speed * delta);
            }

            if (enemyHitbox.overlaps(playerHitbox)) {
                enemySprites.removeIndex(index);
            }

            index++;
        }
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
            // add lines to draw stuff here
            spriteBatch.draw(backgroundTexture, 0, 0, viewportWidth, viewportHeight);
            spriteBatch.draw(platformTexture, 0, 0, viewportWidth, viewportHeight);
            playerSprite.draw(spriteBatch);

            for (Sprite enemySprite : enemySprites) {
                enemySprite.draw(spriteBatch);
            }
        spriteBatch.end();

    }

    private void createEnemies() {
        // create local variables for convenience
        float enemyWidth = 1;
        float enemyHeight = 1.5f;

        // create the drop sprite
        enemySprite = new Sprite(enemyTexture);
        enemySprite.setSize(1, 1);
        enemySprite.setSize(enemyWidth, enemyHeight);
        enemySprite.setX(MathUtils.random(0f, viewportWidth - enemyWidth)); // Randomize the drop's x position
        enemySprite.setY(MathUtils.random(0f, viewportHeight - enemyHeight));
        enemySprites.add(enemySprite); // Add it to the list
    }


}
