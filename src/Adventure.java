import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

/**
 * Created by jcannon on 11/29/16.
 */
public class Adventure extends JPanel {

    // constants
    private static final String STUDENT_INFO = "Joseph Cannon\nCS 3250\n9/12/16\n\n";
    private static final String GO = "g";
    private static final String INVENTORY = "i";
    private static final String QUIT = "q";
    private static final String FAREWELL = "Farewell\n";

    // error messages
    private static final String INVALID_COMMAND = "Invalid command: ";
    private static final String ERROR_NO_ARGS = "Error: No command line parameter was entered";

    // GUI Elements
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
    private static Map mMap;
    private GameChar mGameChar;

    public static void main(String[] args) {
        // todo: use args to get map file

        JFrame frame = new JFrame("Adventure");
        frame.setContentPane(new Adventure().main_panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400,500));
        frame.pack();
        frame.setVisible(true);

        if (args.length > 0) {
//            adventure.beginAdventure(args[0]);
            System.out.print("args.length > 0");
            mFileName = args[0];
            mMap.readInFile(mFileName);
        } else {
            System.out.print(ERROR_NO_ARGS);
//            System.exit(1);
            // for now, start it with the test file
            System.out.print("args.length !> 0");
            mFileName = "mapfile.txt";
            mMap.readInFile(mFileName);
        }

        // instantiate object to be able to call non-static methods
//        Adventure adventure = new Adventure();
    }

    Adventure() {
        mMap = new Map();

        // get filename from user and read in the file
//        if (mFileName != null) {
//        }

        // instantiate a gameChar object
        mGameChar = new GameChar(text_output);

        final int margin = 4;
        main_panel.setBorder(new EmptyBorder(margin, margin, margin, margin));
        button_panel.setBorder(new EmptyBorder(margin, 0, 0, 0));
        text_panel.setBorder(new EmptyBorder(margin, 0, margin, 0));

        GridLayout gridLayout = new GridLayout(5, 5);

        map_panel.setLayout(gridLayout);

        button_save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                System.out.print("Save button clicked\n");
            }
        });
        button_open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                System.out.print("Open button clicked\n");
            }
        });
        button_quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                System.out.print("Quit button clicked\n");
                System.exit(0);
            }
        });
        text_input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleInput();
            }
        });

        JLabel imageArray[] = new JLabel[25];

        // todo: clear out map_panel and do this again every time we move?

        // todo: these loops aren't working. They're supposed to start from before the edge of the map...so...figure that out.
        for (int i = 0; i < 5; i++) { // row
            for (int j = 0; j < 5; j++) { // column
                Terrain terrain = mMap.getTerrainAt(i, j);
                System.out.print("terrain is null? " + (terrain == null));
                System.out.print("terrain file path: " + terrain.getFilePath());
                imageArray[(i * 5) + j] = new JLabel(new ImageIcon(terrain.getFilePath()));
                map_panel.add(imageArray[(i * 5) + j]);
//                map_panel.add(new JLabel(new ImageIcon(terrain.getFilePath())));
            }
        }
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
        } else if (command.toLowerCase().startsWith(INVENTORY)) {
            // tell gameChar to print inventory
            text_output.append(mGameChar.getInventory() + '\n');
        } else if (command.toLowerCase().startsWith(QUIT)) {
            // quit program
            text_output.append(FAREWELL + '\n');
        } else {
            text_output.append(INVALID_COMMAND + command + '\n');
        }
        // print location and terrain
        text_output.append("You are at location " +
                mMap.currentRow +
                "," +
                mMap.currentColumn +
                " in terrain " +
                mMap.getTerrainAt(mMap.currentRow, mMap.currentColumn) +
                '\n'
        );
    }
}
