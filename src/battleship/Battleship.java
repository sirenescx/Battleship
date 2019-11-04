package battleship;

class Battleship extends Ship {

    /**
     * The length of the battleship type of ships.
     */
    private static final int BATTLESHIP_LENGTH = 4;

    /**
     * Constructor, the purpose of which is to set the inherited length variable to battleship length (4),
     * and to initialize the hit array.
     */
    Battleship() {
        length = BATTLESHIP_LENGTH;
        hit = new boolean[BATTLESHIP_LENGTH];
    }

    /**
     * Method to get type of a ship.
     *
     * @return type of this particular ship
     */
    @Override
    public String getShipType() {
        return "battleship";
    }
}