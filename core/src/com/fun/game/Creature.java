package com.fun.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public abstract class Creature {
    public Rectangle boundingBox;

    public TextureAtlas animationAtlas;
    public Animation<TextureRegion> animation;

    public float x;
    public float y;

    public Creature(float x, float y) {
        this.x = x;
        this.y = y;

        this.boundingBox = new Rectangle(this.x, this.y, 1, 1);
    }

}