package com.games.toufoulati.screens;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.games.toufoulati.GameLauncher;

public class MainMenuScreen implements Screen {
    private Game game;
    private Stage stage;
    private Skin skin;
    private Texture backgroundTexture;

    public MainMenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);


        // Background image
        backgroundTexture = new Texture(Gdx.files.internal("backgrounds/main_menu.png"));
        Image background = new Image(backgroundTexture);
        background.setFillParent(true);
        stage.addActor(background);

        //UI skin
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        // Game Title
        Label title = new Label("Toufoulati", skin);
        title.setFontScale(2);

        // Start Button
        TextButton startButton = new TextButton("Commencer", skin);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new CharacterSelectionScreen(game));
            }
        });

        // UI Layout table
        Table table = new Table();
        table.setFillParent(true);
        table.add(title).padBottom(30).row();
        table.add(startButton).width(200).height(50);
        stage.addActor(table);


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
        skin.dispose();
    }
}
