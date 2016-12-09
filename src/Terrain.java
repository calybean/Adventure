/**
 * Created by Joey on 12/6/16
 */
class Terrain {
    private char terrainChar;
    private String name;
    private String filePath;

    Terrain(String terrainRow) {
        String elements[] = terrainRow.split(";", -1);

        if (elements.length == 4) { // there's an empty one at the end, but we'll just ignore it.
            terrainChar = elements[0].charAt(0);
            name = elements[1];
            filePath = elements[2];
        } else {
            System.out.print("Error parsing terrain row\n");
            System.exit(1);
        }
    }

    public char getTerrainChar() {
        return terrainChar;
    }

    public void setTerrainChar(char terrainChar) {
        this.terrainChar = terrainChar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
