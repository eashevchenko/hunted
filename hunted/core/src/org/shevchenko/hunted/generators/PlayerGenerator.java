package org.shevchenko.hunted.generators;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.Collision;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject.CollisionFlags;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btCompoundShape;
import org.shevchenko.hunted.manager.AssetFactory;
import org.shevchenko.hunted.utils.BulletConstructor;
import org.shevchenko.hunted.utils.BulletEntity;
import org.shevchenko.hunted.utils.BulletWorld;

public class PlayerGenerator extends InputAdapter {

	private BulletEntity entity;
	private btCollisionShape shape;

	public PlayerGenerator() {

	}

	public PlayerGenerator(BulletEntity entity) {
		this.entity = entity;
		Gdx.input.setInputProcessor(this);
	}

	public PlayerGenerator initToWorld(BulletWorld world, float mass) {
		Model model = AssetFactory.getInstance().get(AssetFactory.ModelType.HUMAN, Model.class);
		shape = Bullet.obtainStaticNodeShape(model.nodes.first(), true);
		btCompoundShape playerShape = new btCompoundShape();
		playerShape.addChildShape(new Matrix4(new Vector3(0, 0, 0.85f), new Quaternion(), new Vector3(0.5f, 0.5f, 0.5f)),
				new btBoxShape(new Vector3(1f, 0.5f, 2f)));
		world.addConstructor("player", new BulletConstructor(model, mass, playerShape));
		return this;
	}

	public BulletEntity getEntity() {
		return entity;
	}

	public void setEntity(BulletEntity entity) {
		this.entity = entity;
	}

	public PlayerGenerator build(BulletWorld world, Vector3 position) {
		entity = world.add("player", position.x, position.y, position.z);
		entity.modelInstance.transform.rotate(new Quaternion(Vector3.X, 90));
		entity.modelInstance.transform.rotate(new Quaternion(Vector3.Y, 180));
		entity.body.setWorldTransform(entity.modelInstance.transform);
		entity.body.setCollisionFlags(CollisionFlags.CF_KINEMATIC_OBJECT);
		entity.body.setActivationState(Collision.DISABLE_DEACTIVATION);
	    
		
		return new PlayerGenerator(entity);
	}

	public void applyMoveQuery() {

		if (Gdx.input.isKeyPressed(Keys.W)) {

		} else if (Gdx.input.isKeyPressed(Keys.S)) {

			// ((btRigidBody)entity.body).applyCentralForce(new
			// Vector3(0,0,100f));
		}
	}

	public Vector3 getPosition() {
		return entity.transform.getTranslation(Vector3.Zero);
	}

}
