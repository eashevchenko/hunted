package org.shevchenko.hunted.generators;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import org.shevchenko.hunted.manager.AssetFactory;

public class Sky {

	private Model skyModel;
	private ModelInstance sky;

	public Sky() {
		initSky();
	}

	private void initSky() {
		skyModel = AssetFactory.getInstance().get(AssetFactory.ModelType.SKY, Model.class);
		sky = new ModelInstance(skyModel);
		sky.transform.setTranslation(Vector3.Zero);
		sky.transform.scl(0.5f, 0.5f, 0.5f);
		
	}
	
	public void drawSky(ModelBatch batch, float delta){
		if (sky != null) batch.render(sky);
		sky.transform.rotate(new Quaternion(Vector3.X,0.25f*delta));
	}

}
