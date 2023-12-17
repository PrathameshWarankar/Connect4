package com.project.connect4;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    private Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
        GridPane rootGridPane = loader.load();
        controller = loader.getController();
        controller.createLayout();

//        Pane menuPane = (Pane) rootGridPane.getChildren().get(0);
//        MenuBar menuBar = createMenu();
//        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
//        menuPane.getChildren().add(menuBar);

        Scene scene = new Scene(rootGridPane);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Connect Four");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

//    private MenuBar createMenu(){
//        Menu fileMenu = new Menu("File");
//        Menu helpMenu = new Menu("Help");
//
//        MenuItem newGame = new MenuItem("New Game");
//        newGame.setOnAction(actionEvent -> resetGame());
//
//        MenuItem resetGame = new MenuItem("Reset Game");
//        resetGame.setOnAction(actionEvent -> resetGame());
//
//        SeparatorMenuItem separatorMenuItem1 = new SeparatorMenuItem();
//        MenuItem exitGame = new MenuItem("Exit Game");
//        exitGame.setOnAction(actionEvent -> exitGame());
//
//        MenuItem aboutConnect4 = new MenuItem("About Connect 4");
//        aboutConnect4.setOnAction(actionEvent -> aboutConnect4());
//
//        SeparatorMenuItem separatorMenuItem2 = new SeparatorMenuItem();
//        MenuItem aboutMe = new MenuItem("About Me");
//        aboutMe.setOnAction(actionEvent -> aboutMe());
//
//        fileMenu.getItems().addAll(newGame, resetGame, separatorMenuItem1, exitGame);
//        helpMenu.getItems().addAll(aboutConnect4, separatorMenuItem2, aboutMe);
//
//        MenuBar menuBar = new MenuBar();
//        menuBar.getMenus().addAll(fileMenu, helpMenu);
//
//        return menuBar;
//    }

//    private void aboutMe() {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("About Me");
//        alert.setHeaderText("Prathamesh Warankar");
//        alert.setContentText("I have great interest in coding. This is a game called Connect 4 developed by me." );
//
//        alert.show();
//    }
//
//    private void aboutConnect4() {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("About Connect 4");
//        alert.setHeaderText("How to play?");
//        Text text = new Text("Connect 4 is a two player connection game in which the " +
//                "player first chooses a color and then takes turn dropping colored disks" +
//                "from the top into a seven column, six row vertically suspended grid. " +
//                "The pieces fall straight down, occupying the next available space within the column. " +
//                "The objective of the game is to be first  to form a horizontal, vertical, " +
//                "or diagonal  line of four  of one's own disks. Connect 4 is a solved game. ");
//
//        text.setWrappingWidth(350);
//        alert.getDialogPane().setContent(text);
//
//        alert.show();
//    }
//
//    private void exitGame() {
//        Platform.exit();
//        System.exit(0);
//    }
//
//    private void resetGame() {
//
//    }

    public static void main(String[] args) {
        launch(args);
    }
}
