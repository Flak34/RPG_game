package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.animation_loaders.AbstractAnimationLoader;


import java.util.List;

public class Sceleton extends BattleUnit {

    private List<Vector2> path;
    public int num;
    private int nodeIndex;
    private Vector2 startPoint;
    private boolean isReturningToTheStartPoint;
    private boolean isChasingTheHero;

    public Sceleton(float x, float y, Stage s, AbstractAnimationLoader animationLoader) {

        super(x,y,s, animationLoader);
        //load_walk_animation("sceleton_walking", 8, 0.08f);
        //load_attack_animation("sceleton_attacking", 10, 0.05f);
        load_death_animation("sceleton_dying", 9, 0.08f);

        startPoint = new Vector2(x + getWidth() / 2, y + getHeight() / 2);
        nodeIndex = 1;
        isReturningToTheStartPoint = false;
        isChasingTheHero = false;
        path = null;

        setAnimation(animationLoader.getWalkAnimations().get("walking_south"));
        setFacingAngle(270);
        setBoundaryPolygon(8);
        setAcceleration(400);
        setMaxSpeed(65);
        setDeceleration(400);

        HP = 30;

        scaleBy(0.8f);
    }

    @Override
    public void act(float dt) {

        super.act(dt);

        if(HP <= 0)
            return;


        if(path != null) {
            float pathAngle = new Vector2(path.get(nodeIndex).x - getPathCoordinates().x, path.get(nodeIndex).y - getPathCoordinates().y).angleDeg();
            accelerateAtAngle(pathAngle);
            applyPhysics(dt);
            boundToWorld();
            float currentDst = new Vector2(getPathCoordinates().x, getPathCoordinates().y).dst(path.get(nodeIndex));

            if (currentDst < Math.abs(getSpeed() * dt - currentDst) && nodeIndex < path.size() - 1) {
                nodeIndex++;
            }
            else if(currentDst < Math.abs(getSpeed() * dt - currentDst) && nodeIndex == path.size() - 1) {
                path = null;
            }
        }

    }

    public void setPath(List<Vector2> newPath) {
        if(newPath != null) {
            this.path = newPath;
            nodeIndex = 1;
        }
    }

    public void printPath() {
        if(path != null) {

            for (int i = 0; i < path.size(); i++) {
                System.out.print(path.get(i));
            }

        }
    }


    public Vector2 getStartPoint() {
        return startPoint;
    }

    public void returnToTheStartPoint(List<Vector2> path) {
        isChasingTheHero = false;
        isReturningToTheStartPoint = true;
        if(path != null)
            this.path = path;
        nodeIndex = 1;
    }

    public void chaseTheHero(List<Vector2> path) {
            isChasingTheHero = true;
            isReturningToTheStartPoint = false;
            if(path != null)
                this.path = path;
            nodeIndex = 1;
    }

    public boolean getIsChasingTheHero() {
        return isChasingTheHero;
    }

    public boolean getIsReturningToTheStartPoint() {
        return isReturningToTheStartPoint;
    }

}
