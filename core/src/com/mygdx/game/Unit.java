package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
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
    private boolean isAttacking;

    protected float HP;
    protected float MaxHP;

    private HealthBar healthBar;
    private float healthBarLength;

    private float damage;

    public Unit(float x, float y, Stage s) {
        super(x, y, s);

        MaxHP = 100;
        HP = MaxHP;
        healthBarLength = 50;

        healthBar = new HealthBar();
        addActor(healthBar);
        healthBar.setWidth(HP / MaxHP * healthBarLength);
        healthBar.setPosition(healthBar.getWidth() / 2, 83);

        damage = 30;
    }


    @Override
    public void act(float dt) {
        super.act(dt);

        if ( getSpeed() == 0 && !isAttacking) {
            setAnimationPaused(true);
        }
        else
        {
            setAnimationPaused(false);

            if(isAttacking) {
                setSpeed(0);
            }

            if (getFacingAngle() == 90)
            {
                if(isAttacking) {
                    setAnimation(attack_north);
                }
                else {
                    setAnimation(walking_north);
                }
            }
            else if (getFacingAngle() == 180)
            {
                if(isAttacking) {
                    setAnimation(attack_west);
                }
                else
                    setAnimation(walking_west);
            }
            else if (getFacingAngle() == 270)
            {
                if(isAttacking) {
                    setAnimation(attack_south);
                }
                else
                    setAnimation(walking_south);
            }
            else
            {
                if(isAttacking) {
                    setAnimation(attack_east);
                }
                else
                    setAnimation(walking_east);
            }

            if(isAttacking && isAnimationFinished()) {
                isAttacking = false;
            }

        }


        healthBar.setWidth(HP / MaxHP * healthBarLength);
        healthBar.setPosition(getWidth()/ 2 - healthBar.getWidth() / 2, 83);
    }

    protected void load_walk_animation(String mainPath, int numOfFiles, float frameDuration) {
        Array<TextureRegion> textureArray = new Array<TextureRegion>(true, numOfFiles, TextureRegion.class);
        //для востока
        for(int i = 0; i < numOfFiles; i++) {
            TextureRegion textureRegion = new TextureRegion();
            Texture texture = new Texture(Gdx.files.internal("assets/" + mainPath + "/walking_e" + i + ".png"));
            textureRegion.setRegion(texture);
            textureArray.add(textureRegion);
        }
        walking_east = new Animation(frameDuration, textureArray, Animation.PlayMode.LOOP);
        textureArray.clear();
        //для запада
        for(int i = 0; i < numOfFiles; i++) {
            TextureRegion textureRegion = new TextureRegion();
            Texture texture = new Texture(Gdx.files.internal("assets/" + mainPath + "/walking_w" + i + ".png"));
            textureRegion.setRegion(texture);
            textureArray.add(textureRegion);
        }
        walking_west = new Animation(frameDuration, textureArray, Animation.PlayMode.LOOP);
        textureArray.clear();
        //для севера
        for(int i = 0; i < numOfFiles; i++) {
            TextureRegion textureRegion = new TextureRegion();
            Texture texture = new Texture(Gdx.files.internal("assets/" + mainPath + "/walking_n" + i + ".png"));
            textureRegion.setRegion(texture);
            textureArray.add(textureRegion);
        }
        walking_north = new Animation(frameDuration, textureArray, Animation.PlayMode.LOOP);
        textureArray.clear();
        //для юга
        for(int i = 0; i < numOfFiles; i++) {
            TextureRegion textureRegion = new TextureRegion();
            Texture texture = new Texture(Gdx.files.internal("assets/" + mainPath + "/walking_s" + i + ".png"));
            textureRegion.setRegion(texture);
            textureArray.add(textureRegion);
        }
        walking_south = new Animation(frameDuration, textureArray, Animation.PlayMode.LOOP);
        textureArray.clear();
    }

    protected void load_attack_animation(String mainPath, int numOfFiles, float frameDuration) {

        Array<TextureRegion> textureArray = new Array<TextureRegion>(true, numOfFiles, TextureRegion.class);
        //востока
        for(int i = 0; i < numOfFiles; i++) {
            TextureRegion textureRegion = new TextureRegion();
            Texture texture = new Texture(Gdx.files.internal("assets/" + mainPath + "/attack_e" + i + ".png"));
            textureRegion.setRegion(texture);
            textureArray.add(textureRegion);
        }
        attack_east = new Animation(frameDuration, textureArray, Animation.PlayMode.NORMAL);
        textureArray.clear();
        //для запада
        for(int i = 0; i < numOfFiles; i++) {
            TextureRegion textureRegion = new TextureRegion();
            Texture texture = new Texture(Gdx.files.internal("assets/" + mainPath + "/attack_w" + i + ".png"));
            textureRegion.setRegion(texture);
            textureArray.add(textureRegion);
        }
        attack_west = new Animation(frameDuration, textureArray, Animation.PlayMode.NORMAL);
        textureArray.clear();
        //для севера
        for(int i = 0; i < numOfFiles; i++) {
            TextureRegion textureRegion = new TextureRegion();
            Texture texture = new Texture(Gdx.files.internal("assets/" + mainPath + "/attack_n" + i + ".png"));
            textureRegion.setRegion(texture);
            textureArray.add(textureRegion);
        }
        attack_north = new Animation(frameDuration, textureArray, Animation.PlayMode.NORMAL);
        textureArray.clear();
        //для юга
        for(int i = 0; i < numOfFiles; i++) {
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


    public Vector2 getPathCoordinates() {
        return new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 4);
    }

    @Override
    public void setBoundaryPolygon(int numSides) {
        float w = getWidth();
        float h = getHeight();

        float[] vertices = new float[2*numSides];
        for (int i = 0; i < numSides; i++)
        {
            float angle = i * 6.28f / numSides;
            // x-coordinate
            vertices[2*i] = ((w * 0.2f)/2 * MathUtils.cos(angle)) + w/2;
            // y-coordinate
            vertices[2*i+1] = ((h * 0.3f))/2 * MathUtils.sin(angle) + h/3;
        }
        setBoundaryPolygon(new Polygon(vertices));
    }

    public void takeDamage(float amount) {
        HP -= amount;
    }

    public void setHealthBarLength(float length) {
        healthBarLength = length;
    }

    public boolean getIsAttacking() {
        return isAttacking;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }
}
