import javax.swing.*;

/**
 * Created by Joey on 12/5/16
 */
public class GameChar {

    // constants
    private static final String OUT_OF_BOUNDS = "X";
    private static final String YOU_ARE_CARRYING = "You are carrying:\n";
    private static final String[] INVENTORY_ARRAY = {"brass lantern", "rope", "rations", "staff"};
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

    void go(Map map, String command) {

        // split string up into go command and direction
        String[] parts = command.split("\\s+");

        // make sure they actually gave a direction
        if (parts.length > 1) {
            String direction = parts[1];

            if (direction.toLowerCase().startsWith(NORTH)) {
                if (map.currentRow > MIN_LAT_LNG) {
                    // if there's room, move
                    map.currentRow -= 1;
                    mOutput.append(MOVING_NORTH);
                } else {
                    // can't go that far
                    mOutput.append(ERROR_TOO_FAR_NORTH);
                }
            } else if (direction.toLowerCase().startsWith(EAST)) {
                if (map.currentColumn < map.numColumns - 1) {
                    // if there's room, move
                    map.currentColumn += 1;
                    mOutput.append(MOVING_EAST);
                } else {
                    // can't go that far
                    mOutput.append(ERROR_TOO_FAR_EAST);
                }
            } else if (direction.toLowerCase().startsWith(SOUTH)) {
                if (map.currentRow < map.numRows - 1) {
                    // if there's room, move
                    map.currentRow += 1;
                    mOutput.append(MOVING_SOUTH);
                } else {
                    // can't go that far
                    mOutput.append(ERROR_TOO_FAR_SOUTH);
                }
            } else if (direction.toLowerCase().startsWith(WEST)) {
                if (map.currentColumn > MIN_LAT_LNG) {
                    // if there's room, move
                    map.currentColumn -= 1;
                    mOutput.append(MOVING_WEST);
                } else {
                    // can't go that far
                    mOutput.append(ERROR_TOO_FAR_WEST);
                }
            } else {
                // wasn't a valid direction.
                mOutput.append(INVALID_DIRECTION);
            }
        } else {
            // wasn't a valid direction.
            mOutput.append(INVALID_DIRECTION);
        }
    }

    String getInventory() {
        String inventory = YOU_ARE_CARRYING;
        // print inventory
        for (String item : INVENTORY_ARRAY) {
            inventory += item + '\n';
        }
        return inventory;
    }
}
