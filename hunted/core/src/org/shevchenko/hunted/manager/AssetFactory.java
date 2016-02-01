package org.shevchenko.hunted.manager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.utils.Array;

public class AssetFactory {
    public static class ModelType {
        public static final String CUBE = "models/test.obj";
        public static final String HUMAN = "models/lowpoly_character_final.g3dj";
        public static final String LAND = "models/land.g3dj";
        public static final String SKY = "models/skydome.g3db";
    }

    private static AssetFactory instance;

    private AssetManager assets;

    private AssetFactory() {
        assets = new AssetManager();
    }

    public <T> T get(String fileName, Class<T> type) {
        return assets.get(fileName, type);
    }

    public boolean update() {
        return assets.update();
    }

    public boolean isLoaded(String fileName, Class<?> type) {
        return assets.isLoaded(fileName, type);
    }

    public float getProgress() {
        return assets.getProgress();
    }

    public void dispose() {
        assets.dispose();
        instance = null;
    }

    public void load() {
        assets.load(ModelType.CUBE, Model.class);
        assets.load(ModelType.HUMAN, Model.class);
        assets.load(ModelType.LAND, Model.class);
        assets.load(ModelType.SKY, Model.class);

        //	assets.finishLoading();
    }

    public Array<String> getLoaded() {
        return assets.getAssetNames();
    }

    public void update(float millis) {
        assets.update((int) millis);
    }

    public int getLoadedAssets() {
        return assets.getLoadedAssets();
    }

    public void finishLoading() {
        assets.finishLoading();
    }

    public static AssetFactory getInstance() {
        if (instance == null) {
            instance = new AssetFactory();
        }
        return instance;
    }


}
