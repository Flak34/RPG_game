package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.framework.BaseActor;
import com.mygdx.game.gameai.gamepf.GameGraph;
import com.mygdx.game.gameai.gamepf.GameGraphPath;

import java.util.ArrayList;
import java.util.List;

public class Enemy extends Unit {

    private List<Vector2> path;
    public int num;
    private int nodeIndex;
    private Vector2 startPoint;
    private boolean isReturningToTheStartPoint;
    private boolean isChasingTheHero;


    public Enemy(float x, float y, Stage s) {

        super(x,y,s);
        load_walk_animation("sceleton_walking", 8);
        load_attack_animation("sceleton_attacking", 10);

        startPoint = new Vector2(x + getWidth() / 2, y + getHeight() / 2);
        nodeIndex = 1;
        isReturningToTheStartPoint = false;
        isChasingTheHero = false;
        path = null;

        setAnimation(walking_south);
        facingAngle = 270;
        setBoundaryPolygon(8);
        setAcceleration(400);
        setMaxSpeed(65);
        setDeceleration(400);

        scaleBy(0.8f);
    }

    @Override
    public void act(float dt) {

        super.act(dt);

        if(path != null) {
            System.out.println("l;kl;jlkjklj");
            float angle = new Vector2(path.get(nodeIndex).x - getCenterX(), path.get(nodeIndex).y - getCenterY()).angleDeg();
            accelerateAtAngle(angle);
            applyPhysics(dt);
            boundToWorld();
            float currentDst = new Vector2(getCenterX(), getCenterY()).dst(path.get(nodeIndex));

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
            /*
            for (int i = 0; i < path.size(); i++) {
                System.out.print(" -> " + GameGraph.testMap.get(path.get(i)));
            }
            System.out.println("(current node: " + GameGraph.testMap.get(path.get(nodeIndex)) + ")");
            */

            for(int i = 0; i < path.size(); i++) {
                System.out.print("(" + path.get(i).x + "; " + path.get(i).y + "); " + " num: " + num);
            }
            System.out.println();
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
