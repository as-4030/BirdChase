package com.fun.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Dog extends Creature {
    public boolean goingLeft = false;
    public boolean goingRight = false;
    public boolean goingUp = false;
    public boolean goingDown = false;

    public boolean lookingRight = true;
    public boolean standingStill = true;

    public Dog(float dogX, float dogY) {
        super(dogX, dogY);

        this.animationAtlas = new TextureAtlas("JolteonPack.atlas");
        this.animation = new Animation<TextureRegion>((1/15f), this.animationAtlas.getRegions(), Animation.PlayMode.LOOP_REVERSED);
    }

}