package com.games.toufoulati.screens;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class RockPaperScissorsScreen implements Screen {
    private Game game;
    private SpriteBatch batch;
    private Texture botRock, playerRock, botPaper, playerPaper, botScissors, playerScissors;
    private Texture background;
    private Texture spriteSheetHand, sprideSheetHand2;
    private Random random;
    private Audio audio;

    public RockPaperScissorsScreen(Game game) {
        this.game = game;
        random = new Random();
        this.batch = new SpriteBatch();
        this.botRock = new Texture("assets/hands/botRock.png");
        this.playerRock = new Texture("assets/hands/playerRock.png");
        this.botPaper = new Texture("assets/hands/botPaper.png");
        this.playerPaper = new Texture("assets/hands/playerPaper.png");
        this.botScissors = new Texture("assets/hands/botScissors.png");
        this.playerScissors = new Texture("assets/hands/playerScissors.png");
        this.background = new Texture("assets/backgrounds/rps_background.png");
        this.spriteSheetHand = new Texture("assets/spriteSheets/spriteSheetHand.png");
        this.sprideSheetHand2 = new Texture("assets/spriteSheets/spriteSheetHand2.png");
        this.random = new Random();



    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


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

    }
}
