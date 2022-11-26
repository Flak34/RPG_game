package com.mygdx.game.gameai.gamepf;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameGraph implements IndexedGraph<Vector2> {

    private Array<Vector2> nodes;
    private final Vector2 startNode = new Vector2();
    private final Vector2 endNode = new Vector2();

    private Connection<Vector2>[] otherStartConnection; //связи от вершин карты до начальной вершины
    private Connection<Vector2>[] otherEndConnection; //связи от вершин карты до конечной вершины
    private Array<Connection<Vector2>>[] connections;
    private Array<Connection<Vector2>> startConnections; //связи начальной вершины с остальными вершинами карты
    private Array<Connection<Vector2>> endConnections; //связи конечной вершины с остатльными вершинами карты
    private Polygon[] polygons;

    public static HashMap<Vector2, Integer> testMap = new HashMap<>();

    public GameGraph(Vector2[] nodes, Polygon[] polygons) {
        this.nodes = new Array(nodes);
        for(int i = 0; i < nodes.length; i++) {
            testMap.put(nodes[i], i + 1);
        }
        this.polygons = polygons;

        connections = new Array[nodes.length];
        otherStartConnection = new Connection[nodes.length];
        otherEndConnection = new Connection[nodes.length];
        for(int i = 0; i < nodes.length; i++) {
            Array<Connection<Vector2>> conns = new Array<>();
            for(int k = 0; k < nodes.length; k++) {
                if(i != k) {
                    Vector2 n1 = nodes[i];
                    Vector2 n2 = nodes[k];
                    boolean isIntersected = false;
                    for(int p = 0; p < polygons.length; p++) {


                        if(Intersector.intersectSegmentPolygon(n1, n2, polygons[p])) {
                            isIntersected = true;
                        }

                    }
                    if(!isIntersected) {
                        conns.add(new GameConnection(n1, n2));
                    }
                }
            }
            connections[i] = conns;
        }
    }

    public Array<Vector2> getNodes() {
        return nodes;
    }

    public void updateStartPathConnections() {
        startConnections = updatePathConnections(startNode, endNode, otherStartConnection);
    }

    public void updateEndPathConnections() {
        endConnections = updatePathConnections(endNode, startNode, otherEndConnection);
    }

    private Array<Connection<Vector2>> updatePathConnections(Vector2 node, Vector2 node2, Connection<Vector2>[] otherConnections) {

        //вычисление ребер от вершины node до остальных на карте
        Array<Connection<Vector2>> conns = new Array<>();

        for(int k = 0; k < nodes.size; k++) {
            boolean isIntersected = false;
            Vector2 n2 = nodes.get(k);
            for(int p = 0; p < polygons.length; p++) {
                if(Intersector.intersectSegmentPolygon(node, n2, polygons[p])) {
                    isIntersected = true;
                }
            }
            if(!isIntersected) {
                conns.add(new GameConnection(node, n2));
                otherConnections[k] = new GameConnection(n2, node);
            } else {
                otherConnections[k] = null;
            }
        }

        //определение наличия ребра от вершины node до вершины node2

        boolean isIntersected = false;
        isIntersected = false;
        for(int p = 0; p < polygons.length; p++) {
            if(Intersector.intersectSegmentPolygon(node, node2, polygons[p])) {
                isIntersected = true;
            }
        }
        if(!isIntersected) {
            conns.add(new GameConnection(node, node2));
        }

        return conns;
    }

    @Override
    public int getIndex(Vector2 node) {
        if(node == startNode) {
            return nodes.size;
        } else {
            if(node == endNode) {
                return nodes.size + 1;
            } else {
                return nodes.indexOf(node, true);
            }
        }
    }

    @Override
    public int getNodeCount() {
        return nodes.size + 2;
    }

    @Override
    public Array<Connection<Vector2>> getConnections(Vector2 node) {
        if(node == startNode) {
            return startConnections;
        } else {
            if(node == endNode) {
                return endConnections;
            } else {
                int index = nodes.indexOf(node, true);
                Connection<Vector2> connToStart = otherStartConnection[index];
                Connection<Vector2> connToEnd = otherEndConnection[index];
                Array<Connection<Vector2>> newConn = new Array<>(connections[index]);
                if(connToStart != null) {
                    newConn.add(connToStart);
                }
                if(connToEnd != null) {
                    newConn.add(connToEnd);
                }
                return newConn;
            }
        }
    }

    public void setStartNode(float x, float y) {
        startNode.x = x;
        startNode.y = y;
        testMap.put(startNode, 9);
    }

    public void setEndNode(float x, float y) {
        endNode.x = x;
        endNode.y = y;
        testMap.put(endNode, 10);
    }

    public Vector2 getStartNode() {
        return startNode;
    }

    public Vector2 getEndNode() {
        return endNode;
    }
}
