package com.mygdx.game.gameai.gamepf;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.ai.pfa.indexed.*;

import java.util.List;

public class GamePathFinder {

    private GameGraph graph;
    private IndexedAStarPathFinder<Vector2> pf;
    private Heuristic<Vector2> heuristic;

    public GamePathFinder(TiledMap map) {
        Polygon[] pols = MapTools.getPolygons(map);
        Vector2[] nodes = MapTools.getCornerNodes(pols);
        GameGraph graph = new GameGraph(nodes, pols);
        IndexedAStarPathFinder<Vector2> pf = new IndexedAStarPathFinder<Vector2>(graph);
        this.graph = graph;
        this.pf = pf;
        heuristic = new Heuristic<Vector2>() {
            @Override
            public float estimate(Vector2 gameNode, Vector2 n1) {
                return (new Vector2(gameNode)).dst(n1);
            }
        };
    }


    public List<Vector2> findPath(float x1, float y1, float x2, float y2) {
        graph.setStartNode(x1, y1);
        graph.setEndNode(x2, y2);

        graph.updateEndPathConnections();
        graph.updateStartPathConnections();

        GameGraphPath path = new GameGraphPath();
        if(pf.searchNodePath(graph.getStartNode(), graph.getEndNode(), heuristic, path)) {
            return path.getNodes();
        } else {
            return null;
        }
    }

}
