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
    private static final String ERROR_FILE_NOT_FOUND = "Error: File not found\n";
    private static final String ERROR_INVALID_FILE = "Error: Invalid file format\n";

    private List<String> map = new ArrayList<>();
    List<Item> items = new ArrayList<>();
    List<Terrain> terrains = new ArrayList<>();
    int numRows;
    int numColumns;
    int currentRow;
    int currentColumn;

    int mTileSize;
    String mItemsFileName;

    Map() {
        currentRow = 0;
        currentColumn = 0;
    }

    void readInFile(String fileName) {

        try {
            System.out.print(fileName);
            Scanner fileIn = new Scanner(new File(fileName));

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

            // read in items
            mItemsFileName = fileIn.nextLine();
            items = getItems(mItemsFileName);

            // read in terrains
            while (fileIn.hasNextLine()) {
                terrains.add(new Terrain(fileIn.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.print(ERROR_FILE_NOT_FOUND);
            System.exit(1);
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

    // todo: this isn't working. Needs to return the '-' terrain when it's out of bounds

    Terrain getTerrainAt(int row, int column) {
        if (row < numRows && column < numColumns) {
            for (Terrain terrain : terrains) {
                if (terrain.getTerrainChar() == map.get(row).charAt(column)) {
                    return terrain;
                }
            }
//            for (int i = 0; i < terrains.size(); i++) {
//                if (terrains.get(i).getTerrainChar() == map.get(row).charAt(column)) {
//                    return terrains.get(i);
//                }
//            }
        } else {
            for (Terrain terrain : terrains) {
                if (terrain.getTerrainChar() == '-') {
                    return terrain;
                }
            }
        }
        return null;
    }
}
