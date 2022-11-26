package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.framework.BaseActor;
import com.mygdx.game.framework.BaseGame;
import com.mygdx.game.gameai.gamepf.GameGraph;
import com.mygdx.game.gameai.gamepf.GameGraphPath;

import java.util.List;

public class Enemy extends BaseActor {

    List<Vector2> path;
    int nodeIndex;

    public Enemy(float x, float y, Stage s, List<Vector2> path) {


        super(x,y,s);

        this.path = path;

        loadTexture("assets/enemy.png");

        setBoundaryPolygon(8);
        setAcceleration(400);
        setMaxSpeed(65);
        setDeceleration(400);

        scaleBy(1.2f);

        nodeIndex = 1;

        scaleBy(-1);
    }


    @Override
    public void act(float dt) {
        super.act(dt);

        float angle = new Vector2(path.get(nodeIndex).x - getCenterX(), path.get(nodeIndex).y - getCenterY()).angleDeg();
        accelerateAtAngle(angle);
        applyPhysics(dt);
        boundToWorld();


        float currentDst = new Vector2(getCenterX(), getCenterY()).dst(path.get(nodeIndex));

        if(currentDst  < 5 && nodeIndex < path.size() - 1) {
            nodeIndex++;
        }

    }


    public void updatePath(List<Vector2> newPath) {
        if(newPath != null) {
            this.path = newPath;
            nodeIndex = 1;
        }
    }

    public void printPath() {
        for(int i = 0; i < path.size(); i++) {
            System.out.print(" -> " + GameGraph.testMap.get(path.get(i)));
        }
        System.out.println("(current node: " + GameGraph.testMap.get(path.get(nodeIndex)) + ")");
    }


    public float getCenterX() {
        return getX() + getWidth() / 2;
    }

    public float getCenterY() {
        return getY() + getHeight() / 2;
    }

}
