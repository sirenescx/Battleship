package battleship;

class Cruiser extends Ship {

    /**
     * The length of the cruiser type of ships.
     */
    private static final int CRUISER_LENGTH = 3;

    /**
     * Constructor, the purpose of which is to set the inherited length variable to cruiser length (3),
     * and to initialize the hit array.
     */
    Cruiser() {
        length = CRUISER_LENGTH;
        hit = new boolean[CRUISER_LENGTH];
    }

    /**
     * Method to get type of a ship.
     *
     * @return type of this particular ship
     */
    @Override
    String getShipType() {
        return "cruiser";
    }
}