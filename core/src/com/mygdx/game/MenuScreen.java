package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.framework.BaseGame;
import com.mygdx.game.framework.BaseScreen;

public class MenuScreen extends BaseScreen {


    @Override
    public void initialize() {
        //добавление фонового изображения
        Texture backgroundTexture = new Texture(Gdx.files.internal("assets/mainmenu_ background.bmp"));
        TextureRegion backgroundRegion = new TextureRegion(backgroundTexture);
        uiTable.setBackground(new TextureRegionDrawable(backgroundRegion));

        //добавление кнопок на главный экран
        Button.ButtonStyle playButtonStyle = new Button.ButtonStyle();
        Texture playButtonTex = new Texture( Gdx.files.internal("assets/buttons/play_button1.png") );
        TextureRegion playButtonRegion = new TextureRegion(playButtonTex);
        playButtonStyle.up = new TextureRegionDrawable( playButtonRegion );
        Button playButton = new Button(playButtonStyle);
        uiTable.add(playButton);

        uiTable.row();
        uiTable.add().fillX().height(20);

        Button.ButtonStyle exitButtonStyle = new Button.ButtonStyle();
        Texture exitButtonTex = new Texture( Gdx.files.internal("assets/buttons/exit_button1.png") );
        TextureRegion exitButtonRegion = new TextureRegion(exitButtonTex);
        exitButtonStyle.up = new TextureRegionDrawable( exitButtonRegion );
        Button exitButton = new Button(exitButtonStyle);
        uiTable.row();
        uiTable.add(exitButton);

        //добавление обработчиков на кнопки
        playButton.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) ||
                            !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;
                    CrazyBerserkers.setActiveScreen( new LevelScreen() );
                    return false;
                }
        );

        exitButton.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) ||
                            !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;
                    Gdx.app.exit();
                    return false;
                }
        );




    }

    @Override
    public void update(float dt) {

    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER))
            CrazyBerserkers.setActiveScreen( new LevelScreen());
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();
        return false;
    }
}
