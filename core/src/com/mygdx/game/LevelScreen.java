package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.framework.BaseActor;
import com.mygdx.game.framework.BaseScreen;
import com.mygdx.game.framework.TilemapActor;
import com.mygdx.game.gameai.gamepf.GamePathFinder;

import java.util.ArrayList;
import java.util.List;

public class LevelScreen extends BaseScreen {

    Hero hero;

    GamePathFinder pathFinder;

    private ArrayList<Enemy> enemies;

    @Override
    public void initialize() {
        int count = 0;


        TilemapActor tma = new TilemapActor("test_map.tmx", mainStage);
        pathFinder = new GamePathFinder(new TmxMapLoader().load("test_map.tmx"));


        //добавлям в главную сцену стены
        for (MapObject obj : tma.getRectangleList("Wall")) {
            MapProperties props = obj.getProperties();
            new Wall((float) props.get("x"), (float) props.get("y"),
                    (float) props.get("width"), (float) props.get("height"),
                    mainStage);
        }

        //добавление главного героя на карту
        MapObject startPoint = tma.getRectangleList("start").get(0);
        MapProperties startProps = startPoint.getProperties();
        hero = new Hero( (float)startProps.get("x"), (float)startProps.get("y"), mainStage);

        //добавление врагов на карту
        enemies = new ArrayList<>();
        for(MapObject enemyStartPoint: tma.getRectangleList("enemyStart")) {
            MapProperties enemyStartProps = enemyStartPoint.getProperties();
            count++;
            enemies.add(new Enemy((float) enemyStartProps.get("x"), (float)enemyStartProps.get("y"), mainStage));
        }
    }

    @Override
    public void update(float dt) {

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            hero.accelerateAtAngle(180);
            hero.setFacingAngle(180);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            hero.accelerateAtAngle(0);
            hero.setFacingAngle(0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            hero.accelerateAtAngle(90);
            hero.setFacingAngle(90);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            hero.accelerateAtAngle(270);
            hero.setFacingAngle(270);
        }

        for (BaseActor wall : BaseActor.getList(mainStage, Wall.class))
        {
            hero.preventOverlap(wall);
        }

        for(Enemy enemy: enemies) {
            //обработка перемещений врагов


            /*if (enemy.getStartPoint().dst(new Vector2(enemy.getCenterX(), enemy.getCenterY())) > 500) {
                enemy.returnToTheStartPoint(pathFinder.findPath(enemy.getCenterX(), enemy.getCenterY(), enemy.getStartPoint().x, enemy.getStartPoint().y));
                //enemy.printPath();
                BaseActor actor;
            }*/
            if (enemy.isWithinDistance(400, hero) && enemy.getStartPoint().dst(new Vector2(enemy.getCenterX(), enemy.getCenterY())) < 5) {
                enemy.chaseTheHero(pathFinder.findPath(enemy.getCenterX(), enemy.getCenterY(), hero.getCenterX(), hero.getCenterY()));
            }
            else if (enemy.getIsChasingTheHero() && Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
                enemy.setPath(pathFinder.findPath(enemy.getCenterX(), enemy.getCenterY(), hero.getCenterX(), hero.getCenterY()));
            }

            /*else if (enemy.getIsReturningToTheStartPoint() && enemy.isWithinDistance(150, hero)
                    && Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) && enemy.getStartPoint().dst(new Vector2(enemy.getCenterX(), enemy.getCenterY())) < 600) {
                enemy.chaseTheHero(pathFinder.findPath(enemy.getCenterX(), enemy.getCenterY(), hero.getCenterX(), hero.getCenterY()));
            }*/
        }
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        hero.attack();
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
