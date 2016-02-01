package org.shevchenko.hunted.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.shevchenko.hunted.generators.Sky;
import org.shevchenko.hunted.generators.WorldGenerator;
import org.shevchenko.hunted.input.PlayerInput;
import org.shevchenko.hunted.models.HUD;
import org.shevchenko.hunted.shaders.ColorPassShader;
import org.shevchenko.hunted.shaders.WaveShader;
import org.shevchenko.hunted.utils.BulletWorld;
import org.shevchenko.hunted.utils.Params;

public class GameScreen extends InputAdapter implements Screen {

    private BulletWorld world;
    private Environment environment;
    private PerspectiveCamera camera;
    private ModelBatch batch;
    private FitViewport viewport;
    private WorldGenerator worldGenerator;
    private Sky sky;
    private CameraInputController controller;
    private ColorPassShader shader;
    private WaveShader waveShader;

    private HUD hud;
    private boolean activeteHUD = true;
    private PlayerInput input;

    @Override
    public void show() {
        viewport = new FitViewport(Params.worldWidth, Params.worldHeight);
        initWorld();
        sky = new Sky();
        hud = new HUD();
        setupShaders();
    }

    private void setupShaders() {
        shader = new ColorPassShader();
        waveShader = new WaveShader();
        shader.init();
        waveShader.init();
    }

    private void initWorld() {
        worldGenerator = new WorldGenerator();
        worldGenerator.initJsonData();
        camera = worldGenerator.generatePerspectiveCamera(1, 300, Vector3.Zero);
        environment = new Environment();
        environment.add(new PointLight().set(Color.RED, Vector3.Zero, 10));
        environment.add(new PointLight().set(Color.WHITE, new Vector3(3, -1, 2), 5));
        environment.add(new PointLight().set(Color.BLUE, new Vector3(0, 0, 0), 100));
        environment.add(new DirectionalLight().set(0.4f, 0.4f, 0.4f, 4f, 4f, 4f));
        // envCubemap = new EnvironmentCubemap(new
        // Pixmap(Gdx.files.internal("skybox.png")));
        batch = new ModelBatch();
        world = worldGenerator.generateBaseWorld(true);
        worldGenerator.generatePlayer(world);

        worldGenerator.generateCubes(world);
        worldGenerator.generateLand(world);
        playerPosition = worldGenerator.getPlayer().getPosition();
        //	controller = new CameraInputController(camera);
        input = new PlayerInput(worldGenerator.getPlayer());
        Gdx.input.setInputProcessor(input);

    }

    private Vector3 playerPosition;

    @Override
    public void render(float delta) {
        clearScreen();
        drawWorld(delta);
        worldGenerator.update(world, delta);
        input.update();
        input.cameraToPlayer(camera);
        if (activeteHUD) {
            hud.drawHUD();
        }
        camera.position.set(playerPosition.x, playerPosition.y + 2, playerPosition.z + 4);
        camera.update();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glBlendFunc(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    private void drawWorld(float delta) {
        batch.begin(camera);
        world.render("player", batch, environment, waveShader);
        world.render("cubes", batch, environment);
        world.render("land", batch, environment, shader);
        sky.drawSky(batch, delta);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hud.resizeHUD(width, height);

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        world.dispose();
    }
}
