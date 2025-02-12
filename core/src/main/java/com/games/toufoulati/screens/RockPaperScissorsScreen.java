package com.games.toufoulati.screens;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class RockPaperScissorsScreen implements Screen {
    private Game game;
    private SpriteBatch batch;
    private Texture botRock, playerRock, botPaper, playerPaper, botScissors, playerScissors;
    private Texture spriteSheetHand, sprideSheetHand2;
    private Random random;
    private Audio audio;

    public RockPaperScissorsScreen(Game game) {
        this.game = game;
        random = new Random();


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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
