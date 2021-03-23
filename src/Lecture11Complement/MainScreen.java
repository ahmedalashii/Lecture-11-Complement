package Lecture11Complement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MainScreen extends Stage {

    // This Programm is By Ahmed Hesham Alashi 120191156 ..
    private MenuBar menuBar;
    private Menu menuFile, menuColor, menuAbout;
    private MenuItem fileItemOpen, fileItemClose, fileItemSave, fileItemExit, colorItemGold, colorItemCyan, colorItemRed, aboutItem;
    private TextArea textArea;
    private Slider sliderFontSize;
    private Label aboutMeLabel;

    private Window thisWindow; // I created this instance variable window to reach to it in MyEventHandler Class Below. (this stage class)
    private Stage aboutStage; // this stage is for About Me Information ..

    public MainScreen() {
        // Window and Stage :
        thisWindow = this;
        this.aboutStage = new Stage();
        // Labels :
        aboutMeLabel = new Label("My Name is Ahmed Alashi and I'm 20 years old.\nMy ID : 120191156\nI'm Studying Programming 3");
        // Menus :
        menuBar = new MenuBar();
        menuFile = new Menu("File");
        menuColor = new Menu("Color");
        menuAbout = new Menu("About");
        // Menu Items of File :
        fileItemOpen = new MenuItem("Open");
        fileItemClose = new MenuItem("Close");
        fileItemSave = new MenuItem("Save");
        fileItemExit = new MenuItem("Exit");
        // Menu Items of Color :
        colorItemGold = new MenuItem("Gold");
        colorItemCyan = new MenuItem("Cyan");
        colorItemRed = new MenuItem("Red");
        // Menu Items of About :
        aboutItem = new MenuItem("About Me");
        // Add MenuItems and Menus :
        menuFile.getItems().addAll(fileItemOpen, fileItemClose, fileItemSave, fileItemExit);
        menuColor.getItems().addAll(colorItemGold, colorItemCyan, colorItemRed);
        menuAbout.getItems().add(aboutItem);
        menuBar.getMenus().addAll(menuFile, menuColor, menuAbout);
        // Events and Actions :
        MyEventHandler myEvenetHandler = new MyEventHandler();
        fileItemOpen.setOnAction(myEvenetHandler);
        fileItemClose.setOnAction(myEvenetHandler);
        fileItemSave.setOnAction(myEvenetHandler);
        fileItemExit.setOnAction(myEvenetHandler);
        colorItemGold.setOnAction(myEvenetHandler);
        colorItemCyan.setOnAction(myEvenetHandler);
        colorItemRed.setOnAction(myEvenetHandler);
        aboutItem.setOnAction(myEvenetHandler);
        // TextArea :
        textArea = new TextArea("Playing With JavaFX Advanced Controls");
        textArea.setMinHeight(150);
        // Slider Proopertites :
        sliderFontSize = new Slider(5, 35, 12);
        sliderFontSize.setMajorTickUnit(5);
        sliderFontSize.setMinorTickCount(4);
        sliderFontSize.setShowTickLabels(true);
        sliderFontSize.setShowTickMarks(true);
        sliderFontSize.setSnapToTicks(true);
        sliderFontSize.valueProperty().addListener(e -> {
            textArea.setStyle("-fx-font-size:" + sliderFontSize.getValue() + "pt;");
        });

        ComboBox<String> comboBoxLinks = new ComboBox();
        comboBoxLinks.getItems().addAll("Instructor GitHub", "JavaFX Guide", "My GitHub Account");
        comboBoxLinks.getSelectionModel().selectFirst();
        WebView webView = new WebView();
        webView.getEngine().load("https://github.com/aashgar"); // by default
        comboBoxLinks.setOnAction(e -> {
            if (comboBoxLinks.getValue().equals("Instructor GitHub")) {
                webView.getEngine().load("https://github.com/aashgar");
            } else if (comboBoxLinks.getValue().equals("JavaFX Guide")) {
                webView.getEngine().load("https://en.wikipedia.org/wiki/JavaFX");
            } else if (comboBoxLinks.getValue().equals("My GitHub Account")) {
                webView.getEngine().load("https://github.com/ahmedalashii");
            }
        });
        // Hbox and Vbox :
        HBox hbox1 = new HBox(15, comboBoxLinks, webView);
        HBox aboutHbox = new HBox(aboutMeLabel);
        aboutHbox.setAlignment(Pos.CENTER);

        VBox vbox1 = new VBox(15, textArea, sliderFontSize, hbox1);
        // Border Pane :
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);
        borderPane.setCenter(vbox1);
        // Scene :
        Scene scene = new Scene(borderPane, 1000, 600);
        Scene aboutScene = new Scene(aboutHbox, 400, 300);
        // Methods From Stage Classes
        // This Stage
        setScene(scene); // or this.setScene(scene);
        setTitle("Advanced JavaFX Screen");
        setResizable(false);
        // About Stage :
        aboutStage.setTitle("Some Information About Me");
        aboutStage.setScene(aboutScene);
        aboutStage.setResizable(false);
    }

    private class MyEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("src/CH2Part1Apps"));
            if (event.getSource() == fileItemOpen) { // or we can use switch statement
                File openSelectedFile = fileChooser.showOpenDialog(thisWindow); // I created an instance variable thisWindow to reach to it here.
                if (!textArea.getText().isEmpty()) {
                    textArea.appendText("\n");
                }
                try {
                    Scanner scanner = new Scanner(openSelectedFile);
                    while (scanner.hasNext()) {
                        textArea.appendText(scanner.nextLine() + "\n");
                    }
                    scanner.close();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            } else if (event.getSource() == fileItemClose) {
                textArea.clear();
            } else if (event.getSource() == fileItemSave) {
                try {
                    File saveSelectedFile = fileChooser.showSaveDialog(thisWindow); // this is for store the selected save file in an object just to use it below to delete it to update the data inside it.
                    saveSelectedFile.delete(); // we delete the previous file just to update the data inside it not to append the data to the previous data.
                    FileWriter fr = new FileWriter(saveSelectedFile, true); // append
                    PrintWriter pw = new PrintWriter(fr);
                    pw.print(textArea.getText());
                    pw.close();
                    fr.close();
                } catch (IOException ex) {
                    System.err.println("You didn't Choose any file!");
                }
            } else if (event.getSource() == fileItemExit) {
                close();
                if (aboutStage.isShowing()) { // this if statement is just if I want to close aboutStage with the default stage
                    aboutStage.close();
                }
            } else if (event.getSource() == colorItemGold) {
                textArea.setStyle("-fx-text-fill:gold;");
            } else if (event.getSource() == colorItemCyan) {
                textArea.setStyle("-fx-text-fill:cyan;");
            } else if (event.getSource() == colorItemRed) {
                textArea.setStyle("-fx-text-fill:red;");
            } else if (event.getSource() == aboutItem) {
                aboutStage.show();
            }
        }
    }
}
