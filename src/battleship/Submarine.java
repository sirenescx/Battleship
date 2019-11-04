package battleship;

class Submarine extends Ship {

    /**
     * The length of the submarine type of ships.
     */
    private static final int SUBMARINE_LENGTH = 1;

    /**
     * Constructor, the purpose of which is to set the inherited length variable to submarine length (1),
     * and to initialize the hit array.
     */
    Submarine() {
        length = SUBMARINE_LENGTH;
        hit = new boolean[SUBMARINE_LENGTH];
    }

    /**
     * Method to get type of a ship.
     *
     * @return type of this particular ship
     */
    @Override
    String getShipType() {
        return "submarine";
    }
}