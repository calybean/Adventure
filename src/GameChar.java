// Joseph Cannon
// CS 3250
// 12/9/16
// I declare that the following source code was written solely by me, or provided on
// the course web site for this program. I understand that copying any source code,
// in whole or in part, constitutes cheating, and that I will receive a zero grade
// on this project if I am found in violation of this policy.

import javax.swing.*;

/**
 * Created by Joey on 12/5/16
 */
public class GameChar {

    // constants
    private static final String NORTH = "n";
    private static final String EAST = "e";
    private static final String SOUTH = "s";
    private static final String WEST = "w";
    private static final String MOVING_NORTH = "Moving north...\n";
    private static final String MOVING_EAST = "Moving east...\n";
    private static final String MOVING_SOUTH = "Moving south...\n";
    private static final String MOVING_WEST = "Moving west...\n";
    private static final int MIN_LAT_LNG = 0;

    // error messages
    private static final String INVALID_DIRECTION = "You can't go that way.\n";
    private static final String ERROR_TOO_FAR_NORTH = "You can't go that far north.\n";
    private static final String ERROR_TOO_FAR_EAST = "You can't go that far east.\n";
    private static final String ERROR_TOO_FAR_SOUTH = "You can't go that far south.\n";
    private static final String ERROR_TOO_FAR_WEST = "You can't go that far west.\n";

    private JTextArea mOutput;

    GameChar(JTextArea output) {
        mOutput = output;
    }

    char go(Map map, String command) {
        // split string up into go command and direction
        String[] parts = command.split("\\s+");

        // make sure they actually gave a direction
        if (parts.length > 1) {
            String direction = parts[1];
            if (direction.toLowerCase().startsWith(NORTH)) {
                return goNorth(map);
            } else if (direction.toLowerCase().startsWith(EAST)) {
                return goEast(map);
            } else if (direction.toLowerCase().startsWith(SOUTH)) {
                return goSouth(map);
            } else if (direction.toLowerCase().startsWith(WEST)) {
                return goWest(map);
            } else {
                // wasn't a valid direction.
                mOutput.append(INVALID_DIRECTION);
                return '-';
            }
        } else {
            // wasn't a valid direction.
            mOutput.append(INVALID_DIRECTION);
            return '-';
        }
    }

    public char goNorth(Map map) {
        if (map.currentRow > MIN_LAT_LNG) {
            // if there's room, move
            map.currentRow -= 1;
            mOutput.append(MOVING_NORTH);
            return 'n';
        } else {
            // can't go that far
            mOutput.append(ERROR_TOO_FAR_NORTH);
            return '-';
        }
    }

    public char goEast(Map map) {
        if (map.currentColumn < map.numColumns - 1) {
            // if there's room, move
            map.currentColumn += 1;
            mOutput.append(MOVING_EAST);
            return 'e';
        } else {
            // can't go that far
            mOutput.append(ERROR_TOO_FAR_EAST);
            return '-';
        }
    }

    public char goSouth(Map map) {
        if (map.currentRow < map.numRows - 1) {
            // if there's room, move
            map.currentRow += 1;
            mOutput.append(MOVING_SOUTH);
            return 's';
        } else {
            // can't go that far
            mOutput.append(ERROR_TOO_FAR_SOUTH);
            return '-';
        }
    }

    public char goWest(Map map) {
        if (map.currentColumn > MIN_LAT_LNG) {
            // if there's room, move
            map.currentColumn -= 1;
            mOutput.append(MOVING_WEST);
            return 'w';
        } else {
            // can't go that far
            mOutput.append(ERROR_TOO_FAR_WEST);
            return '-';
        }
    }
}
