package battleship;

import javafx.scene.control.Button;

public class BattlefieldCell extends Button {
    /**
     * Constructor to set up button size.
     *
     * @param battlefield parenting element
     */
    public BattlefieldCell(Battlefield battlefield) {
        var sideLength = battlefield.getCellSideLength();
        this.setMaxSize(sideLength, sideLength);
        this.setPrefSize(sideLength, sideLength);
        this.setMinSize(sideLength, sideLength);
    }
}
