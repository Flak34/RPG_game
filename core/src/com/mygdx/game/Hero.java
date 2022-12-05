package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class Hero extends BattleUnit {

    public Hero(float x, float y, Stage s)
    {
        super(x,y,s);

        //загрузка анимации шага
        load_walk_animation("hero_walking", 8, 0.08f);

        //загрузка анимации атаки
        load_attack_animation("hero_attacking", 12, 0.04f);


        setAnimation(walking_south);
        setFacingAngle(270);
        setBoundaryPolygon(8);
        setAcceleration(400);
        setMaxSpeed(100);
        setDeceleration(400);
        scaleBy(1f);
    }

    public void act(float dt)
    {
        super.act(dt);
        alignCamera();
        boundToWorld();
        applyPhysics(dt);
    }






}
