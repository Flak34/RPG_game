package com.mygdx.game.gameai.gamepf;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class GameGraphPath implements GraphPath<Vector2> {

    private List<Vector2> nodes = new ArrayList<Vector2>();

    public List<Vector2> getNodes() {
        return nodes;
    }

    @Override
    public int getCount() {
        return nodes.size();
    }

    @Override
    public Vector2 get(int index) {
        return nodes.get(index);
    }

    @Override
    public void add(Vector2 node) {
        nodes.add(node);
    }

    @Override
    public void clear() {
        nodes.clear();
    }

    @Override
    public void reverse() {
        int N = nodes.size();
        for(int i = 0; i < N; i++) {
            nodes.add(i, nodes.get(N - 1));
            nodes.remove(N);
        }
    }

    @Override
    public Iterator iterator() {
        return nodes.iterator();
    }
}
