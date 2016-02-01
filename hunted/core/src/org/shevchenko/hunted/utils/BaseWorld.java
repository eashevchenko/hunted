package org.shevchenko.hunted.utils;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;

public class BaseWorld<T extends BaseEntity> implements Disposable {
	public static abstract class Constructor<T extends BaseEntity> implements Disposable {
		public Model model = null;

		public abstract T construct (final float x, final float y, final float z);

		public abstract T construct (final Matrix4 transform);
	}

	private final ObjectMap<String, Constructor<T>> constructors = new ObjectMap<String, Constructor<T>>();
	protected final Array<T> entities = new Array<T>();
	private final Array<Model> models = new Array<Model>();

	public void addConstructor (final String name, final Constructor<T> constructor) {
		constructors.put(name, constructor);
		if (constructor.model != null && !models.contains(constructor.model, true)) models.add(constructor.model);
	}

	public Constructor<T> getConstructor (final String name) {
		return constructors.get(name);
	}

	public void add (final T entity) {
		entities.add(entity);
	}

	public T add (final String type, float x, float y, float z) {
		final T entity = constructors.get(type).construct(x, y, z);
		add(entity);
		return entity;
	}

	public T add (final String type, final Matrix4 transform) {
		final T entity = constructors.get(type).construct(transform);
		add(entity);
		return entity;
	}

	public void render (final ModelBatch batch, final Environment lights) {
		render(batch, lights, entities);
	}
	
	public void render (final String name,final ModelBatch batch, final Environment lights) {
		render(name, batch, lights, entities);
	}
	
	public void render (final String name,final ModelBatch batch, final Environment lights, final Shader shader) {
		render(name, batch, lights, entities, shader);
	}
	
	public void render (final ModelBatch batch, final Environment lights, final Shader shader) {
		render(batch, lights, entities, shader);
	}

	public void render (final ModelBatch batch, final Environment lights, final Iterable<T> entities) {
		for (final T e : entities) {
			batch.render(e.modelInstance, lights);
		}
	}
	
	public void render (final ModelBatch batch, final Environment lights, final Iterable<T> entities, final Shader shader) {
		for (final T e : entities) {
			batch.render(e.modelInstance, lights, shader);
		}
	}

	public void render (final ModelBatch batch, final Environment lights, final T entity) {
		batch.render(entity.modelInstance, lights);
	}
	
	public void render(final ModelBatch batch, final Environment lights, final T entity, final Shader shader){
		batch.render(entity.modelInstance, lights, shader);
	}
	
	public void render(final String tag, final ModelBatch batch, final Environment lights, final Iterable<T> entities){
		Model m = constructors.get(tag).model;
		for(final T e: entities){
			if( e.modelInstance.model.equals(m)){
				batch.render(e.modelInstance,lights);
			}
		}
	}	
	
	public void render(final String tag, final ModelBatch batch, final Environment lights, final Iterable<T> entities, final Shader shader){
		Model m = constructors.get(tag).model;
		for(final T e: entities){
			if( e.modelInstance.model.equals(m)){
				batch.render(e.modelInstance,lights, shader);
			}
		}
	}
	
	public void update(){
		
	}

	@Override
	public void dispose () {
		for (int i = 0; i < entities.size; i++)
			entities.get(i).dispose();
		entities.clear();

		for (Constructor<T> constructor : constructors.values())
			constructor.dispose();
		constructors.clear();

		models.clear();
	}
}