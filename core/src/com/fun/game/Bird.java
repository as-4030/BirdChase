package com.fun.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Bird extends Creature {
    public boolean lookingRight = false;

    public Bird(float x, float y) {
        super(x, y);

        this.animationAtlas = new TextureAtlas("TalonflamePack.atlas");
        this.animation = new Animation<TextureRegion>((1/15f), this.animationAtlas.getRegions());

        this.boundingBox = new Rectangle(this.x - 0.4f, this.y + 0.45f, 2, 1.5f);
    }

}