package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.framework.BaseGame;

public class CrazyBerserkers extends BaseGame {
    public void create()
    {
        super.create();
        setActiveScreen( new MenuScreen() );
    }

}
