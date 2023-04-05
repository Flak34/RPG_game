package com.mygdx.game.animation_loaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;


import java.util.ArrayList;
import java.util.HashMap;

public abstract class AbstractAnimationLoader {

    public abstract HashMap<String, Animation> getWalkAnimations();


    public abstract HashMap<String, Animation> getAttackAnimations();


    protected abstract void loadWalkAnimations();
    protected abstract void loadAttackAnimations();

    protected Animation loadOneDirection(String path, int numberOfFrames, float frameDuration, boolean loop) {
        Array<TextureRegion> textureArray = new Array<TextureRegion>(true, numberOfFrames, TextureRegion.class);
        for(int i = 0; i < numberOfFrames; i++) {
            TextureRegion textureRegion = new TextureRegion();
            Texture texture = new Texture(Gdx.files.internal(path + i + ".png"));
            textureRegion.setRegion(texture);
            textureArray.add(textureRegion);
        }

        Animation.PlayMode playMode;
        if(loop)
            playMode = Animation.PlayMode.LOOP;
        else
            playMode = Animation.PlayMode.NORMAL;

        return new Animation(frameDuration, textureArray, playMode);
    }

}
