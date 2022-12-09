package com.mygdx.game.gameai.gamepf;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MapTools {

    public static float cornerDistance = 50;
    private static float UNIT_SCALE = 1f/32f;

    private MapTools() {};

    private static List<Rectangle> getWalls(TiledMap map) {

        MapObjects objs = map.getLayers().get("Object Layer 1").getObjects();
        List<Rectangle> rects = new ArrayList<Rectangle>();

        for(MapObject obj: objs) {
            Rectangle rectangle = ((RectangleMapObject)obj).getRectangle();
            MapProperties props = obj.getProperties();
            if ( props.containsKey("name") && props.get("name").equals("Solid")) {

                rects.add(rectangle);
            }
        }
        return rects;
    }
    private static Polygon[] getPolygons(List<Rectangle> rects) {
        Polygon[] polygons = new Polygon[rects.size()];
        int index = 0;
        for (Rectangle rect: rects) {
            float x1 = rect.x;
            float y1 = rect.y;
            float x2 = rect.width + x1;
            float y2 = rect.height + y1;

            float vertices[] = new float[10];
            vertices[0] = x1;
            vertices[1] = y1;

            vertices[2] = x1;
            vertices[3] = y2;

            vertices[4] = x2;
            vertices[5] = y2;

            vertices[6] = x2;
            vertices[7] = y1;

           vertices[8] = x1;
           vertices[9] = y1;


            polygons[index++] = new Polygon(vertices);
        }

        return polygons;
    }

    public static Polygon[] getPolygons(TiledMap map) {
        return getPolygons(getWalls(map));
    }
    public static Vector2[] getCornerNode(Polygon polygon) {
        Vector2[] vectors = new Vector2[4];
        float[] values = polygon.getVertices();

        vectors[0] = new Vector2(values[0] - cornerDistance, values[1] - cornerDistance);
        vectors[1] = new Vector2(values[2] - cornerDistance, values[3] + cornerDistance);
        vectors[2] = new Vector2(values[4] + cornerDistance, values[5] + cornerDistance);
        vectors[3] = new Vector2(values[6] + cornerDistance, values[7] - cornerDistance);

        return vectors;
    }

    public static Vector2[] getCornerNodes(Polygon[] polygons) {
        Vector2[] result = new Vector2[polygons.length * 4];
        for(int i = 0; i < polygons.length; i++) {
            Vector2[] losRes = getCornerNode(polygons[i]);
            for(int k = 0; k < 4; k++) {
                result[4 * i + k] = losRes[k];
            }
        }
        return result;
    }

}
