package org.shevchenko.hunted.generators;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import org.shevchenko.hunted.utils.BulletWorld;

public class WorldGenerator {
    public Array<CubeGenerator> cubes;
    private String text = "levels/level1.json";
    private JsonReader reader;
    private JsonValue root;
    private PlayerGenerator player;

    public PlayerGenerator getPlayer() {
        return player;
    }

    public void setPlayer(PlayerGenerator player) {
        this.player = player;
    }

    public void initJsonData() {
        reader = new JsonReader();
        root = reader.parse(Gdx.files.internal(text));
    }

    public PerspectiveCamera generatePerspectiveCamera(float near, float far, Vector3 lookat) {
        PerspectiveCamera camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.lookAt(lookat);
        camera.near = near;
        camera.far = far;
        camera.update();
        return camera;
    }

    public BulletWorld generateBaseWorld(boolean debug) {
        Bullet.init();
        BulletWorld world = new BulletWorld(new Vector3(0, -9.81f, 0));
        if (debug)
            world.setDebugMode(btIDebugDraw.DebugDrawModes.DBG_DrawWireframe
                    | btIDebugDraw.DebugDrawModes.DBG_DrawFeaturesText | btIDebugDraw.DebugDrawModes.DBG_DrawText
                    | btIDebugDraw.DebugDrawModes.DBG_DrawContactPoints);
        return world;
    }

    public void generateCubes(BulletWorld world) {
        cubes = new Array<CubeGenerator>();
        CubeGenerator cube = new CubeGenerator();
        JsonValue cubesJson = root.get("cubes");
        for (JsonValue cubeJson : cubesJson.iterator()) {
            int xPos = cubeJson.getInt("xPos");
            int yPos = cubeJson.getInt("yPos");
            int zPos = cubeJson.getInt("zPos");
            int xCol = cubeJson.getInt("xCol");
            int yCol = cubeJson.getInt("xCol");
            int zCol = cubeJson.getInt("xCol");
            int mass = cubeJson.getInt("mass");
            //    float friction = cubeJson.getFloat("friction");
            cubes.add(cube.setColliderWidth(xCol, yCol, zCol)
                    .initToWorld(cubesJson.name, world, mass)
                    .build(cubesJson.name, world, new Vector3(xPos, yPos, zPos)));
        }
    }

    public void generatePlayer(BulletWorld world) {
        player = new PlayerGenerator();
        JsonValue humansJson = root.get("human");
        for (JsonValue humanJson : humansJson.iterator()) {
            int xPos = humanJson.getInt("xPos");
            int yPos = humanJson.getInt("yPos");
            int zPos = humanJson.getInt("zPos");
            float mass = humanJson.getFloat("mass");
            player.initToWorld(world, mass)
                    .build(world, new Vector3(xPos, yPos, zPos));
        }
    }

    public void generateLand(BulletWorld world) {
        LandGenerator land = new LandGenerator();
        JsonValue landsJson = root.get("land");
        for (JsonValue landJson : landsJson.iterator()) {
            int xPos = landJson.getInt("xPos");
            int yPos = landJson.getInt("yPos");
            int zPos = landJson.getInt("zPos");
            float mass = landJson.getFloat("mass");
            land.initToWorld(world, mass)
                    .build(world, new Vector3(xPos, yPos, zPos));
        }
    }

    public void update(BulletWorld world, float delta) {
        world.update();
        for (CubeGenerator cube : cubes) {
            cube.update(delta, 150);
        }

    }


}
