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

        //загрузка анимации шага
        load_walk_animation("hero_walking", 8);

        //загрузка анимации атаки
        load_attack_animation("hero_attacking", 12);


        setAnimation(walking_south);
        facingAngle = 270;
        setBoundaryPolygon(8);
        setAcceleration(400);
        setMaxSpeed(100);
        setDeceleration(400);
        scaleBy(1f);

        MaxHP = 200;
        HP = MaxHP;

    }

    public void act(float dt)
    {
        super.act(dt);
        alignCamera();
        boundToWorld();
        applyPhysics(dt);
    }






}
