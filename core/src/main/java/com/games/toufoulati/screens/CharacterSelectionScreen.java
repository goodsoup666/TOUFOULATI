package com.games.toufoulati.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.games.toufoulati.utils.PlayerData;


public class CharacterSelectionScreen implements Screen {
    private Game game;
    private Stage stage;
    private Skin skin;
    private Texture maleTexture, femaleTexture;
    private Image selectedCharacter;
    private String selectedGender = "Fille";
    private TextField nameInput;

    public CharacterSelectionScreen(Game game) {
        this.game = game;
    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        // Character textures
        femaleTexture = new Texture("characters/female.png");
        maleTexture = new Texture("characters/male.png");

        // Character preview
        selectedCharacter = new Image(femaleTexture);

        // Name input
        nameInput = new TextField("", skin);
        nameInput.setMessageText("Nom d'utilisateur");

        // Gender selection
        TextButton femaleButton = new TextButton("Fille", skin);
        TextButton maleButton = new TextButton("Garçon", skin);

        femaleButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedGender = "Fille";
                selectedCharacter.setDrawable(new Image(femaleTexture).getDrawable());
            }
        });
        maleButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedGender = "Garçon";
                selectedCharacter.setDrawable(new Image(maleTexture).getDrawable());
            }
        });

        // Save choice and continue
        TextButton confirmButton = new TextButton("Je confirme", skin);
        confirmButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String playerName = nameInput.getText();
                if(playerName.isEmpty()) {
                    return;
                }
                PlayerData.setPlayerName(playerName);
                PlayerData.setPlayerGender(selectedGender);

                game.setScreen(new GameMenuScreen(game));

            }
        });

        // UI Layout table
        Table table = new Table();
        table.setFillParent(true);
        table.add(new Label("Choisis ton personnage", skin)).padBottom(10).row();
        table.add(selectedCharacter).size(150,150).padBottom(10).row();
        table.add(nameInput).width(250).padBottom(10).row();
        table.add(maleButton).pad(5);
        table.add(femaleButton).pad(5).row();
        table.add(confirmButton).width(200).height(50).padTop(20);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

    }

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
        skin.dispose();
        maleTexture.dispose();
        femaleTexture.dispose();

    }
}
