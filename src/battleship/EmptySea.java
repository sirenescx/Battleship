package battleship;

class EmptySea extends Ship {

    /**
     * The length of the empty sea type of ships.
     */
    private static final int EMPTY_SEA_LENGTH = 1;

    /**
     * Constructor, the purpose of which is to set the inherited length variable to empty sea length (1),
     * and to initialize the hit array.
     */
    EmptySea() {
        length = EMPTY_SEA_LENGTH;
        hit = new boolean[EMPTY_SEA_LENGTH];
    }

    @Override
    boolean shootAt(int row, int column) {
        hit[0] = true;
        return false;
    }

    /**
     * Method which is used to check if a ship has been sunk.
     *
     * @return false because it is impossible to sunk the empty sea
     */
    @Override
    boolean isSunk() {
        return false;
    }

    /**
     * Method which is used to get information about the state of the ship.
     *
     * @return "-" if the empty sea was hit by a player
     *         "." – otherwise
     */
    @Override
    public String toString() {
        return hit[0] ? "-" : ".";
    }

    /**
     * Method to get type of a ship.
     *
     * @return type of this particular ship
     */
    @Override
    String getShipType() {
        return "empty sea";
    }
}