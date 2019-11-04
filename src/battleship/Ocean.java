package battleship;

import java.util.Random;

import static java.lang.System.out;

class Ocean {

    /**
     * Constant size of playing field.
     */
    private static final int FIELD_SIZE = 10;

    /**
     * Constant amount of ships on the playing field.
     */
    private static final int SHIPS_AMOUNT = 10;

    /**
     * Array of ships in the ocean.
     */
    private Ship[][] ships = new Ship[FIELD_SIZE][FIELD_SIZE];
    /**
     * The total number of shots fired by the user.
     */
    private int shotsFired;
    /**
     * The number of times a shot hit a ship. If the user shoots the same part of a ship more than once,
     * every hit is counted, even though the additional "hits" don't do the user any good.
     */
    private int hitCount;
    /**
     * The number of ships sunk (maximum = 10).
     */
    private int shipsSunk;

    /**
     * Constructor, the purpose of which is to create an "empty" ocean.
     * Also initializes such game variables as how many shots have been fired,
     * how many hits have been done and how many ships have been sunk.
     */
    Ocean() {
        shotsFired = 0;
        hitCount = 0;
        shipsSunk = 0;
        for (var i = 0; i < FIELD_SIZE; i++) {
            for (var j = 0; j < FIELD_SIZE; j++) {
                new EmptySea().placeShipAt(i, j, true, this);
            }
        }
    }

    /**
     * Generator of pseudo random numbers.
     */
    private static Random rnd = new Random();

    /**
     * Method for placing all ten ships randomly in the ocean.
     */
    void placeAllShipsRandomly() {
        Ship[] ships = new Ship[SHIPS_AMOUNT];

        for (var i = 0; i < ships.length; i++) {
            ships[i] = i == 0 ? new Battleship() : i < 3 ? new Cruiser() : i < 6 ? new Destroyer() : new Submarine();
        }

        int successfullyPlacedCount = 0;
        for (var ship : ships) {
            while (successfullyPlacedCount != ships.length) {
                int row = rnd.nextInt(FIELD_SIZE);
                int column = rnd.nextInt(FIELD_SIZE);
                boolean horizontal = rnd.nextBoolean();
                if (ship.okToPlaceShipAt(row, column, horizontal, this)) {
                    ship.placeShipAt(row, column, horizontal, this);
                    successfullyPlacedCount++;
                    break;
                }
            }
        }
    }

    /**
     * Method to check if the given location contains a ship.
     *
     * @param row integer number 0..9
     * @param column integer number 0..9
     * @return true if the given location contains a ship,
     * otherwise – false.
     */
    boolean isOccupied(int row, int column) {
        return !ships[row][column].getShipType().equals("empty sea");
    }

    /**
     * Method which updates the number of shots that have been fired, and the number of hits.
     *
     * @param row integer number 0..9
     * @param column integer number 0..9
     * @return true if the given location contains a "real" ship, still afloat, otherwise - false
     */
    boolean shootAt(int row, int column) {
        shotsFired++;
        if (isOccupied(row, column)) {
            if (ships[row][column].shootAt(row, column)) {
                hitCount++;
                if (ships[row][column].isSunk()) {
                    shipsSunk++;
                }
                return true;
            }
            return false;
        } else {
            ships[row][column].shootAt(row, column);
        }
        return false;
    }

    /**
     * Getter for the number of shots fired.
     *
     * @return number of shots which have been fired
     */
    int getShotsFired() {
        return shotsFired;
    }

    /**
     * Getter for the number of hits.
     *
     * @return number of hits which have been done
     */
    int getHitCount() {
        return hitCount;
    }

    /**
     * Getter for the number of sunk ships.
     *
     * @return number of ships which have been sunk
     */
    int getShipsSunk() {
        return shipsSunk;
    }

    /**
     * Method to define if game is over or not.
     *
     * @return true if amount of ships sunk is equals to maximum possible ships amount,
     * otherwise - false
     */
    boolean isGameOver() {
        return shipsSunk == SHIPS_AMOUNT;
    }

    /**
     * Getter for ships array.
     *
     * @return array of ships in the ocean
     */
    Ship[][] getShipArray() {
        return ships;
    }

    /**
     * Method which prints the ocean. Row numbers are displayed along the left edge of the array,
     * column numbers are displayed along the top. Numbers are from 0 to 9.
     * The top left corner square is 0, 0.
     * 'S' indicates a location that user have fired upon and hit a (real) ship,
     * '-' indicates a location that user have fired upon and found nothing there,
     * 'x' indicates location containing a sunken ship,
     * '.' indicates a location that user have never fired upon.
     */
    void print() {
        out.print("  ");
        for (int i = 0; i < FIELD_SIZE; i++) {
            out.print(String.format("%3d", i));
        }
        out.println();
        for (int i = 0; i < FIELD_SIZE; i++) {
            out.print(String.format("%3d", i));
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (ships[i][j].isShootAt(i, j)) {
                    out.print(String.format("%2s", ships[i][j]));
                } else {
                    out.print(String.format("%2s", "."));
                }
                out.print(" ");
            }
            out.println();
        }
    }
}