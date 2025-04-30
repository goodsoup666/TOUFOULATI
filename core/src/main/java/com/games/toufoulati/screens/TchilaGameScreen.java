package com.games.toufoulati.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.audio.AudioRecorder;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.games.toufoulati.GameLauncher;
import com.games.toufoulati.objects.TchilaAI;
import com.games.toufoulati.utils.PlayerData;





public class TchilaGameScreen implements Screen {
    private Game game;
    private SpriteBatch batch;
    private Texture playerTexture, aiTexture, backgroundTexture;
    private Vector2 playerPosition;
    private Array<TchilaAI> aiPlayers;
    private float gameTime = 30;



    public TchilaGameScreen(Game game){
        this.game = game;
        this.batch = new SpriteBatch();
        this.playerTexture = new Texture("assets/characters/player.png");
        this.aiTexture = new Texture("assets/characters/ai.png");
        this.backgroundTexture = new Texture("assets/backgrounds/tchila_playground.png");
        this.playerPosition = new Vector2(100, 100);

        aiPlayers = new Array<>();
        aiPlayers.add(new TchilaAI("assets/characters/ai.png", 300, 200));
        aiPlayers.add(new TchilaAI("assets/characters/ai.png", 400,  150));

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        gameTime -= delta; // -- time

        // Losing when time runs out
        if(gameTime <= 0){
            reduceHP(10);
            game.setScreen(new GameOverScreen(game, false));
            return;
        }
        // Collision
        checkTag();

        handleInput();
        for (TchilaAI ai : aiPlayers) ai.update(delta);

        // Render
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(playerTexture, playerPosition.x, playerPosition.y); // Placeholder position
        for (TchilaAI ai : aiPlayers) batch.draw(ai.getTexture(), ai.getPosition().x, ai.getPosition().y);
        ((GameLauncher)game).getFont().draw(batch, "Temps restant: " + (int) gameTime, 20, 460); // Timer
        batch.end();

    }

    private void handleInput(){
        float speed = 200 * Gdx.graphics.getDeltaTime();
        if ((Gdx.input.isKeyPressed(Input.Keys.W)) || Gdx.input.isKeyPressed(Input.Keys.UP)) playerPosition.y += speed; // Up
        if ((Gdx.input.isKeyPressed(Input.Keys.S)) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) playerPosition.y -= speed; // Down
        if ((Gdx.input.isKeyPressed(Input.Keys.A)) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) playerPosition.x -= speed; // Left
        if ((Gdx.input.isKeyPressed(Input.Keys.D)) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) playerPosition.x += speed; // Right

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        playerPosition.x = Math.max(0, Math.min(playerPosition.x, screenWidth - playerTexture.getWidth()));
        playerPosition.y = Math.max(0, Math.min(playerPosition.y, screenHeight - playerTexture.getHeight()));

    }
    private void reduceHP(int amount) {
        int newHP = PlayerData.getPlayerHP() - amount;
        if (newHP < 0) newHP = 0;
        PlayerData.setPlayerHP(newHP);
    }

    private void checkTag(){
        for (TchilaAI ai : aiPlayers){
            if(playerPosition.dst(ai.getPosition()) < 30) {
                aiPlayers.removeValue(ai, true);

                // if no more ai left player wins
                if(aiPlayers.size == 0){
                    reduceHP(10);
                    game.setScreen(new GameOverScreen(game, true));
                }
                break;
            }
        }
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
        batch.dispose();
        playerTexture.dispose();
        aiTexture.dispose();
        backgroundTexture.dispose();

    }
}



