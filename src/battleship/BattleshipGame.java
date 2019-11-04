package battleship;

import java.util.Scanner;

import static java.lang.System.out;

/**
 * Main game class, containing main method.
 */
public class BattleshipGame {

    /**
     * Method to display information about the game such as amount of shots fired, amount of hits and amount of sunk ships.
     *
     * @param ocean instance of Ocean
     */
    private static void displayFinalGameInfo(Ocean ocean) {
        out.println(String.format("Shots fired (score): %s\nHits: %s\nShips sunk: %s\nBest possible score: 20",
                ocean.getShotsFired(), ocean.getHitCount(), ocean.getShipsSunk()));
        out.println("Game over! Wanna play again?" +
                "\nPrint 'y' to start a new game or any other key to exit.\n");
    }

    private static void displayCurrentGameInfo(Ocean ocean, Coordinates coordinates) {
        out.println(hitOrMiss(ocean, coordinates));
        ocean.print();
    }

    /**
     * Method which generates new ocean instance and places ships randomly in it.
     *
     * @return Ocean instance
     */
    private static Ocean generateNewOcean() {
        out.println("Welcome to a Battleship game!");
        printHelp();
        Ocean ocean = new Ocean();
        ocean.placeAllShipsRandomly();
        return ocean;
    }

    /**
     * Method to check whether player has hit a ship or not.
     *
     * @param ocean       Ocean instance
     * @param coordinates where player has shoot
     * @return "You just sunk a {ship type}" if the player has sunk a ship,
     * "hit" if the player has hit the ship, but not sunk, otherwise – "miss"
     */
    private static String hitOrMiss(Ocean ocean, Coordinates coordinates) {
        if (ocean.shootAt(coordinates.getX(), coordinates.getY())) {
            Ship ship = ocean.getShipArray()[coordinates.getX()][coordinates.getY()];
            if (ship.isSunk()) {
                return String.format("\n\tYou just sunk a %s!\n", ship.getShipType());
            } else {
                return "\n\thit\n";
            }
        }
        return "\n\tmiss\n";
    }

    /**
     * Method to check whether player wants to quit the game or not.
     *
     * @param ocean Ocean instance
     */
    private static void quitGame(Ocean ocean) {
        out.println(String.format("Game over!\nShots fired: %s\nHits: %s\nShips sunk: %s",
                ocean.getShotsFired(), ocean.getHitCount(), ocean.getShipsSunk()));
    }

    /**
     * Method which prints game rules and input format.
     */
    private static void printHelp() {
        out.println("\nGame rules:" +
                "\nYou try to hit the ships, by calling out a row and column number." +
                "\nThe game responds with information: \"hit\" or \"miss\"." +
                "\nIf a ship is hit and sinks, you will see a message \"You just sank a {ship type}!\"" +
                "\nA ship is \"sunk\" when every square of the ship has been hit." +
                "\nIt takes:" +
                "\n\tfour hits to sink a battleship" +
                "\n\tthree to sink a cruiser" +
                "\n\ttwo for a destroyer" +
                "\n\tone for a submarine\n" +
                "\nInput format:" +
                "\n{x, y} to shoot" +
                "\n'q' to quit" +
                "\n'h' to get help");
    }

    /**
     * Scanner class instance to get user input.
     */
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Method where game is simulated.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Ocean ocean = generateNewOcean();

        while (true) {
            out.print("\nEnter coordinates: ");
            String input = scanner.nextLine();

            if (input.equals("q")) {
                quitGame(ocean);
                break;
            }

            if (input.equals("h")) {
                printHelp();
            }

            Coordinates coordinates;
            try {
                coordinates = new Coordinates(input.split("[, ]+"));
            } catch (IllegalArgumentException e) {
                out.println(e.getMessage());
                continue;
            }

            displayCurrentGameInfo(ocean, coordinates);

            if (ocean.isGameOver()) {
                displayFinalGameInfo(ocean);
                if (scanner.nextLine().equals("y")) {
                    ocean = generateNewOcean();
                } else {
                    break;
                }
            }
        }
    }
}