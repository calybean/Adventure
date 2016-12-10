// Joseph Cannon
// CS 3250
// 12/9/16
// I declare that the following source code was written solely by me, or provided on
// the course web site for this program. I understand that copying any source code,
// in whole or in part, constitutes cheating, and that I will receive a zero grade
// on this project if I am found in violation of this policy.

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Joey on 12/5/16
 */
class Map {

    // constants
    private static final String YOU_ARE_CARRYING = "You are carrying:\n";
    private static final String INVENTORY_EMPTY = "You aren't carrying anything.\n";
    private static final String ERROR_FILE_NOT_FOUND = "Error: File not found\n";
    private static final String ERROR_INVALID_FILE = "Error: Invalid file format\n";

    List<String> map = new ArrayList<>();
    List<Item> mItems = new ArrayList<>();
    List<Terrain> terrains = new ArrayList<>();
    int numRows;
    int numColumns;
    int currentRow;
    int currentColumn;

    int mTileSize;
    private String mItemsFileName;
    List<Item> mInventory = new ArrayList<>();

    Map() {
        currentRow = 0;
        currentColumn = 0;
    }

    void readInFile(String fileName) {
        Scanner fileIn = null;
        try {
             fileIn = new Scanner(new File(fileName));

            String[] rowsAndColumns = fileIn.nextLine().split("\\s+");

            if (rowsAndColumns.length > 1) {
                numRows = Integer.valueOf(rowsAndColumns[0]);
                numColumns = Integer.valueOf(rowsAndColumns[1]);
            } else {
                System.out.print(ERROR_INVALID_FILE);
                System.exit(1);
            }

            // read in the map grid:
            for(int i = 0; i < numRows; i++) {
                map.add(fileIn.nextLine());
            }

            // read in tile size
            String[] tileSize = fileIn.nextLine().split("\\s+");
            mTileSize = Integer.parseInt(tileSize[0]);

            // read in mItems
            mItemsFileName = fileIn.nextLine();
            mItems = getItems(mItemsFileName);

            // read in terrains
            while (fileIn.hasNextLine()) {
                terrains.add(new Terrain(fileIn.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.print(ERROR_FILE_NOT_FOUND);
            System.exit(1);
        } finally {
            if (fileIn != null) fileIn.close();
        }
    }

    private List<Item> getItems(String fileName) throws FileNotFoundException {
        List<Item> items = new ArrayList<>();

        Scanner itemsFile = new Scanner(new File(fileName));

        while (itemsFile.hasNextLine()) {
            items.add(new Item(itemsFile.nextLine()));
        }

        return items;
    }

    boolean take(String itemName) {
        boolean itemTaken = false;
        for (Item item : mItems) { // check for item in mItems
            if (itemName.equals(item.getName()) && currentRow == item.getRow() && currentColumn == item.getColumn()) {
                // make a new item with invalid location info
                mInventory.add(new Item(itemName, -1, -1));
                // remove this item from the map
                mItems.remove(item);
                itemTaken = true;
                break;
            }
        }
        return itemTaken;
    }

    boolean drop(String itemName) {
        boolean itemDropped = false;
        for (Item item : mInventory) { // check for item in inventory
            if (itemName.equals(item.getName())) {
                // add this item to the map at this location
                mItems.add(new Item(itemName, currentRow, currentColumn));
                // remove this item from the map
                mInventory.remove(item);
                itemDropped = true;
                break;
            }
        }
        return itemDropped;
    }

    Terrain getTerrainAt(int row, int column) {
        // if we're out of bounds:
        if (row < 0 || column < 0 || row > map.size() - 1 || column > map.get(0).length() - 1) {
            for (Terrain terrain : terrains) {
                if (terrain.getTerrainChar() == '-') {
                    return terrain;
                }
            }
        } else if (row < numRows && column < numColumns) { // if we're in bounds:
            for (Terrain terrain : terrains) {
                if (terrain.getTerrainChar() == map.get(row).charAt(column)) {
                    return terrain;
                }
            }
        }
        return null;
    }

    String getInventory() {
        // if you're not carrying anything, just output that.
        if (mInventory.isEmpty()) {
            return INVENTORY_EMPTY;
        } else {
            String inventory = YOU_ARE_CARRYING;
            // print inventory
            for (Item item : mInventory) {
                inventory += item.getName() + '\n';
            }
            return inventory;
        }
    }
}
