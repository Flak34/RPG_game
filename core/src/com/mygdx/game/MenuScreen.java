package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.framework.BaseScreen;


public class MenuScreen extends BaseScreen {


    @Override
    public void initialize() {
        //добавление фонового изображения

        Texture backgroundTexture = new Texture(Gdx.files.internal("assets/mainmenu_background.bmp"));
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
        uiTable.row();

        //добавление кнопок на главный экран
        playButtonStyle = new Button.ButtonStyle();
        playButtonTex = new Texture( Gdx.files.internal("assets/buttons/play_button1.png") );
        playButtonRegion = new TextureRegion(playButtonTex);
        playButtonStyle.up = new TextureRegionDrawable( playButtonRegion );
        Button playButton2 = new Button(playButtonStyle);
        uiTable.add(playButton2);

        uiTable.row();
        uiTable.add().fillX().height(20);
        uiTable.row();

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

        playButton2.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) ||
                            !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;
                    CrazyBerserkers.setActiveScreen( new PVPScreen() );
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
