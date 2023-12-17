package com.project.connect4;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private static final int columns = 7;
    private static final int rows = 6;
    private static final int circleDiameter = 80;
    private static final String diskColor1 = "#24303E";
    private static final String diskColor2 = "4CAA88";

    private static String playerOne = "Player One";
    private static String playerTwo = "Player Two";

    private boolean isPlayerOneTurn = true;

    String[][] insertedDiscArray = new String[rows][columns];

    @FXML
    public GridPane rootGridPane;

    @FXML
    public Label playerNameLabel;

    @FXML
    public Pane insertedDiscPane;

    @FXML
    public Pane playerTurnPane;

    public void createLayout() {
        Pane menuPane = (Pane) rootGridPane.getChildren().get(0);
        MenuBar menuBar = createMenuBar();

        menuBar.setPrefWidth(columns*circleDiameter + 60 + 300);

        menuPane.getChildren().add(menuBar);

        createPlayground();
    }

    public MenuBar createMenuBar(){
        Menu fileMenu = new Menu("File");
        Menu helpMenu = new Menu("Help");

        MenuItem newGame = new MenuItem("New Game");
        newGame.setOnAction(actionEvent -> resetGame());

        MenuItem resetGame = new MenuItem("Reset Game");
        resetGame.setOnAction(actionEvent -> resetGame());

        SeparatorMenuItem separatorMenuItem1 = new SeparatorMenuItem();
        MenuItem exitGame = new MenuItem("Exit Game");
        exitGame.setOnAction(actionEvent -> exitGame());

        MenuItem aboutConnect4 = new MenuItem("About Connect 4");
        aboutConnect4.setOnAction(actionEvent -> aboutConnect4());

        SeparatorMenuItem separatorMenuItem2 = new SeparatorMenuItem();
        MenuItem aboutMe = new MenuItem("About Me");
        aboutMe.setOnAction(actionEvent -> aboutMe());

        fileMenu.getItems().addAll(newGame, resetGame, separatorMenuItem1, exitGame);
        helpMenu.getItems().addAll(aboutConnect4, separatorMenuItem2, aboutMe);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, helpMenu);

        return menuBar;
    }

    private void aboutMe() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Me");
        alert.setHeaderText("Prathamesh Warankar");
        alert.setContentText("I have great interest in coding. This is a game called Connect 4 developed by me." );

        alert.show();
    }

    private void aboutConnect4() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Connect 4");
        alert.setHeaderText("How to play?");
        Text text = new Text("Connect 4 is a two player connection game in which the player first chooses a color and then takes turn dropping colored disks from the top into a seven column, six row vertically suspended grid. The pieces fall straight down, occupying the next available space within the column. The objective of the game is to be first  to form a horizontal, vertical, or diagonal  line of four  of one's own disks. Connect 4 is a solved game. ");

        text.setWrappingWidth(350);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setContent(text);

        // Add padding to the DialogPane
        dialogPane.setPadding(new Insets(0, 10, 0, 10)); // Customize padding as needed

        alert.showAndWait();
    }

    private void exitGame() {
        Platform.exit();
        System.exit(0);
    }

    private void resetGame() {
        insertedDiscPane.getChildren().clear();

        for (int i=0; i < rows; i++){
            for (int j=0; j < columns; j++){
                insertedDiscArray[i][j] = null;
            }
        }

        isPlayerOneTurn = true;
        playerNameLabel.setText(playerOne);

        createPlayground();
    }

    public void createPlayground(){
        Shape rectangleWithHoles = createPlaygroundGrid();
        rectangleWithHoles.setFill(Color.WHITE);
        rootGridPane.add(rectangleWithHoles, 0,1);

        for (int col=0; col<columns; col++){
            Rectangle rectangle = createClickableColumns();
            rectangle.setTranslateX(col*circleDiameter + col*5 + 15);
            rootGridPane.add(rectangle, 0, 1);

            int finalCol = col;
            rectangle.setOnMouseClicked(mouseEvent -> insertDisc(finalCol));
        }
    }

    public Shape createPlaygroundGrid(){
        Shape rectangleWithHoles = new Rectangle(columns*circleDiameter + 60, rows*circleDiameter + 60);

        for (int row = 0; row < rows; row++){
            for (int col = 0; col < columns; col++) {
                Circle circle = new Circle();
                circle.setRadius(circleDiameter/2);
                circle.setCenterX(circleDiameter/2 + col*circleDiameter + col*5 + 15);
                circle.setCenterY(circleDiameter/2 + row*circleDiameter + row*5 + 15);

                rectangleWithHoles = Shape.subtract(rectangleWithHoles, circle);
            }
        }

        return rectangleWithHoles;
    }

    public Rectangle createClickableColumns(){
        Rectangle rectangle = new Rectangle();
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setHeight(rows*circleDiameter + 60);
        rectangle.setWidth(circleDiameter);
        rectangle.setOnMouseEntered(mouseEvent -> rectangle.setFill(Color.valueOf("#eeeeee28")));
        rectangle.setOnMouseExited(mouseEvent -> rectangle.setFill(Color.TRANSPARENT));

        return rectangle;
    }

    public void insertDisc(int col) {
        Disc disc = new Disc(isPlayerOneTurn, col);

        int row = rows - 1;

        while (row > 0){
            if(insertedDiscArray[row][col] == null){
                break;
            }
            else{
                row--;
            }
        }

        if (row < 0) {
            return;
        }

        if(disc.getFill().toString().equals(Color.valueOf(diskColor1).toString())){
            insertedDiscArray[row][col] = "d1";
        } else {
            insertedDiscArray[row][col] = "d2";
        }

        insertedDiscPane.getChildren().add(disc);

//        if(disc.getFill().toString().equals(Color.valueOf(diskColor1).toString())){
//            System.out.println("Player One played");
//        }
//        else {
//            System.out.println("Player Two played");
//        }

        disc.setCenterX(circleDiameter /2 + col*circleDiameter + col*5 + 15);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), disc);
        translateTransition.setToY(row*circleDiameter + row*5);
        
        int currRow = row;
        String currPlayer = isPlayerOneTurn?"d1":"d2";

        translateTransition.setOnFinished(actionEvent -> {
            if (isGameEnded(currRow, col, insertedDiscArray, currPlayer)) {
                gameOver();
            }

            isPlayerOneTurn = !isPlayerOneTurn;
            playerNameLabel.setText(isPlayerOneTurn ? playerOne : playerTwo);
        });
        translateTransition.play();
    }

    private void gameOver() {
        String winner = isPlayerOneTurn?"Winner is: Player 1":"Winner is: Player 2";

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Connect 4");
        alert.setHeaderText(winner);
        alert.setContentText("Want yo play again?");

        ButtonType yesBtn = new ButtonType("Yes");
        ButtonType noBtn = new ButtonType("No, exit");
        alert.getButtonTypes().setAll(yesBtn, noBtn);

        Platform.runLater(() -> {
            Optional<ButtonType> btnClicked = alert.showAndWait();
            if(btnClicked.get() == yesBtn){
                resetGame();
            } else {
                exitGame();
            }
        });
    }

    private boolean isGameEnded(int currRow, int col, String[][] insertedDiscArray, String currPlayer) {

        return isHorizontalCombination(currRow, col, insertedDiscArray, currPlayer) ||
                isVerticalCombination(currRow, col, insertedDiscArray, currPlayer) ||
                isLeftDiagonalCombination(currRow, col, insertedDiscArray, currPlayer)||
                isRightDiagonalCombination(currRow, col, insertedDiscArray, currPlayer);
    }

    private boolean isRightDiagonalCombination(int row, int col, String[][] insertedDiscArray, String currPlayer) {
        int currRow = row;
        int currCol = col;
        int validCount = 0;

        while(currRow>=row-3 && currRow >= 0 && currCol < columns){
            if(insertedDiscArray[currRow][currCol] == null){
                break;
            }
            if(insertedDiscArray[currRow][currCol].equals(currPlayer)){
                validCount++;
            } else {
                break;
            }
            currRow--;
            currCol++;
        }

        if( validCount == 4){
            return true;
        } else {
            currRow = row;
            currCol = col;
            validCount = 0;
            while(currRow<=row+3 && currRow < rows && currCol >= 0){
                if(insertedDiscArray[currRow][currCol] == null){
                    break;
                }
                if(insertedDiscArray[currRow][currCol].equals(currPlayer)){
                    validCount++;
                } else {
                    break;
                }
                currRow++;
                currCol--;
            }
        }
        return validCount == 4;
    }

    private boolean isLeftDiagonalCombination(int row, int col, String[][] insertedDiscArray, String currPlayer) {
        int currRow = row;
        int currCol = col;
        int validCount = 0;

        while(currRow>=row-3 && currRow >= 0 && currCol >= 0){
            if(insertedDiscArray[currRow][currCol] == null){
                break;
            }
            if(insertedDiscArray[currRow][currCol].equals(currPlayer)){
                validCount++;
            } else {
                break;
            }
            currRow--;
            currCol--;
        }

        if( validCount == 4){
            return true;
        } else {
            currRow = row;
            currCol = col;
            validCount = 0;
            while(currRow<=row+3 && currRow < rows && currCol < columns){
                if(insertedDiscArray[currRow][currCol] == null){
                    break;
                }
                if(insertedDiscArray[currRow][currCol].equals(currPlayer)){
                    validCount++;
                } else {
                    break;
                }
                currRow++;
                currCol++;
            }
        }
        return validCount == 4;
    }

    private boolean isVerticalCombination(int row, int col, String[][] insertedDiscArray, String currPlayer) {
        int currRow = row;
        int validCount = 0;

        while(currRow<=row+3 && currRow < rows ){
            if(insertedDiscArray[currRow][col] == null){
                break;
            }
            if(insertedDiscArray[currRow][col].equals(currPlayer)){
                validCount++;
            } else {
                break;
            }
            currRow++;
        }

//        if(validCount == 4) {
//            return true;
//        } else {
//            currRow = row;
//            validCount = 0;
//            while (currRow<=row+3 && currRow < rows){
//                if(insertedDiscArray[currRow][col] == null){
//                    break;
//                }
//                if(insertedDiscArray[currRow][col].equals(currPlayer)){
//                    validCount++;
//                } else {
//                    break;
//                }
//                currRow++;
//            }
//        }

        return validCount == 4;
    }

    private boolean isHorizontalCombination(int row, int col, String[][] insertedDiscArray, String currPlayer) {
        int currCol = col;
        int validCount = 0;
        while(currCol>=col-3 && currCol >= 0 ){
            if(insertedDiscArray[row][currCol] == null){
                break;
            }
            if(insertedDiscArray[row][currCol].equals(currPlayer)){
                validCount++;
            } else {
                break;
            }
            currCol--;
        }

        if(validCount == 4) {
            return true;
        } else {
            currCol = col;
            validCount = 0;
            while (currCol<=col+3 && currCol < columns){
                if(insertedDiscArray[row][currCol] == null){
                    break;
                }
                if(insertedDiscArray[row][currCol].equals(currPlayer)){
                    validCount++;
                } else {
                    break;
                }
                currCol++;
            }
        }

        return validCount == 4;
    }

    private static class Disc extends Circle{

        private final boolean isPlayerOneTurn;

        public Disc(boolean isPlayerOneTurn, int col){
            this.isPlayerOneTurn = isPlayerOneTurn;
            setFill(isPlayerOneTurn? Color.valueOf(diskColor1): Color.valueOf(diskColor2));
            setRadius(circleDiameter/2);
            setCenterY(circleDiameter/2 + 15);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
