package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.framework.BaseActor;

public class Wall extends BaseActor {
    public Wall(float x, float y, float width, float height, Stage s) {
        super(x,y,s);
        setSize(width, height);
        setBoundaryRectangle();
    }

}
