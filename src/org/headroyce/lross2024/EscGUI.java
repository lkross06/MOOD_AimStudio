package org.headroyce.lross2024;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.*;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * EscGUI is the settings menu accessed by pressing Escape
 */
public class EscGUI extends BorderPane {
    private int sens;
    private int vol2;
    private final Game root;

    /**
     * Constructor that makes a new BorderPane layout
     * @param width width of screen
     * @param height height of screen
     * @param root the class that the 3D game is made from
     */
    public EscGUI(double width, double height, Game root) {
        vol2 = 50;
        sens = 100;
        this.root = root;

        //different fonts
        Font common = new Font("Verdana", 12);
        Font subTitle = new Font(16).font("Verdana", FontWeight.BOLD, 16);

        //different margins
        Insets bottomMarg = new Insets(height*0.01, width*0.01, height*0.01, width*0.01);
        Insets rightMarg = new Insets(height*0.01, width*0.01, height*0.01, width*0.01);
        Insets leftMarg = new Insets(height*0.01, width*0.01, height*0.01, width*0.01);

        //declare/define new Nodes
        ToggleGroup e = new ToggleGroup();
        Slider sensitivity = new Slider();
        Slider volume = new Slider();
        Slider volume2 = new Slider();
        Spinner sensitivityS = new Spinner();
        Spinner volumeS = new Spinner();
        Spinner volumeS2 = new Spinner();
        Label sensitivityL = new Label("Sensitivity:");
        sensitivityL.setFont(subTitle);
        Label volumeL = new Label("Volume:");
        volumeL.setFont(subTitle);
        Label volumeLsub1 = new Label("Music");
        volumeLsub1.setFont(common);
        Label volumeLsub2 = new Label("Sound Effects");
        volumeLsub2.setFont(common);
        Label diffL = new Label("Difficulty:");
        diffL.setFont(subTitle);

        //adding audio
        Media media = new Media(getClass().getResource("/audio/swimming_pools.mp3").toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(0.5);

        //very easy button
        ToggleButton vEasy = new ToggleButton();
        Image img = new Image(getClass().getResource("/images/VeryEasyButton.png").toString());
        ImageView view = new ImageView(img);
        view.setFitHeight(height*0.1);
        view.setFitWidth(width*0.1);
        vEasy.setGraphic(view);
        vEasy.setId("veasy");
        vEasy.setOnAction(new difficulty());

        //easy button
        ToggleButton easy = new ToggleButton();
        Image img1 = new Image(getClass().getResource("/images/EasyButton.png").toString());
        ImageView view1 = new ImageView(img1);
        view1.setFitHeight(height*0.1);
        view1.setFitWidth(width*0.1);
        easy.setGraphic(view1);
        easy.setId("easy");
        easy.setOnAction(new difficulty());

        //medium button
        ToggleButton medium = new ToggleButton();
        Image img2 = new Image(getClass().getResource("/images/MediumButton.png").toString());
        ImageView view2 = new ImageView(img2);
        view2.setFitHeight(height*0.1);
        view2.setFitWidth(width*0.1);
        medium.setGraphic(view2);
        medium.setId("medium");
        medium.setOnAction(new difficulty());

        //hard button
        ToggleButton hard = new ToggleButton();
        Image img3 = new Image(getClass().getResource("/images/HardButton.png").toString());
        ImageView view3 = new ImageView(img3);
        view3.setFitHeight(height*0.1);
        view3.setFitWidth(width*0.1);
        hard.setGraphic(view3);
        hard.setId("hard");
        hard.setOnAction(new difficulty());

        //very hard button
        ToggleButton vHard = new ToggleButton();
        Image img4 = new Image(getClass().getResource("/images/VeryHardButton.png").toString());
        ImageView view4 = new ImageView(img4);
        view4.setFitHeight(height*0.1);
        view4.setFitWidth(width*0.1);
        vHard.setGraphic(view4);
        vHard.setId("vhard");
        vHard.setOnAction(new difficulty());


        HBox diffBox = new HBox(width*0.01);
        diffBox.getChildren().addAll(vEasy, easy, medium, hard, vHard);

        vEasy.setToggleGroup(e);
        easy.setToggleGroup(e);
        medium.setToggleGroup(e);
        hard.setToggleGroup(e);
        vHard.setToggleGroup(e);

        vEasy.setPrefSize(width*0.1, height*0.1);
        easy.setPrefSize(width*0.1, height*0.1);
        medium.setPrefSize(width*0.1, height*0.1);
        hard.setPrefSize(width*0.1, height*0.1);
        vHard.setPrefSize(width*0.1, height*0.1);

        vEasy.setStyle("-fx-background-color: #F0FFFF");
        easy.setStyle("-fx-background-color: #F0FFFF");
        medium.setStyle("-fx-background-color: #F0FFFF");
        hard.setStyle("-fx-background-color: #F0FFFF");
        vHard.setStyle("-fx-background-color: #F0FFFF");

        HBox volumeBox = new HBox(height*0.02);

        VBox musicBox = new VBox(height*0.01);
        VBox soundBox = new VBox(height*0.01);
        musicBox.getChildren().addAll(volumeLsub1, volumeS, volume);
        soundBox.getChildren().addAll(volumeLsub2, volumeS2, volume2);
        volumeBox.getChildren().addAll(musicBox, soundBox);
        volumeBox.setPrefSize(width*0.04, height*0.04);

        HBox sens2Box = new HBox(height*0.02);
        VBox sensitivityBox = new VBox(height*0.01);
        sensitivityBox.getChildren().addAll(sensitivityS, sensitivity);
        sensitivityBox.setPrefSize(width*0.06, height*0.06);
        sens2Box.getChildren().add(sensitivityBox);

        Label guide = new Label("Press Q to exit Settings");
        guide.setFont(subTitle);
        guide.setPadding(new Insets(height*0.1, 0, 0, 0));

        HBox title = new HBox();
        Region spacer1 = new Region();
        Region spacer2 = new Region();
        Label settings = new Label("Settings");
        settings.setFont(Font.font("Verdana", FontWeight.BOLD, 42));
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        title.getChildren().addAll(spacer1, settings, spacer2);
        setMargin(title, new Insets(0, 0, height*0.05, 0));

        //the main VBox that everything is built from
        VBox centerSettings = new VBox(height*0.01);
        centerSettings.getChildren().addAll(title, diffL, diffBox, volumeL, volumeBox, sensitivityL, sens2Box, guide);
        centerSettings.setPrefSize(width*0.6, height*0.4);
        setMargin(centerSettings, new Insets(height*0.1, width*0.2, height*0.1, width*0.2));
        this.setCenter(centerSettings);

        sensitivity.setMin(0);
        sensitivity.setMax(500);
        sensitivity.setValue(100);

        volume.setMin(0);
        volume.setMax(100);
        volume.setValue(50);


        volume2.setMin(0);
        volume2.setMax(100);
        volume2.setValue(50);

        sensitivityS.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 500));
        sensitivityS.getValueFactory().setValue(100);

        volumeS.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
        volumeS.getValueFactory().setValue(50);
        volumeS2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
        volumeS2.getValueFactory().setValue(50);

        //the three bottom buttons
        Font buttons = new Font("Verdana", 36);
        Button Exit = new Button("Exit");
        Exit.setFont(buttons);
        Button Credits = new Button("Credits");
        Credits.setFont(buttons);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Exit.setPrefSize(width*0.2, height*0.1);
        Credits.setPrefSize(width*0.2, height*0.1);
        HBox bottomBox = new HBox(width*0.01);
        Exit.setOnAction(event1 -> exit());
        Credits.setOnAction(event -> credits());
        this.setBottom(bottomBox);
        bottomBox.getChildren().addAll(Credits, spacer, Exit);

        //set all of the margins of buttons
        setMargin(bottomBox, bottomMarg);
        setMargin(volumeBox, leftMarg);
        setMargin(diffBox, rightMarg);

        //binding sensitivity
        sensitivity.valueProperty().addListener(new ChangeListener<Number>() {

            /**
             * Observes changes in sensitivity.valueProperty()
             * @param observableValue observable value from ChangeListener
             * @param oldValue old value of sensitivity.valueProperty()
             * @param newValue new value of sensitivity.valueProperty()
             */
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                sensitivityS.getValueFactory().setValue(newValue.intValue());
                sens = newValue.intValue();
                root.setVals(sens, vol2);
            }
        });
        sensitivityS.valueProperty().addListener(new ChangeListener<Number>() {
            /**
             * Observes changes in sensitivityS.valueProperty()
             * @param observableValue observable value from ChangeListener
             * @param oldValue old value of sensitivityS.valueProperty()
             * @param newValue new value of sensitivityS.valueProperty()
             */
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                sensitivity.setValue(newValue.intValue());
            }
        });

        //binding music
        volume.valueProperty().addListener(new ChangeListener<Number>() {
            /**
             * Observes changes in volume.valueProperty()
             * @param observableValue observable value from ChangeListener
             * @param oldValue old value of volume.valueProperty()
             * @param newValue new value of volume.valueProperty()
             */
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                volumeS.getValueFactory().setValue(newValue.intValue());
                mediaPlayer.setVolume(newValue.doubleValue()/100);

            }
        });
        volumeS.valueProperty().addListener(new ChangeListener<Number>() {
            /**
             * Observes changes in volumeS.valueProperty()
             * @param observableValue observable value from ChangeListener
             * @param oldValue old value of volumeS.valueProperty()
             * @param newValue new value of volumeS.valueProperty()
             */
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                volume.setValue(newValue.intValue());
            }
        });


        //binding sound effects
        volume2.valueProperty().addListener(new ChangeListener<Number>() {
            /**
             * Observes changes in volume2.valueProperty()
             * @param observableValue observable value from ChangeListener
             * @param oldValue old value of volume2.valueProperty()
             * @param newValue new value of volume2.valueProperty()
             */
        public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
            volumeS2.getValueFactory().setValue(newValue.intValue());
            vol2 = newValue.intValue();
            root.setVals(sens, vol2);

        }
    });
        volumeS2.valueProperty().addListener(new ChangeListener<Number>() {
            /**
             * Observes changes in volumeS2.valueProperty()
             * @param observableValue observable value from ChangeListener
             * @param oldValue old value of volumeS2.valueProperty()
             * @param newValue new value of volumeS2.valueProperty()
             */
        public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
            volume2.setValue(newValue.intValue());
        }
    });

    }

    private void credits() {
        //make a new pop-out stage
        Stage credits = new Stage();
        credits.setTitle("Aim Studio - Credits");
        credits.setScene(new Scene(new Credits(), 550, 450));
        credits.setResizable(false);
        credits.show();
    }

    private void exit(){
        System.exit(0);
    }

    private class difficulty implements EventHandler<ActionEvent> {
        /**
         * calls handle() everytime a ToggleButton is pressed
         * @param e calls the Node that the ActionEvent sourced from
         */
        public void handle (ActionEvent e){
            ToggleButton source = (ToggleButton) e.getSource();
            //pre-defined ID of each toggle button is a value
            root.setDifficulty(source.getId());

        }
    }

}



