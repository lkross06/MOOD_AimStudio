package org.headroyce.lross2024;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

/**
 * credits text, exists in its own Stage created on button click in EscGUI object
 */
public class Credits extends BorderPane {
    private Label creditsList;

    /**
     * constructor that writes the text of creditsList, sets Font and renders to Stage
     */
    public Credits(){
        creditsList = new Label();
        creditsList.setText(
                "Aim Studio is a collaborative project by \rLucas Ross, Aaron Hobson, " +
                        "and Everett Villiger, as \rtheir submission for the 2021 Mobile & " +
                        "Object-Oriented \rDesign\"Design Project\". This application " +
                        "was \rdeveloped using Java and the JavaFX library. \rPlease enjoy!"
        );
        creditsList.setFont(new Font("Verdana", 18.0));
        this.setCenter(creditsList);
    }
}
