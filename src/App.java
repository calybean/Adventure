import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jcannon on 11/29/16.
 */
public class App extends JPanel {
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

    public static void main(String[] args) {
        // todo: use args to get map file

        JFrame frame = new JFrame("Adventure");
        frame.setContentPane(new App().main_panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400,500));
        frame.pack();
        frame.setVisible(true);
    }

    App() {
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
//                System.out.print("text input: " + text_input.getText());
                text_output.append("> " + text_input.getText() + '\n');
                text_input.setText("");
            }
        });

        JLabel imageArray[] = new JLabel[25];

        for (int i =  0; i < 25; i++) {
            imageArray[i] = new JLabel(new ImageIcon("mapPics/forest.png"));
            map_panel.add(imageArray[i]);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
