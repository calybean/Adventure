import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jcannon on 11/29/16.
 */
public class Adventure extends JPanel {

    // constants
    private static final String GO = "g";
    private static final String INVENTORY = "i";
    private static final String TAKE = "t";
    private static final String DROP = "d";
    private static final String QUIT = "q";
    private static final String FAREWELL = "Farewell\n";

    // error messages
    private static final String INVALID_COMMAND = "Invalid command: ";
    private static final String ERROR_NO_ARGS = "Error: No command line parameter was entered\n";
    private static final String GAME_SAVED = "Game saved.\n";
    private static final String GAME_LOADED = "Game loaded.\n";

    // GUI Elements

    private static JFrame mFrame;

    private JPanel container_panel;
    private JPanel main_panel;
    private JPanel map_panel;
    private JPanel button_panel;
    private JPanel text_panel;
    private JButton button_save;
    private JButton button_quit;
    private JButton button_open;
    private JTextArea text_output;
    private JTextField text_input;

    // member variables
    private static String mFileName;
    private Map mMap;
    private GameChar mGameChar;
    private String mSaveLocation;

    private static final KeyStroke LEFT = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0);
    private static final KeyStroke RIGHT = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0);
    private static final KeyStroke UP = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0);
    private static final KeyStroke DOWN = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);

    public static void main(String[] args) {
        // todo: use args to get map file

        mFrame = new JFrame("Adventure - Joseph Cannon");
        mFrame.setContentPane(new Adventure().main_panel);
        mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mFrame.setPreferredSize(new Dimension(400,600));
        mFrame.pack();
        mFrame.setVisible(true);


        if (args.length > 0) {
            System.out.print("args.length > 0\n");
            mFileName = args[0];
        } else {
            System.out.print(ERROR_NO_ARGS);
//            System.exit(1);
        }
    }

    Adventure() {
        mMap = new Map();

        if (mFileName != null) {
            mMap.readInFile(mFileName);
        } else {
            System.out.print("No map filename\n");
            mFileName = "mapfile.txt";
            mMap.readInFile(mFileName);
        }

        // instantiate a gameChar object
        mGameChar = new GameChar(text_output);

        final int margin = 4;
        main_panel.setBorder(new EmptyBorder(margin, margin, margin, margin));
        button_panel.setBorder(new EmptyBorder(margin, 0, 0, 0));
        text_panel.setBorder(new EmptyBorder(margin, 0, margin, 0));
        final GridLayout gridLayout = new GridLayout(5, 5);
        map_panel.setLayout(gridLayout);

        // populate images for the first time
        updateMap(mMap.currentRow, mMap.currentColumn);

        // action listeners for buttons and text input
        text_input.addActionListener((ActionEvent e) -> handleInput());
        button_save.addActionListener((java.awt.event.ActionEvent e) -> saveGame());
        button_open.addActionListener((java.awt.event.ActionEvent e) -> loadGame());
        button_quit.addActionListener((java.awt.event.ActionEvent e) -> System.exit(0));


        // todo: command line parameter!!!!!


        // key bindings:
        mFrame.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(LEFT, "left");
        mFrame.getRootPane().getActionMap().put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mGameChar.goWest(mMap);
                updateMap(mMap.currentRow, mMap.currentColumn);
            }
        });
        mFrame.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(RIGHT, "right");
        mFrame.getRootPane().getActionMap().put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mGameChar.goEast(mMap);
                updateMap(mMap.currentRow, mMap.currentColumn);
            }
        });
        mFrame.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(UP, "up");
        mFrame.getRootPane().getActionMap().put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mGameChar.goNorth(mMap);
                updateMap(mMap.currentRow, mMap.currentColumn);
            }
        });
        mFrame.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(DOWN, "down");
        mFrame.getRootPane().getActionMap().put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mGameChar.goSouth(mMap);
                updateMap(mMap.currentRow, mMap.currentColumn);
            }
        });
    }

    private void saveGame() {
        List<String> fileList = new ArrayList<>();

        fileList.add(mMap.map.size() + " " + mMap.map.get(0).length()); // map num rows and columns
        for (String row : mMap.map) { // map
            fileList.add(row);
        }
        fileList.add(mMap.currentRow + " " + mMap.currentColumn); // current position
        for (Item item : mMap.mInventory) { // inventory
            fileList.add(item.getItemRow());
        }
        for (Item item : mMap.mItems) { // map items
            fileList.add(item.getItemRow());
        }

        if (mSaveLocation != null) {
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter(mSaveLocation));
                for (String line : fileList) {
                    writer.write((line + '\n'));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    // close the writer regardless of what happens
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            JFileChooser fileChooser = new JFileChooser();
            int userSelection = fileChooser.showSaveDialog(mFrame);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                mSaveLocation = fileToSave.getAbsolutePath();
                BufferedWriter writer = null;
                try {
                    writer = new BufferedWriter(new FileWriter(mSaveLocation));
                    for (String line : fileList) {
                        writer.write((line + '\n'));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        writer.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                return;
            }
        }
        text_output.append(GAME_SAVED);
    }

    // todo: output messages for dropping, taking, and error messages when user can't do one or the other.


    private void loadGame() {
        if (mSaveLocation == null) {
            text_output.append("Error: No file saved.\n");
            return;
        }

        File fileToLoad = new File(mSaveLocation);

        if (fileToLoad.exists()) {
            BufferedReader bufferedReader;
            try {
                bufferedReader = new BufferedReader(new FileReader(fileToLoad));
                List<String> fileContents = new ArrayList<>();
                String currentLine;
                while ((currentLine = bufferedReader.readLine()) != null) {
                    System.out.println(currentLine);
                    fileContents.add(currentLine);
                }

                // use list to set all map values from saved file
                restoreGameFromFile(fileContents);

                // update the UI with the new values
                updateMap(mMap.currentRow, mMap.currentColumn);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // no game saved
            text_output.append("Error: No file saved.\n");
        }
        text_output.append(GAME_LOADED);
    }

    private void restoreGameFromFile(List<String> fileContents) {
        // index to keep track of where in the list we are
        int index = 0;

        //read in rows and columns
        String[] rowsAndColumns = fileContents.get(index).split("\\s+");
        mMap.numRows = Integer.valueOf(rowsAndColumns[0]);
        mMap.numColumns = Integer.valueOf(rowsAndColumns[1]);
        index++;

        // read in the map grid:
        mMap.map.clear();
        for (int i = 0; i < mMap.numRows; i++) {
            mMap.map.add(fileContents.get(index));
            index++;
        }

        // read in current position
        String[] currentPosition = fileContents.get(index).split("\\s+");
        mMap.currentRow = Integer.valueOf(currentPosition[0]);
        mMap.currentColumn = Integer.valueOf(currentPosition[1]);
        index++;

        // read in items
        List<String> allItems = new ArrayList<>();
        for (int i = index; i < fileContents.size(); i++) {
            allItems.add(fileContents.get(index));
            index++;
        }
        // clear out mItems and mImventory
        mMap.mItems.clear();
        mMap.mInventory.clear();
        // separate all items into mItems and mInventory
        for (String itemRow : allItems) {
            if (itemRow.startsWith("-1;-1;")) {
                mMap.mInventory.add(new Item(itemRow));
            } else {
                mMap.mItems.add(new Item(itemRow));
            }
        }
    }

    private void updateMap(int row, int column) {
        // clear map panel and start over
        map_panel.removeAll();

        JLabel imageArray[] = new JLabel[25];
        for (int i = 0; i < 5; i++) { // row
            for (int j = 0; j < 5; j++) { // column
                // todo: account for the bottom and right out of bounds
                Terrain terrain = mMap.getTerrainAt(i + row - 2, j + column - 2);
                imageArray[(i * 5) + j] = new JLabel(new ImageIcon(terrain.getFilePath()));
                map_panel.add(imageArray[(i * 5) + j]);
            }
        }
        // update UI
        mFrame.revalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    private void handleInput() {
        // lng = row
        // lat = column
        String command = text_input.getText();
        text_output.append("> " + text_input.getText() + '\n');
        text_input.setText("");
        // do stuff with the command
        if (command.toLowerCase().startsWith(GO)) {
            // tell gameChar to move player
            mGameChar.go(mMap, command);
            // when the player moves, update the map
            updateMap(mMap.currentRow, mMap.currentColumn);
            // check for an item here:
            for (Item item : mMap.mItems) {
                if (item.getRow() == mMap.currentRow && item.getColumn() == mMap.currentColumn) {
                    text_output.append("There is " + item.getName() + " at this location.\n");
                }
            }
        } else if (command.toLowerCase().startsWith(TAKE)) {
            mMap.take(command);
            // check to make sure the item they want to take is actually here
            // take item
            // delete it from mMap.mItems
        } else if (command.toLowerCase().startsWith(DROP)) {
            mMap.drop(command);
            // check to make sure they actually have the item they want to drop
            // drop item
            // add it to mMap.mItems (with new location)
        } else if (command.toLowerCase().startsWith(INVENTORY)) {
            // tell map to print inventory
            text_output.append(mMap.getInventory());
        } else if (command.toLowerCase().startsWith(QUIT)) {
            // quit program
            text_output.append(FAREWELL + '\n');
            System.exit(0);
        } else {
            text_output.append(INVALID_COMMAND + command + '\n');
        }
        // print location and terrain
        text_output.append("You are at location " +
                mMap.currentRow +
                "," +
                mMap.currentColumn +
                " in terrain " +
                mMap.getTerrainAt(mMap.currentRow, mMap.currentColumn).getTerrainChar() +
                " (" +
                mMap.getTerrainAt(mMap.currentRow, mMap.currentColumn).getName() +
                ")\n"
        );
    }
}
