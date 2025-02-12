package com.games.toufoulati;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.games.toufoulati.screens.MainMenuScreen;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class GameLauncher extends Game {
    private SpriteBatch batch;
    private BitmapFont font;
    //private Texture image;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(2);
        this.setScreen(new MainMenuScreen(this));
        //image = new Texture("libgdx.png");

    }
    public BitmapFont getFont(){
        return font;
    }

    public SpriteBatch getBatch(){
        return batch;
    }

    @Override
    public void render() {
        if (this.getScreen() == null) {
            this.setScreen(new MainMenuScreen(this));
        }
        super.render();
        //ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        //batch.begin();
        //batch.draw(image, 140, 210);
        //batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        //image.dispose();
    }
}


