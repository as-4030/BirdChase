package com.fun.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Dog {
    public TextureAtlas dogAtlas;
    public Animation<TextureRegion> dogAnimation;

    public float dogX;
    public float dogY;

    public boolean goingLeft = false;
    public boolean goingRight = false;
    public boolean goingUp = false;
    public boolean goingDown = false;

    public boolean lookingRight = true;

    public boolean standingStill = true;

    public Dog(float dogX, float dogY) {
        this.dogAtlas = new TextureAtlas("JolteonPack.atlas");
        this.dogAnimation = new Animation<TextureRegion>((1/15f), this.dogAtlas.getRegions(), Animation.PlayMode.LOOP_REVERSED);

        this.dogX = dogX;
        this.dogY = dogY;
    }

}