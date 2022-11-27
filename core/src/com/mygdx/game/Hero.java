package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.framework.BaseActor;

public class Hero extends BaseActor implements Attackable {
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
    public Hero(float x, float y, Stage s)
    {

        super(x,y,s);
        isAttacking = false;

        //загрузка анимации шага
        load_walk_animation();

        //загрузка анимации атаки
        load_attack_animation();


        setAnimation(walking_south);
        facingAngle = 270;
        setBoundaryPolygon(8);
        setAcceleration(400);
        setMaxSpeed(100);
        setDeceleration(400);

        scaleBy(1.2f);
    }

    public void act(float dt)
    {
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

            if (facingAngle == 90)
            {
                if(isAttacking) {
                    setAnimation(attack_north);
                }
                else {
                    setAnimation(walking_north);
                }
            }
            else if (facingAngle == 180)
            {
                if(isAttacking) {
                    setAnimation(attack_west);
                }
                else
                    setAnimation(walking_west);
            }
            else if (facingAngle == 270)
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

        alignCamera();
        boundToWorld();
        applyPhysics(dt);
    }

    public void attack() {
        setSpeed(0);
        setAnimationPaused(true);
        isAttacking = true;
    }

    private void load_walk_animation() {
        float frameDuration = 0.08f;
        Array<TextureRegion> textureArray = new Array<TextureRegion>(true, 8, TextureRegion.class);
        //востока
        for(int i = 0; i < 8; i++) {
            TextureRegion textureRegion = new TextureRegion();
            Texture texture = new Texture(Gdx.files.internal("assets/walking_east/walking_e" + i + ".png"));
            textureRegion.setRegion(texture);
            textureArray.add(textureRegion);
        }
        walking_east = new Animation(frameDuration, textureArray, Animation.PlayMode.LOOP);
        textureArray.clear();
        //для запада
        for(int i = 0; i < 8; i++) {
            TextureRegion textureRegion = new TextureRegion();
            Texture texture = new Texture(Gdx.files.internal("assets/walking_west/walking_w" + i + ".png"));
            textureRegion.setRegion(texture);
            textureArray.add(textureRegion);
        }
        walking_west = new Animation(frameDuration, textureArray, Animation.PlayMode.LOOP);
        textureArray.clear();
        //для севера
        for(int i = 0; i < 8; i++) {
            TextureRegion textureRegion = new TextureRegion();
            Texture texture = new Texture(Gdx.files.internal("assets/walking_north/walking_n" + i + ".png"));
            textureRegion.setRegion(texture);
            textureArray.add(textureRegion);
        }
        walking_north = new Animation(frameDuration, textureArray, Animation.PlayMode.LOOP);
        textureArray.clear();
        //для юга
        for(int i = 0; i < 8; i++) {
            TextureRegion textureRegion = new TextureRegion();
            Texture texture = new Texture(Gdx.files.internal("assets/walking_south/walking_s" + i + ".png"));
            textureRegion.setRegion(texture);
            textureArray.add(textureRegion);
        }
        walking_south = new Animation(frameDuration, textureArray, Animation.PlayMode.LOOP);
        textureArray.clear();
    }

    private void load_attack_animation() {
        float frameDuration = 0.04f;
        Array<TextureRegion> textureArray = new Array<TextureRegion>(true, 12, TextureRegion.class);
        //востока
        for(int i = 0; i < 12; i++) {
            TextureRegion textureRegion = new TextureRegion();
            Texture texture = new Texture(Gdx.files.internal("assets/attack_east/attack_e" + i + ".png"));
            textureRegion.setRegion(texture);
            textureArray.add(textureRegion);
        }
        attack_east = new Animation(frameDuration, textureArray, Animation.PlayMode.NORMAL);
        textureArray.clear();
        //для запада
        for(int i = 0; i < 12; i++) {
            TextureRegion textureRegion = new TextureRegion();
            Texture texture = new Texture(Gdx.files.internal("assets/attack_west/attack_w" + i + ".png"));
            textureRegion.setRegion(texture);
            textureArray.add(textureRegion);
        }
        attack_west = new Animation(frameDuration, textureArray, Animation.PlayMode.NORMAL);
        textureArray.clear();
        //для севера
        for(int i = 0; i < 12; i++) {
            TextureRegion textureRegion = new TextureRegion();
            Texture texture = new Texture(Gdx.files.internal("assets/attack_north/attack_n" + i + ".png"));
            textureRegion.setRegion(texture);
            textureArray.add(textureRegion);
        }
        attack_north = new Animation(frameDuration, textureArray, Animation.PlayMode.NORMAL);
        textureArray.clear();
        //для юга
        for(int i = 0; i < 12; i++) {
            TextureRegion textureRegion = new TextureRegion();
            Texture texture = new Texture(Gdx.files.internal("assets/attack_south/attack_s" + i + ".png"));
            textureRegion.setRegion(texture);
            textureArray.add(textureRegion);
        }
        attack_south = new Animation(frameDuration, textureArray, Animation.PlayMode.NORMAL);
        textureArray.clear();
    }

    public float getFacingAngle()
    {
        return facingAngle;
    }

    public void setFacingAngle(float facingAngle) {
        this.facingAngle = facingAngle;
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

    public float getCenterX() {
        return getX() + getWidth() / 2;
    }

    public float getCenterY() {
        return getY() + getHeight() / 2;
    }

}
