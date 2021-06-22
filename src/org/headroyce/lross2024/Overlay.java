package org.headroyce.lross2024;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.Stack;

/**
 * A pane that consists of a 2D overlay to be placed in front of the 3D game screen
 */
public class Overlay extends Pane {
    private final Rectangle top, left, right, down;
    private final Label score;
    private final Label time;
    private final Label pos;
    private final updateTimer timer;
    private final double w, h;

    /**
     * constructor that makes the crosshair and top GUI bar
     * @param width width of screen
     * @param height height of screen
     */
    public Overlay(double width, double height){
        Font common = new Font("Verdana", 30);
        Insets common2 = new Insets(10, 40, 10, 40);
        score = new Label();
        score.setFont(common);
        score.setPadding(common2);
        time = new Label();
        time.setFont(common);
        time.setPadding(common2);
        pos = new Label();
        pos.setFont(common);
        pos.setPadding(common2);
        timer = new updateTimer();
        w = width;
        h = height;
        HBox guibar = createGUIbar();

        //w2 = width of rectangle
        //h2 = height of rectangle
        //in = in x in square that is in the middle of screen
        double w2, h2, in;
        w2 = 4;
        h2 = 7;
        in = 4;

        top = new Rectangle(w/2 - (w2/2), h/2 - ((in/2) + h2), w2, h2);
        left = new Rectangle(w/2 - ((in/2) + h2), h/2 - (w2/2), h2, w2);
        right = new Rectangle(w/2 + (in/2), h/2 - (w2/2), h2, w2);
        down = new Rectangle(w/2 - (w2/2), h/2 + (in/2), w2, h2);

        this.getChildren().addAll(guibar, top, left, right, down);
        guibar.setAlignment(Pos.TOP_CENTER);
        timer.start();

    }


    private HBox createGUIbar() {
        HBox rtn = new HBox(w/100);
        rtn.getChildren().addAll(score, time, pos);
        return rtn;
    }

    public void updateText(int[] arr){
        score.setText("Score: " + arr[0]);
        time.setText("Time Elapsed: " + arr[1]);
        pos.setText("Nearest Target: " + arr[2] + "x, " + arr[3]*-1 + "y");
    }

    //separate animation timer that exists to update overlay
    private class updateTimer extends AnimationTimer {
        private long lastUpdate;

        /**
         * constructor that makes a new AnimationTimer Class and defines lastUpdate
         */
        public updateTimer() {
            lastUpdate = 0;
        }

        /**
         * handle() runs every tick of AnimationTimer
         * @param now last tick of AnimationTimer
         */
        @Override
        public void handle(long now) {
            long time_elapsed = (now - lastUpdate) / 100000;
            if (time_elapsed > 17) {
//                score.setText("Score: " + root.getData()[0]);
//                time.setText("Time Elapsed: " + root.getData()[1]);
            }
        }
    }


}
