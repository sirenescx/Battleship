package battleship;

abstract class Ship {

    /**
     * Constant size of playing field.
     */
    private static final int FIELD_SIZE = 10;

    /**
     * The row (0 to 9) which contains the bow (front) of the ship.
     */
    private int bowRow;

    /**
     * The column (0 to 9) which contains the bow (front) of the ship.
     */
    private int bowColumn;

    /**
     * The number of squares occupied by the ship. An "empty sea" location has length 1.
     */
    int length;

    /**
     * True if the ship occupies a single row, false otherwise.
     */
    private boolean horizontal;

    /**
     * An array of booleans telling whether that part of the ship has been hit.
     */
    boolean[] hit = new boolean[4];

    /**
     * Getter for length variable.
     *
     * @return length of this particular ship.
     */
    int getLength() {
        return length;
    }

    /**
     * Getter for bowRow variable.
     *
     * @return bowRow value – integer number 0..9
     */
    int getBowRow() {
        return bowRow;
    }

    /**
     * Getter for bowColumn variable.
     *
     * @return bowColumn value – integer number 0..9
     */
    int getBowColumn() {
        return bowColumn;
    }

    /**
     * Getter for horizontal variable.
     *
     * @return horizontal value – boolean variable true or false
     */
    boolean isHorizontal() {
        return horizontal;
    }

    /**
     * Setter for the value of bowRow.
     *
     * @param row integer number 0..9
     */
    void setBowRow(int row) {
        bowRow = row;
    }

    /**
     * Setter for the value of bowColumn.
     *
     * @param column integer number 0..9
     */
    void setBowColumn(int column) {
        bowColumn = column;
    }

    /**
     * Setter for the value of the instance variable horizontal.
     *
     * @param horizontal boolean variable true or false
     */
    void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    /**
     * Getter for the ship type.
     *
     * @return type of the ship
     */
    abstract String getShipType();

    /**
     * Method to check whether it is legal to place the ship in the specific place in the ocean.
     *
     * @param row        integer number 0..9
     * @param column     integer number 0..9
     * @param horizontal boolean parameter which is true if ship is horizontally oriented, otherwise - false
     * @param ocean      instance of Ocean
     * @return true if it is okay to put a ship of this length with its bow in this location,
     * with the given orientation, false – otherwise
     */
    boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
        if (horizontal) {

            if (column + getLength() > FIELD_SIZE) {
                return false;
            }

            for (var i = row - 1; i <= row + 1; i++) {
                for (var j = column - 1; j <= column + getLength() + 1; j++) {
                    if (!indexesOutOfBounds(i, j)) {
                        if (ocean.isOccupied(i, j)) {
                            return false;
                        }
                    }
                }
            }

        } else {

            if (row + getLength() > FIELD_SIZE) {
                return false;
            }

            for (var i = row - 1; i < row + getLength() + 1; i++) {
                for (var j = column - 1; j <= column + 1; j++) {
                    if (!indexesOutOfBounds(i, j)) {
                        if (ocean.isOccupied(i, j)) {
                            return false;
                        }
                    }
                }
            }

        }
        return true;
    }

    /**
     * Method to check whether i and j are out of bounds of playing field
     *
     * @param i row number
     * @param j column number
     * @return true if at least one of the indexes is out of bounds, otherwise – false
     */
    private boolean indexesOutOfBounds(int i, int j) {
        return i < 0 || i > 9 || j < 0 || j > 9;
    }

    /**
     * Setter for bowRow, bowColumn and horizontal fields.
     *
     * @param row        integer number 0..9
     * @param column     integer number 0..9
     * @param horizontal boolean parameter which is true if ship is horizontally oriented, otherwise - false
     */
    private void setProperties(int row, int column, boolean horizontal) {
        setBowRow(row);
        setBowColumn(column);
        setHorizontal(horizontal);
    }

    /**
     * Method which "puts" the ship into the ocean.
     *
     * @param row        integer number 0..9
     * @param column     integer number 0..9
     * @param horizontal boolean parameter which is true if ship is horizontally oriented, otherwise - false
     * @param ocean      instance of Ocean
     */
    void placeShipAt(int row, int column, boolean horizontal, Ocean ocean) {
        setProperties(row, column, horizontal);

        if (isHorizontal()) {
            for (var j = column; j < column + getLength(); j++) {
                ocean.getShipArray()[row][j] = this;
            }
        } else {
            for (var i = row; i < row + getLength(); i++) {
                ocean.getShipArray()[i][column] = this;
            }
        }
    }

    /**
     * Method to check whether given coordinate was hit by a player.
     *
     * @param row    integer number 0..9
     * @param column integer number 0..9
     * @return boolean variable which is true if ship at the given position was hit, otherwise - false
     */
    boolean isShootAt(int row, int column) {
        return isHorizontal() ? hit[column - this.bowColumn] : hit[row - this.bowRow];
    }

    /**
     * Method which is used to check if a ship has been hit and mark affected part.
     *
     * @param row    integer number 0..9
     * @param column integer number 0..9
     * @return true if a part of the ship occupies the given row and column, and the ship hasn't been sunk,
     * otherwise – false
     */
    boolean shootAt(int row, int column) {
        if (!isSunk()) {
            if (isHorizontal()) {
                if (row == getBowRow() && column >= getBowColumn() && column < getBowColumn() + getLength()) {
                    hit[column - getBowColumn()] = true;
                    return true;
                }
            } else {
                if (column == getBowColumn() && row >= getBowRow() && row < getBowRow() + getLength()) {
                    hit[row - getBowRow()] = true;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method which is used to check if a ship has been sunk.
     *
     * @return true if every part of the ship has been hit, otherwise – false
     */
    boolean isSunk() {
        int amountOfAffectedParts = 0;
        for (boolean partWasHit : hit) {
            if (partWasHit) {
                amountOfAffectedParts++;
            }
        }
        return amountOfAffectedParts == hit.length;
    }

    /**
     * Method which is used to get information about the state of the ship.
     *
     * @return "x" if the ship has been sunk, "s" if it has not been sunk
     */
    public String toString() {
        return isSunk() ? "x" : "S";
    }
}