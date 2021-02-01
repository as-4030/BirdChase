package com.fun.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Bird extends Creature {

    public Bird(float x, float y) {
        super(x, y);

        this.animationAtlas = new TextureAtlas("TalonflamePack.atlas");
        this.animation = new Animation<TextureRegion>((1/15f), this.animationAtlas.getRegions());
    }
}