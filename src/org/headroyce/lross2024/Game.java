package org.headroyce.lross2024;

import javafx.animation.AnimationTimer;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import java.util.ArrayList;

/**
 * class that handles the 3D game scene and information between Classes
 */
public class Game extends StackPane {
    private Cam camera;

    private Box floor;
    private Group rootGroup;
    private SubScene sub;

    private int score;

    private int difficulty;
    /**
     * 1 = vEasy
     * 2 = easy
     * 3 = medium
     * 4 = hard
     * 5 = vHard
     *
     * ascending numbers so that the radius can be a partial value
     */

    private final int game_timer;

    private ArrayList<Target> targetList;

    private boolean noTarget = false;
    private animTimer gameTimer;

    private final int shoot_cooldown;
    private int shoot_timer;

    private final double ScreenW;
    private final double ScreenH;

    private int oneSecond;
    private int time;
    private boolean enableShooter;

    private double sensitivity;
    private double volume;

    private Overlay overlay;

    //TODO: javadoc comments in overlay, game

    /**
     * Constructor makes a new Game 3D world
     * @param width width of screen
     * @param height height of screen
     * @param overlay the overlay on top of this SubScene
     */
    public Game(double width, double height, Overlay overlay) {
        sensitivity = 1;
        volume = 0.5;

        //player waits 2 frames to fire again
        shoot_cooldown = 34;
        shoot_timer = 0;
        game_timer = 17;
        difficulty = 1;

        this.overlay = overlay;

        targetList = new ArrayList<>(1);
        rootGroup = new Group();

        gameTimer = new animTimer();

        ScreenW = width;
        ScreenH = height;

        floor = new Box(100000, 1, 100000);
        floor.setTranslateY(750);
        floor.setMaterial(new PhongMaterial(Color.SLATEGRAY));

        camera = new Cam(ScreenW, ScreenH);

        sub = new SubScene(rootGroup, width, height, true, SceneAntialiasing.BALANCED);
        sub.setCamera(camera.getCam());
        rootGroup.getChildren().add(floor);
        this.getChildren().addAll(sub);
    }

    /**
     * handles if the mouse position changes
     * @param event a new MouseEvent that is MouseEvent.MOUSE_MOVED
     */
    public void handleMouseMove(MouseEvent event) {
        camera.turn(event.getSceneX(), event.getSceneY());
    }

    /**
     * sets values of sensitivity and sound effect volume, both handled in Game
     * @param s new sensitivity value
     * @param v new sound effect volume value
     */
    public void setVals(double s, double v) {
        sensitivity = s/100;
        volume = v/100;
    }

    /**
     * handles if the left mouse button is pressed
     */
    public void shoot() {
        //if the crosshair is on a target and the cooldown has reset
        if (camera.intersects() && enableShooter) {
            score++;
            noTarget = true;
            rootGroup.getChildren().remove(1);
            //play target "ding" sound
            AudioClip targetSound = new AudioClip(getClass().getResource("/audio/ding.mp3").toString());
            targetSound.setVolume(volume);
            targetSound.play();
        }
        if (enableShooter) {
            enableShooter = false;
            AudioClip shootSound = new AudioClip(getClass().getResource("/audio/shoot.mp3").toString());
            shootSound.setVolume(volume);
            shootSound.play();
        }
    }

    /**
     * creates a new target to replace the old one
     */
    public void createTarget(){
        Target target = new Target(ScreenW, ScreenH, difficulty);
        targetList.add(target);
        rootGroup.getChildren().addAll(target.create());
        noTarget = false;
    }

    /**
     * sets the new difficulty of the game to affect Target objects
     * @param difficulty new difficulty
     */
    public void setDifficulty(String difficulty) {
        System.out.println(difficulty);
        switch(difficulty){
            case "easy" -> this.difficulty = 2;
            case "medium" -> this.difficulty = 3;
            case "hard" -> this.difficulty = 4;
            case "vhard" -> this.difficulty = 5;
        }
    }

    /**
     * resets the non-final variables when the settings menu is closed
     */
    public void reset() {
        targetList.clear();
        for (int i = 0; i < rootGroup.getChildren().size(); i++){
            if (i >= 1){
                rootGroup.getChildren().remove(1);
            }
        }
        score = 0;
        time = 0;
        oneSecond = 1000;
    }

    public AnimationTimer getTimer() {
        return gameTimer;
    }

    private class animTimer extends AnimationTimer {
        private long lastUpdate;

        /**
         * constructor that makes a new AnimationTimer Class and defines lastUpdate
         */
        public animTimer() {
            lastUpdate = 0;
        }

        /**
         * handle() runs every tick of AnimationTimer
         * @param now last tick of AnimationTimer
         */
        @Override
        public void handle(long now) {

            long time_elapsed = (now - lastUpdate) / 1000000;

            oneSecond -= time_elapsed;
            if (oneSecond <= 0){
                oneSecond = 1000;
                time++;
            }

            if (time_elapsed > game_timer) {

                if(noTarget){
                    createTarget();
                }
                camera.updateBounds();
                camera.setSensitivity(sensitivity);

                for (int i = 0; i < targetList.size(); i++) {
                    if (i >= 1){
                        targetList.remove(0);
                    } else {
                        targetList.get(i).update();
                    }
                }

                shoot_timer++;
                if (shoot_timer >= shoot_cooldown){
                    enableShooter = true;
                    shoot_timer = 0;
                }

                int[] rtn;
                if (noTarget){
                    rtn = new int[]{score, time/100, 0, 0};
                } else {
                    rtn = new int[]{score, time/100, (int) targetList.get(0).getTarget().getTranslateX(), (int) targetList.get(0).getTarget().getTranslateY()};
                }
                overlay.updateText(rtn);
            }
        }
    }
}