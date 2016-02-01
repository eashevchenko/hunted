package org.shevchenko.hunted.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FillViewport;
import org.shevchenko.hunted.manager.AssetFactory;
import org.shevchenko.hunted.utils.Params;

public class LoadingScreen implements Screen {
	private BitmapFont loading;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Texture img;
	private FillViewport viewport;
	private ShapeRenderer shapeRenderer;

    private float progress = 0;

	@Override
	public void show() {
		 shapeRenderer = new ShapeRenderer();
		AssetFactory.getInstance().load();
		loading = new BitmapFont(Gdx.files.internal("ui/skin.fnt"), false);
	    batch   =  new SpriteBatch();
	    img = new Texture("gfx/OpenGL_ES-3D.png");	    
	   
        camera = new OrthographicCamera();
        viewport = new FillViewport(Params.worldWidth,Params.worldHeight,camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
	}
	
	private void draw() {
	       shapeRenderer.setProjectionMatrix(camera.projection);
	       shapeRenderer.setTransformMatrix(camera.view);
	       shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
	       shapeRenderer.setColor(Color.BLACK);
	       shapeRenderer.rect(0,0,progress *Params.worldWidth, Params.worldHeight/45);
	    	        
	    	       shapeRenderer.end();
	    	     }

	@Override
	public void render(float delta) {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
        
        
        batch.begin();
        batch.draw(img,0,0,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        loading.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        loading.draw(batch, "Loading ... " + progress*100 + " %",Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/7);
        batch.end();
      
        draw();
        if(AssetFactory.getInstance().update()){
 	 	((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
 	//if(progress*100 == 75)
   //     	AssetFactory.getInstance().finishLoading();
      } else {
          progress = AssetFactory.getInstance().getProgress();
        }
	}

	@Override
	public void resize(int width, int height) {
		 viewport.update(width,height,true);		
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
		// TODO Auto-generated method stub
		batch.dispose();
		loading.dispose();
		img.dispose();
	}

}
