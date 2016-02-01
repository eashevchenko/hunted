package org.shevchenko.hunted.test;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBvhTriangleMeshShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import org.shevchenko.hunted.manager.AssetFactory;
import org.shevchenko.hunted.utils.BulletConstructor;
import org.shevchenko.hunted.utils.BulletEntity;
import org.shevchenko.hunted.utils.BulletWorld;

public class ModelEntity {
    private Model model;

    private BulletEntity e;
    private Vector3 position;

    ModelEntity() {
        setupModel();

    }


    private void setupModel() {
        model = AssetFactory.getInstance().get(AssetFactory.ModelType.CUBE, Model.class);

    }

    public void addPhysics(BulletWorld world, float mass, String name, Vector3 position) {
        btCollisionShape b = new btBvhTriangleMeshShape(getModel().meshParts);
        world.addConstructor(name, new BulletConstructor(getModel(), mass, b));
        world.add(name, position.x, position.y, position.z);
    }


    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public BulletEntity getE() {
        return e;
    }

    public void setE(BulletEntity e) {
        this.e = e;
    }


    public Vector3 getPosition() {
        return position;
    }

}
