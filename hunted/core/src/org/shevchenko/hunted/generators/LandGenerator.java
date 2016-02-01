package org.shevchenko.hunted.generators;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject.CollisionFlags;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import org.shevchenko.hunted.manager.AssetFactory;
import org.shevchenko.hunted.utils.BulletConstructor;
import org.shevchenko.hunted.utils.BulletEntity;
import org.shevchenko.hunted.utils.BulletWorld;

public class LandGenerator {

    private BulletEntity entity;

    private btCollisionShape shape;

    public LandGenerator() {

    }

    public LandGenerator(BulletEntity entity) {
        this.entity = entity;
    }

    public LandGenerator initToWorld(BulletWorld world, float mass) {
        Model model = AssetFactory.getInstance().get(AssetFactory.ModelType.LAND, Model.class);
        shape = Bullet.obtainStaticNodeShape(model.nodes);
        world.addConstructor("land", new BulletConstructor(model, mass, shape));
        return this;
    }

    public LandGenerator build(BulletWorld world, Vector3 position) {
        entity = world.add("land", position.x, position.y, position.z);
        entity.modelInstance.transform.rotate(new Quaternion(Vector3.X, -90));
        entity.body.setWorldTransform(entity.modelInstance.transform);
        entity.body.setCollisionFlags(CollisionFlags.CF_KINEMATIC_OBJECT);
        return new LandGenerator(entity);
    }

    public LandGenerator setColliderWidth(int x, int y, int z) {
        return this;
    }

}
