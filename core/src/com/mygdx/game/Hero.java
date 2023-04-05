package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.animation_loaders.AbstractAnimationLoader;

public class Hero extends BattleUnit {

    public Hero(float x, float y, Stage s, AbstractAnimationLoader animationLoader)
    {
        super(x,y,s, animationLoader);

        //load_walk_animation("hero_walking", 8, 0.08f);
        //load_attack_animation("hero_attacking", 12, 0.04f);
        load_death_animation("hero_dying", 12, 0.07f);

        setAnimation(animationLoader.getWalkAnimations().get("walking_south"));
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

        if(HP <= 0)
            return;

        alignCamera();
        boundToWorld();
        applyPhysics(dt);

    }






}
