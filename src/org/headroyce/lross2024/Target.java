package org.headroyce.lross2024;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;

/**
 * Makes a target object that consists of attributes for movement and creation and updating properties
 */
public class Target {
    private final double width, height;
    private final int difficulty;

    /**
     * 1 = vEasy
     * 2 = easy
     * 3 = medium
     * 4 = hard
     * 5 = vHard
     *
     * descending numbers so that the radius can be a multiplied value
     */

    private boolean moving = false;
    private double velX;
    private Sphere target;

    private int cycleStep;

    /**
     * cycleStep depicts the animation cycle of targets going back and forth, each time it increases its another cycle frame
     * 0 - 400, full animation is moving 200px left then 200 px right
     */

    /**
     * constructor that defines new Target object
     * @param width width of screen
     * @param height height of screen
     * @param d difficulty selected (or updated) in Escape GUI
     */
    public Target(double width, double height, int d){
        cycleStep = 0;
        velX = 3;
        difficulty = d;
        this.width = width;
        this.height = height;
    }

    /**
     * responsible for creating the Sphere object that the Target object is represented by
     * @return newly-created Sphere object
     */
    public Sphere create(){
        //the difficulty affects the size of the ball, harder is smaller
        target = new Sphere(75 - (difficulty*5));
        target.setCullFace(CullFace.NONE);
        PhongMaterial color = new PhongMaterial();
        color.setSpecularColor(null);
        color.setSpecularPower(0);
        //depending on difficulty, a harsher color is selected
        switch(difficulty){
            case 1 -> color.setDiffuseColor(Color.LIGHTSEAGREEN);
            case 2 -> color.setDiffuseColor(Color.YELLOWGREEN);
            case 3 -> color.setDiffuseColor(Color.DARKGOLDENROD);
            case 4 -> color.setDiffuseColor(Color.ORANGE);
            case 5 -> color.setDiffuseColor(Color.ORANGERED);
        }
        target.setMaterial(color);
        randomSpawn(target);
        //if difficulty is hard or very hard, it moves
        if (difficulty > 3){
            moving = true;
        }
        //if difficulty is previously hard/vhard and now is easier difficulty
        if (difficulty < 4){
            moving = false;
        }
        //very hard difficulty has an increased x-velocity
        if (difficulty > 4){
            velX *= 1.75;
        }
        return target;
    }

    private void randomSpawn(Sphere target){
        double x = Math.random() * width;
        double y = Math.random() * -height;
        target.setTranslateX(x);
        target.setTranslateY(y);
    }

    /**
     * every game tick, the target's position and existence is updated
     */
    public void update() {
        if (moving) {
            cycleStep++;
            if (cycleStep == 200){
                velX *= -1;
            }
            if (cycleStep == 400){
                velX *= -1;
                cycleStep = 0;
            }
            this.target.setTranslateX(this.target.getTranslateX() + velX);
        }
    }


    public Sphere getTarget() {
        return target;
    }


}
