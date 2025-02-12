package com.games.toufoulati.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.games.toufoulati.utils.PlayerData;

public class GameMenuScreen implements Screen {
    private Game game;
    private Stage stage;
    private Skin skin;
    private Label hpLabel;

    public GameMenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        // Game Menu
        Label title = new Label("Menu", skin);
        title.setFontScale(2);

        Label GameOver = new Label("", skin);

        // Display HP
        hpLabel = new Label("" , skin);
        updateHPLabel();

        // Buttons for mini-games
        TextButton game1Button = new TextButton("Tchila", skin);
        TextButton game2Button = new TextButton("Lhadja l3amia", skin);
        TextButton game3Button = new TextButton("Game 3", skin);
        TextButton restButton = new TextButton("Repos", skin);

        // Button Listeners
        game1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(PlayerData.getPlayerHP()>10){
                    game.setScreen(new TchilaGameScreen(game));
                } else {
                    GameOver.setText("NO HP");
                }

            }
        });

        game2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                reduceHP(10);
                updateHPLabel();
                if(PlayerData.getPlayerHP()==0){
                    GameOver.setText("Game Over");
                }
            }
        });

        game3Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                reduceHP(10);
                updateHPLabel();
                if(PlayerData.getPlayerHP()==0){
                    GameOver.setText("Game Over");
                }
            }
        });

        restButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                restoreHP();
                updateHPLabel();
                if(PlayerData.getPlayerHP()==0){
                    GameOver.setText("Game Over");
                }
            }
        });

        // Table Layout
        Table table = new Table();
        table.setFillParent(true);
        table.add(title).padBottom(20).row();
        table.add(hpLabel).padBottom(20).row();
        table.add(GameOver).padBottom(20).row();
        table.add(game1Button).width(200).height(50).padBottom(10).row();
        table.add(game2Button).width(200).height(50).padBottom(10).row();
        table.add(game3Button).width(200).height(50).padBottom(10).row();
        table.add(restButton).width(200).height(50).padBottom(10).row();

        stage.addActor(table);



    }
    private void reduceHP(int amount) {
        int newHP = PlayerData.getPlayerHP() - amount;
        if (newHP < 0) newHP = 0;
        PlayerData.setPlayerHP(newHP);
        hpLabel.setText("HP: " + newHP);
    }

    private void noHP(){
        if(PlayerData.getPlayerHP() == 0) hpLabel.setText("you're done");
    }

    private void restoreHP() {
        PlayerData.setPlayerHP(100);
        hpLabel.setText("HP: 100");
    }

    private void updateHPLabel(){
        hpLabel.setText("HP: " + PlayerData.getPlayerHP());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        updateHPLabel();
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
    }
}



