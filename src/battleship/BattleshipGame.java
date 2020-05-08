package battleship;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Collections;
import java.util.HashSet;

/**
 * Main game class, containing main method.
 */
public class BattleshipGame extends Application {

    /**
     * Possible keyboard inputs.
     */
    private static HashSet<String> keys = new HashSet<>();

    static {
        Collections.addAll(keys, "0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
    }

    /**
     * Method to set styling to main layout.
     *
     * @param gridPane to which styling is set
     */
    private static void setMainGridStyling(GridPane gridPane) {
        gridPane.setStyle("-fx-background-image: url('/battleship/background.jpg');");
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(0, (Utils.getScreenWidth() - 2 * Utils.getScreenWidth() / 2.5) / 2, 0, 10));
    }

    /**
     * Method to set styling to stage.
     *
     * @param stage to which styling is set
     */
    private static void setStageStyling(Stage stage) {
        stage.setTitle("Battleship");
        stage.setMinWidth(Utils.getScreenWidth());
        stage.setMinHeight(Utils.getScreenHeight());
    }

    /**
     * Method to add all needed for game components to main grid pane.
     *
     * @param rootGrid main layout
     * @return instance of battleship field
     */
    private static Battlefield createGameUI(GridPane rootGrid) {
        setMainGridStyling(rootGrid);

        var logArea = new TextArea();
        var gameInfoArea = new TextArea();
        var field = new Battlefield(logArea, gameInfoArea);

        rootGrid.add(field, 0, 0);
        rootGrid.add(logArea, 0, 1);
        rootGrid.add(gameInfoArea, 1, 0);

        return field;
    }

    @Override
    public void start(Stage stage) throws Exception {
        setStageStyling(stage);

        var rootGrid = new GridPane();
        var field = createGameUI(rootGrid);

        var scene = new Scene(rootGrid, Utils.getScreenWidth(), Utils.getScreenHeight());
        scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
        scene.setOnKeyTyped(key -> {
            var buttonString = key.getCharacter();
            if (keys.contains(buttonString)) {
                field.keyboardInput(Short.parseShort(buttonString));
            }
        });

        stage.setScene(scene);
        stage.show();

        rootGrid.requestFocus();
    }

    /**
     * Method where game is simulated.
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}