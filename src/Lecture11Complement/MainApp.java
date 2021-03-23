package Lecture11Complement;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Label labelTitle, labelError;
    private TextField textFieldLoginName;
    private PasswordField passwordField;
    private Button buttonSubmit, buttonCancel;

    @Override
    public void start(Stage primaryStage) throws Exception {
        labelTitle = new Label("Login Information : ");
        textFieldLoginName = new TextField();
        textFieldLoginName.setPromptText("Login Name");
        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        labelError = new Label("Initial Text");
        labelError.setId("label-error");
        VBox vBox1 = new VBox(10, labelTitle, textFieldLoginName, passwordField, labelError);
        vBox1.setAlignment(Pos.CENTER);
        vBox1.setStyle("-fx-border-color: red"); // inline css styling
        buttonSubmit = new Button("Submit");
        buttonCancel = new Button("Cancel");
        buttonSubmit.setOnAction(new MyEventHandler());
        buttonCancel.setOnAction(new MyEventHandler());

        HBox hBox1 = new HBox();
        hBox1.getChildren().addAll(buttonSubmit, buttonCancel);
        hBox1.setSpacing(20);
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setStyle("-fx-border-color : lime");

        VBox vBox2 = new VBox(10, vBox1, hBox1);
        vBox2.setAlignment(Pos.CENTER);
        vBox2.setStyle("-fx-border-color : navy");
        vBox2.setPadding(new Insets(10, 20, 10, 20));
        vBox2.getStyleClass().add("vbox");
        FlowPane flowPane = new FlowPane(vBox2);
        flowPane.setAlignment(Pos.CENTER);

        Scene scene = new Scene(flowPane, 400, 300);
        scene.getStylesheets().add("file:src/Ch1Part1Apps/style.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("My First JavaFX Application !");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private class MyEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            if (event.getSource() == buttonSubmit) {
                File loginDataFile = new File("src/CH2Part1Apps/Login Data.txt"); // PS : the file Login Data should contains just correct login data because it's a database for the correct Login Information!
                int counter = 0; // loginData[0] >> username , loginData[1] >> password, and so on ..
                boolean isValid = false;
                try {
                    Scanner input = new Scanner(loginDataFile);
                    while (input.hasNextLine()) {
                        String tempLoginData = input.nextLine();
                        String[] loginData = tempLoginData.split(",");
                        if (textFieldLoginName.getText().equals(loginData[counter]) && passwordField.getText().equals(loginData[++counter])) {
                            labelError.setText("Login Succesful !"); // or Access Granted !
                            isValid = true;
                            new MainScreen().show();
                            break;
                        }
                    }
                    if (!isValid) { // if it's false then it means that the username or password is wrong ..
                        labelError.setText("Invalid Username/Password !");
                    }
                } catch (FileNotFoundException ex) {
                    System.err.println("Login Information File Not Found !");
                }
            } else if (event.getSource() == buttonCancel) {
                textFieldLoginName.clear();
                passwordField.clear();
                // or :
                // textFieldLoginName.setText(");
                // passwordField.setText(");
            }
        }

    }
}
