package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.mygdx.game.animation_loaders.HeroAnimationLoader;
import com.mygdx.game.animation_loaders.SceletonAnimationLoader;
import com.mygdx.game.framework.BaseScreen;
import com.mygdx.game.framework.TilemapActor;

import java.util.ArrayList;

public class PVPScreen extends BaseScreen {

    private Hero player1;
    private Hero player2;

    private ArrayList<Wall> walls;


    @Override
    public void initialize() {
        TilemapActor tma = new TilemapActor("assets/pvp_map.tmx", mainStage);


        //добавление игроков на карту
        MapObject startPoint = tma.getRectangleList("player1Start").get(0);
        MapProperties startProps = startPoint.getProperties();
        player1 = new Hero( (float)startProps.get("x"), (float)startProps.get("y"), mainStage, new HeroAnimationLoader());

        startPoint = tma.getRectangleList("player2Start").get(0);
        startProps = startPoint.getProperties();
        player2 = new Hero( (float)startProps.get("x"), (float)startProps.get("y"), mainStage, new SceletonAnimationLoader());
    }

    @Override
    public void update(float dt) {
        //обработка перемещений игрока 1
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player1.accelerateAtAngle(180);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player1.accelerateAtAngle(0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player1.accelerateAtAngle(90);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player1.accelerateAtAngle(270);
        }

        //обработка перемещений игрока 2
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player2.accelerateAtAngle(180);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player2.accelerateAtAngle(0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player2.accelerateAtAngle(90);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player2.accelerateAtAngle(270);
        }


        if(player1.getIsAttacking() && player1.getPathCoordinates().dst(player2.getPathCoordinates()) <= 70 &&
                Math.abs(player1.getFacingAngle() - player2.getFacingAngle()) >= 150)
        {
            player2.takeDamage(player1.getDamage() * dt);
        }

        if(player2.getIsAttacking() && player2.getPathCoordinates().dst(player1.getPathCoordinates()) <= 70 &&
                Math.abs(player2.getFacingAngle() - player1.getFacingAngle()) >= 150)
        {
            player1.takeDamage(player2.getDamage() * dt);
        }

        player1.preventOverlap(player2);
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }


    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.C && !getIsPaused()) {
            player1.attack();
        }
        else if(keycode == Input.Keys.J && !getIsPaused()) {
           player2.attack();
        }

        return true;
    }

}
