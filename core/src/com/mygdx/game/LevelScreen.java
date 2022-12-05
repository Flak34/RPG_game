package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygdx.game.framework.BaseActor;
import com.mygdx.game.framework.BaseScreen;
import com.mygdx.game.framework.TilemapActor;
import com.mygdx.game.gameai.gamepf.GamePathFinder;

import java.awt.*;
import java.util.ArrayList;

public class LevelScreen extends BaseScreen {

    Hero hero;

    GamePathFinder pathFinder;

    private ArrayList<Sceleton> sceletons;


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

        //добавление врагов на карту
        sceletons = new ArrayList<>();
        for(MapObject enemyStartPoint: tma.getRectangleList("enemyStart")) {
            MapProperties enemyStartProps = enemyStartPoint.getProperties();
            count++;
            Sceleton sceleton = new Sceleton((float) enemyStartProps.get("x"), (float)enemyStartProps.get("y"), mainStage);
            sceleton.setDamage(300);
            sceletons.add(sceleton);
        }


        //добавление главного героя на карту
        MapObject startPoint = tma.getRectangleList("start").get(0);
        MapProperties startProps = startPoint.getProperties();
        hero = new Hero( (float)startProps.get("x"), (float)startProps.get("y"), mainStage);
        hero.setZIndex(5);

    }

    @Override
    public void update(float dt) {

        //обработка перемещений героя
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            hero.accelerateAtAngle(180);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            hero.accelerateAtAngle(0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            hero.accelerateAtAngle(90);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            hero.accelerateAtAngle(270);
        }

        System.out.println(hero.getFacingAngle());

        //обработка столкновений героя и врагов друг с другом и со стенами
        for (BaseActor wall : BaseActor.getList(mainStage, Wall.class))
        {
            hero.preventOverlap(wall);
            for(Sceleton sceleton: sceletons) {
                sceleton.preventOverlap(wall);
                sceleton.preventOverlap(hero);
                hero.preventOverlap(sceleton);
                if(sceleton.getY() > hero.getY())
                    sceleton.setZIndex(1);
                else
                    sceleton.setZIndex(6);
            }
        }



        //обработка поведения врагов
        for(Sceleton sceleton : sceletons) {
            //обработка перемещений врагов
            if (sceleton.getPathCoordinates().dst(hero.getPathCoordinates()) < 400 && sceleton.getStartPoint().dst(sceleton.getPathCoordinates()) < 100) {
                sceleton.chaseTheHero(pathFinder.findPath(sceleton.getPathCoordinates().x, sceleton.getPathCoordinates().y, hero.getPathCoordinates().x, hero.getPathCoordinates().y));
            }
            else if (sceleton.getIsChasingTheHero() && Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
                sceleton.setPath(pathFinder.findPath(sceleton.getPathCoordinates().x, sceleton.getPathCoordinates().y, hero.getPathCoordinates().x, hero.getPathCoordinates().y));
            }

            for(Sceleton other: sceletons) {
                if(other != sceleton) {
                    sceleton.preventOverlap(other);
                }
            }

            //обработка принятия урона скелетами от героя
            if(hero.getIsAttacking() && hero.getPathCoordinates().dst(sceleton.getPathCoordinates()) <= 70 &&
            Math.abs(hero.getFacingAngle() - sceleton.getFacingAngle()) >= 150)
            {
                sceleton.takeDamage(hero.getDamage() * dt);
            }

            //обработка принятия урона героем от скелетов
            if(sceleton.getIsChasingTheHero() && sceleton.getPathCoordinates().dst(hero.getPathCoordinates()) <= 70 &&
            !sceleton.getIsAttacking()) {
                sceleton.attack();
                hero.takeDamage(sceleton.getDamage() * dt);
            }

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
