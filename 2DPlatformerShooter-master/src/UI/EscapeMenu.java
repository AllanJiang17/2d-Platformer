package UI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

public class EscapeMenu extends JPanel{
    private JButton resume;
    private JButton goBack;
    private JButton tutorial;
    private static final int XPOSITION = 210;
    private static final int YPOSITION = 150;
    private static final int WIDTH = 200;
    private static final int HEIGHT = 35;
    private final GameWindow gw;

    public EscapeMenu(GameWindow gw) {
        this.gw = gw;
        buttonInitiation();
        buttonLayout();
        String basepath = new File("").getAbsolutePath();
        ImageIcon backgroundImage = new ImageIcon(basepath + "\\src\\Resources\\background.jpg");
        JLabel background = new JLabel(backgroundImage);
        background.setSize(900,500);
        this.add(background);
        listenersInitiation();
    }

    private void buttonInitiation() {
        resume = new JButton("Resume");
        goBack = new JButton("Return To Menu");
        tutorial = new JButton("Tutorial");
    }

    //MODIFIES: this
    //EFFECTS: modifies the buttons positions
    private void buttonLayout() {
        setLayout(null);
        this.setBounds(0, 0, 700, 500);
        resume.setBounds(XPOSITION,YPOSITION, WIDTH,HEIGHT);
        add(resume);
        goBack.setBounds(XPOSITION,YPOSITION + 50, WIDTH,HEIGHT);
        add(goBack);
        tutorial.setBounds(XPOSITION,YPOSITION + 100, WIDTH,HEIGHT);
        add(tutorial);
    }

    //MODIFIES: this
    //EFFECTS: creates a action selector class and adds listeners to the buttons
    private void listenersInitiation() {
        EscapeMenu.ActionSelector listener = new EscapeMenu.ActionSelector();
        resume.addActionListener(listener);
        goBack.addActionListener(listener);
        tutorial.addActionListener(listener);
    }

    //represents an ActionListener class
    private class ActionSelector implements ActionListener {

        //REQUIRES: ActionEvent
        //MODIFIES: DoggyDayCareGui
        //EFFECT: goes to corresponding panel with selected buttons or save and load when save and load buttons are
        //pressed
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == resume) {
                gw.resumeGame();
            } else if (e.getSource() == goBack) {
                gw.mainMenu();
            } else if (e.getSource() == tutorial) {
                openFrame();
            }
        }
    }

    protected void openFrame() {
        InternalFrame frame = new InternalFrame();
        frame.setBounds(500, 200, 500, 300);
        frame.setVisible(true);
        JOptionPane.showMessageDialog(frame, "WASD to walk, J to fire. Multiplayer: WASD and arrows to walk");
        JLabel invalid = new JLabel("Tutorial");
        invalid.setBounds(180, 500, 500, 300);
        frame.add(invalid);
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
    }
}