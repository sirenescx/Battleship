package battleship;

/**
 * Class of coordinates on the playing field.
 */
final class Coordinates {

    /**
     * x - coordinate
     */
    private int x;
    /**
     * y - coordinate
     */
    private int y;

    /**
     * Constant lower bound of playing field indexes.
     */
    private static final int LOWER_BOUND = 0;

    /**
     * Constant upper bound of playing field indexes.
     */
    private static final int UPPER_BOUND = 9;

    /**
     * Constructor, the purpose of which is to parse coordinates from string array.
     *
     * @param possibleCoordinates string array, storing possible coordinates values
     * @throws IllegalArgumentException when it is impossible to parse string array into coordinates.
     */
    Coordinates(String[] possibleCoordinates) throws IllegalArgumentException {
        final String ILLEGAL_ARGUMENT_MESSAGE = "Incorrect coordinates. Try again!";

        if (possibleCoordinates.length != 2) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
        }

        Integer x = intTryParse(possibleCoordinates[0]);
        Integer y = intTryParse(possibleCoordinates[1]);
        if (x == null || y == null || outsideOfBounds(x) || outsideOfBounds(y)) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_MESSAGE);
        }

        this.x = x;
        this.y = y;
    }

    /**
     * Method, the purpose of which is to parse number from string.
     * @param parseString string to parse number from
     * @return null if it is impossible to parse number from string, otherwise – number value
     */
    private static Integer intTryParse(String parseString) {
        try {
            return Integer.parseInt(parseString);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    /**
     * Method to check whether the coordinate is inside of the playing field or not.
     * @param coordinate to check
     * @return true if the coordinate is outside of the bounds, otherwise – true
     */
    private static boolean outsideOfBounds(int coordinate) {
        return coordinate < LOWER_BOUND || coordinate > UPPER_BOUND;
    }

    /**
     * Getter for x - coordinate
     *
     * @return x - coordinate
     */
    int getX() {
        return x;
    }

    /**
     * Getter for y - coordinate
     *
     * @return y - coordinate
     */
    int getY() {
        return y;
    }
}
