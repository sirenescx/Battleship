package battleship;

class Destroyer extends Ship {

    /**
     * The length of the destroyer type of ships.
     */
    private static final int DESTROYER_LENGTH = 2;

    /**
     * Constructor, the purpose of which is to set the inherited length variable to destroyer length (2),
     * and to initialize the hit array.
     */
    Destroyer() {
        length = DESTROYER_LENGTH;
        hit = new boolean[DESTROYER_LENGTH];
    }

    /**
     * Method to get type of a ship.
     *
     * @return type of this particular ship
     */
    @Override
    String getShipType() {
        return "destroyer";
    }
}