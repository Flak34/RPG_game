package com.mygdx.game.gameai.gamepf;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.math.Vector2;

public class GameConnection implements Connection<Vector2> {

    private Vector2 startNode;
    private Vector2 endNode;

    private float dist = 0;

    public GameConnection(Vector2 startNode, Vector2 endNode) {
        this.startNode = startNode;
        this.endNode = endNode;
        float dx = startNode.x - endNode.x;
        float dy = startNode.y - endNode.y;
        dist = (float) Math.sqrt(dx * dx + dy * dy);
    }
    @Override
    public float getCost() {
        return dist;
    }

    @Override
    public Vector2 getFromNode() {
        return startNode;
    }

    @Override
    public Vector2 getToNode() {
        return endNode;
    }
}
