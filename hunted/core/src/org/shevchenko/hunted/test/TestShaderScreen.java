package org.shevchenko.hunted.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.shevchenko.hunted.shaders.ColorPassShader;


public class TestShaderScreen implements Screen {

    private final int W = Gdx.graphics.getWidth();
    private final int H = Gdx.graphics.getHeight();

    private PerspectiveCamera camera;
    private Environment env;
    private ModelBatch batch;

    private Array<ModelInstance> inst = new Array<ModelInstance>();
    private FitViewport viewport;
    public Shader shader;


    @Override
    public void show() {
        setupCamera();
        setupLights();
        batch = new ModelBatch();
        shader = new ColorPassShader();
        shader.init();
    }


    private void setupLights() {
        env = new Environment();
        env.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.2f, 0.2f, 0.2f, 1.f));
        env.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -0.5f, -1f, -0.7f));
        env.add(new PointLight().set(Color.WHITE, new Vector3(0, 4, 2), 5));
    }

    private void setupCamera() {
        camera = new PerspectiveCamera(70, W, H);
        viewport = new FitViewport(W, H, camera);
        camera.lookAt(Vector3.Zero);
        camera.position.set(new Vector3(0, 3, 10));
        camera.near = 1f;
        camera.far = 300f;
        camera.update();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);


        batch.begin(camera);
        batch.render(inst, env);
        batch.end();


    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        for (ModelInstance i : inst) {
            i.model.dispose();
        }

    }

}
