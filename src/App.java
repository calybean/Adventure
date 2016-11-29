import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by jcannon on 11/29/16.
 */
public class App {
    private JPanel container_panel;
    private JPanel main_panel;
    private JPanel map_panel;
    private JPanel button_panel;
    private JPanel text_panel;
    private JButton button_save;
    private JButton button_quit;
    private JButton button_open;
    private JTextArea text_area;

    public static void main(String[] args) {
        // todo: use args to get map file

        JFrame frame = new JFrame("Adventure");
        frame.setContentPane(new App().main_panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800,600));
        frame.pack();
        frame.setVisible(true);
    }

    App() {
        final int margin = 4;
        main_panel.setBorder(new EmptyBorder(margin, margin, margin, margin));
        button_panel.setBorder(new EmptyBorder(margin, 0, 0, 0));
        text_panel.setBorder(new EmptyBorder(margin, 0, margin, 0));

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
    }
}
