package org.headroyce.lross2024;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * launches the program from a Stage and Scene
 */
public class Main extends Application {
    private Stage stage;
    private Scene primaryScene;
    private Scene secondaryScene;
    private Game root3d;
    private Overlay root2d;
    private int toggle;

    @Override
    public void start(Stage primaryStage) {
        toggle = 1;

        stage = primaryStage;
        stage.setMaximized(true);

        //this Rectangle2D object represents the size of the screen (which may fluctuate)
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        root2d = new Overlay(screen.getWidth(), screen.getHeight());
        root3d = new Game(screen.getWidth(), screen.getHeight(), root2d);
        primaryScene = new Scene(new StackPane(root3d, root2d), screen.getWidth(), screen.getHeight());
        secondaryScene = new Scene(new EscGUI(screen.getWidth(), screen.getHeight(), root3d), screen.getWidth(), screen.getHeight());
        primaryScene.setFill(Color.SILVER);

        stage.setTitle("Aim Studio");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(secondaryScene);
        stage.show();

        primaryStage.addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                root3d.handleMouseMove(event);
            }
        });
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.Q) {
                switchScenes();
            }
        });
        primaryStage.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.getButton() == MouseButton.PRIMARY){
                if(stage.getScene() == primaryScene) {
                    root3d.shoot();
                }
            }
        });
    }

    public void switchScenes() {
        toggle *= -1;

        if (toggle > 0) {
            //EscGUI scene
            stage.setScene(secondaryScene);
            root3d.getTimer().stop();
        } else if (toggle < 0) {
            //main scene
            stage.setScene(primaryScene);
            root3d.reset();
            root3d.getTimer().start();
            root3d.createTarget();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}