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

    void take(String command) {
        // split string up into go command and direction
        String[] parts = command.split("\\s+");

        if (parts.length < 2) {
            return;
        }

        String itemName = "";
        for (int i = 1; i < parts.length; i++) {
            itemName += parts[i] + " ";
        }
        itemName = itemName.trim();

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
        if (itemTaken) {

        }
    }

    void drop(String command) {
        // split string up into go command and direction
        String[] parts = command.split("\\s+");

        if (parts.length < 2) {
            return;
        }

        String itemName = "";
        for (int i = 1; i < parts.length; i++) {
            itemName += parts[i] + " ";
        }
        itemName = itemName.trim();

        for (Item item : mInventory) { // check for item in inventory
            if (itemName.equals(item.getName())) {
                // add this item to the map at this location
                mItems.add(new Item(itemName, currentRow, currentColumn));
                // remove this item from the map
                mInventory.remove(item);
                break;
            }
        }
    }

    Terrain getTerrainAt(int row, int column) {
        // if we're out of bounds:
        if (row < 0 || column < 0) {
            for (Terrain terrain : terrains) {
                if (terrain.getTerrainChar() == '-') {
                    return terrain;
                }
            }
        }
        // if we're in bounds:
        if (row < numRows && column < numColumns) {
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
