package com.BombingGames;

import com.BombingGames.EngineCore.Controller;
import com.BombingGames.EngineCore.GameplayScreen;
import com.BombingGames.EngineCore.View;
import com.BombingGames.EngineCore.WorkingDirectory;
import com.BombingGames.MainMenu.MainMenuScreen;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import java.io.File;

/**
 *The Main class of the engine. To create a new engine use  {@link com.BombingGames.WurfelEngine#construct(java.lang.String, java.lang.String[]) WurfelEngine.construct}
 * The Wurfel Engine needs the API libGDX0.9.9
 * @author Benedikt Vogler
 */
public class WurfelEngine extends Game {
    /**
     * The version of the Engine
     */
    public static final String VERSION = "1.1.16";    
    private static File workingDirectory;
    private static boolean fullscreen = false;
    private static WurfelEngine instance;

    /**
     * Create the Engine. Don't use this constructor. Use construct() instead. 
     * @param title The title, which is displayed in the window.
     * @param args custom display resolution: [0] width, [1] height, [2] fullscreen
     */
    private WurfelEngine(String title, String[] args){
        // set the name of the application menu item on mac
        if (System.getProperty("os.name").toLowerCase().contains("mac"))
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", title);
        
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.setFromDisplayMode(LwjglApplicationConfiguration.getDesktopDisplayMode());
        config.fullscreen = false;
        config.vSyncEnabled = false;//if set to true the FPS is locked to 60
        config.foregroundFPS = 0;
        config.useGL20 = false;
         
        //arguments
        //you can start the game with a custom resolution
        if (args.length == 0){
           config.setFromDisplayMode(LwjglApplicationConfiguration.getDesktopDisplayMode());
        } else {
            if (args.length > 1){
                config.width = Integer.parseInt(args[0]);
                config.height = Integer.parseInt(args[1]);
                if (args.length >= 3){
                    config.fullscreen = ("true".equals(args[2]));
                }
            }
        }    
        
        config.title = title + " " + config.width + "x"+config.height;

        workingDirectory = WorkingDirectory.getWorkingDirectory("Wurfelengine");
        
        Texture.setEnforcePotImages(false);//allow non-power-of-two textures
        LwjglApplication application = new LwjglApplication(this, config);
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
         
        //LIBGDX: no equivalent found in libGDX yet
        //setUpdateOnlyWhenVisible(true);        
        //setMaximumLogicUpdateInterval(200);//delta can not be bigger than 200ms ^= 5 FPS
        //setMinimumLogicUpdateInterval(1);//delta can not be smaller than 1 ^= 1000FPS  
    }
    
    @Override
    public void create() {
        setScreen(new MainMenuScreen());
    }
    
        /**
     * Create a new instance of the engine.
     * @param title The title, which is displayed in the window.
     * @param args custom display resolution: [0] width, [1] height, [2] fullscreen
     */
    public static void construct(String title, String[] args){
        instance = new WurfelEngine(title,args);
    }
    
    /**
     * Singleton method to get the only instance.
     * @return the wurfelengine
     */
    public static WurfelEngine getInstance(){
        return instance;
    }
    
    /**
     * Launch the main game with you custom controller and view.
     * @param controller
     * @param view 
     */
    public static void startGame(Controller controller, View view){
        instance.setScreen(
            new GameplayScreen(
                controller,
                view
            )
        );
    }
    
    /**
     * Get the credits of the engine.
     * @return a long string with breaks
     */
    public static String getCredits() {
        String newline = System.getProperty("line.separator");
        return "Created by:"+newline
            + " Benedikt Vogler"+newline+newline
            + "Programming:"+newline
            + "Benedikt Vogler"+newline+newline
            + "Art:"+newline
            + "Benedikt Vogler"+newline
            + "Pia Lenßen"+newline+newline
            + "Sound:"+newline
            + "Benedikt Vogler"+newline+newline
            + "Quality Assurance"+newline
            + "Thomas Vogt";
    }
    
   /**
     * Returns the save file folder, wich is different on every OS.
     * @return a folder
     */
    public static File getWorkingDirectory() {
        return workingDirectory;
    }

    /**
     *You can switch to fullscreen. It only works if the current window resolution is supported by your hardware.
     * @param fullscreen
     */
    public static void setFullscreen(boolean fullscreen) {
        WurfelEngine.fullscreen = fullscreen;
        Gdx.graphics.setDisplayMode(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), fullscreen);
        Gdx.app.debug("Wurfel Engine","Set to fullscreen:"+fullscreen + " It is now:"+WurfelEngine.isFullscreen());
    }

    /**
     *Check if the game is running in fullscreen.
     * @return true when running in fullscreen, false if in window mode
     */
    public static boolean isFullscreen() {
        return fullscreen;
    } 
}