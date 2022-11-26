package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.framework.BaseActor;
import com.mygdx.game.framework.BaseScreen;
import com.mygdx.game.framework.TilemapActor;
import com.mygdx.game.gameai.gamepf.GameGraph;
import com.mygdx.game.gameai.gamepf.GamePathFinder;

import java.util.List;

public class LevelScreen extends BaseScreen {

    Hero hero;
    Enemy enemy;

    GamePathFinder pathFinder;



    @Override
    public void initialize() {


        TilemapActor tma = new TilemapActor("test_map.tmx", mainStage);
        pathFinder = new GamePathFinder(new TmxMapLoader().load("test_map.tmx"));


        //добавлям в главную сцену стены
        for (MapObject obj : tma.getRectangleList("Wall")) {
            MapProperties props = obj.getProperties();
            new Wall((float) props.get("x"), (float) props.get("y"),
                    (float) props.get("width"), (float) props.get("height"),
                    mainStage);
        }


        MapObject startPoint = tma.getRectangleList("start").get(0);
        MapProperties startProps = startPoint.getProperties();
        hero = new Hero( (float)startProps.get("x"), (float)startProps.get("y"), mainStage);
        enemy = new Enemy(900, 900, mainStage, pathFinder.findPath(900, 900, hero.getX(), hero.getY()));


    }

    @Override
    public void update(float dt) {



        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            hero.accelerateAtAngle(180);
            hero.setFacingAngle(180);
            enemy.updatePath(pathFinder.findPath(enemy.getCenterX(), enemy.getCenterY(), hero.getCenterX(), hero.getCenterY()));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            hero.accelerateAtAngle(0);
            hero.setFacingAngle(0);
            enemy.updatePath(pathFinder.findPath(enemy.getCenterX(), enemy.getCenterY(), hero.getCenterX(), hero.getCenterY()));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            hero.accelerateAtAngle(90);
            hero.setFacingAngle(90);
            enemy.updatePath(pathFinder.findPath(enemy.getCenterX(), enemy.getCenterY(), hero.getCenterX(), hero.getCenterY()));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            hero.accelerateAtAngle(270);
            hero.setFacingAngle(270);
            enemy.updatePath(pathFinder.findPath(enemy.getCenterX(), enemy.getCenterY(), hero.getCenterX(), hero.getCenterY()));
        }

        for (BaseActor solid : BaseActor.getList(mainStage, Solid.class))
        {
            hero.preventOverlap(solid);
        }
        for (BaseActor wall : BaseActor.getList(mainStage, Wall.class))
        {
            hero.preventOverlap(wall);
        }
        enemy.printPath();


    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        hero.attack();
        //return super.touchDown(screenX, screenY, pointer, button);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.P && getIsPaused()) {
            resume();
        }
        else if(keycode == Input.Keys.P && !getIsPaused()) {
            pause();
        }

        return true;
    }
}
