package org.shevchenko.hunted.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class HUD {
    private Stage stage;
    private Button play;
    private Label l;

    public HUD() {
        stage = new Stage();
        //	Gdx.input.setInputProcessor(stage);
        //	initButtons();
        initLabel();
    }

    @SuppressWarnings("unused")
    private void initButtons() {
        Texture actor = new Texture(Gdx.files.internal("gfx/blue-button-png-hi.png"));
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(actor));
        play = new Button(buttonStyle);
        stage.addActor(play);

        play.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();

            }
        });
    }

    private void initLabel() {
        BitmapFont font = new
                BitmapFont(Gdx.files.internal("ui/skin.fnt"));
        LabelStyle style = new LabelStyle(font, Color.RED);
        l = new Label(null, style);
        l.setPosition(0, Gdx.graphics.getHeight() * 5 / 6);
        stage.addActor(l);
    }

    public void drawHUD() {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        l.setText("FPS: " + String.valueOf(Gdx.graphics.getFramesPerSecond()));
    }

    public void resizeHUD(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

}
