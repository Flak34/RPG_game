package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.framework.BaseActor;

public abstract class Unit extends BaseActor {
    Animation walking_north;
    Animation walking_south;
    Animation walking_east;
    Animation walking_west;
    Animation attack_east;
    Animation attack_west;
    Animation attack_north;
    Animation attack_south;
    float facingAngle;
    boolean isAttacking;

    public Unit(float x, float y, Stage s) {
        super(x, y, s);
    }

    protected void load_walk_animation(String mainPath) {
        float frameDuration = 0.08f;
        Array<TextureRegion> textureArray = new Array<TextureRegion>(true, 8, TextureRegion.class);
        //для востока
        for(int i = 0; i < 8; i++) {
            TextureRegion textureRegion = new TextureRegion();
            Texture texture = new Texture(Gdx.files.internal("assets/" + mainPath + "/walking_e" + i + ".png"));
            textureRegion.setRegion(texture);
            textureArray.add(textureRegion);
        }
        walking_east = new Animation(frameDuration, textureArray, Animation.PlayMode.LOOP);
        textureArray.clear();
        //для запада
        for(int i = 0; i < 8; i++) {
            TextureRegion textureRegion = new TextureRegion();
            Texture texture = new Texture(Gdx.files.internal("assets/" + mainPath + "/walking_w" + i + ".png"));
            textureRegion.setRegion(texture);
            textureArray.add(textureRegion);
        }
        walking_west = new Animation(frameDuration, textureArray, Animation.PlayMode.LOOP);
        textureArray.clear();
        //для севера
        for(int i = 0; i < 8; i++) {
            TextureRegion textureRegion = new TextureRegion();
            Texture texture = new Texture(Gdx.files.internal("assets/" + mainPath + "/walking_n" + i + ".png"));
            textureRegion.setRegion(texture);
            textureArray.add(textureRegion);
        }
        walking_north = new Animation(frameDuration, textureArray, Animation.PlayMode.LOOP);
        textureArray.clear();
        //для юга
        for(int i = 0; i < 8; i++) {
            TextureRegion textureRegion = new TextureRegion();
            Texture texture = new Texture(Gdx.files.internal("assets/" + mainPath + "/walking_s" + i + ".png"));
            textureRegion.setRegion(texture);
            textureArray.add(textureRegion);
        }
        walking_south = new Animation(frameDuration, textureArray, Animation.PlayMode.LOOP);
        textureArray.clear();
    }

    protected void load_attack_animation(String mainPath) {
        float frameDuration = 0.04f;
        Array<TextureRegion> textureArray = new Array<TextureRegion>(true, 12, TextureRegion.class);
        //востока
        for(int i = 0; i < 12; i++) {
            TextureRegion textureRegion = new TextureRegion();
            Texture texture = new Texture(Gdx.files.internal("assets/" + mainPath + "/attack_e" + i + ".png"));
            textureRegion.setRegion(texture);
            textureArray.add(textureRegion);
        }
        attack_east = new Animation(frameDuration, textureArray, Animation.PlayMode.NORMAL);
        textureArray.clear();
        //для запада
        for(int i = 0; i < 12; i++) {
            TextureRegion textureRegion = new TextureRegion();
            Texture texture = new Texture(Gdx.files.internal("assets/" + mainPath + "/attack_w" + i + ".png"));
            textureRegion.setRegion(texture);
            textureArray.add(textureRegion);
        }
        attack_west = new Animation(frameDuration, textureArray, Animation.PlayMode.NORMAL);
        textureArray.clear();
        //для севера
        for(int i = 0; i < 12; i++) {
            TextureRegion textureRegion = new TextureRegion();
            Texture texture = new Texture(Gdx.files.internal("assets/" + mainPath + "/attack_n" + i + ".png"));
            textureRegion.setRegion(texture);
            textureArray.add(textureRegion);
        }
        attack_north = new Animation(frameDuration, textureArray, Animation.PlayMode.NORMAL);
        textureArray.clear();
        //для юга
        for(int i = 0; i < 12; i++) {
            TextureRegion textureRegion = new TextureRegion();
            Texture texture = new Texture(Gdx.files.internal("assets/" + mainPath + "/attack_s" + i + ".png"));
            textureRegion.setRegion(texture);
            textureArray.add(textureRegion);
        }
        attack_south = new Animation(frameDuration, textureArray, Animation.PlayMode.NORMAL);
        textureArray.clear();
    }

    public void attack() {
        setSpeed(0);
        setAnimationPaused(true);
        isAttacking = true;
    }

    public float getFacingAngle()
    {
        return facingAngle;
    }

    public void setFacingAngle(float facingAngle) {
        this.facingAngle = facingAngle;
    }

    public float getCenterX() {
        return getX() + getWidth() / 2;
    }

    public float getCenterY() {
        return getY() + getHeight() / 2;
    }

}
