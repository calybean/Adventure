/**
 * Created by Joey on 12/6/16
 */
class Item {
    private String name;
    private int row;
    private int column;

    Item (String itemRow) {
        String elements[] = itemRow.split(";");

        if (elements.length == 3) { // there's an empty one at the end
            row = Integer.parseInt(elements[0]);
            column = Integer.parseInt(elements[1]);
            name = elements[2];
        } else {
            System.out.print("Error: invalid item file\n");
        }
    }

    Item (String name, int row, int column) {
        this.name = name;
        this.row = row;
        this.column = column;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getItemRow() {
        return row + ";" + column + ";" + name + ";";
    }
}
