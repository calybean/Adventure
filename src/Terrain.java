// Joseph Cannon
// CS 3250
// 12/9/16
// I declare that the following source code was written solely by me, or provided on
// the course web site for this program. I understand that copying any source code,
// in whole or in part, constitutes cheating, and that I will receive a zero grade
// on this project if I am found in violation of this policy.

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
