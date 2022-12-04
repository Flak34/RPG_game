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

public class Hero extends Unit {

    public Hero(float x, float y, Stage s)
    {
        super(x,y,s);
        isAttacking = false;

        //загрузка анимации шага
        load_walk_animation("hero_walking");

        //загрузка анимации атаки
        load_attack_animation("hero_attacking");


        setAnimation(walking_south);
        facingAngle = 270;
        setBoundaryPolygon(8);
        setAcceleration(400);
        setMaxSpeed(100);
        setDeceleration(400);

        scaleBy(1f);

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



}
