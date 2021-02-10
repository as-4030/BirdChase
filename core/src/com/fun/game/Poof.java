package com.fun.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Poof { // https://drive.google.com/drive/folders/1x3WjvzWSsGQHxEIRRtqeZK3YpTKszM4C
    public TextureAtlas spawnAtlas;
    public Animation<TextureRegion> spawnAnimation;

    public float poofTimer = 0;

    public float x;
    public float y;

    public Poof(float x, float y) {
        this.spawnAtlas = new TextureAtlas("PoofPack.atlas");
        this.spawnAnimation = new Animation<TextureRegion>((1/15f), this.spawnAtlas.getRegions(), Animation.PlayMode.NORMAL);
        this.x = x;
        this.y = y;
    }

    public void update(float deltaTime) {
        this.poofTimer += deltaTime;
    }

    public boolean isFinished() {
        return this.spawnAnimation.isAnimationFinished(this.poofTimer);
    }

    public void draw (SpriteBatch batch) {
        batch.draw(this.spawnAnimation.getKeyFrame(poofTimer),
                this.x,
                this.y,
                2,
                1.5f);
    }

}