package com.BombingGames.Game;

import com.BombingGames.EngineCore.Controller;
import com.BombingGames.EngineCore.Gameobjects.AbstractCharacter;
import com.BombingGames.EngineCore.Gameobjects.AbstractEntity;
import com.BombingGames.EngineCore.Gameobjects.Block;
import com.BombingGames.EngineCore.GameplayScreen;
import com.BombingGames.EngineCore.Map.Chunk;
import com.BombingGames.EngineCore.Map.Coordinate;
import com.BombingGames.EngineCore.Map.Map;
import com.BombingGames.EngineCore.Map.Minimap;
import com.BombingGames.EngineCore.WECamera;
import com.BombingGames.MainMenu.MainMenuScreen;
import com.BombingGames.WurfelEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 *The <i>CustomGameController</i> is for the game code. Put engine code into <i>Controller</i>.
 * @author Benedikt
 */
public class ExplosivesDemoController extends Controller {

    @Override
    public void init(){
        Chunk.setGenerator(4);
        super.init();

         AbstractCharacter player = (AbstractCharacter) AbstractEntity.getInstance(
                40,
                0,
                Map.getCenter(Map.getGameHeight())
        );
        player.setControls("WASD");
        setPlayer(player);
        
//        addCamera(
//            new WECamera(
//                getPlayer(),
//                0, //left
//                0, //top
//                Gdx.graphics.getWidth(), //width 
//                Gdx.graphics.getHeight()//height
//            )
//        );
        
        addCamera(
            new WECamera(
                0, //left
                0, //top
                Gdx.graphics.getWidth(), //width 
                Gdx.graphics.getHeight()//height
            )
        );
        
        setMinimap(
            new Minimap(this, getCameras().get(0), Gdx.graphics.getWidth() - 400,10)
        );
        
        //useLightEngine(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
    }

    
    @Override
    public void update(float delta){
        //get input and do actions
        Input input = Gdx.input;
        
        if (!GameplayScreen.msgSystem().isListeningForInput()) {
            if (input.isKeyPressed(Input.Keys.ESCAPE)) WurfelEngine.getInstance().setScreen(new MainMenuScreen());


            //walk
            if (getPlayer() != null){
                if ("WASD".equals(getPlayer().getControls()))
                    getPlayer().walk(
                        input.isKeyPressed(Input.Keys.W),
                        input.isKeyPressed(Input.Keys.S),
                        input.isKeyPressed(Input.Keys.A),
                        input.isKeyPressed(Input.Keys.D),
                        .25f+(input.isKeyPressed(Input.Keys.SHIFT_LEFT)? 0.75f: 0)
                    );
                if (input.isKeyPressed(Input.Keys.SPACE)) getPlayer().jump();
            } else {
                //update camera position
                WECamera camera = getCameras().get(0);
                camera.setOutputPosY( (int) (camera.getOutputPosY()
                    - (input.isKeyPressed(Input.Keys.W)? delta: 0)
                    + (input.isKeyPressed(Input.Keys.S)? delta: 0)));
                camera.setOutputPosX( (int) (camera.getOutputPosX()
                    + (input.isKeyPressed(Input.Keys.D)? delta: 0)
                    - (input.isKeyPressed(Input.Keys.A)? delta: 0)));
            }
            
        } else {
            //fetch input and write it down
            //to-do!
            //Gdx.input.getTextInput(new textInput(), "Überschrift", "test");
            //TextField textfield = new TextField("enter text", new Skin());           
        }
        
        super.update(delta);
    }
    
    }