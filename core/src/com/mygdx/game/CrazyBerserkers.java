package com.mygdx.game;

import com.mygdx.game.framework.BaseGame;

public class CrazyBerserkers extends BaseGame {
    public void create()
    {
        super.create();
        setActiveScreen( new MenuScreen() );
    }

}
