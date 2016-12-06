/**
 * Created by Joey on 12/6/16
 */
public class Item {
    String name;
    int row;
    int column;

    Item(String itemRow) {
        String elements[] = itemRow.split(";");

        row = Integer.parseInt(elements[0]);
        column = Integer.parseInt(elements[1]);
        name = elements[2];
    }

    Item(String name, int row, int column) {
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
}
