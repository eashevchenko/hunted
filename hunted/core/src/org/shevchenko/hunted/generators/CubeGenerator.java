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

public class CubeGenerator {

    private btCollisionShape collisionShape;
    private BulletEntity entity;
    private float friction;


    public CubeGenerator(BulletEntity entity) {
        this.entity = entity;
    }

    public CubeGenerator() {

    }

    public CubeGenerator initToWorld(String tag, BulletWorld world, float mass) {
        Model model = AssetFactory.getInstance().get(AssetFactory.ModelType.CUBE, Model.class);
        collisionShape = Bullet.obtainStaticNodeShape(model.nodes);
        world.addConstructor(tag, new BulletConstructor(model, mass, collisionShape));
        return this;
    }

    public CubeGenerator build(String tag, BulletWorld world, Vector3 position) {
        entity = world.add(tag, position.x, position.y, position.z);
        entity.body.setCollisionFlags(CollisionFlags.CF_KINEMATIC_OBJECT);
        //	entity.body.setActivationState(Collision.DISABLE_DEACTIVATION);
        return new CubeGenerator(entity);
    }

    public CubeGenerator setColliderWidth(int x, int y, int z) {
        return this;
    }

    public CubeGenerator setBobyFriction(float value) {
        entity.body.setFriction(friction);
        return this;
    }

    public CubeGenerator setAnizotriopicFriction() {
        entity.body.setAnisotropicFriction(new Vector3(0, 1, 0));
        return this;
    }

    public BulletEntity getEntity() {
        return entity;
    }

    public void setEntity(BulletEntity entity) {
        this.entity = entity;
    }

    public float getFriction() {
        return friction;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }

    public void update(float delta, int speed) {
        entity.body.setWorldTransform(entity.modelInstance.transform);
        entity.transform.rotate(new Quaternion(Vector3.X, speed * delta));
    }

}
