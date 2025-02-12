package com.games.toufoulati.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Random;

public class TchilaAI {

    private Texture texture;
    private Vector2 position;
    private Vector2 velocity;
    private long lastMoveTime;
    private Random random;

    public TchilaAI(String texturePath, float startX, float startY){
        this.texture = new Texture(texturePath);
        this.position = new Vector2(startX, startY);
        this.velocity = new Vector2(0, 0);
        this.random = new Random();
        this.lastMoveTime = TimeUtils.nanoTime();
    }
    public void update(float deltaTime){
        if (TimeUtils.nanoTime() - lastMoveTime > 1_000_000_000){ // Change direction every second
            velocity.x = (random.nextBoolean() ? 1 : -1) * 100;
            velocity.y = (random.nextBoolean() ? 1 : -1) * 100;
            lastMoveTime = TimeUtils.nanoTime();
        }
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        // Keep AI inside boundaries
        if (position.x < 0) position.x = 0;
        if (position.x > Gdx.graphics.getWidth() - texture.getWidth()) position.x = Gdx.graphics.getWidth() - texture.getWidth();
        if (position.y < 0) position.y = 0;
        if (position.y > Gdx.graphics.getHeight() - texture.getHeight()) position.y = Gdx.graphics.getHeight() - texture.getHeight();
    }
    public Texture getTexture(){
        return texture;
    }

    public Vector2 getPosition(){
        return position;
    }
    public Rectangle getBoundingRectangle(){
        return new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
    }
}



