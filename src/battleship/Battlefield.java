package battleship;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class Battlefield extends GridPane {
    /**
     * Grid side length.
     */
    private double sideLength;

    /**
     * Grid element side length;
     */
    private double cellSideLength;

    /**
     * Decoration symbol.
     */
    private static final String ANCHOR = "⚓";

    /**
     * Field mark saying that player hit empty cell.
     */
    private static final String MISS = "•";

    /**
     * Field mark saying that ship was hit.
     */
    private static final String HIT = "╳";

    /**
     * Text area for displaying game logs information.
     */
    private TextArea logArea;

    /**
     * Text area for displaying current game state information.
     */
    private TextArea gameInfoArea;

    /**
     * Current game ocean instance.
     */
    private Ocean ocean;

    /**
     * Helper array to process keyboard input.
     */
    Short[] keyboardInput = new Short[2];

    /**
     * Constructor which purpose is to set up field and information areas and create ocean
     *
     * @param logArea      area for displaying game logs information
     * @param gameInfoArea area for displaying current game state information
     */
    public Battlefield(TextArea logArea, TextArea gameInfoArea) {
        this.logArea = logArea;
        this.gameInfoArea = gameInfoArea;
        this.sideLength = (double) Math.min(Utils.getScreenWidth(), Utils.getScreenHeight()) / 2.5;
        this.cellSideLength = sideLength / 11;
        setStyling();
        setAnchor();
        setBordersCaption();
        generateNewOcean();
    }

    /**
     * Getter for sideLength
     *
     * @return double size of field side
     */
    public double getSideLength() {
        return sideLength;
    }

    /**
     * Getter for cellSideLength
     *
     * @return double size of cell side length
     */
    public double getCellSideLength() {
        return cellSideLength;
    }

    /**
     * Method to set field styling.
     */
    public void setStyling() {
        this.setHgap(1);
        this.setVgap(2);
        this.setPadding(new Insets(5, 5, 6, 5));
        this.setStyle("-fx-background-color: lightblue");
        this.setMaxSize(sideLength, sideLength);
        GridPane.setMargin(this, new Insets(0, 0, 0, 10));
        setLogAreaStyling();
        setGameInfoAreaStyling();
    }

    /**
     * Method to set log area styling.
     */
    public void setLogAreaStyling() {
        logArea.setMaxWidth(sideLength + cellSideLength / 4 * 3);
        logArea.setMinWidth(sideLength + cellSideLength / 4 * 3);
        logArea.setEditable(false);
        GridPane.setMargin(logArea, new Insets(20, 10, 0, 10));
    }

    /**
     * Method to set game info area styling.
     */
    public void setGameInfoAreaStyling() {
        gameInfoArea.setMaxWidth(sideLength + cellSideLength / 4 * 3);
        gameInfoArea.setMinWidth(sideLength + cellSideLength / 4 * 3);
        gameInfoArea.setMaxHeight(sideLength + cellSideLength + 5);
        gameInfoArea.setMinHeight(sideLength + cellSideLength + 5);
        gameInfoArea.setEditable(false);
        GridPane.setMargin(gameInfoArea, new Insets(0, 0, 0, 10));
        gameInfoArea.setStyle("-fx-font-size: large;");
    }

    /**
     * Method to set decorating icon to field.
     */
    private void setAnchor() {
        var anchorCell = new BattlefieldCell(this);
        anchorCell.setText(ANCHOR);
        anchorCell.setMaxSize(cellSideLength, cellSideLength);
        anchorCell.setMinSize(cellSideLength, cellSideLength);
        this.add(anchorCell, 0, 0);
    }

    /**
     * Helper method to set field side caption.
     *
     * @param isRow true if current caption location is row, false – otherwise
     */
    private void setCaption(boolean isRow) {
        for (int i = 1; i < 10 + 1; ++i) {
            var captionCell = new BattlefieldCell(this);
            captionCell.setMaxSize(cellSideLength, cellSideLength);
            captionCell.setMinSize(cellSideLength, cellSideLength);
            captionCell.setPrefSize(cellSideLength, cellSideLength);
            captionCell.setText(Integer.toString(i - 1));
            this.add(captionCell, isRow ? 0 : i, isRow ? i : 0);
        }
    }

    /**
     * Method to set to field side caption about coordinates
     */
    private void setBordersCaption() {
        setCaption(true);
        setCaption(false);
    }

    /**
     * Method which generates new ocean instance and places ships randomly in it.
     */
    private void generateNewOcean() {
        ocean = new Ocean();
        ocean.placeAllShipsRandomly();
        logArea.clear();
        gameInfoArea.clear();
        setBattlefield();
        displayGameInfo();
    }

    /**
     * Method to display warning if player tries to shoot at already shot coordinate.
     */
    public void showWarning() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.NONE, "Warning!", ButtonType.OK);
            alert.setTitle("Warning!");
            alert.setContentText("You have already shoot at this coordinate!");
            alert.showAndWait();
        });
    }

    /**
     * Method to check whether player has hit a ship or not.
     *
     * @param cell   battlefield cell with coordinates (row, column)
     * @param row    where player has shoot
     * @param column where player has shoot
     */
    private void hitOrMiss(BattlefieldCell cell, int row, int column) {
        if (cell.getText().isEmpty()) {
            if (ocean.shootAt(row, column)) {
                cell.setText(HIT);
                Ship ship = ocean.getShipArray()[row][column];
                if (ship.isSunk()) {
                    displaySunk(ship);
                    logArea.appendText(String.format("You just sunk a %s!\n", ship.getShipType()));
                } else {
                    logArea.appendText("Hit!\n");
                }
            } else {
                cell.setText(MISS);
                logArea.appendText("Miss!\n");
            }
        } else {
            showWarning();
            logArea.appendText("Re-shot at coordinate.\n");
        }
    }

    /**
     * Method to set sunk ship cells red.
     *
     * @param bowRow       row (0 to 9) which contains the bow (front) of the ship
     * @param bowColumn    column (0 to 9) which contains the bow (front) of the ship
     * @param shipLength   number of squares occupied by the ship
     * @param isHorizontal true if the ship occupies a single row, false otherwise
     */
    private void setSunkStyle(int bowRow, int bowColumn, int shipLength, boolean isHorizontal) {
        int bow = isHorizontal ? bowColumn : bowRow;
        for (int i = bow; i < bow + shipLength; ++i) {
            var buttons = this.getChildren();
            for (var b : buttons) {
                if (GridPane.getRowIndex(b) == (isHorizontal ? bowRow : i) &&
                        GridPane.getColumnIndex(b) == (isHorizontal ? i : bowColumn)) {
                    b.setStyle("-fx-background-radius: 0; " +
                            "-fx-border-style: none; " +
                            "-fx-background-color: lavenderblush;");
                }
            }
        }
    }

    /**
     * Method to display sunk ship style on field.
     *
     * @param ship which is sunk
     */
    private void displaySunk(Ship ship) {
        int bowRow = ship.getBowRow() + 1;
        int bowColumn = ship.getBowColumn() + 1;
        int shipLength = ship.getLength();
        setSunkStyle(bowRow, bowColumn, shipLength, ship.isHorizontal());
    }

    /**
     * Method to display game information after its end.
     */
    public void displayFinalGameInfo() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.NONE, "Warning!", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Game over!");
            alert.setContentText(getFinalGameInfo());
            alert.showAndWait().ifPresent(type -> {
                if (type == ButtonType.YES) {
                    generateNewOcean();
                } else {
                    System.exit(0);
                }
            });
        });
    }

    /**
     * Method to display information about the game such as amount of shots fired, amount of hits and amount of sunk ships.
     */
    private String getFinalGameInfo() {
        var sb = new StringBuilder();
        sb.append(String.format("Shots fired: %s\nHits: %s\nShips sunk: %s\nBest possible score: 20",
                ocean.getShotsFired(), ocean.getHitCount(), ocean.getShipsSunk()));
        sb.append("\nWanna play again?");

        return sb.toString();
    }

    /**
     * Method to set up clear battlefield.
     */
    private void setBattlefield() {
        for (var i = 1; i < 10 + 1; ++i) {
            for (var j = 1; j < 10 + 1; ++j) {
                var cell = new BattlefieldCell(this);
                cell.setOnAction(e -> {
                    int row = GridPane.getRowIndex(cell) - 1;
                    int column = GridPane.getColumnIndex(cell) - 1;
                    processMove(cell, row, column);
                });

                this.add(cell, j, i);
            }
        }
    }

    /**
     * Method to process one player game move.
     *
     * @param cell where player has shot
     * @param row x-coordinate of cell on field
     * @param column y-coordinate of cell on field
     */
    private void processMove(BattlefieldCell cell, int row, int column) {
        logArea.appendText(String.format("%d %d\n", row, column));
        hitOrMiss(cell, row, column);
        displayGameInfo();
        if (ocean.isGameOver()) {
            displayFinalGameInfo();
        }
    }

    /**
     * Method to display current game state.
     */
    private void displayGameInfo() {
        gameInfoArea.clear();
        gameInfoArea.appendText(String.format("\n\nSHOTS FIRED: %s\n\n\n", ocean.getShotsFired()));
        gameInfoArea.appendText(String.format("HITS: %s\n\n\n", ocean.getHitCount()));
        var shipsSunk = ocean.getShipsSunk();
        var shipsShot = ocean.getShipsShot();
        gameInfoArea.appendText(String.format("SHIPS SUNK: %s\n\n\n", shipsSunk));
        gameInfoArea.appendText(String.format("SHIPS SHOT: %s\n\n\n", shipsShot));
        gameInfoArea.appendText(String.format("WHOLE SHIPS: %s", 10 - shipsShot - shipsSunk));
    }

    /**
     * Method to process gaming using keyboard input.
     *
     * @param value integer number from 0 to 9 representing coordinate on field.
     */
    public void keyboardInput(Short value) {
        if (keyboardInput[0] == null) {
            keyboardInput[0] = value;
        } else if (keyboardInput[1] == null) {
            keyboardInput[1] = value;
        } else {
            keyboardInput[0] = value;
            keyboardInput[1] = null;
        }

        if (keyboardInput[0] != null && keyboardInput[1] != null) {
            short row = keyboardInput[0];
            short column = keyboardInput[1];
            for (var cell : this.getChildren()) {
                if (row == GridPane.getRowIndex(cell) - 1 && column == GridPane.getColumnIndex(cell) - 1) {
                    processMove((BattlefieldCell) cell, row, column);
                }
            }
        }
    }
}
