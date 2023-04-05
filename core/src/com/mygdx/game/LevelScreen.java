package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygdx.game.animation_loaders.HeroAnimationLoader;
import com.mygdx.game.animation_loaders.SceletonAnimationLoader;
import com.mygdx.game.framework.BaseScreen;
import com.mygdx.game.framework.TilemapActor;
import com.mygdx.game.gameai.gamepf.GamePathFinder;

import java.util.ArrayList;

public class LevelScreen extends BaseScreen {

    Hero hero;

    GamePathFinder pathFinder;
    ArrayList<Sceleton> sceletons;
    ArrayList<Wall> walls;


    @Override
    public void initialize() {

        TilemapActor tma = new TilemapActor("assets/map.tmx", mainStage);
        pathFinder = new GamePathFinder(new TmxMapLoader().load("assets/map.tmx"));


        //добавлям в главную сцену стены
        walls = new ArrayList<>();
        for (MapObject obj : tma.getRectangleList("Solid")) {
            MapProperties props = obj.getProperties();
            Wall wall = new Wall((float) props.get("x"), (float) props.get("y"),
                   (float) props.get("width"), (float) props.get("height"),
                    mainStage);
            walls.add(wall);
        }

        //добавление врагов на карту
        SceletonAnimationLoader sceletonAnimationLoader = new SceletonAnimationLoader();
        sceletons = new ArrayList<>();
        for(MapObject enemyStartPoint: tma.getRectangleList("enemyStart")) {
            MapProperties enemyStartProps = enemyStartPoint.getProperties();
            Sceleton sceleton = new Sceleton((float) enemyStartProps.get("x"), (float)enemyStartProps.get("y"), mainStage, sceletonAnimationLoader);
            sceleton.setDamage(300);
            sceletons.add(sceleton);
        }



        //добавление главного героя на карту

        MapObject startPoint = tma.getRectangleList("heroStart").get(0);
        MapProperties startProps = startPoint.getProperties();
        hero = new Hero( (float)startProps.get("x"), (float)startProps.get("y"), mainStage, new HeroAnimationLoader());
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


        //обработка столкновений героя и врагов друг с другом и со стенами
        for (int i = 0; i < walls.size(); i++)
        {
            hero.preventOverlap(walls.get(i));

            for(int j = 0; j < sceletons.size(); j++) {
                sceletons.get(j).preventOverlap(walls.get(i));
                sceletons.get(j).preventOverlap(hero);
                hero.preventOverlap(sceletons.get(j));
            }
        }



        //обработка поведения врагов
        for(int i = 0; i < sceletons.size(); i++) {

            //обработка перемещения героя на задний план, когда он пересекается со скелетом
            if(sceletons.get(i).getY() > hero.getY())
                sceletons.get(i).setZIndex(1);
            else
                sceletons.get(i).setZIndex(6);


            //обработка перемещений врагов
            if (sceletons.get(i).getPathCoordinates().dst(hero.getPathCoordinates()) < 400 && sceletons.get(i).getStartPoint().dst(sceletons.get(i).getPathCoordinates()) < 100) {
                sceletons.get(i).chaseTheHero(pathFinder.findPath(sceletons.get(i).getPathCoordinates().x, sceletons.get(i).getPathCoordinates().y, hero.getPathCoordinates().x, hero.getPathCoordinates().y));
            }
            else if (sceletons.get(i).getIsChasingTheHero() && Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
                sceletons.get(i).setPath(pathFinder.findPath(sceletons.get(i).getPathCoordinates().x, sceletons.get(i).getPathCoordinates().y, hero.getPathCoordinates().x, hero.getPathCoordinates().y));
            }

            for(Sceleton other: sceletons) {
                if(other != sceletons.get(i)) {
                    sceletons.get(i).preventOverlap(other);
                }
            }

            //обработка принятия урона скелетами от героя
            if(hero.getIsAttacking() && hero.getPathCoordinates().dst(sceletons.get(i).getPathCoordinates()) <= 70 &&
                    Math.abs(hero.getFacingAngle() - sceletons.get(i).getFacingAngle()) >= 150)
            {
                sceletons.get(i).takeDamage(hero.getDamage() * dt);
            }

            //обработка принятия урона героем от скелетов
            if(sceletons.get(i).getIsChasingTheHero() && sceletons.get(i).getPathCoordinates().dst(hero.getPathCoordinates()) <= 70 &&
                    !sceletons.get(i).getIsAttacking()) {
                sceletons.get(i).attack();
                hero.takeDamage(sceletons.get(i).getDamage() * dt);
            }

            //обработка смерти героя
            if(hero.HP <= 0 && hero.isAnimationFinished()) {
                hero.remove();
            }

            //удаление убитых скелетов
            if(sceletons.get(i).HP <= 0 && sceletons.get(i).isAnimationFinished()) {
                sceletons.get(i).remove();
                sceletons.remove(sceletons.get(i));
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
