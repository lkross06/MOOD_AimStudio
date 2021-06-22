package org.headroyce.lross2024;

import javafx.scene.PerspectiveCamera;
import javafx.scene.robot.Robot;
import javafx.scene.transform.*;

/**
 * Class that handles the first-person camera
 */
public class Cam {

    private double oldX, oldY, newX, newY;
    private double width, height;
    private Robot robot;
    private Rotate rotX, rotY;
    private double sens;
    private boolean alreadyMoved;

    private PerspectiveCamera camera;

    /**
     * Constructor for camera-handling class
     * @param w width of screen
     * @param h height of screen
     */
    public Cam(double w, double h) {
        alreadyMoved = false;
        sens = 1;
        width = w;
        height = h;

        rotX = new Rotate(0, Rotate.Y_AXIS);
        rotY = new Rotate(0, Rotate.X_AXIS);
        camera = makeCam();
        camera.getTransforms().addAll(rotX, rotY);
        robot = new Robot();
    }

    private PerspectiveCamera makeCam() {
        PerspectiveCamera rtn = new PerspectiveCamera( );
        rtn.setFieldOfView(67.5);
        rtn.setFarClip(500.0);
        setPivot(0, 0, -width);
        rtn.setNearClip(0.01);
        rtn.setScaleX(1);
        rtn.setScaleY(1);
        rtn.setScaleZ(1);
        return rtn;
    }

    /**
     * gets the PerspectiveCamera being used
     * @return active PerspectiveCamera
     */
    public PerspectiveCamera getCam() {
        return camera;
    }

    private void setPivot(double x, double y, double z){
        //i have ligma
        rotX.setPivotX(x);
        rotX.setPivotY(y);
        rotX.setPivotZ(z);

        rotY.setPivotX(x);
        rotY.setPivotY(y);
        rotY.setPivotZ(z);


    }

    /**
     * checks new and old X/Y position of mouse and angles Rotate transformations
     * @param xPos new x-coordinate of mouse
     * @param yPos new y-coordinate of mouse
     */
    public void turn(double xPos, double yPos) {
        if ( alreadyMoved ) {
            newX = xPos;
            newY = yPos;

            if ( oldX < newX ) { // if mouse moved right
                rotX.setAngle(rotX.getAngle() + sens);
            } else if ( oldX > newX ) { // if mouse moved left
                rotX.setAngle(rotX.getAngle() - sens);
            }
            if ( oldY < newY ) { // if mouse moved up
                rotY.setAngle(rotY.getAngle() - sens);
            } else if ( oldY > newY ) { // if mouse moved down
                rotY.setAngle(rotY.getAngle() + sens);
            }

            oldX = newX;
            oldY = newY;
        } else {
            oldX = xPos;
            oldY = yPos;
            alreadyMoved = true;
        }
    }

    /**
     * checks the pixel that the crosshair is centered on, sees if it is a target or the background
     * @return true if the center pixel is on a target, false if not
     */
    public boolean intersects(){
        return (!robot.getPixelColor(width / 2, height / 2).toString().equals("0xf4f4f4ff"));
    }

    /**
     * sets the sensitivity (multiplier) of the camera
     * @param x sensitivity to be set to
     */
    public void setSensitivity(double x) {
        sens = x;
    }

    /**
     * updates the camera's Rotate transforms or mouse position if necessary
     */
    public void updateBounds() {
//        //mouse is at left edge, keep scrolling it
//        if ((int)robot.getMouseX() < width/2 - 1){
//            robot.mouseMove(width/2 - 1, robot.getMouseY());
//            rotX.setAngle(rotX.getAngle() - (sens));
//
//        }
//        //mouse is at right edge, keep scrolling it
//        if ((int) robot.getMouseX() > width/2 + 1){
//            robot.mouseMove(width/2 + 1, robot.getMouseY());
//            rotX.setAngle(rotX.getAngle() + (sens));
//        }
//
//        //mouse is at upper edge, keep scrolling it
//        if ((int)robot.getMouseY() < height/2 - 1){
//            robot.mouseMove(robot.getMouseX(), height/2 - 1);
//            rotY.setAngle(rotY.getAngle() + (sens));
//        }
//        //mouse is at lower edge, keep scrolling it
//        if ((int) robot.getMouseY() > height/2 + 1) {
//            robot.mouseMove(robot.getMouseX(), height/2 + 1);
//            rotY.setAngle(rotY.getAngle() - (sens));
//        }

        //mouse is at left edge, keep scrolling it
        if ((int)robot.getMouseX() < 1){
            rotX.setAngle(rotX.getAngle() - (sens*2));
        }

        //mouse is at right edge, keep scrolling it
        if ((int) robot.getMouseX() > width - 2){
            rotX.setAngle(rotX.getAngle() + (sens*2));
        }

        //mouse is at upper edge, keep scrolling it
        if ((int)robot.getMouseY() < 1){
            rotY.setAngle(rotY.getAngle() + (sens*2));
        }
        //mouse is at lower edge, keep scrolling it
        if ((int) robot.getMouseY() > height) {
            rotY.setAngle(rotY.getAngle() - (sens*2));
        }

        //looking too far up
        if (rotY.getAngle() > 90){
            rotY.setAngle(90);
        }
        //looking too far down
        if (rotY.getAngle() < 0) {
            rotY.setAngle(0);
        }

        //looking too far right
        if (rotX.getAngle() > 180){
            rotX.setAngle(180);
        }
        //looking too far left
        if (rotX.getAngle() < -180) {
            rotX.setAngle(-180);
        }

    }

}