package org.shevchenko.hunted.utils;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody.btRigidBodyConstructionInfo;


public class BulletConstructor extends BaseWorld.Constructor<BulletEntity> {
	public btRigidBodyConstructionInfo bodyInfo = null;
	public btCollisionShape shape = null;
	private final static Vector3 tmpV = new Vector3();


	public BulletConstructor (final Model model, final float mass, final btCollisionShape shape) {
		create(model, mass, shape);
	}

	public BulletConstructor (final Model model, final btCollisionShape shape) {
		this(model, -1f, shape);
	}


	public BulletConstructor (final Model model, final float mass, final float width, final float height, final float depth) {
		create(model, mass, width, height, depth);
	}

	public BulletConstructor (final Model model, final float width, final float height, final float depth) {
		this(model, -1f, width, height, depth);
	}

	public BulletConstructor (final Model model, final float mass) {
		final BoundingBox boundingBox = new BoundingBox();
		model.calculateBoundingBox(boundingBox);
		create(model, mass, boundingBox.getWidth(), boundingBox.getHeight(), boundingBox.getDepth());
	}

	public BulletConstructor (final Model model) {
		this(model, -1f);
	}

	private void create (final Model model, final float mass, final float width, final float height, final float depth) {
		create(model, mass, new btBoxShape(tmpV.set(width * 0.5f, height * 0.5f, depth * 0.5f)));
	}

	private void create (final Model model, final float mass, final btCollisionShape shape) {
		this.model = model;
		this.shape = shape;

		if (shape != null && mass >= 0) {

			Vector3 localInertia;
			if (mass == 0)
				localInertia = Vector3.Zero;
			else {
				shape.calculateLocalInertia(mass, tmpV);
				localInertia = tmpV;
			}

			bodyInfo = new btRigidBodyConstructionInfo(mass, null, shape, localInertia);
		}
	}

	@Override
	public void dispose () {

		if (bodyInfo != null) bodyInfo.dispose();
		if (shape != null) shape.dispose();

		bodyInfo = null;
		shape = null;
	}

	@Override
	public BulletEntity construct (float x, float y, float z) {
		if (bodyInfo == null && shape != null) {
			btCollisionObject obj = new btCollisionObject();
			obj.setCollisionShape(shape);
			return new BulletEntity(model, obj, x, y, z);
		} else
			return new BulletEntity(model, bodyInfo, x, y, z);
	}

	@Override
	public BulletEntity construct (final Matrix4 transform) {
		if (bodyInfo == null && shape != null) {
			btCollisionObject obj = new btCollisionObject();
			obj.setCollisionShape(shape);
			return new BulletEntity(model, obj, transform);
		} else
			return new BulletEntity(model, bodyInfo, transform);
	}
}